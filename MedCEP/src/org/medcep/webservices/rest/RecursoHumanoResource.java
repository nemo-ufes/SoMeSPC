/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */
package org.medcep.webservices.rest;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.model.organizacao.*;
import org.openxava.jpa.*;

/**
 * API REST para Recurso Humano.
 * @author Vinicius
 *
 */
@Path("RecursoHumano")
public class RecursoHumanoResource {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public RecursoHumano get(@PathParam("id") String id) {
		return XPersistence.getManager().find(RecursoHumano.class, id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<RecursoHumano> getAll()
	{
		TypedQuery<RecursoHumano> query = XPersistence.getManager().createQuery("FROM RecursoHumano", RecursoHumano.class);
	    return (List<RecursoHumano>) query.getResultList();		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public RecursoHumano post(RecursoHumano recursoHumano) throws Exception
	{
		try
		{
			XPersistence.getManager().persist(recursoHumano);
			XPersistence.getManager().getTransaction().commit();
			return recursoHumano;	
		}
		catch (Exception ex)
		{
			XPersistence.getManager().getTransaction().rollback();
			throw ex;
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")	
	public RecursoHumano put(RecursoHumano recursoHumano) throws Exception
	{
		try
		{
			XPersistence.getManager().merge(recursoHumano);
			XPersistence.getManager().getTransaction().commit();
			return recursoHumano;	
		}
		catch (Exception ex)
		{
			XPersistence.getManager().getTransaction().rollback();
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
			RecursoHumano recursoHumano = XPersistence.getManager().find(RecursoHumano.class, id);
			XPersistence.getManager().remove(recursoHumano);
			XPersistence.getManager().getTransaction().commit();
			 return Response.status(Status.NO_CONTENT).build();
		}
		catch (Exception ex)
		{
			XPersistence.getManager().getTransaction().rollback();
			throw ex;
		}
	}
}
