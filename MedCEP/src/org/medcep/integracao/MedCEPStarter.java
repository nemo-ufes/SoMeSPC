package org.medcep.integracao;

import java.util.*;

import javax.persistence.*;

import org.hibernate.exception.*;
import org.medcep.model.medicao.*;
import org.openxava.jpa.*;

/**
 * Classe para inicializar a MedCEP.
 * 
 * @author Vinicius
 *
 */
public class MedCEPStarter
{

    /**
     * Inicializa o banco de dados da MedCEP com informações básicas.
     * 
     * @throws Exception
     */
    public static void inicializarMedCEP() throws Exception
    {
	inicializarTiposElementosMensuraveis();
	inicializarTiposEntidadesMensuraveis();
	inicializarPeriodicidades();
	inicializarEscalas();
    }

    private static void inicializarTiposElementosMensuraveis() throws Exception
    {
	//Configura os tipos de elementos mensuráveis.	
	EntityManager manager = XPersistence.createManager();

	TipoElementoMensuravel dm = new TipoElementoMensuravel();
	TipoElementoMensuravel im = new TipoElementoMensuravel();

	dm.setNome("Elemento Diretamente Mensurável");
	im.setNome("Elemento Indiretamente Mensurável");

	//Persiste.
	try
	{
	    manager.getTransaction().begin();

	    manager.persist(dm);
	    manager.persist(im);
	    manager.getTransaction().commit();
	}
	catch (Exception ex)
	{
	    if (ex.getCause() != null &&
		    ex.getCause().getCause() != null &&
		    ex.getCause().getCause() instanceof ConstraintViolationException)
	    {
		System.out.println("Tipos de Elementos Mensuráveis já cadastrados.");
	    }
	    else
	    {
		throw ex;
	    }
	}
	finally
	{
	    manager.close();
	}
    }

    private static void inicializarTiposEntidadesMensuraveis() throws Exception
    {
	//Configura os tipos de entidades mensuráveis básicas.	
	EntityManager manager = XPersistence.createManager();

	TipoDeEntidadeMensuravel tipoProjeto = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoPSPadrao = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoAPadrao = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoPSProjeto = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoAProjeto = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoOcorrenciaPS = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoOcorrenciaAtividade = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoTipoArtefato = new TipoDeEntidadeMensuravel();
	TipoDeEntidadeMensuravel tipoArtefato = new TipoDeEntidadeMensuravel();

	tipoProjeto.setNome("Projeto");
	tipoProjeto.setDescricao("Representa um novo Projeto de software.");

	tipoPSPadrao.setNome("Processo de Software Padrão");
	tipoPSPadrao.setDescricao("Processo de Software Padrão da organização. Ex: Processo de Gerência de Requisitos, Processo de Testes, Processo de Desenvolvimento de Software.");

	tipoAPadrao.setNome("Atividade Padrão");
	tipoAPadrao.setDescricao("Atividade presente no Processo de Software Padrão");

	tipoPSProjeto.setNome("Processo de Software de Projeto");
	tipoPSProjeto.setDescricao("Processo ajustado para um determinado projeto de acordo com um Processo de Software Padrão. Ex: Processo de Testes do Projeto Sincap.");

	tipoAProjeto.setNome("Atividade de Projeto");
	tipoAProjeto.setDescricao("Atividade ajustada para um determinado projeto de acordo com uma Atividade Padrão.");

	tipoOcorrenciaPS.setNome("Ocorrência de Processo de Software");
	tipoOcorrenciaPS.setDescricao("Ocorrência de uma determinada instância do Processo de Software de Projeto. Ex: Ocorrência do Processo de Testes.");

	tipoOcorrenciaAtividade.setNome("Ocorrência de Atividade");
	tipoOcorrenciaAtividade.setDescricao("Ocorrência de uma determinada atividade do Processo de Software de Projeto.");

	tipoTipoArtefato.setNome("Tipo de Artefato");
	tipoTipoArtefato.setDescricao("Tipo de Artefato de software. Ex: Documento, Modelo, Código fonte, Planos, etc.");

	tipoArtefato.setNome("Artefato");
	tipoArtefato.setDescricao("Representa um novo artefato de software.");

	//Persiste.
	List<TipoDeEntidadeMensuravel> tiposParaPersistir = new ArrayList<TipoDeEntidadeMensuravel>();
	tiposParaPersistir.add(tipoProjeto);
	tiposParaPersistir.add(tipoPSPadrao);
	tiposParaPersistir.add(tipoAPadrao);
	tiposParaPersistir.add(tipoPSProjeto);
	tiposParaPersistir.add(tipoAProjeto);
	tiposParaPersistir.add(tipoOcorrenciaPS);
	tiposParaPersistir.add(tipoOcorrenciaAtividade);
	tiposParaPersistir.add(tipoTipoArtefato);
	tiposParaPersistir.add(tipoArtefato);

	for (TipoDeEntidadeMensuravel tipo : tiposParaPersistir)
	{
	    try
	    {
		manager.getTransaction().begin();
		manager.persist(tipo);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (ex.getCause() != null &&
			ex.getCause().getCause() != null &&
			ex.getCause().getCause() instanceof ConstraintViolationException)
		{
		    System.out.println(String.format("O Tipo de Entidades Mensurável %s já existe.", tipo.getNome()));
		}
		else
		{
		    throw ex;
		}
	    }
	}

	manager.close();
    }

