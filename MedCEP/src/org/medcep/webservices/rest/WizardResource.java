package org.medcep.webservices.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("WizardResource")
public class WizardResource
{
    @Path("/Step1")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response step1(String json) throws Exception
    {	
	String valor = json;	
	return Response.ok().entity(new String("Executou step 1")).build();
    }
    
    @Path("/Step2")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response step2(String json) throws Exception
    {
	
	String valor = json;	
	return Response.ok().entity(new String("Executou step 2")).build();
    }
    
    
    private class DadosLogin{
	
	
    }
}
