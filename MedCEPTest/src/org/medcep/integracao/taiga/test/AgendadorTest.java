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

	//Cria 30 agendamentos para o mesmo job.
	for (int i = 1; i <= 30; i++)
	{

	    String nomeJob = "Job de Teste ";
	    String nomeGrupo = "Grupo de Teste";
	    String nomeTrigger = "Agendamento de Teste " + Math.round(Math.random() * 10000);
	    boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

	    if (!existeJob)
	    {
		job = JobBuilder.newJob(HelloWorldJob.class)
			.withIdentity(nomeJob, nomeGrupo)
			.build();

		trigger = TriggerBuilder.newTrigger().forJob(job)
			.withIdentity(nomeTrigger, nomeGrupo)
			.startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds((int) Math.round(Math.random() * 60))
				.repeatForever())
			.build();

		sched.scheduleJob(job, trigger);
	    }
	    else
	    {
		job = sched.getJobDetail(new JobKey(nomeJob, nomeGrupo));

		trigger = TriggerBuilder.newTrigger().forJob(job)
			.withIdentity(nomeTrigger, nomeGrupo)
			.startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds((int) Math.round(Math.random() * 60))
				.repeatForever())
			.build();

		sched.scheduleJob(trigger);
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
