package org.somespc.integracao.jobs;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.openxava.jpa.XPersistence;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.taiga.TaigaIntegrator;
import org.somespc.integracao.taiga.model.EstadoProjeto;
import org.somespc.integracao.taiga.model.EstadoSprint;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;

@DisallowConcurrentExecution
public class TaigaMedicaoJob extends MedicaoJob {

	@Override
	public void executarMedicao(JobExecutionContext context) throws JobExecutionException {
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

				float valor = estado.getPontosFechados() / estado.getTotalPontos();
				valorMedido = String.valueOf(valor);
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Sprints Planejadas para o Projeto")) {

				valorMedido = String.valueOf(estado.getTotalMilestones());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Sprints Realizadas no Projeto")) {

				int sprintsConcluidas = 0;
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					if (estadoSprint.isConcluida()) {
						sprintsConcluidas++;
					}
				}

				valorMedido = String.valueOf(sprintsConcluidas);
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Sprints no Projeto")) {

				int sprintsConcluidas = 0;
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					if (estadoSprint.isConcluida()) {
						sprintsConcluidas++;
					}
				}

				int sprintsPlanejadas = estado.getTotalMilestones();

				float taxaConclusaoSprints = sprintsConcluidas / sprintsPlanejadas;

				valorMedido = String.valueOf(taxaConclusaoSprints);
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Planejadas para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTotalEstorias());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}

			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Concluídas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getEstoriasCompletadas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Estórias na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					float taxaConclusaoEstorias = estadoSprint.getEstoriasCompletadas() / estadoSprint.getTotalEstorias();
					valorMedido = String.valueOf(taxaConclusaoEstorias);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}			

			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Planejados para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					Map<String, Float> map = estadoSprint.getTotalPontos();

					float totalPontos = 0f;
					for (Float ponto : map.values()) {
						totalPontos += ponto;
					}

					valorMedido = String.valueOf(totalPontos);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Concluídos na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
		
					float totalPontosCompletados = 0f;
					for (Float ponto : estadoSprint.getPontosCompletados()) {
						totalPontosCompletados += ponto;
					}

					valorMedido = String.valueOf(totalPontosCompletados);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estórias na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
		
					Map<String, Float> map = estadoSprint.getTotalPontos();

					float totalPontos = 0f;
					for (Float ponto : map.values()) {
						totalPontos += ponto;
					}

					float totalPontosCompletados = 0f;
					for (Float ponto : estadoSprint.getPontosCompletados()) {
						totalPontosCompletados += ponto;
					}

					float taxaConclusaoEstorias = totalPontos / totalPontosCompletados;
					valorMedido = String.valueOf(taxaConclusaoEstorias);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	
								
			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Planejadas para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTotalTarefas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}									

			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Concluídas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTarefasCompletadas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Tarefas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					float taxaConclusaoTarefas = estadoSprint.getTarefasCompletadas() / estadoSprint.getTotalTarefas();
					valorMedido = String.valueOf(taxaConclusaoTarefas);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Concluídas para o Projeto")) {

				int estoriasConcluidasProjeto = 0;
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					estoriasConcluidasProjeto += estadoSprint.getEstoriasCompletadas();
				}

				valorMedido = String.valueOf(estoriasConcluidasProjeto);
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Média de Estórias Concluídas por Sprint do Projeto")) {

				int estoriasConcluidasProjeto = 0;
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					estoriasConcluidasProjeto += estadoSprint.getEstoriasCompletadas();
				}

				int sprintsConcluidas = 0;
				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					if (estadoSprint.isConcluida()) {
						sprintsConcluidas++;
					}
				}

				float mediaEstoriasConcluidas = estoriasConcluidasProjeto / sprintsConcluidas;
				valorMedido = String.valueOf(mediaEstoriasConcluidas);
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Velocidade da Equipe no Projeto")) {

				valorMedido = String.valueOf(estado.getVelocidade());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Doses de Iocaine na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getDosesIocaine());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}								

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Doses de Iocaine na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					float taxaDosesIocaine = estadoSprint.getDosesIocaine() / estadoSprint.getEstoriasCompletadas();
					valorMedido = String.valueOf(taxaDosesIocaine);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, sprint.getNome(), valorMedido);
				}						
			
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
