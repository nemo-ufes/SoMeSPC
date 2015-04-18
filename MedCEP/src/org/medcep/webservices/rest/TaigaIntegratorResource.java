package org.medcep.webservices.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;

@Path("TaigaIntegrator")
public class TaigaIntegratorResource
{
    @Path("/Projetos")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterProjetos() throws Exception
    {		
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	List<Projeto> projetos = integrator.obterProjetosTaiga();
        
	return Response.ok().entity(projetos).build();
    }
    
  
}
