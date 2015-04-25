package org.medcep.interacao.agendador;

import java.sql.*;
import java.text.*;

import org.quartz.*;

public class HelloWorldJob implements Job
{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());

	System.out.println("Job executado em "  + dataHora);

    }

}
