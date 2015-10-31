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

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.Projeto;
import org.somespc.webservices.rest.dto.TaigaLoginDTO;

@Path("TaigaIntegrator")
public class TaigaIntegratorResource {
	@Path("/Projetos")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterProjetos(TaigaLoginDTO login) throws Exception {

		Response response;

		if (login == null || login.getUrl().isEmpty() || login.getUsuario().isEmpty() || login.getSenha().isEmpty()) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else {
			TaigaIntegrator integrator = new TaigaIntegrator(login.getUrl(), login.getUsuario(), login.getSenha());
			try {
				List<Projeto> projetos = integrator.obterProjetosTaiga();
				response = Response.ok().entity(projetos).build();
			} catch (Exception ex) {
				ex.printStackTrace();
				response = Response.status(Status.BAD_REQUEST).build();
			}
		}

		return response;
	}
}
