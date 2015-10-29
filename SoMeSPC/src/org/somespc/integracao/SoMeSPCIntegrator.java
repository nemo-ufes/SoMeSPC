package org.somespc.integracao;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.openxava.jpa.XPersistence;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.medicao.ContextoDeMedicao;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.medicao.ValorNumerico;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;

public class SoMeSPCIntegrator {

	/**
	 * Cria uma medição.
	 * 
	 * @throws Exception
	 */
	public static synchronized void criarMedicaoSoMeSPC(PlanoDeMedicaoDoProjeto plano, Timestamp data, String nomeMedida,
			String entidadeMedida, String valorMedido) throws Exception {
		EntityManager manager = XPersistence.createManager();
		Medicao medicao = new Medicao();

		medicao.setData(data);
		medicao.setPlanoDeMedicao(plano);

		for (MedidaPlanoDeMedicao med : plano.getMedidaPlanoDeMedicao()) {
			if (med.getMedida().getNome().equalsIgnoreCase(nomeMedida)) {
				medicao.setMedidaPlanoDeMedicao(med);
			}
		}

		String queryEntidade = String.format("SELECT p FROM EntidadeMensuravel p WHERE p.nome='%s'", entidadeMedida);
		TypedQuery<EntidadeMensuravel> typedQueryEntidade = manager.createQuery(queryEntidade,
				EntidadeMensuravel.class);
		medicao.setEntidadeMensuravel(typedQueryEntidade.getSingleResult());

		ValorNumerico valor = new ValorNumerico();
		valor.setValorNumerico(Float.parseFloat(valorMedido));
		valor.setValorMedido(valorMedido);

		medicao.setValorMedido(valor);

		RecursoHumano medicaoJob = new RecursoHumano();
		medicaoJob.setNome("Medição Job");

		// Persiste o job como RH.
		try {
			if (!manager.isOpen())
				manager = XPersistence.createManager();

			String query = String.format("SELECT p FROM RecursoHumano p WHERE p.nome='%s'", medicaoJob.getNome());
			TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);

			medicaoJob = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			manager.close();
			manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(medicaoJob);
			manager.getTransaction().commit();
		} finally {
			manager.close();
		}

		medicao.setExecutorDaMedicao(medicaoJob);

		ContextoDeMedicao contexto = new ContextoDeMedicao();
		contexto.setDescricao("Medição automática feita pelo Job de Medição.");

		// Persiste o contexto.
		try {
			if (!manager.isOpen())
				manager = XPersistence.createManager();

			String query = String.format("SELECT p FROM ContextoDeMedicao p WHERE p.descricao='%s'",
					contexto.getDescricao());
			TypedQuery<ContextoDeMedicao> typedQuery = manager.createQuery(query, ContextoDeMedicao.class);

			contexto = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			manager.close();
			manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(contexto);
			manager.getTransaction().commit();
		} finally {
			manager.close();
		}

		medicao.setContextoDeMedicao(contexto);

		if (!manager.isOpen())
			manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medicao);
		manager.getTransaction().commit();

	}

}
