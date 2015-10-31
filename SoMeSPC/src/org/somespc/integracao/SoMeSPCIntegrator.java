package org.somespc.integracao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
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
import org.somespc.integracao.model.FerramentaColetora;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.medicao.ValorNumerico;
import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.objetivos.ObjetivoEstrategico;
import org.somespc.model.organizacao_de_software.Objetivo;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;
import org.somespc.model.plano_de_medicao.TreeItemPlanoMedicao;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;
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
	public static PapelRecursoHumano criarPapelRecursoHumano(PapelRecursoHumano papel) throws Exception {
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
	public static synchronized void criarMedicao(PlanoDeMedicaoDoProjeto plano, Timestamp data,
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


	public static synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoTaigaSoMeSPC(List<ItemPlanoDeMedicaoDTO> itemPlanoDeMedicaoDTO, 
			Periodicidade periodicidadeMedicao, TaigaLoginDTO taigaLogin, Projeto projeto) throws Exception {
    	
    	   	
    PlanoDeMedicaoDoProjeto plano = new PlanoDeMedicaoDoProjeto();
	EntityManager manager = XPersistence.createManager();
	
	org.somespc.model.organizacao_de_software.Projeto proj = new org.somespc.model.organizacao_de_software.Projeto();
	//Verifica se o projeto está criado.
	try
	{
	    String queryProjeto = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
	    TypedQuery<org.somespc.model.organizacao_de_software.Projeto> typedQueryProjeto = manager.createQuery(queryProjeto, org.somespc.model.organizacao_de_software.Projeto.class);
	    proj = typedQueryProjeto.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    TaigaIntegrator taigaIntegrator = new TaigaIntegrator(taigaLogin.getUrl(), taigaLogin.getUsuario(), taigaLogin.getSenha());	    
	    proj = taigaIntegrator.criarProjetoSoMeSPC(projeto);
	}

	Calendar cal = Calendar.getInstance();
	plano.setData(cal.getTime());

	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	plano.setNome("Plano de Medição do Projeto " + projeto.getNome() + " (Wizard) - " + dataHora);
	plano.setVersao("1");
	plano.setDescricao("Plano de Medição do Projeto criado via wizard em " + dataHora);
	plano.setProjeto(proj);
	
	//Criação os objetos necessários para criação do Plano de Medição do Projeto - SoMeSPC
	for(ItemPlanoDeMedicaoDTO item: itemPlanoDeMedicaoDTO){
			
		//Criando a ferramenta coletora do item
		FerramentaColetora ferramenta = new FerramentaColetora();
		ferramenta.setNome(item.getNomeFerramentaColetora());
	
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(ferramenta);
		    manager.getTransaction().commit();

		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    String query = "SELECT p FROM FerramentaColetora p WHERE p.nome='" + item.getNomeFerramentaColetora() + "'" ;
		    TypedQuery<FerramentaColetora> typedQuery = manager.createQuery(query, FerramentaColetora.class);
		    ferramenta = typedQuery.getSingleResult();
		}
		
		//Criando Objetivo Estrategico do Plano
		ObjetivoEstrategico objEstrategico = new ObjetivoEstrategico();
		objEstrategico.setNome(item.getNomeObjetivoEstrategico());
	
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(objEstrategico);
		    manager.getTransaction().commit();

		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    String query = "SELECT p FROM ObjetivoEstrategico p WHERE p.nome='" + item.getNomeObjetivoEstrategico() + "'" ;
		    TypedQuery<ObjetivoEstrategico> typedQuery = manager.createQuery(query, ObjetivoEstrategico.class);
		    objEstrategico = typedQuery.getSingleResult();
		}
		
		//TreeItem do Objetivo Estrategico
		TreeItemPlanoMedicao objEstrategicoTree = new TreeItemPlanoMedicao();
		objEstrategicoTree.setNome(objEstrategico.getNome());
		objEstrategicoTree.setItem(objEstrategico);
		objEstrategicoTree.setPlanoDeMedicaoContainer(plano);
		objEstrategicoTree.setFerramentaColetora(ferramenta);
		
		//Criando o Objetivo de Medição do Plano
		ObjetivoDeMedicao objMedicao = new ObjetivoDeMedicao();
		objMedicao.setNome(item.getNomeObjetivoDeMedicao());
		objMedicao.setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>(Arrays.asList(objEstrategico)));
			
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(objMedicao);
		    manager.getTransaction().commit();

		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    String query2 = "SELECT p FROM ObjetivoDeMedicao p WHERE p.nome='" + item.getNomeObjetivoDeMedicao() + "'";
		    TypedQuery<ObjetivoDeMedicao> typedQuery2 = manager.createQuery(query2, ObjetivoDeMedicao.class);
		    objMedicao = typedQuery2.getSingleResult();

		}
		
		//TreeItem do Objetivo de Medição 
		TreeItemPlanoMedicao objMedicaoTree = new TreeItemPlanoMedicao();
		objMedicaoTree.setNome(objMedicao.getNome());
		objMedicaoTree.setItem(objMedicao);
		objMedicaoTree.setPlanoDeMedicaoContainer(plano);		
		objMedicaoTree.setFerramentaColetora(ferramenta);
				
		//Criando a Necessidade de Informação do Plano
		NecessidadeDeInformacao necessidade = new NecessidadeDeInformacao();
		necessidade.setNome(item.getNomeNecessidadeDeInformacao());
		
		List<Objetivo> objetivos = new ArrayList<Objetivo>();
		objetivos.add(objMedicao);

		necessidade.setIndicadoPelosObjetivos(objetivos);
		
		try
		    {
			if (!manager.isOpen())
			    manager = XPersistence.createManager();
	
			manager.getTransaction().begin();
			manager.persist(necessidade);
			manager.getTransaction().commit();
	
		    }
		    catch (Exception ex)
		    {
			if (manager.getTransaction().isActive())
			    manager.getTransaction().rollback();
	
			manager.close();
			manager = XPersistence.createManager();
	
			if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
				(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
			{
			    String query = String.format("SELECT p FROM NecessidadeDeInformacao p WHERE p.nome='%s'", necessidade.getNome());
			    TypedQuery<NecessidadeDeInformacao> typedQuery = manager.createQuery(query, NecessidadeDeInformacao.class);
			    necessidade = typedQuery.getSingleResult();
			}
			else
			{
			    throw ex;
			}
		}	    
	    
	    String queryMedida = String.format("SELECT p FROM Medida p WHERE p.nome='%s'", item.getMedida());
	    TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);
	    Medida med = typedQueryMedida.getSingleResult();

	    String queryDefMedida = "SELECT p FROM DefinicaoOperacionalDeMedida p WHERE p.nome='Definição operacional padrão Taiga-SoMeSPC para " + item.getMedida() + "'";
	    TypedQuery<DefinicaoOperacionalDeMedida> typedQueryDefMedida = manager.createQuery(queryDefMedida, DefinicaoOperacionalDeMedida.class);
	    DefinicaoOperacionalDeMedida defMed = typedQueryDefMedida.getSingleResult();
	    defMed.setPeriodicidadeDeMedicao(periodicidadeMedicao);

	    //Obtem as Medidas como MedidaPlanoDeMedicao
	    MedidaPlanoDeMedicao medidaPlano = new MedidaPlanoDeMedicao();
	    medidaPlano.setMedida(med);
	    medidaPlano.setDefinicaoOperacionalDeMedida(defMed);

	    //Persiste a Medida Plano.	
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(defMed);
		manager.persist(medidaPlano);
		manager.getTransaction().commit();

	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		manager.close();
		manager = XPersistence.createManager();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    String query = String.format("SELECT mp FROM MedidaPlanoDeMedicao mp WHERE mp.medida.nome='%s' AND mp.definicaoOperacionalDeMedida.nome='%s'",
			    medidaPlano.getMedida().getNome(), medidaPlano.getDefinicaoOperacionalDeMedida().getNome());
		    TypedQuery<MedidaPlanoDeMedicao> typedQuery = manager.createQuery(query, MedidaPlanoDeMedicao.class);
		    medidaPlano = typedQuery.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

		//Persiste o Objetivo Estratégico e Objetivo de Medição (tree e tree base também).
		manager.getTransaction().begin();
		manager.persist(objEstrategicoTree);
		Integer idObjEstrategicoTree = objEstrategicoTree.getId();
		objMedicaoTree.setPath("/" + idObjEstrategicoTree);
		manager.persist(objMedicaoTree);
		Integer idObjMedicaoTree = objMedicaoTree.getId();
		
		//ItemTree de Necessidade de Informação
		TreeItemPlanoMedicao necessidadeTree = new TreeItemPlanoMedicao();
	    necessidadeTree.setNome(necessidade.getNome());
	    necessidadeTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree);
	    necessidadeTree.setItem(necessidade);
	    necessidadeTree.setPlanoDeMedicaoContainer(plano);
	    necessidadeTree.setFerramentaColetora(ferramenta);
		
	    //Persiste NI
	    manager.persist(necessidadeTree);
	    Integer idNecessidadeTree = necessidadeTree.getId();
	    
	    //ItemTree de Medida
	    TreeItemPlanoMedicao medidaTree = new TreeItemPlanoMedicao();
	    medidaTree.setNome(med.getNome());
	    medidaTree.setPath("/" + objEstrategicoTree.getId() + "/" + objMedicaoTree.getId() + "/" + idNecessidadeTree);
	    medidaTree.setItem(med);
	    medidaTree.setPlanoDeMedicaoContainer(plano);
	    medidaTree.setFerramentaColetora(ferramenta);
	    
	    //Persiste Medida
	    manager.persist(medidaTree);
	    manager.getTransaction().commit();    
	    
	    plano.getPlanoTree().add(medidaTree);
	    plano.getPlanoTree().add(necessidadeTree);
	    plano.getPlanoTree().add(objMedicaoTree);
	    plano.getPlanoTree().add(objEstrategicoTree);

	}

	//Finalmente... Persiste o plano.
	try
	{
	    if (!manager.isOpen())
		manager = XPersistence.createManager();

	    manager.getTransaction().begin();

	    manager.persist(plano);

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    throw ex;

	}
	finally
	{
	    manager.close();
	}

	//Após criar o plano, agenda as medições.
	//SoMeSPCIntegrator.agendarMedicoesPlanoMedicaoProjeto(plano, taigaLogin, projeto, sonar);

	return plano;
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
