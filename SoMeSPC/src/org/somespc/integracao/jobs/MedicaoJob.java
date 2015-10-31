package org.somespc.integracao.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.plano_de_medicao.ItemPlanoMedicao;

public abstract class MedicaoJob implements Job {

	private ItemPlanoMedicao itemParaMedir;
	private Periodicidade periodicidade;

	@Override
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		this.executarMedicao(arg0);
	}

	public abstract void executarMedicao(JobExecutionContext jec) throws JobExecutionException;

	public ItemPlanoMedicao getItemParaMedir() {
		return itemParaMedir;
	}

	public void setItemParaMedir(ItemPlanoMedicao itemParaMedir) {
		this.itemParaMedir = itemParaMedir;
	}

	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Periodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}

}
