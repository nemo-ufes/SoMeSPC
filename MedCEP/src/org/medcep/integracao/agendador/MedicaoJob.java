package org.medcep.integracao.agendador;

import java.sql.*;
import java.util.*;

import javax.persistence.*;

import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.openxava.jpa.*;
import org.quartz.*;

@DisallowConcurrentExecution
public class MedicaoJob implements Job
{

    @Override
    public synchronized void execute(JobExecutionContext context) throws JobExecutionException
    {
	EntityManager manager = XPersistence.createManager();

	try
	{
	    JobDataMap dataMap = context.getMergedJobDataMap();

	    String urlTaiga = dataMap.getString("urlTaiga");
	    String usuarioTaiga = dataMap.getString("usuarioTaiga");
	    String senhaTaiga = dataMap.getString("senhaTaiga");
	    String apelidoProjeto = dataMap.getString("apelidoProjeto");
	    String nomePlano = dataMap.getString("nomePlano");
	    String nomeMedida = dataMap.getString("nomeMedida");
	    String entidadeMedida = dataMap.getString("entidadeMedida");
	    String momento = dataMap.getString("momento");

	    String query = String.format("SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='%s'", nomePlano);
	    TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query, PlanoDeMedicaoDoProjeto.class);
	    PlanoDeMedicaoDoProjeto plano = typedQuery.getSingleResult();

	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    TaigaIntegrator integrator = new TaigaIntegrator(urlTaiga, usuarioTaiga, senhaTaiga);

	    String valorMedido = null;
	    EstadoProjeto estado = integrator.obterEstadoProjetoTaiga(apelidoProjeto);

	    if (nomeMedida.equalsIgnoreCase("Pontos Alocados no Projeto"))
	    {
		valorMedido = String.valueOf(estado.getPontosAlocados());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Definidos no Projeto"))
	    {
		valorMedido = String.valueOf(estado.getPontosDefinidos());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Fechados no Projeto"))
	    {
		valorMedido = String.valueOf(estado.getPontosFechados());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Total de Sprints do Projeto"))
	    {
		valorMedido = String.valueOf(estado.getTotalSprints());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Total de Pontos do Projeto"))
	    {
		valorMedido = String.valueOf(estado.getTotalPontos());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Velocidade do Projeto"))
	    {
		valorMedido = String.valueOf(estado.getVelocidade());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Alocados por Papel no Projeto"))
	    {
		//Meda cada alocação.
		Map<String, Float> pontos = estado.getPontosAlocadosPorPapel();

		for (Map.Entry<String, Float> entry : pontos.entrySet())
		{
		    Membro membro = integrator.obterMembroTaiga(Integer.parseInt(entry.getKey()));
		    
		    if (membro == null)
		    {
			membro = new Membro();
			membro.setNome("Panthom");
			membro.setPapel("Fantasma");

			for (Equipe equipe : plano.getProjeto().getEquipe())
			{
			    if (equipe.getNome().equalsIgnoreCase("Equipe " + plano.getProjeto().getNome()))
			    {
				integrator.adicionarMembroEmEquipeMedCEP(equipe, membro);
				break;
			    }
			}
		    }
		    
		    String nomeAlocacao = String.format("%s %s em Equipe %s", membro.getPapel(), membro.getNome(), plano.getProjeto().getNome());
		    valorMedido = String.valueOf(entry.getValue());
		    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, nomeAlocacao, valorMedido, momento);
		}
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Definidos por Papel no Projeto"))
	    {
		//Meda cada alocação.
		Map<String, Float> pontos = estado.getPontosDefinidosPorPapel();

		for (Map.Entry<String, Float> entry : pontos.entrySet())
		{
		    Membro membro = integrator.obterMembroTaiga(Integer.parseInt(entry.getKey()));

		    if (membro == null)
		    {
			membro = new Membro();
			membro.setNome("Panthom");
			membro.setPapel("Fantasma");

			for (Equipe equipe : plano.getProjeto().getEquipe())
			{
			    if (equipe.getNome().equalsIgnoreCase("Equipe " + plano.getProjeto().getNome()))
			    {
				integrator.adicionarMembroEmEquipeMedCEP(equipe, membro);
				break;
			    }
			}
		    }

		    String nomeAlocacao = String.format("%s %s em Equipe %s", membro.getPapel(), membro.getNome(), plano.getProjeto().getNome());
		    valorMedido = String.valueOf(entry.getValue());
		    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, nomeAlocacao, valorMedido, momento);
		}
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Fechados por Papel no Projeto"))
	    {
		//Meda cada alocação.
		Map<String, Float> pontos = estado.getPontosFechadosPorPapel();

		for (Map.Entry<String, Float> entry : pontos.entrySet())
		{
		    Membro membro = integrator.obterMembroTaiga(Integer.parseInt(entry.getKey()));

		    if (membro == null)
		    {
			membro = new Membro();
			membro.setNome("Panthom");
			membro.setPapel("Fantasma");

			for (Equipe equipe : plano.getProjeto().getEquipe())
			{
			    if (equipe.getNome().equalsIgnoreCase("Equipe " + plano.getProjeto().getNome()))
			    {
				integrator.adicionarMembroEmEquipeMedCEP(equipe, membro);
				break;
			    }
			}
		    }

		    String nomeAlocacao = String.format("%s %s em Equipe %s", membro.getPapel(), membro.getNome(), plano.getProjeto().getNome());
		    valorMedido = String.valueOf(entry.getValue());
		    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, nomeAlocacao, valorMedido, momento);
		}
	    }
	    else if (nomeMedida.equalsIgnoreCase("Doses de Iocaine da Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		valorMedido = String.valueOf(estadoSprint.getDosesIocaine());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Estórias Completadas na Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		valorMedido = String.valueOf(estadoSprint.getEstoriasCompletadas());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos Completados na Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		List<Integer> pontos = estadoSprint.getPontosCompletados();

		for (Integer ponto : pontos)
		{
		    valorMedido = String.valueOf(ponto);
		    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
		}
	    }
	    else if (nomeMedida.equalsIgnoreCase("Tarefas Completadas na Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		valorMedido = String.valueOf(estadoSprint.getTarefasCompletadas());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Total de Estórias da Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		valorMedido = String.valueOf(estadoSprint.getTotalEstorias());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Total de Pontos da Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		Map<String, Integer> pontos = estadoSprint.getTotalPontos();

		for (Map.Entry<String, Integer> entry : pontos.entrySet())
		{
		    valorMedido = String.valueOf(entry.getValue());
		    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
		}
	    }
	    else if (nomeMedida.equalsIgnoreCase("Total de Tarefas da Sprint"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");
		EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
		valorMedido = String.valueOf(estadoSprint.getTotalTarefas());
		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else if (nomeMedida.equalsIgnoreCase("Pontos da Estória"))
	    {
		String apelidoSprint = dataMap.getString("apelidoSprint");

		List<Estoria> estorias = integrator.obterEstoriasDaSprintBacklogTaiga(apelidoProjeto, apelidoSprint);
		for (Estoria estoria : estorias)
		{
		    if (estoria.getTitulo().equalsIgnoreCase(entidadeMedida))
		    {
			valorMedido = String.valueOf(estoria.getTotalPontos());
			break;
		    }
		}

		integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    }
	    else
	    {
		throw new Exception(String.format("Medida %s não encontrada.", nomeMedida));
	    }

	    System.out.println(String.format("Job %s (%s) executado com sucesso.",
		    context.getTrigger().getKey().getName(),
		    context.getTrigger().getKey().getGroup()));
	}
	catch (Exception ex)
	{
	    System.err.println(String.format("Erro ao executar o job %s (%s): %s ",
		    context.getTrigger().getKey().getName(),
		    context.getTrigger().getKey().getGroup(),
		    ex.getMessage()));

	    ex.printStackTrace();
	}
	finally
	{
	    manager.close();
	}
    }
}
