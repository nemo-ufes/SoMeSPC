package org.medcep.webservices.rest;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.medcep.webservices.rest.dto.*;
import org.medcep.webservices.rest.exceptions.*;
import org.openxava.jpa.*;
import org.quartz.*;

@Path("Agendador")
public class MedCEPSchedulerResource
{

    @Path("/Job")
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

		dto.setNomeAgendador(obj[0] == null ? "" : obj[0].toString());
		dto.setNomeJob(obj[1] == null ? "" : obj[1].toString());
		dto.setGrupoJob(obj[2] == null ? "" : obj[2].toString());
		dto.setDescricao(obj[3] == null ? "" : obj[3].toString());
		dto.setClasseJob(obj[4] == null ? "" : obj[4].toString());
		dto.setIsDuravel(obj[5] == null ? false : (Boolean) obj[5]);
		dto.setIsNaoConcorrente(obj[6] == null ? false : (Boolean) obj[6]);
		dto.setIsAtualizacaoDados(obj[7] == null ? false : (Boolean) obj[7]);
		dto.setRequerRecuperacao(obj[8] == null ? false : (Boolean) obj[8]);

		listaDTO.add(dto);
	    }

	    response = Response.status(Status.OK).entity(listaDTO).build();
	}

	manager.close();
	return response;
    }

    @Path("/Agendamento")
    @GET
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response obterAgendamentos()
    {

	Response response;
	EntityManager manager = XPersistence.createManager();

	String sql = "SELECT * FROM qrtz_triggers";

	Query query = manager.createNativeQuery(sql);
	@SuppressWarnings("unchecked")
	List<Object[]> result = query.getResultList();

	if (result == null)
	    response = Response.status(Status.NOT_FOUND).build();
	else
	{
	    List<AgendamentoDTO> listaDTO = new ArrayList<AgendamentoDTO>();

	    for (Object[] obj : result)
	    {
		AgendamentoDTO dto = new AgendamentoDTO();

		dto.setNomeAgendador(obj[0] == null ? "" : obj[0].toString());
		dto.setNomeAgendamento(obj[1] == null ? "" : obj[1].toString());
		dto.setGrupoAgendamento(obj[2] == null ? "" : obj[2].toString());
		dto.setNomeJob(obj[3] == null ? "" : obj[3].toString());
		dto.setGrupoJob(obj[4] == null ? "" : obj[4].toString());
		dto.setDescricao(obj[5] == null ? "" : obj[5].toString());
		dto.setProximaExecucao(obj[6] == null ? new BigInteger("0") : (BigInteger) obj[6]);
		dto.setExecucaoAnterior(obj[7] == null ? new BigInteger("0") : (BigInteger) obj[7]);
		dto.setPrioridade(obj[8] == null ? 0 : (Integer) obj[8]);
		dto.setEstadoAgendamento(obj[9] == null ? "" : obj[9].toString());
		dto.setTipoAgendamento(obj[10] == null ? "" : obj[10].toString());
		dto.setInicioAgendamento(obj[11] == null ? new BigInteger("0") : (BigInteger) obj[11]);
		dto.setFimAgendamento(obj[12] == null ? new BigInteger("0") : (BigInteger) obj[12]);
		dto.setCalendario(obj[13] == null ? "" : obj[13].toString());
		dto.setInstrucaoErro(obj[14] == null ? 0 : (Short) obj[14]);

		listaDTO.add(dto);
	    }

	    response = Response.status(Status.OK).entity(listaDTO).build();
	}

	manager.close();
	return response;
    }

    @Path("/Agendamento/Comando")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public synchronized Response executarComandoAgendamento(ComandoDTO comando) throws SchedulerException
    {

	Response response = null;
	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	Scheduler sched = schedFact.getScheduler();

	if (!sched.isStarted())
	    sched.start();

	TriggerKey id = new TriggerKey(comando.getNomeAgendamento(), comando.getGrupoAgendamento());

	boolean existe = sched.checkExists(id);
	if (existe)
	{

	    if (comando.getComando().equalsIgnoreCase("Iniciar"))
	    {
		sched.resumeTrigger(id);
		response = Response.status(Status.OK).build();
	    }
	    else if (comando.getComando().equalsIgnoreCase("Pausar"))
	    {
		sched.pauseTrigger(id);
		response = Response.status(Status.OK).build();
	    }
	    else
	    {
		throw new ComandoInexistenteException(String.format("Comando %s desconhecido.", comando.getComando()));
	    }

	}
	else
	{
	    throw new ComandoInexistenteException(String.format("Agendamento %s não encontrado.", comando.getNomeAgendamento()));
	}

	return response;
    }

}
