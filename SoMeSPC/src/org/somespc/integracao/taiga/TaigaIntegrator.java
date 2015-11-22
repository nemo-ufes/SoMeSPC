/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.integracao.taiga;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.jobs.TaigaMedicaoJob;
import org.somespc.integracao.taiga.model.AuthInfo;
import org.somespc.integracao.taiga.model.EstadoProjeto;
import org.somespc.integracao.taiga.model.EstadoSprint;
import org.somespc.integracao.taiga.model.Estoria;
import org.somespc.integracao.taiga.model.MedidasTaiga;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.integracao.taiga.model.Tarefa;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.Escala;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoMedida;
import org.somespc.model.entidades_e_medidas.UnidadeDeMedida;
import org.somespc.model.organizacao_de_software.AlocacaoEquipe;
import org.somespc.model.organizacao_de_software.Equipe;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.ItemPlanoMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;
import org.somespc.util.json.JSONObject;
import org.somespc.webservices.rest.dto.TaigaLoginDTO;

/**
 * Classe para integração do Taiga com a SoMeSPC.
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
     * Obtem as estorias do Project Backlog de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return List<Estoria> com as estórias do Project Backlog.
     */
    public List<Estoria> obterEstoriasDoProjectBacklogTaiga(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone__isnull", true);

	List<Estoria> estorias = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get(new GenericType<List<Estoria>>() {
		});

	return estorias;
    }
    
    /**
     * Obtem as tarefas de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return List<Tarefa> com as tarefas do projeto.
     */
    public List<Tarefa> obterTarefasDoProjeto(String apelidoProjeto)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("tasks").queryParam("project", idProjeto);

	List<Tarefa> tarefas = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get(new GenericType<List<Tarefa>>() {
		});

	return tarefas;
    }
    
    /**
     * Obtem as estorias do Project Backlog de um Projeto Taiga.
     * 
     * @param apelidoProjeto
     *            - Apelido (slug) do projeto.
     * @return List<Estoria> com as estórias do Project Backlog.
     */
    public List<Estoria> obterEstoriasDaSprintBacklogTaiga(String apelidoProjeto, String apelidoSprint)
    {
	//Resolve o ID do projeto.
	WebTarget target = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase()).queryParam("milestone", apelidoSprint.toLowerCase());

	Response response = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");
	int idSprint = json.getInt("milestone");

	//Busca informações do projeto.
	target = client.target(this.urlTaiga).path("userstories").queryParam("project", idProjeto).queryParam("milestone", idSprint);

	List<Estoria> estorias = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get(new GenericType<List<Estoria>>() {
		});

	return estorias;
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
		.header("x-disable-pagination", "True")
		.get();

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));
	int idProjeto = json.getInt("project");

	//Busca informações das sprints.
	target = client.target(this.urlTaiga).path("milestones").queryParam("project", idProjeto);

	List<Sprint> sprints = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
		.header("x-disable-pagination", "True")
		.get(new GenericType<List<Sprint>>() {
		});

	return sprints;
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
		.header("x-disable-pagination", "True")
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
		.header("x-disable-pagination", "True")
		.get(EstadoSprint.class);

	return estadoSprint;
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
		.header("x-disable-pagination", "True")
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
		.header("x-disable-pagination", "True")
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
		.header("x-disable-pagination", "True")
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
	Membro membro;

	try
	{
	    membro = target
		    .request(MediaType.APPLICATION_JSON_TYPE)
		    .header("Authorization", String.format("Bearer %s", obterAuthToken()))
		    .header("x-disable-pagination", "True")
		    .get(Membro.class);
	}
	catch (Exception ex)
	{
	    membro = null;
	}

	return membro;
    }
    
    /**
     * Obtem os dados do Membro pelo ID.
     * 
     * @param idMembro
     *            - ID do membro a ser buscado.
     * @return Membro
     */
    public List<Membro> obterMembrosDoProjetoTaiga(String apelidoProjeto)
    {
    	
    	//Resolve o ID do projeto.
    	WebTarget projeto = client.target(this.urlTaiga).path("resolver").queryParam("project", apelidoProjeto.toLowerCase());

    	Response response = projeto
    		.request(MediaType.APPLICATION_JSON_TYPE)
    		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
    		.header("x-disable-pagination", "True")
    		.get();

    	if (response.getStatus() != Status.OK.getStatusCode())
    	{
    	    throw new RuntimeException(String.format("Erro ao obter o ID do projeto %s pela API Resolver. HTTP Code: %s", apelidoProjeto, response.getStatus()));
    	}

    	JSONObject json = new JSONObject(response.readEntity(String.class));
    	int idProjeto = json.getInt("project");
    	
		//Busca informações do membro.
		WebTarget target = client.target(this.urlTaiga).path("memberships").queryParam("project", idProjeto);
	
		List<Membro> membros = target
			.request(MediaType.APPLICATION_JSON_TYPE)
			.header("Authorization", String.format("Bearer %s", obterAuthToken()))
			.header("x-disable-pagination", "True")
			.get(new GenericType<List<Membro>>() {
			});
	
		return membros;
    }

    /**
     * Cadastra um RecursoHumano na SoMeSPC a partir de um Membro do Taiga.
     * Se já existir, retorna o RecursoHumano existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return RecursoHumano criado/existente.
     * @throws Exception
     */
	public RecursoHumano criarRecursoHumanoSoMeSPC(Membro membro) throws Exception {
				
		RecursoHumano recursoHumano = new RecursoHumano();
		recursoHumano.setNome(membro.getNome());
		
		
		return SoMeSPCIntegrator.criarRecursoHumano(recursoHumano);
	}

    /**
     * Cadastra um Papel de Recurso Humano na SoMeSPC a partir de um Membro do Taiga.
     * Se já existir, retorna o Papel existente.
     * 
     * @param membro
     *            - Membro a ser cadastrado.
     * @return Papel criado/existente.
     * @throws Exception
     */
	public PapelRecursoHumano criarPapelRecursoHumanoSoMeSPC(Membro membro) throws Exception {
		PapelRecursoHumano papel = new PapelRecursoHumano();
		papel.setNome(membro.getPapel());

		return SoMeSPCIntegrator.criarPapelRecursoHumano(papel);
	}

    /**
     * Cria uma Equipe na SoMeSPC baseado nos Membros do projeto Taiga.
     * Se já existir, retorna a Equipe existente.
     * 
     * @param nomeEquipe
     *            - Nome da equipe a ser criada.
     * @param membrosDaEquipe
     *            - Membros da equipe.
     * @return Equipe criada/existente.
     * @throws Exception
     */
    public Equipe criarEquipeSoMeSPC(String nomeEquipe, List<Membro> membrosDaEquipe) throws Exception
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
		if (membro.getId() != 0 && membro.getNome() != null && !membro.getNome().isEmpty()) {
		    AlocacaoEquipe alocacao = new AlocacaoEquipe();
		    alocacao.setEquipe(equipe);
	
		    //Insere o Recurso Humano na Equipe e na Alocacao. 
		    //Acredito que relacionamento direto entre Equipe <-> RecursoHumano seja para facilitar a visualização dos recursos da equipe.
		    RecursoHumano rec = this.criarRecursoHumanoSoMeSPC(membro);
		    recursosHumanos.add(rec);
	
		    alocacao.setRecursoHumano(rec);
		    alocacao.setPapelRecursoHumano(this.criarPapelRecursoHumanoSoMeSPC(membro));
		    alocacao.setTipoDeEntidadeMensuravel(tipoAlocacaco);
		    alocacoes.add(alocacao);
		}
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

    public EntidadeMensuravel criarEntidadeMensuravelSprintSoMeSPC(Sprint sprint, String nomeProjeto) throws Exception{
    	
    	String nome = String.format("Sprint %s (%s)", sprint.getNome(), nomeProjeto);
    	String descricao =  String.format("Sprint %s (%s) do projeto %s.", sprint.getNome(), sprint.getApelido(), nomeProjeto); 
    	
    	return SoMeSPCIntegrator.criarEntidadeMensuravel(nome, descricao, "Sprint");
    }
    
    /**
     * Cria um Projeto na SoMeSPC baseado em um Projeto Taiga.
     * Se já existir, retorna o Projeto SoMeSPC existente.
     * 
     * @param projeto
     *            - projeto Taiga a ser criado.
     * @return Projeto SoMeSPC criado/existente.
     * @throws Exception
     */
    public org.somespc.model.organizacao_de_software.Projeto criarProjetoSoMeSPC(Projeto projeto) throws Exception
    {
	EntityManager manager = XPersistence.createManager();
	
	if (projeto.getEquipe() == null || projeto.getEquipe().isEmpty()){
		List<Membro> equipe = this.obterMembrosDoProjetoTaiga(projeto.getApelido());
		projeto.setEquipe(equipe);
	}
	
	Equipe equipe = this.criarEquipeSoMeSPC("Equipe " + projeto.getNome(), projeto.getEquipe());
	List<Equipe> equipes = new ArrayList<Equipe>();
	equipes.add(equipe);

	String tipoEntidadeQuery = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Projeto'");
	TypedQuery<TipoDeEntidadeMensuravel> tipoEntidadeTypedQuery = manager.createQuery(tipoEntidadeQuery, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = tipoEntidadeTypedQuery.getSingleResult();

	org.somespc.model.organizacao_de_software.Projeto projetoSoMeSPC = new org.somespc.model.organizacao_de_software.Projeto();
	projetoSoMeSPC.setNome(projeto.getNome());
	projetoSoMeSPC.setEquipe(equipes);
	projetoSoMeSPC.setDataInicio(projeto.getDataCriacao());
	projetoSoMeSPC.setTipoDeEntidadeMensuravel(tipoProjeto);

	try
	{
	    manager.getTransaction().begin();
	    manager.persist(projetoSoMeSPC);
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
		TypedQuery<org.somespc.model.organizacao_de_software.Projeto> typedQuery = manager.createQuery(query, org.somespc.model.organizacao_de_software.Projeto.class);

		projetoSoMeSPC = typedQuery.getSingleResult();
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

	return projetoSoMeSPC;
    }

    /**
     * Cria as Medidas do Taiga no banco de dados da SoMeSPC.
     * Não atribui valores, somente cria as definições das medidas.
     * 
     * @param medidasTaiga
     *            - Lista com as medidas Taiga a serem criadas na SoMeSPC.
     * @return List<Medida> - Medidas criadas na SoMeSPC.
     * @throws Exception
     */
    public List<Medida> criarMedidasSoMeSPC(List<MedidasTaiga> medidasTaiga) throws Exception
    {

	EntityManager manager = XPersistence.createManager();
	List<Medida> medidasCadastradas = new ArrayList<Medida>();

	//Obtem o tipo de medida base.
	String query1 = "SELECT mb FROM TipoMedida mb WHERE mb.nome='Medida Base'";
	TypedQuery<TipoMedida> typedQuery1 = manager.createQuery(query1, TipoMedida.class);
	TipoMedida medidaBase = typedQuery1.getSingleResult();

	//Obtem a escala racional.
	String query2 = "SELECT e FROM Escala e WHERE e.nome='Escala formada pelos números reais'";
	TypedQuery<Escala> typedQuery2 = manager.createQuery(query2, Escala.class);
	Escala escala = typedQuery2.getSingleResult();
	
	//Obtem a unidade de medida Pontos de Estória.
	String query3 = "SELECT u FROM UnidadeDeMedida u WHERE u.nome='Pontos de Estória'";
	TypedQuery<UnidadeDeMedida> typedQuery3 = manager.createQuery(query3, UnidadeDeMedida.class);
	UnidadeDeMedida unidadeMedida = typedQuery3.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Alocação.
	String query4 = String.format("SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Alocação de Recurso Humano'");
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery4 = manager.createQuery(query4, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoAlocacao = typedQuery4.getSingleResult();
	
	//Obtem o tipo de Entidade Mensurável Projeto.
	String query5 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery5 = manager.createQuery(query5, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = typedQuery5.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Sprint.
	String query7 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Sprint'";
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
	@SuppressWarnings("unused")
	ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	manager.close();

	//Define a medida de acordo com a lista informada.
	for (MedidasTaiga medidaTaiga : medidasTaiga)
	{
	    Medida medida = new Medida();
	    medida.setNome(medidaTaiga.toString());

	    switch (medidaTaiga)
	    {
		case PONTOS_ESTORIA_PLANEJADOS_PROJETO:
		    medida.setMnemonico("PEPP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PONTOS_ESTORIA_CONCLUIDOS_PROJETO:
		    medida.setMnemonico("PECP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case TAXA_CONCLUSAO_PONTOS_ESTORIA_PROJETO:
		    medida.setMnemonico("TCPEP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case NUMERO_SPRINTS_PLANEJADAS_PROJETO:
		    medida.setMnemonico("NSPP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case NUMERO_SPRINTS_REALIZADAS_PROJETO:
		    medida.setMnemonico("NSRP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case TAXA_CONCLUSAO_SPRINTS_PROJETO:
		    medida.setMnemonico("TCSP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case NUMERO_ESTORIAS_PLANEJADAS_SPRINT:
		    medida.setMnemonico("NEPS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case NUMERO_ESTORIAS_CONCLUIDAS_SPRINT:
		    medida.setMnemonico("NECS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TAXA_CONCLUSAO_ESTORIAS_SPRINT:
		    medida.setMnemonico("TCES");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case PONTOS_ESTORIAS_PLANEJADOS_SPRINT:
		    medida.setMnemonico("PEPS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case PONTOS_ESTORIAS_CONCLUIDAS_SPRINT:
		    medida.setMnemonico("PECS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TAXA_CONCLUSAO_PONTOS_ESTORIAS_SPRINT:
		    medida.setMnemonico("TCPES");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case NUMERO_TAREFAS_PLANEJADAS_SPRINT:
		    medida.setMnemonico("NTPS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case NUMERO_TAREFAS_CONCLUIDAS_SPRINT:
		    medida.setMnemonico("NTCS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TAXA_CONCLUSAO_TAREFAS_SPRINT:
		    medida.setMnemonico("TCTS");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case NUMERO_ESTORIAS_CONCLUIDAS_PROJETO:
		    medida.setMnemonico("NECP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case MEDIA_ESTORIAS_CONCLUIDAS_SPRINT_PROJETO:
		    medida.setMnemonico("MECSP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;	
		case NUMERO_TAREFAS_ATRIBUIDAS_MEMBRO:
		    medida.setMnemonico("NTAM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	 
		case NUMERO_TAREFAS_CONCLUIDAS_MEMBRO:
		    medida.setMnemonico("NTCM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	 
		case TAXA_CONCLUSAO_TAREFAS_MEMBRO:
		    medida.setMnemonico("TCTM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	
		case NUMERO_PONTOS_ESTORIA_ATRIBUIDOS_MEMBRO:
		    medida.setMnemonico("NPEAM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	
		case NUMERO_PONTOS_ESTORIA_CONCLUIDOS_MEMBRO:
		    medida.setMnemonico("NPECM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	
		case TAXA_CONCLUSAO_PONTOS_ESTORIA_MEMBRO:
		    medida.setMnemonico("TCPEM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	
		case NUMERO_DOSES_IOCAINE_ATRIBUIDAS_MEMBRO:
		    medida.setMnemonico("NDIAM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;	
		case TAXA_DOSES_IOCAINE_MEMBRO:
		    medida.setMnemonico("TDIM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAlocacao)));
		    break;		    		    
		case NUMERO_IOCAINE_SPRINT:
		    medida.setMnemonico("NDIS");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case TAXA_IOCAINE_SPRINT:
		    medida.setMnemonico("TDIS");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case VELOCIDADE_EQUIPE_PROJETO:
		    medida.setMnemonico("VEP");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		default:
		    System.out.println(String.format("Medida %s inexistente no Taiga.", medidaTaiga.toString()));
	    }

	    medida.setDescricao("Medida obtida pela API Taiga conforme a documentação: http://taigaio.github.io/taiga-doc/dist/api.html");
	    medida.setTipoMedida(medidaBase);
	    medida.setEscala(escala);
	    medida.setUnidadeDeMedida(unidadeMedida);

	    DefinicaoOperacionalDeMedida definicao = new DefinicaoOperacionalDeMedida();

	    definicao.setNome("Definição operacional da medida do Taiga - " + medida.getNome());
	    Calendar cal = Calendar.getInstance();
	    definicao.setData(cal.getTime());
	    definicao.setDescricao("Definição operacional criada automaticamente.");
	    definicao.setMedida(medida);
	  
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medida);
		manager.persist(definicao);
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
   
	public synchronized void agendarTaigaMedicaoJob(PlanoDeMedicaoDoProjeto plano, String apelidoProjeto, 
			ItemPlanoMedicao item, Periodicidade periodicidade, TaigaLoginDTO login) throws Exception {

		// Inicia o scheduler.
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();

		if (!sched.isStarted())
			sched.start();

		String nomeMedida = item.getNome();

		// Se o item não é uma medida, pula para o próximo item.
		if (!nomeMedida.startsWith("ME - ")) {
			throw new Exception("O item do plano não é uma medida: " + nomeMedida);
		}

		JobDetail job = null;
		Trigger trigger = null;
		
		// Converte a periodicidade em horas.
		String period = periodicidade.getNome();
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
			String mensagem = String.format("Periodicidade %s inexistente.", period);
			System.out.println(mensagem);
			throw new Exception(mensagem);
		}

		JobDataMap map = new JobDataMap();

		map.put("urlTaiga", login.getUrl());
		map.put("usuarioTaiga", login.getUsuario());
		map.put("senhaTaiga", login.getSenha());
		map.put("apelidoProjeto", apelidoProjeto);
		map.put("nomePlano", plano.getNome());
		map.put("nomeMedida", nomeMedida);
		map.put("entidadeMedida", plano.getProjeto().getNome());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

		String nomeGrupo = plano.getProjeto().getNome();
		String nomeTrigger = String.format("TaigaMediçãoJob - Medição %s da medida %s - criado em %s",
				periodicidade.getNome(), nomeMedida, dataHora);
		String nomeJob = nomeTrigger;

		boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));
	
		//Se o job existir, apenas agenda. Senão, cria o job e agenda sua execução.
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
