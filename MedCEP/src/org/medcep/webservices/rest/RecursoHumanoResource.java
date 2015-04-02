package org.medcep.webservices.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("RecursoHumano")
public class RecursoHumanoResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "GET  funcionou!";
	}

}
