package org.medcep.integracao.taiga;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.*;

import org.medcep.integracao.taiga.model.*;
import org.medcep.util.json.*;

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
     * @return JSON com os dados do projeto.
     */
    public Projeto obterProjeto(String nomeProjeto)
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
     * Os membros já vem populados pelo método obterProjeto.
     * @param nomeProjeto
     * @return
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
	    membrosInfoJson.put(i, obterMembro(idMembro));
	}

	return membrosInfoJson.toString();
    }

    public Membro obterMembro(int idMembro)
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
     * Classe para mapear as informações de autenticação com o Taiga.
     * 
     * @author Vinicius
     *
     */
    @XmlRootElement
    public class AuthInfo
    {
	//TODO: Avaliar implementação do tipo Github.
	private String type = "normal";
	private String username;
	private String password;

	public AuthInfo()
	{

	}

	public AuthInfo(String username, String password)
	{
	    this.username = username;
	    this.password = password;
	}

	public String getType()
	{
	    return type;
	}

	public String getUsername()
	{
	    return username;
	}

	public void setUsername(String username)
	{
	    this.username = username;
	}

	public String getPassword()
	{
	    return password;
	}

	public void setPassword(String password)
	{
	    this.password = password;
	}

    }

}
