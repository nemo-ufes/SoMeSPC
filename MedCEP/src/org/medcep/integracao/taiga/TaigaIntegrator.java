package org.medcep.integracao.taiga;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.client.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.integracao.conversores.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
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
     * @param nomeProjeto
     *            - Nome do projeto a ser buscado.
     * @return Projeto
     */
    public Projeto obterProjetoTaiga(String nomeProjeto)
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

	Projeto projeto = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.get(Projeto.class);

	return projeto;
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
	RecursoHumano recursoHumano = TaigaConverter.converterMembroParaRecursoHumano(membro);

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
	PapelRecursoHumano papel = TaigaConverter.converterMembroParaPapelRecursoHumano(membro);

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

}
