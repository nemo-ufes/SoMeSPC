package org.medcep.webservices.rest;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.webservices.rest.dto.*;
import org.openxava.jpa.*;

@Path("Agendador")
public class MedCEPSchedulerResource
{

    @Path("/Jobs")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterJobs()
    {
	Response response;
	EntityManager manager = XPersistence.createManager();

	String sql = "SELECT * FROM qrtz_job_details";

	Query query = manager.createNativeQuery(sql);
	@SuppressWarnings("unchecked")
	List<Object[]> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<JobDTO> listaDTO = new ArrayList<JobDTO>();

	    for (Object[] obj : result)
	    {
		JobDTO dto = new JobDTO();
		
		dto.setSchedulerName(obj[0] == null ? "" : obj[0].toString());
		dto.setJobName(obj[1] == null ? "" : obj[1].toString());
		dto.setJobGroup(obj[2] == null ? "" : obj[2].toString());
		dto.setDescription(obj[3] == null ? "" : obj[3].toString());
		dto.setJobClassName(obj[4] == null ? "" : obj[4].toString());
		dto.setIsDurable(obj[5] == null ? false : (Boolean) obj[5]);
		dto.setIsNonConcurrent(obj[6] == null ? false : (Boolean) obj[6]);
		dto.setIsUpdateData(obj[7] == null ? false : (Boolean) obj[7]);
		dto.setRequestsRecovery(obj[8] == null ? false : (Boolean) obj[8]);

		listaDTO.add(dto);
	    }

	    response = Response.status(Status.OK).entity(listaDTO).build();
	}

	manager.close();
	return response;
    }

}
