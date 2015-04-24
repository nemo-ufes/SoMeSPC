package org.medcep.interacao.agendador;

import org.quartz.*;

public class HelloWorldJob implements Job
{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
	System.out.println("Job funcionando!");

    }

}
