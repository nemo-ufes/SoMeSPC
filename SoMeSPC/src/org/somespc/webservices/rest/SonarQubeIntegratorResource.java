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
package org.somespc.webservices.rest;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.somespc.integracao.sonarqube.*;
import org.somespc.integracao.sonarqube.model.*;
import org.somespc.webservices.rest.dto.SonarLoginDTO;

@Path("SonarQubeIntegrator")
public class SonarQubeIntegratorResource {

	@Path("/Projetos")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterProjetos(SonarLoginDTO sonarDto) throws Exception {
		Response response;

		if (sonarDto == null || sonarDto.getUrl() == null || sonarDto.getUrl().isEmpty()) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else {
			SonarQubeIntegrator integrator = new SonarQubeIntegrator(sonarDto.getUrl());
			try {
				List<Recurso> projetos = integrator.obterProjetos();
				response = Response.ok().entity(projetos).build();
			} catch (Exception ex) {
				ex.printStackTrace();
				response = Response.status(Status.BAD_REQUEST).build();
			}
		}

		return response;
	}

}
