package org.somespc.integracao.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class MedicaoJob implements Job {

	@Override
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.executarMedicao(arg0);
	}

	public abstract void executarMedicao(JobExecutionContext context) throws JobExecutionException;

}
