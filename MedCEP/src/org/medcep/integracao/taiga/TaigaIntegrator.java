package org.medcep.integracao.taiga;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.client.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.medicao.*;
import org.medcep.model.organizacao.*;
import org.medcep.model.processo.*;
import org.medcep.util.json.*;
import org.openxava.jpa.*;

/**
 * Classe para integração do Taiga com a MedCEP.
 * 
 * @author Vinicius
 *
 */
public class TaigaIntegrator
{
    private final String urlTaiga;
    private final AuthInfo authInfo;
    private static Client client;

    /**
     * Construtor
     * 
     * @param urlTaiga
     *            - URL base do Taiga.
     * @param usuario
     *            - login do Taiga.
     * @param senha
     *            - senha do Taiga.
     */
    public TaigaIntegrator(String urlTaiga, String usuario, String senha)
    {
	if (urlTaiga.endsWith("/"))
	{
	    urlTaiga = urlTaiga.substring(0, urlTaiga.length() - 1);
	}

	this.urlTaiga = urlTaiga + "/api/v1/";
	this.authInfo = new AuthInfo(usuario, senha);
	client = ClientBuilder.newClient();
    }

    /**
     * Busca o token de autenticação do Taiga.
     */
    public String obterAuthToken()
    {
	WebTarget target = client.target(this.urlTaiga).path("auth");

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.post(Entity.entity(authInfo, MediaType.APPLICATION_JSON));

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException("Erro ao obter o token de autenticação do Taiga. HTTP Code: " + response.getStatus());
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	return json.get("auth_token").toString();
    }

    /**
     * Obtem os dados do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Projeto
     */
    public Projeto obterProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("projects/" + idProjeto);

	Projeto projeto = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(Projeto.class);

