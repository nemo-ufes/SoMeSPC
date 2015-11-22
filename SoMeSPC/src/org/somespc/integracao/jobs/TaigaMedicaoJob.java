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
import org.somespc.integracao.taiga.model.Estoria;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.integracao.taiga.model.Sprint;
import org.somespc.integracao.taiga.model.Tarefa;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;

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
			String nomeMedida = dataMap.getString("nomeMedida").replace("ME - " , "");
			String entidadeMedida = dataMap.getString("entidadeMedida");

			String query = String.format("SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='%s'", nomePlano);
			TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query, PlanoDeMedicaoDoProjeto.class);
			PlanoDeMedicaoDoProjeto plano = typedQuery.getSingleResult();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			TaigaIntegrator integrator = new TaigaIntegrator(urlTaiga, usuarioTaiga, senhaTaiga);

			String valorMedido = null;
			EstadoProjeto estado = integrator.obterEstadoProjetoTaiga(apelidoProjeto);
			org.somespc.integracao.taiga.model.Projeto projeto = integrator.obterProjetoTaiga(apelidoProjeto);
			
			List<Sprint> sprintsCriar = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

			for (Sprint sprint : sprintsCriar) {
				integrator.criarEntidadeMensuravelSprintSoMeSPC(sprint, projeto.getNome());
			}

			if (nomeMedida.equalsIgnoreCase("Pontos de Estória Planejados para o Projeto")) {

				valorMedido = String.valueOf(estado.getTotalPontos());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Concluídos no Projeto")) {

				valorMedido = String.valueOf(estado.getPontosFechados());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estória no Projeto")) {

				
				if (estado.getTotalPontos() > 0){
					double valor = estado.getPontosFechados() / estado.getTotalPontos();
					valorMedido = String.valueOf(valor);	
				} else {
					valorMedido = String.valueOf(0);
				}
				
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

				if (sprintsPlanejadas > 0){
					double taxaConclusaoSprints = sprintsConcluidas / sprintsPlanejadas;
					valorMedido = String.valueOf(taxaConclusaoSprints);	
				} else {
					valorMedido = String.valueOf(0);
				}				
				
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Planejadas para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTotalEstorias());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}

			} else if (nomeMedida.equalsIgnoreCase("Número de Estórias Concluídas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getEstoriasCompletadas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}	

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Estórias na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					
					if (estadoSprint.getTotalEstorias() > 0){
						double taxaConclusaoEstorias = estadoSprint.getEstoriasCompletadas() / estadoSprint.getTotalEstorias();
						valorMedido = String.valueOf(taxaConclusaoEstorias);
					} else {
						valorMedido = String.valueOf(0);
					}	
					
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}			

			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Planejados para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					Map<String, Double> map = estadoSprint.getTotalPontos();

					double totalPontos = 0f;
					for (double ponto : map.values()) {
						totalPontos += ponto;
					}

					valorMedido = String.valueOf(totalPontos);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Pontos de Estória Concluídos na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
		
					double totalPontosCompletados = 0f;
					for (double ponto : estadoSprint.getPontosCompletados()) {
						totalPontosCompletados += ponto;
					}

					valorMedido = String.valueOf(totalPontosCompletados);
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}	

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estórias na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
		
					Map<String, Double> map = estadoSprint.getTotalPontos();

					double totalPontos = 0f;
					for (double ponto : map.values()) {
						totalPontos += ponto;
					}

					double totalPontosCompletados = 0f;
					for (double ponto : estadoSprint.getPontosCompletados()) {
						totalPontosCompletados += ponto;
					}

					if (totalPontosCompletados > 0){
						double taxaConclusaoEstorias = totalPontos / totalPontosCompletados;
						valorMedido = String.valueOf(taxaConclusaoEstorias);
					} else {
						valorMedido = String.valueOf(0);
					}	
					
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}	
								
			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Planejadas para a Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTotalTarefas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}									

			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Concluídas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());					
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getTarefasCompletadas());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Tarefas na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					
					if (estadoSprint.getTotalTarefas() > 0){
						double taxaConclusaoTarefas = estadoSprint.getTarefasCompletadas() / estadoSprint.getTotalTarefas();
						valorMedido = String.valueOf(taxaConclusaoTarefas);
					} else {
						valorMedido = String.valueOf(0);
					}	
					
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
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
				
				if (estado.getTotalMilestones() > 0){
					double mediaEstoriasConcluidasPorSprint = estoriasConcluidasProjeto / estado.getTotalMilestones();
					valorMedido = String.valueOf(mediaEstoriasConcluidasPorSprint);
				} else {
					valorMedido = String.valueOf(0);
				}	
							
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);		

			} else if (nomeMedida.equalsIgnoreCase("Velocidade da Equipe no Projeto")) {

				valorMedido = String.valueOf(estado.getVelocidade());
				SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);

			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Atribuídas a Membro do Projeto")) {
				
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
				List<Tarefa> tarefas = integrator.obterTarefasDoProjeto(apelidoProjeto);
				
				for(Membro membro : membros) {		
							
					int totalTarefasMembro = 0;
					
					for(Tarefa tarefa : tarefas){						
						if (tarefa.getIdDono() == membro.getIdUsuario() && !tarefa.isFechada()){
							totalTarefasMembro++;
						}				
					}
					
					valorMedido = String.valueOf(totalTarefasMembro);
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Tarefas Concluídas pelo Membro do Projeto")) {
				
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
				List<Tarefa> tarefas = integrator.obterTarefasDoProjeto(apelidoProjeto);
				
				for(Membro membro : membros) {		
							
					int totalTarefasConcluidasMembro = 0;
					
					for(Tarefa tarefa : tarefas){						
						if (tarefa.getIdDono() == membro.getIdUsuario() && tarefa.isFechada()){
							totalTarefasConcluidasMembro++;
						}				
					}
					
					valorMedido = String.valueOf(totalTarefasConcluidasMembro);
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Tarefas de Membro do Projeto")) {
				
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
				List<Tarefa> tarefas = integrator.obterTarefasDoProjeto(apelidoProjeto);
				
				for(Membro membro : membros) {		
							
					int totalTarefasConcluidasMembro = 0;
					int totalTarefasMembro = 0;
					
					for(Tarefa tarefa : tarefas){					
						if (tarefa.getIdDono() == membro.getIdUsuario() && !tarefa.isFechada()){
							totalTarefasMembro++;
						}
						
						if (tarefa.getIdDono() == membro.getIdUsuario() && tarefa.isFechada()){
							totalTarefasConcluidasMembro++;
						}				
					}
					
					if (totalTarefasConcluidasMembro > 0){
						float taxaConclusaoTarefas = totalTarefasMembro / totalTarefasConcluidasMembro;						
						valorMedido = String.valueOf(taxaConclusaoTarefas);
					} else {
						valorMedido = String.valueOf(0);
					}	
										
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}				
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Pontos de Estória Atribuídos a Membro do Projeto")) {
				
				//Obtem todas as estórias, tanto do backlog do projeto quanto das sprints.				
				List<Estoria> estorias = integrator.obterEstoriasDoProjectBacklogTaiga(apelidoProjeto);
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);
				
				for(Sprint sprint : sprints) {					
					List<Estoria> estoriasSprint = integrator.obterEstoriasDaSprintBacklogTaiga(apelidoProjeto, sprint.getApelido());					
					estorias.addAll(estoriasSprint);					
				}
				
				//Calcula a quantidade de estórias do membro.
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
								
				for(Membro membro : membros){
					
					double pontosEstoriaMembro = 0;
					
					for(Estoria estoria : estorias) {
						if (estoria.getIdDono() == membro.getIdUsuario() && !estoria.isFechada() && estoria.getTotalPontos() != null){
							pontosEstoriaMembro += estoria.getTotalPontos();
						}	
					}	
					
					valorMedido = String.valueOf(pontosEstoriaMembro);
					
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}						
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Pontos de Estória Concluídos pelo Membro do Projeto")) {
				
				//Obtem todas as estórias, tanto do backlog do projeto quanto das sprints.				
				List<Estoria> estorias = integrator.obterEstoriasDoProjectBacklogTaiga(apelidoProjeto);
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);
				
				for(Sprint sprint : sprints) {					
					List<Estoria> estoriasSprint = integrator.obterEstoriasDaSprintBacklogTaiga(apelidoProjeto, sprint.getApelido());					
					estorias.addAll(estoriasSprint);					
				}
				
				//Calcula a quantidade de estórias do membro.
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
								
				for(Membro membro : membros){
					
					double pontosEstoriaConcluidosMembro = 0;
					
					for(Estoria estoria : estorias) {
						if (estoria.getIdDono() == membro.getIdUsuario() && estoria.isFechada() && estoria.getTotalPontos() != null){
							pontosEstoriaConcluidosMembro += estoria.getTotalPontos();
						}	
					}	
					
					valorMedido = String.valueOf(pontosEstoriaConcluidosMembro);
					
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}	
				
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Conclusão de Pontos de Estória de Membro do Projeto")) {
				
				//Obtem todas as estórias, tanto do backlog do projeto quanto das sprints.				
				List<Estoria> estorias = integrator.obterEstoriasDoProjectBacklogTaiga(apelidoProjeto);
				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);
				
				for(Sprint sprint : sprints) {					
					List<Estoria> estoriasSprint = integrator.obterEstoriasDaSprintBacklogTaiga(apelidoProjeto, sprint.getApelido());					
					estorias.addAll(estoriasSprint);					
				}
				
				//Calcula a quantidade de estórias do membro.
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
								
				for(Membro membro : membros){
					
					double pontosEstoriaMembro = 0;
					double pontosEstoriaConcluidosMembro = 0;
					
					for(Estoria estoria : estorias) {
						
						if (estoria.getIdDono() == membro.getIdUsuario() && !estoria.isFechada() && estoria.getTotalPontos() != null){							
							pontosEstoriaMembro += estoria.getTotalPontos();
						}	
						
						if (estoria.getIdDono() == membro.getIdUsuario() && estoria.isFechada() && estoria.getTotalPontos() != null){
							pontosEstoriaConcluidosMembro += estoria.getTotalPontos();
						}	
					}	
					
					if (pontosEstoriaConcluidosMembro > 0){
						double taxaConclusaoPontosMembro = pontosEstoriaMembro / pontosEstoriaConcluidosMembro;						
						valorMedido = String.valueOf(taxaConclusaoPontosMembro);
					} else {
						valorMedido = String.valueOf(0);
					}					
										
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}	
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Doses de Iocaine Atribuídas a Membro do Projeto")) {
				
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
				List<Tarefa> tarefas = integrator.obterTarefasDoProjeto(apelidoProjeto);
				
				for(Membro membro : membros) {		
							
					int totalTarefasIocaineMembro = 0;
					
					for(Tarefa tarefa : tarefas){					
						if (tarefa.getIdDono() == membro.getIdUsuario() && tarefa.isIocaine()){
							totalTarefasIocaineMembro++;
						}							
					}
										
					valorMedido = String.valueOf(totalTarefasIocaineMembro);
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}		
				
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Doses de Iocaine de Membro do Projeto")) {
				
				List<Membro> membros = integrator.obterMembrosDoProjetoTaiga(apelidoProjeto);
				List<Tarefa> tarefas = integrator.obterTarefasDoProjeto(apelidoProjeto);
				
				for(Membro membro : membros) {		
							
					int totalTarefasIocaineMembro = 0;
					int totalTarefasConcluidasMembro = 0;
					
					for(Tarefa tarefa : tarefas){					
						if (tarefa.getIdDono() == membro.getIdUsuario() && tarefa.isIocaine()){
							totalTarefasIocaineMembro++;
						}			
						
						if (tarefa.getIdDono() == membro.getIdUsuario() && tarefa.isFechada()){
							totalTarefasConcluidasMembro++;
						}	
					}
					
					if (totalTarefasConcluidasMembro > 0){
						float taxaDosesIocaineMembro = totalTarefasIocaineMembro / totalTarefasConcluidasMembro;
						valorMedido = String.valueOf(taxaDosesIocaineMembro);
					} else {
						valorMedido = String.valueOf(0);
					}				
					
					try {
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, membro.getNome(), valorMedido);	
					} catch (Exception ex) {
						String mensagem = String.format("Membro %s não encontrado no projeto %s.", membro.getNome(), projeto.getNome());
						System.err.println(mensagem);
					}
				}		
				
				
			} else if (nomeMedida.equalsIgnoreCase("Número de Doses de Iocaine na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					valorMedido = String.valueOf(estadoSprint.getDosesIocaine());
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
				}								

			} else if (nomeMedida.equalsIgnoreCase("Taxa de Doses de Iocaine na Sprint")) {

				List<Sprint> sprints = integrator.obterSprintsDoProjetoTaiga(apelidoProjeto);

				for (Sprint sprint : sprints) {
					String nome = String.format("Sprint %s (%s)", sprint.getNome(), projeto.getNome());	
					EstadoSprint estadoSprint = integrator.obterEstadoSprintTaiga(apelidoProjeto, sprint.getApelido());
					
					if (estadoSprint.getEstoriasCompletadas() > 0){
						double taxaDosesIocaine = estadoSprint.getDosesIocaine() / estadoSprint.getEstoriasCompletadas();
						valorMedido = String.valueOf(taxaDosesIocaine);
					} else {
						valorMedido = String.valueOf(0);
					}	
					
					SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, nome, valorMedido);
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
