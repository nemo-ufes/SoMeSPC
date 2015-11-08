package org.somespc.integracao.jobs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.openxava.jpa.XPersistence;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.somespc.integracao.SoMeSPCIntegrator;
import org.somespc.integracao.sonarqube.SonarQubeIntegrator;
import org.somespc.integracao.sonarqube.model.Medida;
import org.somespc.integracao.sonarqube.model.Metrica;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;

public class SonarQubeMedicaoJob extends MedicaoJob {

	@Override
	public void executarMedicao(JobExecutionContext context) throws JobExecutionException {
		EntityManager manager = XPersistence.createManager();

		try {
			JobDataMap dataMap = context.getMergedJobDataMap();

			String urlSonar = dataMap.getString("urlSonar");
			String chaveRecurso = dataMap.getString("chaveRecurso");
			String nomePlano = dataMap.getString("nomePlano");
			String nomeMedida = dataMap.getString("nomeMedida").replace("ME - " , "");
			String entidadeMedida = dataMap.getString("entidadeMedida");

			String query = String.format("SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='%s'", nomePlano);
			TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query, PlanoDeMedicaoDoProjeto.class);
			PlanoDeMedicaoDoProjeto plano = typedQuery.getSingleResult();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SonarQubeIntegrator integrator = new SonarQubeIntegrator(urlSonar);

			String valorMedido = null;
			Recurso recurso = integrator.obterRecurso(chaveRecurso);
			
			Metrica functionComplexity = new Metrica();
			functionComplexity.setChave("function_complexity");
					
			Metrica duplicatedLinesDensity = new Metrica();
			duplicatedLinesDensity.setChave("duplicated_lines_density");
			
			Metrica sqaleDebtRatio = new Metrica();
			sqaleDebtRatio.setChave("sqale_debt_ratio");
			
			ArrayList<Metrica> metricas = new ArrayList<Metrica>();
			metricas.add(functionComplexity);
			metricas.add(duplicatedLinesDensity);
			metricas.add(sqaleDebtRatio);
			
			List<Medida> medidas = integrator.obterMedidasDoRecurso(metricas, recurso);

			if (nomeMedida.equalsIgnoreCase("Média da Complexidade Ciclomática por Método")) {

				for(Medida medida : medidas) {					
					if (medida.getChave().equalsIgnoreCase("function_complexity")){
						valorMedido = String.valueOf(medida.getValor());
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
					}					
				}
				
			} else if (nomeMedida.equalsIgnoreCase("Taxa de Duplicação de Código")) {
			
				for(Medida medida : medidas) {					
					if (medida.getChave().equalsIgnoreCase("duplicated_lines_density")){
						valorMedido = String.valueOf(medida.getValor());
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
					}					
				}
				
			} else if (nomeMedida.equalsIgnoreCase("Percentual da Dívida Técnica")) {
								
				for(Medida medida : medidas) {					
					if (medida.getChave().equalsIgnoreCase("sqale_debt_ratio")){
						valorMedido = String.valueOf(medida.getValor());
						SoMeSPCIntegrator.criarMedicao(plano, timestamp, nomeMedida, entidadeMedida, valorMedido);
					}					
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
