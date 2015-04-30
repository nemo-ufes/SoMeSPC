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

	    String nomeJob = "Job de Teste";
	    String nomeGrupo = "Grupo de Teste";
	    String nomeTrigger = "Agendamento de Teste " + Math.round(Math.random() * 10000);
	    boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

	    int segundos = (int) Math.round(Math.random() * 60);

	    if (segundos == 0)
		segundos = 1;

	    if (!existeJob)
	    {
		job = JobBuilder.newJob(MedicaoJob.class)
			.withIdentity(nomeJob, nomeGrupo)
			.build();

		trigger = TriggerBuilder.newTrigger().forJob(job)
			.withIdentity(nomeTrigger, nomeGrupo)
			.startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(segundos)
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
				.withIntervalInSeconds(segundos)
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

	String nomeJob = "Medição Job";
	String nomeGrupo = "Plano de Medição do Projeto Sincap (Wizard) - 27/04/2015 00:03:46";
	boolean existeJob = sched.checkExists(new JobKey(nomeJob, nomeGrupo));

	if (existeJob)
	{	    
	    sched.deleteJob(new JobKey(nomeJob, nomeGrupo));
	}

    }
    
    @Test
    public void excluirTudo() throws SchedulerException
    {
	SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	Scheduler sched = schedFact.getScheduler();
	sched.clear();
    }

}
