package org.somespc.integracao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.openxava.jpa.XPersistence;
import org.somespc.integracao.taiga.model.Membro;
import org.somespc.model.definicao_operacional_de_medida.Periodicidade;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.medicao.Medicao;
import org.somespc.model.medicao.ValorNumerico;
import org.somespc.model.organizacao_de_software.PapelRecursoHumano;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.model.plano_de_medicao.MedidaPlanoDeMedicao;
import org.somespc.model.plano_de_medicao.PlanoDeMedicaoDoProjeto;

public class SoMeSPCIntegrator {

	/**
	 * Obtem as periodicidades cadastradas.
	 * 
	 * @return
	 */
	public static List<Periodicidade> obterPeriodicidades() {
		EntityManager manager = XPersistence.createManager();

		TypedQuery<Periodicidade> query = manager.createQuery("FROM Periodicidade", Periodicidade.class);
		List<Periodicidade> result = query.getResultList();

		manager.close();
		return result;
	}

	/**
	 * Cadastra um RecursoHumano na SoMeSPC. Se já existir, retorna o
	 * RecursoHumano existente.
	 * 
	 * @param membro
	 *            - Membro a ser cadastrado.
	 * @return RecursoHumano criado/existente.
	 * @throws Exception
	 */
	public static RecursoHumano criarRecursoHumano(RecursoHumano recursoHumano) throws Exception {

		EntityManager manager = XPersistence.createManager();

		try {
			String query = String.format("SELECT r FROM RecursoHumano r WHERE r.nome='%s'", recursoHumano.getNome());
			TypedQuery<RecursoHumano> typedQuery = manager.createQuery(query, RecursoHumano.class);
			recursoHumano = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			if (!manager.isOpen())
				manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(recursoHumano);
			manager.getTransaction().commit();

		} finally {
			manager.close();
		}

		return recursoHumano;
	}

	/**
	 * Cadastra um Papel de Recurso Humano na SoMeSPC. Se já existir, retorna o Papel existente.
	 * 
	 * @param membro
	 *            - Membro a ser cadastrado.
	 * @return Papel criado/existente.
	 * @throws Exception
	 */
	public static PapelRecursoHumano criarPapelRecursoHumanoSoMeSPC(PapelRecursoHumano papel) throws Exception {
		EntityManager manager = XPersistence.createManager();
		try {
			String query = String.format("SELECT p FROM PapelRecursoHumano p WHERE p.nome='%s'", papel.getNome());
			TypedQuery<PapelRecursoHumano> typedQuery = manager.createQuery(query, PapelRecursoHumano.class);
			papel = typedQuery.getSingleResult();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive())
				manager.getTransaction().rollback();

			if (!manager.isOpen())
				manager = XPersistence.createManager();

			manager.getTransaction().begin();
			manager.persist(papel);
			manager.getTransaction().commit();

		} finally {
			manager.close();
		}

		return papel;
	}

	/**
	 * Cria uma medição.
	 * 
	 * @throws Exception
	 */
	public static synchronized void criarMedicaoSoMeSPC(PlanoDeMedicaoDoProjeto plano, Timestamp data,
			String nomeMedida, String entidadeMedida, String valorMedido) throws Exception {
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

		if (!manager.isOpen())
			manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medicao);
		manager.getTransaction().commit();

	}

}
