/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.webservices.rest;

import java.net.*;
import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.*;
import org.medcep.model.organizacao.*;
import org.openxava.jpa.*;

/**
 * API REST para Recurso Humano.
 * 
 * @author Vinicius
 *
 */
@Path("RecursoHumano")
public class RecursoHumanoResource
{

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response get(@PathParam("id") String id)
    {

	Response response;

	 if (!XPersistence.getManager().getTransaction().isActive())
		XPersistence.getManager().getTransaction().begin();	
	
	if (id == null || id.isEmpty())
	    response = Response.status(Status.BAD_REQUEST).build();

	RecursoHumano recursoHumano = XPersistence.getManager().find(RecursoHumano.class, id);

	if (recursoHumano == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	    response = Response.status(Status.OK).entity(recursoHumano).build();

	return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAll()
    {
	Response response;
	
	 if (!XPersistence.getManager().getTransaction().isActive())
		XPersistence.getManager().getTransaction().begin();
	
	TypedQuery<RecursoHumano> query = XPersistence.getManager().createQuery("FROM RecursoHumano", RecursoHumano.class);
	List<RecursoHumano> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	    response = Response.status(Status.OK).entity(result).build();

	return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response post(RecursoHumano recursoHumano) throws Exception
    {
	try
	{
	    Response response;

	    if (!XPersistence.getManager().getTransaction().isActive())
		XPersistence.getManager().getTransaction().begin();
		
	    if (recursoHumano == null || recursoHumano.getId() != null)
	    {
		response = Response.status(Status.BAD_REQUEST).build();
	    }
	    else if (XPersistence.getManager().contains(recursoHumano))
	    {
		response = Response.status(Status.CONFLICT).build();
	    }
	    else
	    {
		XPersistence.getManager().persist(recursoHumano);
		XPersistence.getManager().getTransaction().commit();
		URI location = new URI(String.format("%s/%s", uriInfo.getAbsolutePath().toString(), recursoHumano.getId()));
		response = Response.created(location).entity(recursoHumano).build();
	    }

	    return response;
	}
	catch (Exception ex)
	{
	    if (ex.getCause() != null && 
		    ex.getCause().getCause() != null && 
		    ex.getCause().getCause() instanceof ConstraintViolationException)
		return Response.status(Status.CONFLICT).build();

	    throw ex;
	}
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response put(RecursoHumano recursoHumano) throws Exception
    {
	try
	{
	    Response response;
	    
	    if (!XPersistence.getManager().getTransaction().isActive())
		XPersistence.getManager().getTransaction().begin();
	    
	    if (recursoHumano == null || recursoHumano.getId() == null || recursoHumano.getId().isEmpty())
		response = Response.status(Status.BAD_REQUEST).build();
	    else
	    {
		XPersistence.getManager().merge(recursoHumano);
		XPersistence.getManager().getTransaction().commit();
		response = Response.status(Status.OK).build();
	    }

	    return response;
	}
	catch (Exception ex)
	{
	    throw ex;
	}
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@PathParam("id") String id) throws Exception
    {
	try
	{
	    Response response;
	    
	    if (!XPersistence.getManager().getTransaction().isActive())
		XPersistence.getManager().getTransaction().begin();
	    
	    RecursoHumano recursoHumano = XPersistence.getManager().find(RecursoHumano.class, id);

	    if (recursoHumano == null)
		response = Response.status(Status.OK).build();
	    else
	    {
		XPersistence.getManager().remove(recursoHumano);
		XPersistence.getManager().getTransaction().commit();
		response = Response.status(Status.OK).build();
	    }

	    return response;
	}
	catch (Exception ex)
	{
	    throw ex;
	}
    }
}
