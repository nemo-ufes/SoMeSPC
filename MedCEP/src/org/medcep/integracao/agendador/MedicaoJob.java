package org.medcep.integracao.agendador;

import java.sql.*;

import javax.persistence.*;

import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.model.medicao.planejamento.*;
import org.openxava.jpa.*;
import org.quartz.*;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution 
public class MedicaoJob implements Job
{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
	JobDataMap dataMap = context.getMergedJobDataMap();;
	String apelidoProjeto = dataMap.getString("apelidoProjeto");
	String nomePlano = dataMap.getString("nomePlano");
	String nomeMedida = dataMap.getString("nomeMedida");
	String entidadeMedida = dataMap.getString("entidadeMedida");
	String momento = dataMap.getString("momento");
	
	System.out.println(apelidoProjeto);
	System.out.println(nomePlano);
	System.out.println(nomeMedida);
	System.out.println(entidadeMedida);
	System.out.println(momento);

	EntityManager manager = XPersistence.createManager();

	try
	{
	    String query = String.format("SELECT p FROM PlanoDeMedicaoDoProjeto p WHERE p.nome='%s'", nomePlano);
	    TypedQuery<PlanoDeMedicaoDoProjeto> typedQuery = manager.createQuery(query, PlanoDeMedicaoDoProjeto.class);

	    PlanoDeMedicaoDoProjeto plano = typedQuery.getSingleResult();

	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");

	    String valorMedido = this.medir(nomeMedida, apelidoProjeto, integrator);

	    integrator.criarMedicaoMedCEP(plano, timestamp, nomeMedida, entidadeMedida, valorMedido, momento);
	    
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

    private String medir(String nomeMedida, String apelidoProjeto, TaigaIntegrator integrator)
    {
	String valorMedido = null;

	if (nomeMedida.equalsIgnoreCase("Pontos Alocados no Projeto"))
	{
	    EstadoProjeto estado = integrator.obterEstadoProjetoTaiga(apelidoProjeto);
	    valorMedido = String.valueOf(estado.getPontosAlocados());
	}
	else if (nomeMedida.equalsIgnoreCase("Pontos Definidos no Projeto"))
	{

	}

	return valorMedido;
    }
}
