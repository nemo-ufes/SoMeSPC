package org.medcep.webservices.rest.exceptions;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@SuppressWarnings("serial")
public class ComandoInexistenteException extends WebApplicationException
{

    public ComandoInexistenteException(String comando)
    {
	super(Response.status(Response.Status.NOT_FOUND)
		.entity(comando).type(MediaType.TEXT_PLAIN).build());
    }
}
