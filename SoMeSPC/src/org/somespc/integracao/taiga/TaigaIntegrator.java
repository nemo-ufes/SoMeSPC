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
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.model.AuthInfo;
import org.somespc.integracao.taiga.model.EstadoProjeto;
import org.somespc.integracao.taiga.model.EstadoSprint;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.Escala;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoMedida;
import org.somespc.model.entidades_e_medidas.UnidadeDeMedida;
import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.objetivos.ObjetivoEstrategico;
import org.somespc.model.organizacao_de_software.AlocacaoEquipe;
import org.somespc.model.organizacao_de_software.Equipe;
import org.somespc.model.organizacao_de_software.Objetivo;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;
import org.somespc.model.plano_de_medicao.TreeItemPlanoMedicao;
import org.somespc.util.json.JSONObject;
import org.somespc.webservices.rest.dto.ItemPlanoDeMedicaoDTO;

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
	target = client.target(this.urlTaiga).path("milestones").queryParam("project", idProjeto);

	List<Sprint> sprints = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.header("Authorization", String.format("Bearer %s", obterAuthToken()))
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
	Membro membro;

	try
	{
	    membro = target
		    .request(MediaType.APPLICATION_JSON_TYPE)
		    .header("Authorization", String.format("Bearer %s", obterAuthToken()))
		    .get(Membro.class);
	}
	catch (Exception ex)
	{
	    membro = null;
	}

	return membro;
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

		return SoMeSPCIntegrator.criarPapelRecursoHumanoSoMeSPC(papel);
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

	//Obtem o tipo de Entidade Mensurável Tipo de Artefatoo.
	String query8 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Tipo de Artefato'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery8 = manager.createQuery(query8, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoArtefato = typedQuery8.getSingleResult();

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
		case PONTOS_ESTORIA_ALOCADOS_PROJETO:
		    medida.setMnemonico("PEAP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PONTOS_ESTORIA_CONCLUIDOS_PROJETO_CONCLUSAO:
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
		case NUMERO_SPRINTS_REALIZADA_PROJETO:
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
		case NUMERO_SPRINTS_REALIZADAS_PROJETO:
		    medida.setMnemonico("NSRP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
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
		case PONTOS_ESTORIA_CONCLUIDOS_PROJETO_DESEMPENHO:
		    medida.setMnemonico("PECP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case VELOCIDADE_PROJETO:
		    medida.setMnemonico("VEP");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case NUMERO_IOCAINE_SPRINT:
		    medida.setMnemonico("NDIS");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoAtividadePadrao)));
		    break;
		case NUMERO_TAREFAS_REALIZADAS_SPRINT:
		    medida.setMnemonico("NTRS");
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
     * Cria o plano de medição do projeto com as medidas informadas.
     * 
     * @param itemPlanoDeMedicaoDTO
     *            - Itens a serem incluídas no Plano de Medição do Projeto.
     * @param projeto
     *            - Projeto a ser incluído no Plano de Medição do Projeto.
     * @throws Exception
     */
    public synchronized PlanoDeMedicaoDoProjeto criarPlanoMedicaoProjetoSoMeSPC(List<ItemPlanoDeMedicaoDTO> itemPlanoDeMedicaoDTO, Periodicidade periodicidadeMedicao, Projeto projeto) throws Exception
    {
    System.out.println("CHEGUEI AQUI 1");
    	
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

	    proj = this.criarProjetoSoMeSPC(projeto);
	}

	Calendar cal = Calendar.getInstance();
	plano.setData(cal.getTime());

	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	plano.setNome("Plano de Medição do Projeto " + projeto.getNome() + " (Wizard) - " + dataHora);
	plano.setVersao("1");
	plano.setDescricao("Plano de Medição do Projeto criado via wizard em " + dataHora);
	plano.setProjeto(proj);

	System.out.println("CHEGUEI AQUI");
	
	//Criação os objetos necessários para criação do Plano de Medição do Projeto - SoMeSPC
	for(ItemPlanoDeMedicaoDTO item: itemPlanoDeMedicaoDTO){
		
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
		objEstrategicoTree.setNome("OE - " + objEstrategico.getNome());
		objEstrategicoTree.setItem(objEstrategico);
		objEstrategicoTree.setPlanoDeMedicaoContainer(plano);
		
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
		objMedicaoTree.setNome("OM - " + objMedicao.getNome());
		objMedicaoTree.setItem(objMedicao);
		objMedicaoTree.setPlanoDeMedicaoContainer(plano);		
				
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
	    necessidadeTree.setNome("NI - " + necessidade.getNome());
	    necessidadeTree.setPath("/" + idObjEstrategicoTree + "/" + idObjMedicaoTree);
	    necessidadeTree.setItem(necessidade);
	    necessidadeTree.setPlanoDeMedicaoContainer(plano);
		
	    //Persiste NI
	    manager.persist(necessidadeTree);
	    Integer idNecessidadeTree = necessidadeTree.getId();
	    
	    //ItemTree de Medida
	    TreeItemPlanoMedicao medidaTree = new TreeItemPlanoMedicao();
	    medidaTree.setNome("ME - " + med.getNome());
	    medidaTree.setPath("/" + objEstrategicoTree.getId() + "/" + objMedicaoTree.getId() + "/" + idNecessidadeTree);
	    medidaTree.setItem(med);
	    medidaTree.setPlanoDeMedicaoContainer(plano);

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
	//SoMeSPCIntegrator.agendarMedicoesPlanoMedicaoProjeto(plano, projeto);

	return plano;
    }


}
