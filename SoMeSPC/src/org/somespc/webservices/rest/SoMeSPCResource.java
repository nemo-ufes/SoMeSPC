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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.openxava.jpa.XPersistence;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.medicao.Medicao;
import org.somespc.webservices.rest.dto.EntidadeMensuravelDTO;
import org.somespc.webservices.rest.dto.MedicaoDTO;
import org.somespc.webservices.rest.dto.MedidaDTO;
import org.somespc.webservices.rest.dto.PeriodicidadeDTO;

/**
 * API REST para os recursos da SoMeSPC
 * 
 * @author Vinicius
 *
 */
@Path("")
public class SoMeSPCResource {
	@Context
	private UriInfo uriInfo;

	/**
	 * Obtem as periodicidades.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("Periodicidade")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterPeriodicidades() throws Exception {
		Response response;
		EntityManager manager = XPersistence.createManager();

		TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
		List<Periodicidade> result = query.getResultList();

		if (result == null)
			response = Response.status(Status.NOT_FOUND).build();
		else {
			List<PeriodicidadeDTO> listaDto = new ArrayList<PeriodicidadeDTO>();

			for (Periodicidade periodicidade : result) {
				PeriodicidadeDTO p = new PeriodicidadeDTO();
				p.setNome(periodicidade.getNome());
				listaDto.add(p);
			}

			response = Response.status(Status.OK).entity(listaDto).build();
		}

		manager.close();
		return response;
	}

	@Path("Medicao/Pagina")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterPaginasMedicoes(@QueryParam("tamanhoPagina") int tamanhoPagina,
			@QueryParam("medida") int idMedidaPlano, @QueryParam("entidade") int idEntidade) {
		Response response;
		EntityManager manager = XPersistence.createManager();

		Query queryTotal = manager.createQuery(
				String.format("SELECT COUNT(*) FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
						+ "AND m.entidadeMensuravel.id = %d", idMedidaPlano, idEntidade));

		Long total = (Long) queryTotal.getSingleResult();

		manager.close();
		int ultimaPagina = (int) ((total / tamanhoPagina) + 1);

		List<Integer> paginas = new ArrayList<Integer>();
		for (int i = 1; i < ultimaPagina; i++) {
			paginas.add(new Integer(i));
		}

		response = Response.status(Status.OK).entity(paginas).build();
		return response;
	}

	@Path("Medicao/Total")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterTotalMedicoes(@QueryParam("medida") int idMedidaPlano,
			@QueryParam("entidade") int idEntidade) {
		Response response;
		EntityManager manager = XPersistence.createManager();

		Query queryTotal = manager.createQuery(
				String.format("SELECT COUNT(*) FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
						+ "AND m.entidadeMensuravel.id = %d", idMedidaPlano, idEntidade));

		Long total = (Long) queryTotal.getSingleResult();

		manager.close();

		response = Response.status(Status.OK).entity(total).build();
		return response;
	}

	/**
	 * Obtem as medicoes.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("Medicao")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterMedicoes(@QueryParam("medida") int idMedidaPlano, @QueryParam("entidade") int idEntidade,
			@QueryParam("indiceAtual") int indiceAtual, @QueryParam("tamanhoPagina") int tamanhoPagina)
					throws Exception {
		Response response;

		if (idMedidaPlano == 0 || idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		} else {
			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medicao> query = manager
					.createQuery(String.format(
							"Select m FROM Medicao m " + "WHERE m.medidaPlanoDeMedicao.medida.id = %d "
									+ "AND m.entidadeMensuravel.id = %d ORDER BY m.data ASC",
							idMedidaPlano, idEntidade), Medicao.class)
					.setFirstResult((indiceAtual * tamanhoPagina) - tamanhoPagina).setMaxResults(tamanhoPagina);

			List<Medicao> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<MedicaoDTO> listaDto = new ArrayList<MedicaoDTO>();

				for (Medicao medicao : result) {
					MedicaoDTO dto = new MedicaoDTO();

					dto.setId(medicao.getId());
					dto.setData(medicao.getData());
					dto.setValorMedido(medicao.getValorMedido().getValorMedido());

					listaDto.add(dto);
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}

		return response;
	}

	/**
	 * Obtem as entidades com medições.
	 * 
	 * @return Entidades com medições.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterEntidadesComMedicoes() throws Exception {
		Response response;

		EntityManager manager = XPersistence.createManager();

		TypedQuery<EntidadeMensuravel> query = manager
				.createQuery("Select distinct(m.entidadeMensuravel) FROM Medicao m ", EntidadeMensuravel.class);
		List<EntidadeMensuravel> result = query.getResultList();

		if (result == null)
			response = Response.status(Status.NOT_FOUND).build();
		else {
			List<EntidadeMensuravelDTO> listaDto = new ArrayList<EntidadeMensuravelDTO>();

			for (EntidadeMensuravel em : result) {
				EntidadeMensuravelDTO dto = new EntidadeMensuravelDTO();

				dto.setId(em.getId());
				dto.setNome(em.getNome());

				listaDto.add(dto);
			}

			response = Response.status(Status.OK).entity(listaDto).build();
		}

		manager.close();

		return response;
	}

	/**
	 * Obtém as medidas das entidades.
	 * 
	 * @param id-
	 *            Id da entidade para buscar as medidas.
	 * @return Medidas das da entidade informada.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade/{id}/Medida")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterMedidasdasEntidadesComMedicoes(@PathParam("id") int id) throws Exception {
		Response response;

		if (id == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medida> query = manager.createQuery("Select distinct(m.medidaPlanoDeMedicao.medida) "
					+ "FROM Medicao m " + "WHERE m.entidadeMensuravel.id = " + id, Medida.class);
			List<Medida> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<MedidaDTO> listaDto = new ArrayList<MedidaDTO>();

				for (Medida med : result) {
					MedidaDTO dto = new MedidaDTO();

					dto.setId(med.getId());
					dto.setNome(med.getNome());

					listaDto.add(dto);
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

	/**
	 * Obtém os valores das medidas por entidade.
	 * 
	 * @param idEntidade
	 *            - Id da entidade para buscar os valores
	 * @param idMedida
	 *            - Id da medida para buscar os valores
	 * @return Valores da medida.
	 * @throws Exception
	 */
	@Path("Medicao/Entidade/{id}/Medida/{id2}/Valor")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterValorMedicoesPorMedidaEntidade(@QueryParam("id") int idEntidade,
			@QueryParam("id2") int idMedida) throws Exception {
		Response response;

		if (idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<String> query = manager.createQuery("Select m.valorMedido.valorMedido " + "FROM Medicao m "
					+ "WHERE m.entidadeMensuravel.id = " + idEntidade + " " + "AND m.medidaPlanoDeMedicao.medida.id = "
					+ idMedida + " ORDER BY m.id", String.class);
			List<String> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<Integer> listaDto = new ArrayList<Integer>();

				for (String valor : result) {
					listaDto.add(Integer.valueOf(valor));
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

	/**
	 * Obtêmc as Datas/Horas das medições pelo projeto e pela medida.
	 * 
	 * @param idProjeto
	 * @param idMedida
	 * @return
	 * @throws Exception
	 */
	@Path("Medicao/DataHora")
	@GET
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response obterDataHoraMedicoesPorMedidaEProjeto(@QueryParam("entidade") int idEntidade,
			@QueryParam("medida") int idMedida) throws Exception {
		Response response;

		if (idEntidade == 0) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		else {

			EntityManager manager = XPersistence.createManager();

			TypedQuery<Medicao> query = manager
					.createQuery(
							"Select m " + "FROM Medicao m " + "WHERE m.entidadeMensuravel.id = " + idEntidade + " "
									+ "AND m.medidaPlanoDeMedicao.medida.id = " + idMedida + " ORDER BY m.id",
							Medicao.class);
			List<Medicao> result = query.getResultList();

			if (result == null)
				response = Response.status(Status.NOT_FOUND).build();
			else {
				List<Date> listaDto = new ArrayList<Date>();

				for (Medicao med : result) {
					listaDto.add(med.getData());
				}

				response = Response.status(Status.OK).entity(listaDto).build();
			}

			manager.close();
		}
		return response;
	}

}
