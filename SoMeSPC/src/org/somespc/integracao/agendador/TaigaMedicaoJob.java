package org.somespc.integracao.agendador;

import java.sql.*;
import java.util.*;

import javax.persistence.*;

import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.*;
import org.somespc.integracao.taiga.model.*;
import org.somespc.model.organizacao_de_software.*;
import org.somespc.model.plano_de_medicao.*;
import org.openxava.jpa.*;
import org.quartz.*;

@DisallowConcurrentExecution
public class TaigaMedicaoJob implements Job {

	@Override
	public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
		EntityManager manager = XPersistence.createManager();

		try {
			JobDataMap dataMap = context.getMergedJobDataMap();

			String urlTaiga = dataMap.getString("urlTaiga");
			String usuarioTaiga = dataMap.getString("usuarioTaiga");
			String senhaTaiga = dataMap.getString("senhaTaiga");
			String apelidoProjeto = dataMap.getString("apelidoProjeto");
			String nomePlano = dataMap.getString("nomePlano");
			String nomeMedida = dataMap.getString("nomeMedida");
			String entidadeMedida = dataMap.getString("entidadeMedida");

			String query = String.format("SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='%s'", nomePlano);
			TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query, PlanoDeMedicaoDoProjeto.class);
			PlanoDeMedicaoDoProjeto plano = typedQuery.getSingleResult();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			TaigaIntegrator integrator = new TaigaIntegrator(urlTaiga, usuarioTaiga, senhaTaiga);

			String valorMedido = null;
			EstadoProjeto estado = integrator.obterEstadoProjetoTaiga(apelidoProjeto);

			if (nomeMedida.equalsIgnoreCase("Pontos de Estória Planejados para o Projeto")) {

				valorMedido = String.valueOf(estado.getTotalPontos());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Concluídos no Projeto")) {

				valorMedido = String.valueOf(estado.getPontosFechados());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estória no Projeto")) {

				//TODO Pontos Concluidos / Planejados

			} else if (nomeMedida.equalsIgnoreCase("Número de Sprints Planejadas para o Projeto")) {

				valorMedido = String.valueOf(estado.getTotalMilestones());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Sprints Realizadas no Projeto")) {

				//TODO Milestone detail -> closed = true
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Sprints no Projeto")) {

				//TODO Sprints Concluidas / Planejadas	
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Planejadas para a Sprint")) {

				String apelidoSprint = dataMap.getString("apelidoSprint");
				EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
				valorMedido = String.valueOf(estadoSprint.getTotalEstorias());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Concluídas na Sprint")) {

				String apelidoSprint = dataMap.getString("apelidoSprint");
				EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
				valorMedido = String.valueOf(estadoSprint.getEstoriasCompletadas());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Estórias na Sprint")) {

				//TODO	Estorias Concluidas / Planejadas
				
			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Planejados para a Sprint")) {

				//TODO	 Milestone detail - total_points
				
			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Concluídos na Sprint")) {

				//TODO	Milestone detail - completed_points
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estórias na Sprint")) {

				//TODO	Pontos da Sprint Concluidos / Planejados
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Planejadas para a Sprint")) {

				String apelidoSprint = dataMap.getString("apelidoSprint");
				EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
				valorMedido = String.valueOf(estadoSprint.getTotalTarefas());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Concluídas na Sprint")) {

				String apelidoSprint = dataMap.getString("apelidoSprint");
				EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, apelidoSprint);
				valorMedido = String.valueOf(estadoSprint.getTarefasCompletadas());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Tarefas na Sprint")) {

				//TODO	Tarefas Concluidas / Planejadas

			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Concluídas para o Projeto")) {

				//TODO	EstadoSprint.completed_userstories
				
			} else if (nomeMedida.equalsIgnoreCase("Média de Estórias Concluídas por Sprint do Projeto")) {

				//TODO	(Soma de EstadoSprint.completed_userstories) / (Soma de EstadoSprint.isClosed = true)

			} else if (nomeMedida.equalsIgnoreCase("Velocidade da Equipe no Projeto")) {

				valorMedido = String.valueOf(estado.getVelocidade());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Doses de Iocaine na Sprint")) {

				//TODO EstadoSprint -> iocaine_doses

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Doses de Iocaine na Sprint")) {

				//TODO EstadoSprint -> iocaine_doses / completed_userstories

			} else {
				throw new Exception(String.format("Medida %s não encontrada.", nomeMedida));
			}

			System.out.println(String.format("Job %s (%s) executado com sucesso.",
					context.getTrigger().getKey().getName(), context.getTrigger().getKey().getGroup()));
		} catch (Exception ex) {
			System.err.println(
					String.format("Erro ao executar o job %s (%s): %s ", context.getTrigger().getKey().getName(),
							context.getTrigger().getKey().getGroup(), ex.getMessage()));

			ex.printStackTrace();
		} finally {
			manager.close();
		}
	}
}
