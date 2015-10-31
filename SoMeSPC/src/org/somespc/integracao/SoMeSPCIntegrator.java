package org.somespc.integracao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.openxava.jpa.XPersistence;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.somespc.integracao.agendador.TaigaMedicaoJob;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.medicao.ValorNumerico;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;
import org.somespc.webservices.rest.dto.SonarLoginDTO;
import org.somespc.webservices.rest.dto.TaigaLoginDTO;

public class SoMeSPCIntegrator {

	/**
	 * Obtem as periodicidades cadastradas.
	 * 
	 * @return
	 */
	public static List<Periodicidade> obterPeriodicidades() {
		EntityManager manager = XPersistence.createManager();

		TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
		List<Periodicidade> result = query.getResultList();

		manager.close();
		return result;
	}

	/**
	 * Cadastra um RecursoHumano na SoMeSPC. Se já existir, retorna o
	 * RecursoHumano existente.
	 * 
	 * @param membro
	 *            - Membro a ser cadastrado.
	 * @return RecursoHumano criado/existente.
	 * @throws Exception
	 */
	public static RecursoHumano criarRecursoHumano(RecursoHumano recursoHumano) throws Exception {

		EntityManager manager = XPersistence.createManager();

		try {
			String query = String.format("SELECT r FROM RecursoHumano r WHERE r.nome='%s'", recursoHumano.getNome());
			TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);
			recursoHumano = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			if (!manager.isOpen())
				manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(recursoHumano);
			manager.getTransaction().commit();

		} finally {
			manager.close();
		}

