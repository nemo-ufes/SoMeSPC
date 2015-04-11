package org.medcep.integracao.taiga;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.client.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.EstadoProjeto.Medidas;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.medicao.*;
import org.medcep.model.organizacao.*;
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
     * Obtem demais dados de andamento do projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto a ser buscado.
     * @return Json de dados do andamento do projeto Taiga
     */
    public String obterAndamentoProjetoTaigaJson(String apelidoProjeto)
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
     * Os membros já vem populados pelo método obterProjeto.
     * 
     * @param nomeProjeto
     * @return Projeto
     */
    @Deprecated
    public String obterMembrosDoProjeto(String nomeProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", nomeProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", nomeProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("projects/" + idProjeto);

	response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o projeto %s. HTTP Code: %s", nomeProjeto, response.getStatus()));
	}

	json = new JSONObject(response.readEntity(String.class));
	JSONArray membrosJson = json.getJSONArray("members");
	JSONArray membrosInfoJson = new JSONArray();

	for (int i = 0; i < membrosJson.length() - 1; i++)
	{
	    int idMembro = membrosJson.getInt(i);
	    membrosInfoJson.put(i, obterMembroTaiga(idMembro));
	}

	return membrosInfoJson.toString();
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
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		System.out.println(String.format("Recurso Humano %s já existe.", membro.getNome()));

		String query = String.format("SELECT r FROM RecursoHumano r WHERE r.nome='%s'", membro.getNome());
		TypedQuery<RecursoHumano> typedQuery = XPersistence.getManager().createQuery(query, RecursoHumano.class);

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
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		System.out.println(String.format("Papel de Recurso Humano %s já existe.", membro.getPapel()));

		String query = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='%s'", membro.getPapel());
		TypedQuery<PapelRecursoHumano> typedQuery = XPersistence.getManager().createQuery(query, PapelRecursoHumano.class);

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
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		System.out.println(String.format("Equipe %s já existe.", equipe.getNome()));

		String query = String.format("SELECT e FROM Equipe e WHERE e.nome='%s'", equipe.getNome());
		TypedQuery<Equipe> typedQuery = XPersistence.getManager().createQuery(query, Equipe.class);

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

	org.medcep.model.organizacao.Projeto projetoMedCEP = new org.medcep.model.organizacao.Projeto();
	projetoMedCEP.setNome(projeto.getNome());
	projetoMedCEP.setEquipe(equipes);
	projetoMedCEP.setDataInicio(projeto.getDataCriacao());

	//Tenta preencher o Tipo de Entidade Mensurável do Projeto.
	try
	{
	    String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Projeto'");
	    TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = XPersistence.getManager().createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	    TipoDeEntidadeMensuravel tipoProjeto = tipoEntidadeTypedQuery.getSingleResult();
	    projetoMedCEP.setTipoDeEntidadeMensuravel(tipoProjeto);
	}
	catch (Exception ex)
	{
	    System.out.println("Erro ao cadastrar o tipo de entidade mensurável do projeto.");
	}

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(projetoMedCEP);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		System.out.println(String.format("Projeto %s já existe.", projeto.getNome()));

		String query = String.format("SELECT p FROM Projeto p WHERE p.nome='%s'", projeto.getNome());
		TypedQuery<org.medcep.model.organizacao.Projeto> typedQuery = XPersistence.getManager().createQuery(query, org.medcep.model.organizacao.Projeto.class);

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
     * Cria as Medidas do Taiga no banco de dados da MedCEP.
     * Não atribui valores, somente cria as definições das medidas.
     * 
     * @param medidasDoProjetoTaiga
     *            - Lista com as medidas Taiga a serem criadas na MedCEP.
     * @return List<Medida> - Medidas criadas na MedCEP.
     * @throws Exception
     */
    public List<Medida> criarMedidasMedCEP(List<EstadoProjeto.Medidas> medidasDoProjetoTaiga) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	List<Medida> medidasCadastradas = new ArrayList<Medida>();

	//Obtem o tipo de medida base.
	String query = "SELECT mb FROM TipoMedida mb WHERE mb.nome='Medida Base'";
	TypedQuery<TipoMedida> typedQuery = XPersistence.getManager().createQuery(query, TipoMedida.class);
	TipoMedida medidaBase = typedQuery.getSingleResult();

	//Obtem a escala racional.
	String query2 = "SELECT e FROM Escala e WHERE e.nome='Números Racionais'";
	TypedQuery<Escala> typedQuery2 = XPersistence.getManager().createQuery(query2, Escala.class);
	Escala escala = typedQuery2.getSingleResult();

	//Obtem a unidade de medida Pontos de Estória.
	String query3 = "SELECT u FROM UnidadeDeMedida u WHERE u.nome='Pontos de Estória'";
	TypedQuery<UnidadeDeMedida> typedQuery3 = XPersistence.getManager().createQuery(query3, UnidadeDeMedida.class);
	UnidadeDeMedida unidadeMedida = typedQuery3.getSingleResult();

	//Obtem o tipo Elemento Diretamente Mensurável.
	String query4 = "SELECT t FROM TipoElementoMensuravel t WHERE t.nome='Elemento Diretamente Mensurável'";
	TypedQuery<TipoElementoMensuravel> typedQuery4 = XPersistence.getManager().createQuery(query4, TipoElementoMensuravel.class);
	TipoElementoMensuravel elementoDiretamenteMensuravel = typedQuery4.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Projeto.
	String query5 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery5 = XPersistence.getManager().createQuery(query5, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = typedQuery5.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Recurso Humano.
	String query6 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Recurso Humano'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery6 = XPersistence.getManager().createQuery(query6, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoRecursoHumano = typedQuery6.getSingleResult();

	//Cadastra ou obtem o ElementoMensuravel Desempenho.
	ElementoMensuravel desempenho = new ElementoMensuravel();
	try
	{
	    desempenho.setNome("Desempenho");
	    desempenho.setDescricao("Desempenho");
	    desempenho.setTipoElementoMensuravel(elementoDiretamenteMensuravel);

	    manager.getTransaction().begin();
	    manager.persist(desempenho);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
		TypedQuery<ElementoMensuravel> typedQueryDesempenho = XPersistence.getManager().createQuery(queryDesempenho, ElementoMensuravel.class);

		desempenho = typedQueryDesempenho.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}

	//Cadastra ou obtem o ElementoMensuravel Tamanho.
	ElementoMensuravel tamanho = new ElementoMensuravel();
	try
	{
	    tamanho.setNome("Tamanho");
	    tamanho.setDescricao("Tamanho");
	    tamanho.setTipoElementoMensuravel(elementoDiretamenteMensuravel);

	    manager.getTransaction().begin();
	    manager.persist(tamanho);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
		TypedQuery<ElementoMensuravel> typedQueryTamanho = XPersistence.getManager().createQuery(queryTamanho, ElementoMensuravel.class);

		tamanho = typedQueryTamanho.getSingleResult();
	    }
	    else
	    {
		throw ex;
	    }
	}

	//Define a medida de acordo com a lista informada.
	for (Medidas medidaTaiga : medidasDoProjetoTaiga)
	{
	    Medida medida = new Medida();
	    List<TipoDeEntidadeMensuravel> tiposEntidadesMensuraveis = new ArrayList<TipoDeEntidadeMensuravel>();
	    
	    switch (medidaTaiga)
	    {
		case PONTOS_ALOCADOS:
		    medida.setNome("Pontos Alocados");
		    medida.setMnemonico("TAIGA-PA");
		    medida.setElementoMensuravel(desempenho);		    
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case PONTOS_ALOCADOS_POR_PAPEL:
		    medida.setNome("Pontos Alocados por Papel");
		    medida.setMnemonico("TAIGA-PAP");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoRecursoHumano);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case PONTOS_DEFINIDOS:
		    medida.setNome("Pontos Definidos");
		    medida.setMnemonico("TAIGA-PD");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case PONTOS_DEFINIDOS_POR_PAPEL:
		    medida.setNome("Pontos Definidos por Papel");
		    medida.setMnemonico("TAIGA-PDP");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoRecursoHumano);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case PONTOS_FECHADOS:
		    medida.setNome("Pontos Fechados");
		    medida.setMnemonico("TAIGA-PF");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case PONTOS_FECHADOS_POR_PAPEL:
		    medida.setNome("Pontos Fechados por Papel");
		    medida.setMnemonico("TAIGA-PFP");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoRecursoHumano);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case TOTAL_MARCOS:
		    medida.setNome("Total de Marcos (Sprints)");
		    medida.setMnemonico("TAIGA-TM");
		    medida.setElementoMensuravel(tamanho);
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case TOTAL_PONTOS:
		    medida.setNome("Total de Pontos");
		    medida.setMnemonico("TAIGA-TP");
		    medida.setElementoMensuravel(tamanho);
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		case VELOCIDADE:
		    medida.setNome("Velocidade");
		    medida.setMnemonico("TAIGA-VEL");
		    medida.setElementoMensuravel(desempenho);
		    tiposEntidadesMensuraveis.add(tipoProjeto);
		    medida.setTipoDeEntidadeMensuravel(tiposEntidadesMensuraveis);
		    break;
		default:
		    throw new Exception("Medida inexistente no Taiga.");
	    }

	    medida.setDescricao("Medida obtida pela API Taiga conforme a documentação: http://taigaio.github.io/taiga-doc/dist/api.html#projects-stats");
	    medida.setTipoMedida(medidaBase);
	    medida.setEscala(escala);
	    medida.setUnidadeDeMedida(unidadeMedida);

	  

	    try
	    {
		manager.getTransaction().begin();
		manager.persist(medida);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (ex.getCause() != null &&
			ex.getCause().getCause() != null &&
			ex.getCause().getCause() instanceof ConstraintViolationException)
		{
		    System.out.println(String.format("A Medida %s já existe.", medida.getNome()));

		    String queryMedida = String.format("SELECT m FROM Medida m WHERE m.nome='%s'", medida.getNome());
		    TypedQuery<Medida> typedQueryMedida = XPersistence.getManager().createQuery(queryMedida, Medida.class);

		    medida = typedQueryMedida.getSingleResult();
		}
		else
		{
		    throw ex;
		}
	    }

	    medidasCadastradas.add(medida);
	}

	return medidasCadastradas;
    }

}
