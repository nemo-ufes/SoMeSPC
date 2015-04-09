package org.medcep.integracao.taiga;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.*;

import org.medcep.util.json.*;

public class TaigaIntegrator
{
    private String urlTaiga;
    private AuthInfo authInfo;

    public TaigaIntegrator(String urlTaiga, String usuario, String senha)
    {
	if (urlTaiga.endsWith("/"))
	{
	    urlTaiga = urlTaiga.substring(0, urlTaiga.length() - 1);
	}

	this.urlTaiga = urlTaiga + "/api/v1/";
	this.authInfo = new AuthInfo(usuario, senha);
    }

    /**
     * Busca o token de autenticação do Taiga.
     */
    public String obterAuthToken()
    {
	Client client = ClientBuilder.newClient();
	WebTarget target = client.target(this.urlTaiga).path("auth");
				
	Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(authInfo, MediaType.APPLICATION_JSON));

	if (response.getStatus() != Status.OK.getStatusCode())
	{
	    throw new RuntimeException("Erro ao obter o token de autenticação do Taiga. HTTP Code: " + response.getStatus());
	}

	JSONObject json = new JSONObject(response.readEntity(String.class));	
	return json.get("auth_token").toString();	
    }

    public void obterProjeto(String nomeProjeto)
    {

    }

    /**
     * Classe para mapear as informações de autenticação com o Taiga.
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