	return projeto;
    }

    /**
     * Obtem os dados do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Json do projeto Taiga
     */
    public String obterProjetoTaigaJson(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("projects/" + idProjeto);

	Response projetoResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONObject projetoJson = new JSONObject(projetoResponse.readEntity(String.class));

	return projetoJson.toString();
    }

    /**
     * Obtem os dados de Sprints do Taiga em JSON.
     * 
     * @return Json das Sprints Taiga
     */
    public String obterSprintsTaigaJson()
    {
	//Busca informações de milestones.
	WebTarget target = client.target(this.urlTaiga).path("milestones");

	Response milestoneResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONArray milestoneJson = new JSONArray(milestoneResponse.readEntity(String.class));

	return milestoneJson.toString();
    }

    /**
     * Obtem as Sprints de um determinado projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido do projeto.
     * @return List<Sprint> - Sprints do projeto
     */
    public List<Sprint> obterSprintsDoProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações das sprints.
	target = client.target(this.urlTaiga).path("milestones");

	List<Sprint> sprints = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Sprint>>() {
		});

	List<Sprint> sprintsProjeto = new ArrayList<Sprint>();

	for (Sprint sprint : sprints)
	{
	    if (sprint.getIdProjeto() == idProjeto)
		sprintsProjeto.add(sprint);
	}

	return sprintsProjeto;
    }

    /**
     * Obtem o Estado de uma Sprint de um projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido do projeto.
     * @param apelidoSprint
     *            - Apelido da Sprint.
     * @return Estado atual da Sprint.
     */
    public EstadoSprint obterEstadoSprintTaiga(String apelidoProjeto, String apelidoSprint)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase()).queryParam("milestone", apelidoSprint.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idSprint = json.getInt("milestone");

	//Busca informações da sprint.
	target = client.target(this.urlTaiga).path(String.format("milestones/%d/stats", idSprint));

	EstadoSprint estadoSprint = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(EstadoSprint.class);

	return estadoSprint;
    }

    /**
     * Obtem demais dados de andamento do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Json de dados do andamento do projeto Taiga
     */
    public String obterEstadoProjetoTaigaJson(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path(String.format("projects/%d/stats", idProjeto));

	Response projetoResponse = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	JSONObject projetoJson = new JSONObject(projetoResponse.readEntity(String.class));

	return projetoJson.toString();
    }

    /**
     * Obtem o estado de um projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser obtido.
     * @return EstadoProjeto - Estado do Projeto com medidas obtidas do projeto Taiga.
     */
    public EstadoProjeto obterEstadoProjetoTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path(String.format("projects/%d/stats", idProjeto));

	EstadoProjeto estado = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(EstadoProjeto.class);

	return estado;
    }

    /**
     * Obtem todos os projetos.
     * 
     * @return List<Projeto>
     */
    public List<Projeto> obterProjetosTaiga()
    {
	//Busca informações dos projetos.
	WebTarget target = client.target(this.urlTaiga).path("projects");

	List<Projeto> projetos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(new GenericType<List<Projeto>>() {
		});

	return projetos;
    }

    /**
     * Obtem os dados do Membro pelo ID.
     * 
     * @param idMembro
     *            - ID do membro a ser buscado.
     * @return Membro
     */
    public Membro obterMembroTaiga(int idMembro)
    {
	//Busca informações do membro.
	WebTarget target = client.target(this.urlTaiga).path("memberships/" + idMembro);

	Membro membro = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(Membro.class);

	return membro;
    }

    /**
     * Cadastra um RecursoHumano na MedCEP a partir de um Membro do Taiga.
     * Se já existir, retorna o RecursoHumano existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return RecursoHumano criado/existente.
     * @throws Exception
     */
    public RecursoHumano criarRecursoHumanoMedCEP(Membro membro) throws Exception
    {

	EntityManager manager = XPersistence.createManager();
	RecursoHumano recursoHumano = new RecursoHumano();
	recursoHumano.setNome(membro.getNome());

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(recursoHumano);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Recurso Humano %s já existe.", membro.getNome()));

		String query = String.format("SELECT r FROM RecursoHumano r WHERE r.nome='%s'", membro.getNome());
		TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);

		recursoHumano = typedQuery.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return recursoHumano;
    }

    /**
     * Cadastra um Papel de Recurso Humano na MedCEP a partir de um Membro do Taiga.
     * Se já existir, retorna o Papel existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return Papel criado/existente.
     * @throws Exception
     */
    public PapelRecursoHumano criarPapelRecursoHumanoMedCEP(Membro membro) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	PapelRecursoHumano papel = new PapelRecursoHumano();
	papel.setNome(membro.getPapel());

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(papel);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Papel de Recurso Humano %s já existe.", membro.getPapel()));

		String query = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='%s'", membro.getPapel());
		TypedQuery<PapelRecursoHumano> typedQuery = manager.createQuery(query, PapelRecursoHumano.class);

		papel = typedQuery.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return papel;
    }

    /**
     * Cria uma Equipe na MedCEP baseado nos Membros do projeto Taiga.
     * Se já existir, retorna a Equipe existente.
     * 
     * @param nomeEquipe
     *            - Nome da equipe a ser criada.
     * @param membrosDaEquipe
     *            - Membros da equipe.
     * @return Equipe criada/existente.
     * @throws Exception
     */
    public Equipe criarEquipeMedCEP(String nomeEquipe, List<Membro> membrosDaEquipe) throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	Equipe equipe = new Equipe();
	equipe.setNome(nomeEquipe);

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Alocação de Recurso Humano'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacaco = tipoEntidadeTypedQuery.getSingleResult();

	//Cria os recursos humanos, papeis e faz a alocação.
	List<AlocacaoEquipe> alocacoes = new ArrayList<AlocacaoEquipe>();
	List<RecursoHumano> recursosHumanos = new ArrayList<RecursoHumano>();

	for (Membro membro : membrosDaEquipe)
	{
	    AlocacaoEquipe alocacao = new AlocacaoEquipe();
	    alocacao.setEquipe(equipe);

	    //Insere o Recurso Humano na Equipe e na Alocacao. 
	    //Acredito que relacionamento direto entre Equipe <-> RecursoHumano seja para facilitar a visualização dos recursos da equipe.
	    RecursoHumano rec = this.criarRecursoHumanoMedCEP(membro);
	    recursosHumanos.add(rec);

	    alocacao.setRecursoHumano(rec);
	    alocacao.setPapelRecursoHumano(this.criarPapelRecursoHumanoMedCEP(membro));
	    alocacao.setTipoDeEntidadeMensuravel(tipoAlocacaco);
	    alocacoes.add(alocacao);
	}

	equipe.setRecursoHumano(recursosHumanos);
	equipe.setAlocacaoEquipe(alocacoes);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(equipe);

	    for (AlocacaoEquipe alocacaoEquipe : alocacoes)
	    {
		manager.persist(alocacaoEquipe);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Equipe %s já existe.", equipe.getNome()));

		String query = String.format("SELECT e FROM Equipe e WHERE e.nome='%s'", equipe.getNome());
		TypedQuery<Equipe> typedQuery = manager.createQuery(query, Equipe.class);

		equipe = typedQuery.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return equipe;
    }

    /**
     * Cria um Projeto na MedCEP baseado em um Projeto Taiga.
     * Se já existir, retorna o Projeto MedCEP existente.
     * 
     * @param projeto
     *            - projeto Taiga a ser criado.
     * @return Projeto MedCEP criado/existente.
     * @throws Exception
     */
    public org.medcep.model.organizacao.Projeto criarProjetoMedCEP(Projeto projeto) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	Equipe equipe = this.criarEquipeMedCEP("Equipe " + projeto.getNome(), projeto.getMembros());
	List<Equipe> equipes = new ArrayList<Equipe>();
	equipes.add(equipe);

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Projeto'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = tipoEntidadeTypedQuery.getSingleResult();

	org.medcep.model.organizacao.Projeto projetoMedCEP = new org.medcep.model.organizacao.Projeto();
	projetoMedCEP.setNome(projeto.getNome());
	projetoMedCEP.setEquipe(equipes);
	projetoMedCEP.setDataInicio(projeto.getDataCriacao());
	projetoMedCEP.setTipoDeEntidadeMensuravel(tipoProjeto);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(projetoMedCEP);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println(String.format("Projeto %s já existe.", projeto.getNome()));

		String query = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
		TypedQuery<org.medcep.model.organizacao.Projeto> typedQuery = manager.createQuery(query, org.medcep.model.organizacao.Projeto.class);

		projetoMedCEP = typedQuery.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return projetoMedCEP;
    }

    /**
     * Cria as (Sprints) do Taiga na MedCEP como Atividade Padrão, Atividades de Projeto e Ocorrência de Atividade.
     * 
     * @param projeto
     *            - Projeto Taiga para obter os marcos.
     * @return
     */
    public boolean criarSprintsMedCEP(Projeto projeto)
    {
	return false;
    }

    /**
     * Cria as Medidas do Taiga no banco de dados da MedCEP.
     * Não atribui valores, somente cria as definições das medidas.
     * 
     * @param medidasTaiga
     *            - Lista com as medidas Taiga a serem criadas na MedCEP.
     * @return List<Medida> - Medidas criadas na MedCEP.
     * @throws Exception
     */
    public List<Medida> criarMedidasMedCEP(List<MedidasTaiga> medidasTaiga) throws Exception
    {
	EntityManager manager = XPersistence.createManager();	
	List<Medida> medidasCadastradas = new ArrayList<Medida>();

	//Obtem o tipo de medida base.
	String query = "SELECT mb FROM TipoMedida mb WHERE mb.nome='Medida Base'";
	TypedQuery<TipoMedida> typedQuery = manager.createQuery(query, TipoMedida.class);
	TipoMedida medidaBase = typedQuery.getSingleResult();

	//Obtem a escala racional.
	String query2 = "SELECT e FROM Escala e WHERE e.nome='Números Racionais'";
	TypedQuery<Escala> typedQuery2 = manager.createQuery(query2, Escala.class);
	Escala escala = typedQuery2.getSingleResult();

	//Obtem a unidade de medida Pontos de Estória.
	String query3 = "SELECT u FROM UnidadeDeMedida u WHERE u.nome='Pontos de Estória'";
	TypedQuery<UnidadeDeMedida> typedQuery3 = manager.createQuery(query3, UnidadeDeMedida.class);
	UnidadeDeMedida unidadeMedida = typedQuery3.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Projeto.
	String query5 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery5 = manager.createQuery(query5, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = typedQuery5.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Recurso Humano.
	String query6 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Alocação de Recurso Humano'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery6 = manager.createQuery(query6, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacaoEquipe = typedQuery6.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Atividade Padrão.
	String query7 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Atividade Padrão'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery7 = manager.createQuery(query7, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAtividadePadrao = typedQuery7.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	manager.close();
	
	//Define a medida de acordo com a lista informada.
	for (MedidasTaiga medidaTaiga : medidasTaiga)
	{
	    Medida medida = new Medida();

	    switch (medidaTaiga)
	    {
		case PONTOS_ALOCADOS_PROJETO:
		    medida.setNome("Pontos Alocados");
		    medida.setMnemonico("TAIGA-PA");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case PONTOS_ALOCADOS_POR_PAPEL_PROJETO:
		    medida.setNome("Pontos Alocados por Papel");
		    medida.setMnemonico("TAIGA-PAP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoAlocacaoEquipe));
		    break;
		case PONTOS_DEFINIDOS_PROJETO:
		    medida.setNome("Pontos Definidos");
		    medida.setMnemonico("TAIGA-PD");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case PONTOS_DEFINIDOS_POR_PAPEL_PROJETO:
		    medida.setNome("Pontos Definidos por Papel");
		    medida.setMnemonico("TAIGA-PDP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoAlocacaoEquipe));
		    break;
		case PONTOS_FECHADOS_PROJETO:
		    medida.setNome("Pontos Fechados");
		    medida.setMnemonico("TAIGA-PF");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case PONTOS_FECHADOS_POR_PAPEL_PROJETO:
		    medida.setNome("Pontos Fechados por Papel");
		    medida.setMnemonico("TAIGA-PFP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoAlocacaoEquipe));
		    break;
		case TOTAL_SPRINTS_PROJETO:
		    medida.setNome("Total de Marcos (Sprints)");
		    medida.setMnemonico("TAIGA-TM");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case TOTAL_PONTOS_PROJETO:
		    medida.setNome("Total de Pontos");
		    medida.setMnemonico("TAIGA-TP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case VELOCIDADE_PROJETO:
		    medida.setNome("Velocidade");
		    medida.setMnemonico("TAIGA-VEL");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoProjeto));
		    break;
		case DOSES_IOCAINE_SPRINT:
		    medida.setNome("Doses de Iocaine");
		    medida.setMnemonico("TAIGA-IOC");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(Arrays.asList(tipoAtividadePadrao));
		    break;
		default:
		    throw new Exception("Medida inexistente no Taiga.");
	    }

	    medida.setDescricao("Medida obtida pela API Taiga conforme a documentação: http://taigaio.github.io/taiga-doc/dist/api.html");
	    medida.setTipoMedida(medidaBase);
	    medida.setEscala(escala);
	    medida.setUnidadeDeMedida(unidadeMedida);    
	    
	    try
	    {
		manager = XPersistence.createManager();
		manager.getTransaction().begin();
		manager.persist(medida);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		manager = XPersistence.createManager();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    System.out.println(String.format("A Medida %s já existe.", medida.getNome()));

		    String queryMedida = String.format("SELECT m FROM Medida m WHERE m.nome='%s'", medida.getNome());
		    TypedQuery<Medida> typedQueryMedida = manager.createQuery(queryMedida, Medida.class);

		    medida = typedQueryMedida.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }
	    finally
	    {
		manager.close();
	    }

	    medidasCadastradas.add(medida);
	}

	return medidasCadastradas;
    }

    /**
     * Cria os tipos de artefatos do Scrum.
     * 
     * @throws Exception
     */
    public List<TipoDeArtefato> criarTiposArtefatosScrumMedCEP() throws Exception
    {

	EntityManager manager = XPersistence.createManager();

	//Instancia os tipos de artefatos.
	TipoDeArtefato estoriaPB = new TipoDeArtefato();
	TipoDeArtefato estoriaSB = new TipoDeArtefato();
	TipoDeArtefato codigoFonte = new TipoDeArtefato();
	TipoDeArtefato documentacao = new TipoDeArtefato();
	TipoDeArtefato releaseSoftware = new TipoDeArtefato();

	//Obtem o tipo de Entidade Mensurável Tipo de Artefato.
	String queryTipoArtefato = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Tipo de Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQueryTipoArtefato = manager.createQuery(queryTipoArtefato, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoDeArtefato = typedQueryTipoArtefato.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	estoriaPB.setNome("Estória de Product Backlog");
	estoriaPB.setDescricao("Estória de Product Backlog do Scrum.");
	estoriaPB.setElementoMensuravel(Arrays.asList(tamanho, duracao));

	estoriaSB.setNome("Estória de Sprint Backlog");
	estoriaSB.setDescricao("Estória de Sprint Backlog do Scrum.");
	estoriaSB.setElementoMensuravel(Arrays.asList(tamanho, duracao));

	codigoFonte.setNome("Código Fonte");
	codigoFonte.setDescricao("Código fonte de um software.");
	codigoFonte.setElementoMensuravel(Arrays.asList(tamanho));

	documentacao.setNome("Documentação");
	documentacao.setDescricao("Documentação de software. Inclui modelos, diagramas, relatórios, etc.");
	documentacao.setElementoMensuravel(Arrays.asList(tamanho));

	releaseSoftware.setNome("Release de Software");
	releaseSoftware.setDescricao("Incremento de software pronto para implantação.");
	releaseSoftware.setElementoMensuravel(Arrays.asList(tamanho));

	List<TipoDeArtefato> tiposDeArtefatoParaPersistir = new ArrayList<TipoDeArtefato>();

	tiposDeArtefatoParaPersistir.add(estoriaPB);
	tiposDeArtefatoParaPersistir.add(estoriaSB);
	tiposDeArtefatoParaPersistir.add(codigoFonte);
	tiposDeArtefatoParaPersistir.add(documentacao);
	tiposDeArtefatoParaPersistir.add(releaseSoftware);

	List<TipoDeArtefato> tiposDeArtefato = new ArrayList<TipoDeArtefato>();

	//Persiste.
	for (TipoDeArtefato tipo : tiposDeArtefatoParaPersistir)
	{

	    tipo.setTipoDeEntidadeMensuravel(tipoDeArtefato);

	    try
	    {
		manager.getTransaction().begin();
		manager.persist(tipo);
		manager.getTransaction().commit();
		tiposDeArtefato.add(tipo);
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{

		    System.out.println(String.format("O Tipo de Artefato %s já existe.", tipo.getNome()));

		    String query = String.format("SELECT t FROM TipoDeArtefato t WHERE t.nome='%s'", tipo.getNome());
		    TypedQuery<TipoDeArtefato> typedQuery = manager.createQuery(query, TipoDeArtefato.class);

		    TipoDeArtefato tipoExistente = typedQuery.getSingleResult();
		    tiposDeArtefato.add(tipoExistente);
		}
		else
		{
		    throw ex;
		}
	    }
	}

	manager.close();

	return tiposDeArtefato;
    }

    /**
     * Cria as atividades Reunião de Planejamento da Sprint, Sprint e Reunião de Revisão da Sprint.
     * 
     * @return - List<AtividadeParao> com as atividades criadas.
     * @throws Exception
     */
    public void criarAtividadesPadraoScrumMedCEP() throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	//Instancia os tipos de artefatos.
	AtividadePadrao reuniaoPS = new AtividadePadrao();
	AtividadePadrao sprint = new AtividadePadrao();
	AtividadePadrao reuniaoRS = new AtividadePadrao();

	//Cria os artefatos Scrum.
	List<TipoDeArtefato> tiposCriados = this.criarTiposArtefatosScrumMedCEP();

	List<String> nomes = new ArrayList<String>();

	for (TipoDeArtefato tipo : tiposCriados)
	{
	    nomes.add(tipo.getNome());
	}

	String queryTiposArtefatoScrum = "SELECT e FROM TipoDeArtefato e WHERE e.nome IN :nomes ";
	TypedQuery<TipoDeArtefato> typedQueryTAS = manager.createQuery(queryTiposArtefatoScrum, TipoDeArtefato.class).setParameter("nomes", nomes);
	List<TipoDeArtefato> tiposArtefatos = typedQueryTAS.getResultList();

	//Obtem o tipo de Entidade Mensurável AtividadePadrao.
	String queryAtividadePadrao = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Atividade Padrão'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQueryAP = manager.createQuery(queryAtividadePadrao, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel atividadePadrao = typedQueryAP.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	//Preenche a Reunião de Planejamento da Sprint.
	reuniaoPS.setNome("Reunião de Planejamento da Sprint");
	reuniaoPS.setDescricao("Reunião de Planejamento da Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosReuniaoPS = new ArrayList<TipoDeArtefato>();
	loop_requeridos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Product Backlog"))
	    {
		artefatosRequeridosReuniaoPS.add(tipo);
		break loop_requeridos;
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosReuniaoPS = new ArrayList<TipoDeArtefato>();
	loop_produzidos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog"))
	    {
		artefatosProduzidosReuniaoPS.add(tipo);
		break loop_produzidos;
	    }
	}

	reuniaoPS.setRequerTipoDeArtefato(artefatosRequeridosReuniaoPS);
	reuniaoPS.setProduzTipoDeArtefato(artefatosProduzidosReuniaoPS);
	reuniaoPS.setElementoMensuravel(Arrays.asList(duracao));

	//Preenche a Sprint
	sprint.setNome("Sprint");
	sprint.setDescricao("Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosSprint = new ArrayList<TipoDeArtefato>();
	loop_requeridos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog"))
	    {
		artefatosRequeridosSprint.add(tipo);
		break loop_requeridos;
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosSprint = new ArrayList<TipoDeArtefato>();
	for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Código Fonte")
		    || tipo.getNome().equalsIgnoreCase("Documentação"))
	    {
		artefatosProduzidosSprint.add(tipo);
	    }
	}

	sprint.setRequerTipoDeArtefato(artefatosRequeridosSprint);
	sprint.setProduzTipoDeArtefato(artefatosProduzidosSprint);
	sprint.setDependeDe(Arrays.asList(reuniaoPS));
	sprint.setElementoMensuravel(Arrays.asList(tamanho, desempenho, duracao));

	//Preenche a Reunião de Revisão da Sprint
	reuniaoRS.setNome("Reunião de Revisão da Sprint");
	reuniaoRS.setDescricao("Reunião de Revisão da Sprint do Scrum.");

	List<TipoDeArtefato> artefatosRequeridosReuniaoRS = new ArrayList<TipoDeArtefato>();
	for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Estória de Sprint Backlog")
		    || tipo.getNome().equalsIgnoreCase("Código Fonte")
		    || tipo.getNome().equalsIgnoreCase("Documentação"))
	    {
		artefatosRequeridosReuniaoRS.add(tipo);
	    }
	}

	List<TipoDeArtefato> artefatosProduzidosReuniaoRS = new ArrayList<TipoDeArtefato>();
	loop_produzidos: for (TipoDeArtefato tipo : tiposArtefatos)
	{
	    if (tipo.getNome().equalsIgnoreCase("Release de Software"))
	    {
		artefatosProduzidosReuniaoRS.add(tipo);
		break loop_produzidos;
	    }
	}

	reuniaoRS.setRequerTipoDeArtefato(artefatosRequeridosReuniaoRS);
	reuniaoRS.setProduzTipoDeArtefato(artefatosProduzidosReuniaoRS);
	reuniaoRS.setDependeDe(Arrays.asList(sprint));
	reuniaoRS.setElementoMensuravel(Arrays.asList(duracao));

	List<AtividadePadrao> atividadesParaPersistir = new ArrayList<AtividadePadrao>();

	atividadesParaPersistir.add(reuniaoPS);
	atividadesParaPersistir.add(sprint);
	atividadesParaPersistir.add(reuniaoRS);

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();

	    for (AtividadePadrao atividade : atividadesParaPersistir)
	    {
		atividade.setTipoDeEntidadeMensuravel(atividadePadrao);
		manager.persist(atividade);
	    }

	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println("A Atividades Padrão do Scrum já existem.");
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

    }

    /**
     * Cria o processo Scrum na MedCEP.
     * 
     * @return ProcessoPadrao Scrum criado.
     * @throws Exception
     */
    public ProcessoPadrao criarProcessoPadraoScrumMedCEP() throws Exception
    {
	EntityManager manager = XPersistence.createManager();

	ProcessoPadrao scrum = new ProcessoPadrao();

	scrum.setNome("Scrum");
	scrum.setVersao("1.0");
	scrum.setDescricao("A framework to support teams in complex product development. "
		+ "Scrum consists of Scrum Teams and their associated roles, events, artifacts,"
		+ " and rules, as defined in the Scrum GuideTM (https://www.scrum.org/Resources/Scrum-Glossary).");

	//Obtem as atividades Scrum.
	String queryAtividade1 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Planejamento da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade1 = manager.createQuery(queryAtividade1, AtividadePadrao.class);
	AtividadePadrao atividade1 = typedQueryAtividade1.getSingleResult();

	String queryAtividade2 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade2 = manager.createQuery(queryAtividade2, AtividadePadrao.class);
	AtividadePadrao atividade2 = typedQueryAtividade2.getSingleResult();

	String queryAtividade3 = "SELECT e FROM AtividadePadrao e WHERE e.nome='Reunião de Revisão da Sprint'";
	TypedQuery<AtividadePadrao> typedQueryAtividade3 = manager.createQuery(queryAtividade3, AtividadePadrao.class);
	AtividadePadrao atividade3 = typedQueryAtividade3.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Duração.
	String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	scrum.setAtividadePadrao(Arrays.asList(atividade1, atividade2, atividade3));
	scrum.setElementoMensuravel(Arrays.asList(tamanho, desempenho, duracao));

	//Persiste.	
	try
	{
	    manager.getTransaction().begin();
	    manager.persist(scrum);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
		    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
	    {
		System.out.println("O Processo Padrão Scrum já existe.");

		String query = String.format("SELECT p FROM ProcessoPadrao p WHERE p.nome='%s'", scrum.getNome());
		TypedQuery<ProcessoPadrao> typedQuery = manager.createQuery(query, ProcessoPadrao.class);

		scrum = typedQuery.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}

	return scrum;
    }

}
