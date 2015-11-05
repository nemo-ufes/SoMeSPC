package org.somespc.integracao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.openxava.jpa.XPersistence;
import org.somespc.integracao.sonarqube.SonarQubeIntegrator;
import org.somespc.integracao.sonarqube.model.MedidasSonarQube;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.MedidasTaiga;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.medicao.ValorNumerico;
import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.objetivos.ObjetivoEstrategico;
import org.somespc.model.organizacao_de_software.Objetivo;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.ItemPlanoMedicao;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;
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

	public static EntidadeMensuravel criarEntidadeMensuravel(String nomeEntidade, String descricao,
			String nomeTipoEntidade) throws Exception {

		EntityManager manager = XPersistence.createManager();
		EntidadeMensuravel entidade = null;

		try {
			String query = String.format("SELECT r FROM EntidadeMensuravel r WHERE r.nome='%s'", nomeEntidade);
			TypedQuery<EntidadeMensuravel> typedQuery = manager.createQuery(query, EntidadeMensuravel.class);
			entidade = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			if (!manager.isOpen())
				manager = XPersistence.createManager();

			entidade = new EntidadeMensuravel();

			String query = String.format("SELECT r FROM TipoDeEntidadeMensuravel r WHERE r.nome='%s'",
					nomeTipoEntidade);
			TypedQuery<TipoDeEntidadeMensuravel> typedQuery = manager.createQuery(query,
					TipoDeEntidadeMensuravel.class);
			TipoDeEntidadeMensuravel tipo = typedQuery.getSingleResult();

			entidade.setNome(nomeEntidade);
			entidade.setDescricao(descricao);
			entidade.setTipoDeEntidadeMensuravel(tipo);

			manager.getTransaction().begin();
			manager.persist(entidade);
			manager.getTransaction().commit();

		} finally {
			manager.close();
		}

		return entidade;
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
			
		    String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Recurso Humano'");
			TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
			TipoDeEntidadeMensuravel tipoRh = tipoEntidadeTypedQuery.getSingleResult();
			recursoHumano.setTipoDeEntidadeMensuravel(tipoRh);
			
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
	public static synchronized void criarMedicao(PlanoDeMedicaoDoProjeto plano, Timestamp data, String nomeMedida,
			String entidadeMedida, String valorMedido) throws Exception {
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

	public static synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoTaigaSoMeSPC(
			List<ItemPlanoDeMedicaoDTO> items, Periodicidade periodicidade, TaigaLoginDTO taigaLogin, Projeto projeto)
					throws Exception {

		EntityManager manager = XPersistence.createManager();
		TaigaIntegrator taigaIntegrator = new TaigaIntegrator(taigaLogin.getUrl(), taigaLogin.getUsuario(),
				taigaLogin.getSenha());

		org.somespc.model.organizacao_de_software.Projeto proj = new org.somespc.model.organizacao_de_software.Projeto();

		// Verifica se o projeto está criado.
		try {
			String queryProjeto = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
			TypedQuery<org.somespc.model.organizacao_de_software.Projeto> typedQueryProjeto = manager
					.createQuery(queryProjeto, org.somespc.model.organizacao_de_software.Projeto.class);
			proj = typedQueryProjeto.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			manager.close();
			manager = XPersistence.createManager();

			proj = taigaIntegrator.criarProjetoSoMeSPC(projeto);
		}

		// Cria o plano de medição.
		PlanoDeMedicaoDoProjeto plano = SoMeSPCIntegrator.criarPlanoMedicaoProjeto(proj, items, periodicidade,
				taigaIntegrator, null);

		// Agenda os jobs do Taiga.
		for (ItemPlanoMedicao item : plano.getPlanoTree()) {

			// Se for medida, obtem o item do plano para descobrir de qual
			// ferramenta é.
			if (item.getNome().startsWith("ME - ")) {

				// Aguarda 1 antes de agendar cada job para evitar problemas de
				// concorrência.
				Thread.sleep(1000);

				ItemPlanoMedicao medida = manager.find(ItemPlanoMedicao.class, item.getId());
				taigaIntegrator.agendarTaigaMedicaoJob(plano, projeto.getApelido(), medida, periodicidade, taigaLogin);

			}
		}

		return plano;

	}

	public static synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoSonarQubeSoMeSPC(
			List<ItemPlanoDeMedicaoDTO> items, Periodicidade periodicidade, SonarLoginDTO sonarLogin, Recurso recurso)
					throws Exception {

		EntityManager manager = XPersistence.createManager();
		SonarQubeIntegrator sonarIntegrator = new SonarQubeIntegrator(sonarLogin.getUrl());

		org.somespc.model.organizacao_de_software.Projeto proj = new org.somespc.model.organizacao_de_software.Projeto();

		// Verifica se o projeto está criado.
		try {
			String queryProjeto = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", recurso.getNome());
			TypedQuery<org.somespc.model.organizacao_de_software.Projeto> typedQueryProjeto = manager
					.createQuery(queryProjeto, org.somespc.model.organizacao_de_software.Projeto.class);
			proj = typedQueryProjeto.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			manager.close();
			manager = XPersistence.createManager();

			proj = sonarIntegrator.criarProjetoSoMeSPC(recurso);
		}

		// Cria o plano de medição.
		PlanoDeMedicaoDoProjeto plano = SoMeSPCIntegrator.criarPlanoMedicaoProjeto(proj, items, periodicidade, null,
				sonarIntegrator);

		// Agenda os jobs do Taiga.
		for (ItemPlanoMedicao item : plano.getPlanoTree()) {

			// Se for medida, obtem o item do plano para descobrir de qual
			// ferramenta é.
			if (item.getNome().startsWith("ME - ")) {

				// Aguarda 1 antes de agendar cada job para evitar problemas de
				// concorrência.
				Thread.sleep(1000);

				ItemPlanoMedicao medida = manager.find(ItemPlanoMedicao.class, item.getId());

				sonarIntegrator.agendarSonarQubeMedicaoJob(plano, recurso.getChave(), medida, periodicidade,
						sonarLogin);
			}
		}

		return plano;
	}

	public static synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoTaigaSonarQubeSoMeSPC(
			List<ItemPlanoDeMedicaoDTO> items, Periodicidade periodicidade, TaigaLoginDTO taigaLogin, Projeto projeto,
			SonarLoginDTO sonarLogin, List<Recurso> recursos) throws Exception {

		EntityManager manager = XPersistence.createManager();
		TaigaIntegrator taigaIntegrator = new TaigaIntegrator(taigaLogin.getUrl(), taigaLogin.getUsuario(),
				taigaLogin.getSenha());
		SonarQubeIntegrator sonarIntegrator = new SonarQubeIntegrator(taigaLogin.getUrl());

		org.somespc.model.organizacao_de_software.Projeto proj = new org.somespc.model.organizacao_de_software.Projeto();

		// Verifica se o projeto está criado.
		try {
			String queryProjeto = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
			TypedQuery<org.somespc.model.organizacao_de_software.Projeto> typedQueryProjeto = manager
					.createQuery(queryProjeto, org.somespc.model.organizacao_de_software.Projeto.class);
			proj = typedQueryProjeto.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			manager.close();
			manager = XPersistence.createManager();

			proj = taigaIntegrator.criarProjetoSoMeSPC(projeto);
		}

		// Cria o plano de medição.
		PlanoDeMedicaoDoProjeto plano = SoMeSPCIntegrator.criarPlanoMedicaoProjeto(proj, items, periodicidade,
				taigaIntegrator, sonarIntegrator);

		// Agenda os jobs do Taiga.
		for (ItemPlanoMedicao item : plano.getPlanoTree()) {

			// Se for medida, obtem o item do plano para descobrir de qual
			// ferramenta é.
			if (item.getNome().startsWith("ME - ")) {

				String ferramentaColetora = "";
				for (ItemPlanoDeMedicaoDTO itemDto : items) {
					if (item.getNome().equalsIgnoreCase("ME - " + itemDto.getMedida())) {
						ferramentaColetora = itemDto.getNomeFerramentaColetora();
					}
				}

				// Aguarda 1 antes de agendar cada job para evitar problemas de
				// concorrência.
				Thread.sleep(1000);

				ItemPlanoMedicao medida = manager.find(ItemPlanoMedicao.class, item.getId());

				if (ferramentaColetora.equalsIgnoreCase("Taiga")) {
					taigaIntegrator.agendarTaigaMedicaoJob(plano, projeto.getApelido(), medida, periodicidade,
							taigaLogin);
				} else if (ferramentaColetora.equalsIgnoreCase("SonarQube")) {
					
					for(Recurso recurso : recursos) {
						sonarIntegrator.agendarSonarQubeMedicaoJob(plano, recurso.getChave(), medida, periodicidade,
								sonarLogin);
					}					
				} else {
					System.err.println("Não foi possível agendar os jobs de medição. Ferramenta inválida: " + ferramentaColetora);
				}
			}
		}

		return plano;
	}

	private static synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjeto(
			org.somespc.model.organizacao_de_software.Projeto projeto, List<ItemPlanoDeMedicaoDTO> items,
			Periodicidade periodicidade, TaigaIntegrator taigaIntegrator, SonarQubeIntegrator sonarIntegrator)
					throws Exception {

		EntityManager manager = XPersistence.createManager();

		PlanoDeMedicaoDoProjeto plano = new PlanoDeMedicaoDoProjeto();

		Calendar cal = Calendar.getInstance();
		plano.setData(cal.getTime());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

		plano.setNome("Plano de Medição do Projeto " + projeto.getNome() + " (Wizard) - " + dataHora);
		plano.setVersao("1");
		plano.setDescricao("Plano de Medição do Projeto criado via wizard em " + dataHora);
		plano.setProjeto(projeto);

		boolean primeiraExecucao = true;

		Map<Integer, String> idNomeitemMap = new HashMap<Integer, String>();

		for (ItemPlanoDeMedicaoDTO item : items) {

			if (!primeiraExecucao) {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				String query = "SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='" + plano.getNome() + "'";
				TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query,
						PlanoDeMedicaoDoProjeto.class);
				plano = typedQuery.getSingleResult();
			}

			// Criando Objetivo Estrategico do Plano
			ObjetivoEstrategico objEstrategico = new ObjetivoEstrategico();
			objEstrategico.setNome(item.getNomeObjetivoEstrategico());

			try {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				manager.getTransaction().begin();
				manager.persist(objEstrategico);
				manager.getTransaction().commit();

			} catch (Exception ex) {
				if (manager.getTransaction().isActive())
					manager.getTransaction().rollback();

				manager.close();
				manager = XPersistence.createManager();

				String query = "SELECT p FROM ObjetivoEstrategico p WHERE p.nome='" + item.getNomeObjetivoEstrategico()
						+ "'";
				TypedQuery<ObjetivoEstrategico> typedQuery = manager.createQuery(query, ObjetivoEstrategico.class);
				objEstrategico = typedQuery.getSingleResult();
			}

			// TreeItem do Objetivo Estrategico
			ItemPlanoMedicao objEstrategicoTree = new ItemPlanoMedicao();
			objEstrategicoTree.setNome(objEstrategico.getNome());
			objEstrategicoTree.setItem(objEstrategico);
			objEstrategicoTree.setPlanoDeMedicaoContainer(plano);

			// Criando o Objetivo de Medição do Plano
			ObjetivoDeMedicao objMedicao = new ObjetivoDeMedicao();
			objMedicao.setNome(item.getNomeObjetivoDeMedicao());
			objMedicao.setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>(Arrays.asList(objEstrategico)));

			try {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				manager.getTransaction().begin();
				manager.persist(objMedicao);
				manager.getTransaction().commit();

			} catch (Exception ex) {
				if (manager.getTransaction().isActive())
					manager.getTransaction().rollback();

				manager.close();
				manager = XPersistence.createManager();

				String query2 = "SELECT p FROM ObjetivoDeMedicao p WHERE p.nome='" + item.getNomeObjetivoDeMedicao()
						+ "'";
				TypedQuery<ObjetivoDeMedicao> typedQuery2 = manager.createQuery(query2, ObjetivoDeMedicao.class);
				objMedicao = typedQuery2.getSingleResult();

			}

			// TreeItem do Objetivo de Medição
			ItemPlanoMedicao objMedicaoTree = new ItemPlanoMedicao();
			objMedicaoTree.setNome(objMedicao.getNome());
			objMedicaoTree.setItem(objMedicao);
			objMedicaoTree.setPlanoDeMedicaoContainer(plano);

			// Criando a Necessidade de Informação do Plano
			NecessidadeDeInformacao necessidade = new NecessidadeDeInformacao();
			necessidade.setNome(item.getNomeNecessidadeDeInformacao());

			List<Objetivo> objetivos = new ArrayList<Objetivo>();
			objetivos.add(objMedicao);

			necessidade.setIndicadoPelosObjetivos(objetivos);

			try {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				manager.getTransaction().begin();
				manager.persist(necessidade);
				manager.getTransaction().commit();

			} catch (Exception ex) {
				if (manager.getTransaction().isActive())
					manager.getTransaction().rollback();

				manager.close();
				manager = XPersistence.createManager();

				if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException)
						|| (ex.getCause() != null && ex.getCause().getCause() != null
								&& ex.getCause().getCause() instanceof ConstraintViolationException)) {
					String query = String.format("SELECT p FROM NecessidadeDeInformacao p WHERE p.nome='%s'",
							necessidade.getNome());
					TypedQuery<NecessidadeDeInformacao> typedQuery = manager.createQuery(query,
							NecessidadeDeInformacao.class);
					necessidade = typedQuery.getSingleResult();
				} else {
					throw ex;
				}
			}

			// Persiste as medidas.
			if (item.getNomeFerramentaColetora().equalsIgnoreCase("Taiga")) {

				MedidasTaiga medidaTaiga = MedidasTaiga.get(item.getMedida());
				taigaIntegrator.criarMedidasSoMeSPC(Arrays.asList(medidaTaiga));

			} else if (item.getNomeFerramentaColetora().equalsIgnoreCase("SonarQube")) {

				MedidasSonarQube medidaSonar = MedidasSonarQube.get(item.getMedida());
				sonarIntegrator.criarMedidasSoMeSPC(Arrays.asList(medidaSonar));

			} else {
				throw new Exception("Ferramenta inválida: " + item.getNomeFerramentaColetora());
			}

			String queryMedida = String.format("SELECT p FROM Medida p WHERE p.nome='%s'", item.getMedida());
			TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);
			Medida med = typedQueryMedida.getSingleResult();

			String queryDefMedida = String.format(
					"SELECT p FROM DefinicaoOperacionalDeMedida p WHERE p.nome='Definição operacional da medida do %s - %s'",
					item.getNomeFerramentaColetora(), item.getMedida());

			TypedQuery<DefinicaoOperacionalDeMedida> typedQueryDefMedida = manager.createQuery(queryDefMedida,
					DefinicaoOperacionalDeMedida.class);
			DefinicaoOperacionalDeMedida defMed = typedQueryDefMedida.getSingleResult();

			// Obtem as Medidas como MedidaPlanoDeMedicao
			MedidaPlanoDeMedicao medidaPlano = new MedidaPlanoDeMedicao();
			medidaPlano.setMedida(med);
			medidaPlano.setDefinicaoOperacionalDeMedida(defMed);

			// Persiste a Medida Plano.
			try {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				manager.getTransaction().begin();
				manager.persist(defMed);
				manager.persist(medidaPlano);
				manager.getTransaction().commit();

			} catch (Exception ex) {
				if (manager.getTransaction().isActive())
					manager.getTransaction().rollback();

				manager.close();
				manager = XPersistence.createManager();
			}

			medidaPlano.setPlanoDeMedicao(plano);

			// Finalmente... Persiste o plano.
			try {
				if (!manager.isOpen())
					manager = XPersistence.createManager();

				manager.getTransaction().begin();

				// Persiste o Objetivo Estratégico e Objetivo de Medição (tree e
				// tree base também).
				// Se contem o objetivo estrategico, busca a chave do map.
				// Senão, persiste.
				if (idNomeitemMap.containsValue("OE - " + objEstrategicoTree.getNome())) {

					int chave = 0;
					for (Entry<Integer, String> entry : idNomeitemMap.entrySet()) {
						if (entry.getValue().equals("OE - " + objEstrategicoTree.getNome())) {
							chave = entry.getKey();
							break;
						}
					}

					objEstrategicoTree = manager.find(ItemPlanoMedicao.class, chave);

				} else {
					objEstrategicoTree.setDefinicaoOperacionalDeMedida(defMed);
					manager.persist(objEstrategicoTree);
				}

				int idObjEstrategicoTree = objEstrategicoTree.getId();

				// Se contem o objetivo de medicao, busca do banco. Senão,
				// persiste.
				if (idNomeitemMap.containsValue("OM - " + objMedicaoTree.getNome())) {

					int chave = 0;
					for (Entry<Integer, String> entry : idNomeitemMap.entrySet()) {
						if (entry.getValue().equals("OM - " + objMedicaoTree.getNome())) {
							chave = entry.getKey();
							break;
						}
					}

					objMedicaoTree = manager.find(ItemPlanoMedicao.class, chave);

				} else {
					objMedicaoTree.setDefinicaoOperacionalDeMedida(defMed);
					objMedicaoTree.setPath("/" + idObjEstrategicoTree);
					manager.persist(objMedicaoTree);
				}

				int idObjMedicaoTree = objMedicaoTree.getId();

				// ItemTree de Necessidade de Informação
				ItemPlanoMedicao necessidadeTree = new ItemPlanoMedicao();
				necessidadeTree.setNome(necessidade.getNome());
				necessidadeTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree);
				necessidadeTree.setItem(necessidade);
				necessidadeTree.setPlanoDeMedicaoContainer(plano);
				necessidadeTree.setDefinicaoOperacionalDeMedida(defMed);

				// Persiste NI
				manager.persist(necessidadeTree);
				Integer idNecessidadeTree = necessidadeTree.getId();

				// ItemTree de Medida
				ItemPlanoMedicao medidaTree = new ItemPlanoMedicao();
				medidaTree.setNome(med.getNome());
				medidaTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree + "/" + idNecessidadeTree);
				medidaTree.setItem(med);
				medidaTree.setPlanoDeMedicaoContainer(plano);
				medidaTree.setDefinicaoOperacionalDeMedida(defMed);

				// Persiste Medida
				manager.persist(medidaTree);

				// Persiste o Plano
				if (primeiraExecucao) {
					plano.setPlanoTree(new HashSet<ItemPlanoMedicao>());
					plano.setMedidaPlanoDeMedicao(new HashSet<MedidaPlanoDeMedicao>());
				}

				plano.getMedidaPlanoDeMedicao().add(medidaPlano);
				manager.persist(medidaPlano);

				plano.getPlanoTree().add(medidaTree);
				plano.getPlanoTree().add(necessidadeTree);

				if (!plano.getPlanoTree().contains(objMedicaoTree))
					plano.getPlanoTree().add(objMedicaoTree);

				if (!plano.getPlanoTree().contains(objEstrategicoTree))
					plano.getPlanoTree().add(objEstrategicoTree);

				if (primeiraExecucao)
					manager.persist(plano);
				else
					manager.merge(plano);

				manager.getTransaction().commit();

				if (!idNomeitemMap.containsKey(idObjMedicaoTree))
					idNomeitemMap.put(idObjMedicaoTree, objMedicaoTree.getNome());
				if (!idNomeitemMap.containsKey(idObjEstrategicoTree))
					idNomeitemMap.put(idObjEstrategicoTree, objEstrategicoTree.getNome());

				primeiraExecucao = false;

			} catch (Exception ex) {
				if (manager.getTransaction().isActive())
					manager.getTransaction().rollback();
				throw ex;
			} finally {
				manager.close();
			}
		}

		return plano;
	}
}