    private static void inicializarPeriodicidades() throws Exception
    {
	//Configura as periodicidades.	
	EntityManager manager = XPersistence.createManager();

	Periodicidade cadaOcorrencia = new Periodicidade();
	Periodicidade diaria = new Periodicidade();
	Periodicidade semanal = new Periodicidade();
	Periodicidade quinzenal = new Periodicidade();
	Periodicidade mensal = new Periodicidade();
	Periodicidade trimestral = new Periodicidade();
	Periodicidade semestral = new Periodicidade();
	Periodicidade anual = new Periodicidade();

	cadaOcorrencia.setNome("Em cada ocorrência da atividade");
	cadaOcorrencia.setDescricao("Em cada ocorrência da atividade");

	diaria.setNome("Diária");
	diaria.setDescricao("Diária");

	semanal.setNome("Semanal");
	semanal.setDescricao("Semanal");

	quinzenal.setNome("Quinzenal");
	quinzenal.setDescricao("Quinzenal");

	mensal.setNome("Mensal");
	mensal.setDescricao("Mensal");

	trimestral.setNome("Trimestral");
	trimestral.setDescricao("Trimestral");

	semestral.setNome("Semestral");
	semestral.setDescricao("Semestral");

	anual.setNome("Anual");
	anual.setDescricao("Anual");

	//Persiste.
	List<Periodicidade> periodicidadesParaPersistir = new ArrayList<Periodicidade>();
	periodicidadesParaPersistir.add(cadaOcorrencia);
	periodicidadesParaPersistir.add(diaria);
	periodicidadesParaPersistir.add(semanal);
	periodicidadesParaPersistir.add(quinzenal);
	periodicidadesParaPersistir.add(mensal);
	periodicidadesParaPersistir.add(trimestral);
	periodicidadesParaPersistir.add(semestral);
	periodicidadesParaPersistir.add(anual);

	for (Periodicidade p : periodicidadesParaPersistir)
	{
	    try
	    {
		manager.getTransaction().begin();
		manager.persist(p);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (ex.getCause() != null &&
			ex.getCause().getCause() != null &&
			ex.getCause().getCause() instanceof ConstraintViolationException)
		{
		    System.out.println(String.format("A Periodicidade %s já existe.", p.getNome()));
		}
		else
		{
		    throw ex;
		}
	    }
	}

	manager.close();
    }

    private static void inicializarEscalas() throws Exception
    {
	//Configura as escalas.	
	EntityManager manager = XPersistence.createManager();

	TipoEscala tipoIntervalar = new TipoEscala();
	TipoEscala tipoOrdinal = new TipoEscala();
	TipoEscala tipoRacional = new TipoEscala();

	tipoIntervalar.setNome("Intervalar");
	tipoOrdinal.setNome("Ordinal");
	tipoRacional.setNome("Racional");

	//Persiste.
	List<TipoEscala> tiposParaPersistir = new ArrayList<TipoEscala>();
	tiposParaPersistir.add(tipoIntervalar);
	tiposParaPersistir.add(tipoOrdinal);
	tiposParaPersistir.add(tipoRacional);

	for (TipoEscala tipo : tiposParaPersistir)
	{
	    try
	    {
		manager.getTransaction().begin();
		manager.persist(tipo);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (ex.getCause() != null &&
			ex.getCause().getCause() != null &&
			ex.getCause().getCause() instanceof ConstraintViolationException)
		{
		    System.out.println(String.format("O Tipo de Escala %s já existe.", tipo.getNome()));
		}
		else
		{
		    throw ex;
		}
	    }
	}

	Escala escalaPercentual = new Escala();
	escalaPercentual.setNome("Percentual");
	escalaPercentual.setTipoEscala(tipoRacional);

	List<ValorDeEscala> valoresPercentuais = new ArrayList<ValorDeEscala>();
	ValorDeEscala valorPercentual = new ValorDeEscala();
	valorPercentual.setNumerico(true);
	valorPercentual.setValor("Números racionais de 0 a 100");
	valoresPercentuais.add(valorPercentual);

	escalaPercentual.setValorDeEscala(valoresPercentuais);

	Escala escalaNumerosRacionais = new Escala();
	escalaNumerosRacionais.setNome("Números Racionais");
	escalaNumerosRacionais.setTipoEscala(tipoRacional);

	List<ValorDeEscala> valoresRacionais = new ArrayList<ValorDeEscala>();
	ValorDeEscala valorRacional = new ValorDeEscala();
	valorRacional.setNumerico(true);
	valorRacional.setValor("Números racionais");
	valoresRacionais.add(valorRacional);

	//Persiste.
	List<Escala> escalasParaPersistir = new ArrayList<Escala>();
	escalasParaPersistir.add(escalaPercentual);
	escalasParaPersistir.add(escalaNumerosRacionais);
		
	for (Escala escala : escalasParaPersistir)
	{
	    try
	    {
		manager.getTransaction().begin();
		manager.persist(escala);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (ex.getCause() != null &&
			ex.getCause().getCause() != null &&
			ex.getCause().getCause() instanceof ConstraintViolationException)
		{
		    System.out.println(String.format("A Escala %s já existe.", escala.getNome()));
		}
		else
		{
		    throw ex;
		}
	    }
	}
	
	manager.close();
    }

}
