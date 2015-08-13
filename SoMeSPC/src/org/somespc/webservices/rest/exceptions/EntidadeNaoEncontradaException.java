package org.somespc.webservices.rest.exceptions;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@SuppressWarnings("serial")
public class EntidadeNaoEncontradaException extends WebApplicationException
{

    public EntidadeNaoEncontradaException(String message)
    {
	super(Response.status(Response.Status.NOT_FOUND)
		.entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
