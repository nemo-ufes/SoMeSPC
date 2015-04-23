package org.medcep.inicializacao;

import java.util.*;

import javax.persistence.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.exception.*;
import org.medcep.model.medicao.*;
import org.openxava.jpa.*;

/**
 * Classe para inicializar a MedCEP.
 * 
 * @author Vinicius
 *
 */
public class MedCEPStarter extends HttpServlet
{

    private static final long serialVersionUID = -718214408262760803L;

    public void init() throws ServletException
    {
	try
	{
	    MedCEPStarter.inicializarMedCEP();
	}
	catch (Exception e)
	{
	    System.out.println("Erro ao inicializar os dados básicos da MedCEP.");
	    e.printStackTrace();
	}
    }

    /**
     * Inicializa o banco de dados da MedCEP com informações básicas.
     * 
     * @throws Exception
     */
    public static void inicializarMedCEP() throws Exception
    {
	inicializarTiposElementosMensuraveis();
	inicializarElementosMensuraveis();
	inicializarTiposMedidas();
	inicializarTiposEntidadesMensuraveis();
	inicializarPeriodicidades();
	inicializarEscalas();
	inicializarUnidadesMedida();
    }

    private static void inicializarTiposElementosMensuraveis() throws Exception
    {
	//Configura os tipos de elementos mensuráveis.	
	EntityManager manager = XPersistence.createManager();

	//Persiste.
	try
	{
	    //Tenta obter o tipo Elemento Diretamente Mensurável.
	    String query = "SELECT t FROM TipoElementoMensuravel t WHERE t.nome='Elemento Diretamente Mensurável'";
	    TypedQuery<TipoElementoMensuravel> typedQuery = manager.createQuery(query, TipoElementoMensuravel.class);
	    TipoElementoMensuravel elementoDiretamenteMensuravel = typedQuery.getSingleResult();

	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    TipoElementoMensuravel dm = new TipoElementoMensuravel();
	    TipoElementoMensuravel im = new TipoElementoMensuravel();

	    dm.setNome("Elemento Diretamente Mensurável");
	    im.setNome("Elemento Indiretamente Mensurável");
	    manager.getTransaction().begin();

	    manager.persist(dm);
	    manager.persist(im);
	    manager.getTransaction().commit();
	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}
    }

    private static void inicializarElementosMensuraveis() throws Exception
    {
	//Instancia os elementos mensuráveis.	
	EntityManager manager = XPersistence.createManager();

	//Persiste.
	try
	{
	    String query = "SELECT t FROM ElementoMensuravel t WHERE t.nome='Desempenho'";
	    TypedQuery<ElementoMensuravel> typedQuery = manager.createQuery(query, ElementoMensuravel.class);
	    ElementoMensuravel elementoDiretamenteMensuravel = typedQuery.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    ElementoMensuravel desempenho = new ElementoMensuravel();
	    ElementoMensuravel tamanho = new ElementoMensuravel();
	    ElementoMensuravel duracao = new ElementoMensuravel();

	    //Obtem o tipo Elemento Diretamente Mensurável.
	    String query = "SELECT t FROM TipoElementoMensuravel t WHERE t.nome='Elemento Diretamente Mensurável'";
	    TypedQuery<TipoElementoMensuravel> typedQuery = manager.createQuery(query, TipoElementoMensuravel.class);
	    TipoElementoMensuravel elementoDiretamenteMensuravel = typedQuery.getSingleResult();

	    //Obtem o tipo Elemento Indiretamente Mensurável.
	    String query2 = "SELECT t FROM TipoElementoMensuravel t WHERE t.nome='Elemento Indiretamente Mensurável'";
	    TypedQuery<TipoElementoMensuravel> typedQuery2 = manager.createQuery(query2, TipoElementoMensuravel.class);
	    TipoElementoMensuravel elementoIndiretamenteMensuravel = typedQuery2.getSingleResult();

	    //Configura os elementos mensuráveis.
	    desempenho.setNome("Desempenho");
	    desempenho.setDescricao("Desempenho");
	    desempenho.setTipoElementoMensuravel(elementoDiretamenteMensuravel);

	    tamanho.setNome("Tamanho");
	    tamanho.setDescricao("Tamanho");
	    tamanho.setTipoElementoMensuravel(elementoDiretamenteMensuravel);

	    duracao.setNome("Duração");
	    duracao.setDescricao("Substração da data de fim pela data de início.");
	    duracao.setTipoElementoMensuravel(elementoIndiretamenteMensuravel);

	    List<ElementoMensuravel> elementosParaPersistir = new ArrayList<ElementoMensuravel>();
	    elementosParaPersistir.add(desempenho);
	    elementosParaPersistir.add(tamanho);
	    elementosParaPersistir.add(duracao);

	    for (ElementoMensuravel elementoMensuravel : elementosParaPersistir)
	    {

		manager.getTransaction().begin();
		manager.persist(elementoMensuravel);
		manager.getTransaction().commit();
	    }

	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}

    }

