package org.medcep.integracao.taiga.test;

import org.junit.*;
import org.medcep.integracao.agendador.*;
import org.quartz.*;

public class AgendadorTest
{

    @Test
    public void criarAgendamentos() throws SchedulerException, InterruptedException
    {
	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	Scheduler sched = schedFact.getScheduler();
	
	if (!sched.isStarted())
	sched.start();

	JobDetail job = null;
	Trigger trigger = null;

	for (int i = 0; i < 30; i++)
	{

	    String nomeJob = "Job de Teste";
	    String nomeGrupo = "Grupo de Teste";
	    String nomeTrigger = "Agendamento de Teste " + i;
	    boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));
	    boolean existeTrigger = sched.checkExists(new TriggerKey(nomeTrigger, nomeGrupo));

	    if (!existeJob)
	    {
		job = JobBuilder.newJob(HelloWorldJob.class)
			.withIdentity(nomeJob, nomeGrupo)
			.build();
	    }

	    if (!existeTrigger)
	    {
		trigger = TriggerBuilder.newTrigger()
			.withIdentity(nomeTrigger, nomeGrupo)
			.startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(10)
				.repeatForever())
			.build();
	    }

	    if (!existeJob && !existeTrigger)
	    {
		Thread.sleep(1000);
		sched.scheduleJob(job, trigger);
	    }
	}
	
    }

    @Test
    public void excluirAgendamentos() throws SchedulerException
    {
	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	Scheduler sched = schedFact.getScheduler();
	
	if (!sched.isStarted())
	sched.start();

	for (int i = 0; i < 30; i++)
	{

	    String nomeJob = "Job de Teste";
	    String nomeGrupo = "Grupo de Teste";
	    boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

	    if (existeJob)
	    {
		sched.deleteJob(new JobKey(nomeJob, nomeGrupo));
	    }
	}
	
    }

}