		return recursoHumano;
	}

	/**
	 * Cadastra um Papel de Recurso Humano na SoMeSPC. Se já existir, retorna o
	 * Papel existente.
	 * 
	 * @param membro
	 *            - Membro a ser cadastrado.
	 * @return Papel criado/existente.
	 * @throws Exception
	 */
	public static PapelRecursoHumano criarPapelRecursoHumanoSoMeSPC(PapelRecursoHumano papel) throws Exception {
		EntityManager manager = XPersistence.createManager();
		try {
			String query = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='%s'", papel.getNome());
			TypedQuery<PapelRecursoHumano> typedQuery = manager.createQuery(query, PapelRecursoHumano.class);
			papel = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			if (!manager.isOpen())
				manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(papel);
			manager.getTransaction().commit();

		} finally {
			manager.close();
		}

		return papel;
	}

	/**
	 * Cria uma medição.
	 * 
	 * @throws Exception
	 */
	public static synchronized void criarMedicaoSoMeSPC(PlanoDeMedicaoDoProjeto plano, Timestamp data,
			String nomeMedida, String entidadeMedida, String valorMedido) throws Exception {
		EntityManager manager = XPersistence.createManager();
		Medicao medicao = new Medicao();

		medicao.setData(data);
		medicao.setPlanoDeMedicao(plano);

		for (MedidaPlanoDeMedicao med : plano.getMedidaPlanoDeMedicao()) {
			if (med.getMedida().getNome().equalsIgnoreCase(nomeMedida)) {
				medicao.setMedidaPlanoDeMedicao(med);
			}
		}

		String queryEntidade = String.format("SELECT p FROM EntidadeMensuravel p WHERE p.nome='%s'", entidadeMedida);
		TypedQuery<EntidadeMensuravel> typedQueryEntidade = manager.createQuery(queryEntidade,
				EntidadeMensuravel.class);
		medicao.setEntidadeMensuravel(typedQueryEntidade.getSingleResult());

		ValorNumerico valor = new ValorNumerico();
		valor.setValorNumerico(Float.parseFloat(valorMedido));
		valor.setValorMedido(valorMedido);
		medicao.setValorMedido(valor);

		if (!manager.isOpen())
			manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medicao);
		manager.getTransaction().commit();

	}

	/**
	 * Agenda as medições de acordo com as medidas e definições operacionais de
	 * medida do plano.
	 * 
	 * @param plano
	 * @throws Exception
	 */
	public static synchronized void agendarMedicoesPlanoMedicaoProjeto(PlanoDeMedicaoDoProjeto plano,
			TaigaLoginDTO taigaDto, Projeto projetoTaiga, SonarLoginDTO sonarDto, Recurso projetoSonar)
					throws Exception {
		/**
		 * Inicia os agendamentos.
		 */
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();

		if (!sched.isStarted())
			sched.start();

		// Cria um agendamento de medição para cada medida e entidade medida.
		for (MedidaPlanoDeMedicao medida : plano.getMedidaPlanoDeMedicao()) {
			JobDetail job = null;
			Trigger trigger = null;
			String nomeJob = "Job do plano " + plano.getNome();

			// Converte a periodicidade em horas.
			String period = medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome();
			int horas = 0;

			if (period.equalsIgnoreCase("Por Hora")) {
				horas = 1;
			} else if (period.equalsIgnoreCase("Diária")) {
				horas = 24;
			} else if (period.equalsIgnoreCase("Semanal")) {
				horas = 24 * 7;
			} else if (period.equalsIgnoreCase("Quinzenal")) {
				horas = 24 * 15;
			} else if (period.equalsIgnoreCase("Mensal")) {
				horas = 24 * 30; // mes de 30 dias apenas...
			} else if (period.equalsIgnoreCase("Trimestral")) {
				horas = 24 * 30 * 3; // mes de 30 dias apenas...
			} else if (period.equalsIgnoreCase("Semestral")) {
				horas = 24 * 30 * 6; // mes de 30 dias apenas...
			} else if (period.equalsIgnoreCase("Anual")) {
				horas = 24 * 30 * 12; // mes de 30 dias apenas...
			} else {
				String mensagem = String.format("Periodicidade %s inexistente no Taiga.", period);
				System.out.println(mensagem);
				throw new Exception(mensagem);
			}

			JobDataMap map = new JobDataMap();

			map.put("urlTaiga", taigaDto.getUrl());
			map.put("usuarioTaiga", taigaDto.getUsuario());
			map.put("senhaTaiga", taigaDto.getSenha());

			map.put("apelidoProjeto", projetoTaiga.getApelido());
			map.put("nomePlano", plano.getNome());
			map.put("nomeMedida", medida.getMedida().getNome());

			medida.getDefinicaoOperacionalDeMedida().getResponsavelPelaMedicao();

			// Espera um segundo para cadastrar cada job, para evitar erros.
			Thread.sleep(1000);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

			String nomeGrupo = projetoTaiga.getNome();
			String nomeTrigger = String.format("Medição %s da medida %s (%s) - criado em %s",
					medida.getDefinicaoOperacionalDeMedida().getPeriodicidadeDeMedicao().getNome(),
					medida.getMedida().getNome(), medida.getMedida().getMnemonico(), dataHora);

			map.put("entidadeMedida", plano.getProjeto().getNome());
			map.put("momento", plano.getProjeto().getNome());

			boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

			// Cria um job para cada medida de um projeto.
			if (!existeJob) {
				job = JobBuilder.newJob(TaigaMedicaoJob.class).withIdentity(nomeJob, nomeGrupo).build();

				trigger = TriggerBuilder.newTrigger().forJob(job).withIdentity(nomeTrigger, nomeGrupo).usingJobData(map)
						.startNow()
						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(horas).repeatForever())
						.build();

				sched.scheduleJob(job, trigger);
			} else {
				job = sched.getJobDetail(new JobKey(nomeJob, nomeGrupo));

				trigger = TriggerBuilder.newTrigger().forJob(job).withIdentity(nomeTrigger, nomeGrupo).usingJobData(map)
						.startNow()
						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(horas).repeatForever())
						.build();

				sched.scheduleJob(trigger);
			}
		}
	}

}