    private static void inicializarTiposMedidas() throws Exception
    {
	//Configura os tipos de elementos mensuráveis.	
	EntityManager manager = XPersistence.createManager();

	//Persiste.
	try
	{
	    String query2 = "SELECT t FROM TipoMedida t WHERE t.nome='Medida Base'";
	    TypedQuery<TipoMedida> typedQuery2 = manager.createQuery(query2, TipoMedida.class);
	    TipoMedida elementoIndiretamenteMensuravel = typedQuery2.getSingleResult();
	}
	catch (Exception ex)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    TipoMedida mb = new TipoMedida();
	    TipoMedida md = new TipoMedida();

	    mb.setNome("Medida Base");
	    md.setNome("Medida Derivada");

	    manager.getTransaction().begin();
	    manager.persist(mb);
	    manager.persist(md);
	    manager.getTransaction().commit();

	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}
    }

    private static void inicializarTiposEntidadesMensuraveis() throws Exception
    {
	//Configura os tipos de entidades mensuráveis básicas.	
	EntityManager manager = XPersistence.createManager();

	try
	{
	    String query2 = "SELECT t FROM TipoDeEntidadeMensuravel t WHERE t.nome='Projeto'";
	    TypedQuery<TipoDeEntidadeMensuravel> typedQuery2 = manager.createQuery(query2, TipoDeEntidadeMensuravel.class);
	    TipoDeEntidadeMensuravel teste = typedQuery2.getSingleResult();
	}
	catch (Exception e)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    TipoDeEntidadeMensuravel tipoProjeto = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoPSPadrao = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoAPadrao = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoPSProjeto = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoAProjeto = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoOcorrenciaPS = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoOcorrenciaAtividade = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoTipoArtefato = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoArtefato = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoRecursoHumano = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoPapelRecursoHumano = new TipoDeEntidadeMensuravel();
	    TipoDeEntidadeMensuravel tipoAlocacao = new TipoDeEntidadeMensuravel();

	    tipoProjeto.setNome("Projeto");
	    tipoProjeto.setDescricao("Representa um novo Projeto de software.");

	    tipoPSPadrao.setNome("Processo de Software Padrão");
	    tipoPSPadrao.setDescricao("Processo de Software Padrão da organização. Ex: Processo de Gerência de Requisitos, Processo de Testes, Processo de Desenvolvimento de Software.");

	    tipoAPadrao.setNome("Atividade Padrão");
	    tipoAPadrao.setDescricao("Atividade presente no Processo de Software Padrão");

	    tipoPSProjeto.setNome("Processo de Software em Projeto");
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

	    tipoRecursoHumano.setNome("Recurso Humano");
	    tipoRecursoHumano.setDescricao("Recurso Humano da organização.");

	    tipoPapelRecursoHumano.setNome("Papel Recurso Humano");
	    tipoPapelRecursoHumano.setDescricao("Papel de Recurso Humano da organização.");

	    tipoAlocacao.setNome("Alocação de Recurso Humano");
	    tipoAlocacao.setDescricao("Alocação de um Recurso Humano para desempenhar um Papel em uma Equipe.");

	    //Adiciona elementos mensuraveis.
	    //Obtem o ElementoMensuravel Desempenho.
	    String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	    TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	    ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	    //Obtem o ElementoMensuravel Tamanho.
	    String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	    TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	    ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();

	    //Obtem o ElementoMensuravel Duracao.
	    String queryDuracao = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Duração'";
	    TypedQuery<ElementoMensuravel> typedQueryDuracao = manager.createQuery(queryDuracao, ElementoMensuravel.class);
	    ElementoMensuravel duracao = typedQueryDuracao.getSingleResult();

	    List<ElementoMensuravel> elementosProjeto = new ArrayList<ElementoMensuravel>();
	    elementosProjeto.add(desempenho);
	    elementosProjeto.add(tamanho);
	    tipoProjeto.setElementoMensuravel(elementosProjeto);

	    List<ElementoMensuravel> elementosAPadrao = new ArrayList<ElementoMensuravel>();
	    elementosAPadrao.add(duracao);
	    elementosAPadrao.add(tamanho);
	    tipoAPadrao.setElementoMensuravel(elementosAPadrao);

	    List<ElementoMensuravel> elementosAProjeto = new ArrayList<ElementoMensuravel>();
	    elementosAProjeto.add(duracao);
	    elementosAProjeto.add(tamanho);
	    tipoAProjeto.setElementoMensuravel(elementosAProjeto);

	    List<ElementoMensuravel> elementosOcorrenciaAtividade = new ArrayList<ElementoMensuravel>();
	    elementosOcorrenciaAtividade.add(duracao);
	    elementosOcorrenciaAtividade.add(tamanho);
	    tipoOcorrenciaAtividade.setElementoMensuravel(elementosOcorrenciaAtividade);

	    List<ElementoMensuravel> elementosAlocacao = new ArrayList<ElementoMensuravel>();
	    elementosAlocacao.add(desempenho);
	    elementosAlocacao.add(duracao);
	    tipoAlocacao.setElementoMensuravel(elementosAlocacao);
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
	    tiposParaPersistir.add(tipoRecursoHumano);
	    tiposParaPersistir.add(tipoPapelRecursoHumano);
	    tiposParaPersistir.add(tipoAlocacao);

	    for (TipoDeEntidadeMensuravel tipo : tiposParaPersistir)
	    {
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(tipo);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {
			System.out.println(String.format("O Tipo de Entidades Mensurável %s já existe.", tipo.getNome()));
		    }
		    else
		    {
			throw ex;
		    }
		}

		finally
		{
		    if (manager.isOpen())
			manager.close();
		}
	    }

	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}

    }

    private static void inicializarPeriodicidades() throws Exception
    {
	//Configura as periodicidades.	
	EntityManager manager = XPersistence.createManager();

	try
	{
	    String query2 = "SELECT t FROM Periodicidade t WHERE t.nome='Semanal'";
	    TypedQuery<Periodicidade> typedQuery2 = manager.createQuery(query2, Periodicidade.class);
	    Periodicidade teste = typedQuery2.getSingleResult();
	}
	catch (Exception e)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

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
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(p);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {
			System.out.println(String.format("A Periodicidade %s já existe.", p.getNome()));
		    }
		    else
		    {
			throw ex;
		    }
		}
		finally
		{
		    if (manager.isOpen())
			manager.close();
		}

	    }
	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}

    }

    private static void inicializarEscalas() throws Exception
    {
	//Configura as escalas.	
	EntityManager manager = XPersistence.createManager();

	try
	{
	    String query2 = "SELECT t FROM TipoEscala t WHERE t.nome='Intervalar'";
	    TypedQuery<TipoEscala> typedQuery2 = manager.createQuery(query2, TipoEscala.class);
	    TipoEscala teste = typedQuery2.getSingleResult();
	}
	catch (Exception e)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

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
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(tipo);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    manager.close();
		    manager = XPersistence.createManager();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {
			System.out.println(String.format("O Tipo de Escala %s já existe.", tipo.getNome()));
		    }
		    else
		    {
			throw ex;
		    }
		}
		finally
		{
		    if (manager.isOpen())
			manager.close();
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

	    List<Escala> escalasPercentuais = new ArrayList<Escala>();
	    escalaPercentual.setValorDeEscala(valoresPercentuais);
	    escalasPercentuais.add(escalaPercentual);

	    Escala escalaNumerosRacionais = new Escala();
	    escalaNumerosRacionais.setNome("Números Racionais");
	    escalaNumerosRacionais.setTipoEscala(tipoRacional);

	    List<ValorDeEscala> valoresRacionais = new ArrayList<ValorDeEscala>();
	    ValorDeEscala valorRacional = new ValorDeEscala();
	    valorRacional.setNumerico(true);
	    valorRacional.setValor("Números racionais");
	    valoresRacionais.add(valorRacional);

	    List<Escala> escalasRacionais = new ArrayList<Escala>();
	    escalaNumerosRacionais.setValorDeEscala(valoresRacionais);
	    escalasRacionais.add(escalaNumerosRacionais);

	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(escalaPercentual);
		manager.persist(escalaNumerosRacionais);
		manager.persist(valorPercentual);
		manager.persist(valorRacional);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    System.out.println("As escalas já existem.");
		}
		else
		{
		    throw ex;
		}
	    }
	    finally
	    {
		if (manager.isOpen())
		    manager.close();
	    }
	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}

    }

    private static void inicializarUnidadesMedida() throws Exception
    {
	//Configura as periodicidades.	
	EntityManager manager = XPersistence.createManager();

	try
	{
	    String query2 = "SELECT t FROM UnidadeDeMedida t WHERE t.nome='Pontos de Estória'";
	    TypedQuery<UnidadeDeMedida> typedQuery2 = manager.createQuery(query2, UnidadeDeMedida.class);
	    UnidadeDeMedida teste = typedQuery2.getSingleResult();
	}
	catch (Exception e)
	{
	    if (manager.getTransaction().isActive())
		manager.getTransaction().rollback();

	    manager.close();
	    manager = XPersistence.createManager();

	    UnidadeDeMedida pontosEstoria = new UnidadeDeMedida();

	    pontosEstoria.setNome("Pontos de Estória");
	    pontosEstoria.setDescricao("Pontos de Estória do Scrum.");

	    //Persiste.
	    List<UnidadeDeMedida> unidadesParaPersistir = new ArrayList<UnidadeDeMedida>();
	    unidadesParaPersistir.add(pontosEstoria);

	    for (UnidadeDeMedida u : unidadesParaPersistir)
	    {
		try
		{
		    if (!manager.isOpen())
			manager = XPersistence.createManager();

		    manager.getTransaction().begin();
		    manager.persist(u);
		    manager.getTransaction().commit();
		}
		catch (Exception ex)
		{
		    if (manager.getTransaction().isActive())
			manager.getTransaction().rollback();

		    if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			    (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		    {
			System.out.println(String.format("A Unidade de Medida %s já existe.", u.getNome()));
		    }
		    else
		    {
			throw ex;
		    }
		}
		finally
		{
		    if (manager.isOpen())
			manager.close();
		}
	    }
	}
	finally
	{
	    if (manager.isOpen())
		manager.close();
	}

    }

}
