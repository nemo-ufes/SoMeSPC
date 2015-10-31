package org.somespc.integracao.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.TreeItemPlanoMedicao;

public abstract class MedicaoJob implements Job {

	private TreeItemPlanoMedicao itemParaMedir;
	private Periodicidade periodicidade;

	@Override
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.executarMedicao(arg0);
	}

	public abstract void executarMedicao(JobExecutionContext jec) throws JobExecutionException;

	public TreeItemPlanoMedicao getItemParaMedir() {
		return itemParaMedir;
	}

	public void setItemParaMedir(TreeItemPlanoMedicao itemParaMedir) {
		this.itemParaMedir = itemParaMedir;
	}

	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Periodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}

}
