/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.integracao.sonarqube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.hibernate.exception.ConstraintViolationException;
import org.openxava.jpa.XPersistence;
import org.somespc.integracao.sonarqube.model.Medida;
import org.somespc.integracao.sonarqube.model.MedidasSonarQube;
import org.somespc.integracao.sonarqube.model.Metrica;
import org.somespc.integracao.sonarqube.model.Recurso;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.Escala;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoMedida;

/**
 * Classe para a integração da SoMeSPC com o SonarQube.
 * 
 * @author Vinicius
 *
 */
public class SonarQubeIntegrator {

	private final String urlSonarResources;
	private final String urlSonarMetrics;
	private static Client client;

	/**
	 * Construtor
	 *
	 */
	public SonarQubeIntegrator(String urlSonar) {
		
		if (urlSonar.endsWith("/")) {
			urlSonar = urlSonar.substring(0, urlSonar.length() - 1);
		}

		this.urlSonarResources = urlSonar + "/api/resources";
		this.urlSonarMetrics = urlSonar + "/api/metrics";

		client = ClientBuilder.newClient();
	}

	/**
	 * Obtem todos os projetos (recursos).
	 * 
	 * @return List<Recurso> projetos
	 */
	public List<Recurso> obterProjetos() {
		// Busca informações dos projetos.
		WebTarget target = client.target(this.urlSonarResources);

		List<Recurso> projetos = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Recurso>>() {
		});

		return projetos;
	}

	/**
	 * Obtém os recursos do projeto.
	 * 
	 * @param chaveProjeto
	 *            - Chave do projeto (case sensitive!).
	 * @return List<Recurso> recursos (pacotes, arquivos, etc).
	 */
	public List<Recurso> obterRecursosDoProjeto(String chaveProjeto) {
		// Busca informações dos recursos do projeto.
		WebTarget target = client.target(this.urlSonarResources).queryParam("resource", chaveProjeto)
				.queryParam("depth", "-1");

		List<Recurso> projetos = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Recurso>>() {
		});

		return projetos;
	}

	/**
	 * Obtém um recurso pela chave.
	 * 
	 * @param chaveRecurso
	 *            - Chave do recurso.
	 * @return Recurso obtido.
	 */
	public Recurso obterRecurso(String chaveRecurso) {
		WebTarget target = client.target(this.urlSonarResources).queryParam("resource", chaveRecurso);

		List<Recurso> recursos = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Recurso>>() {
		});

		return recursos.get(0);
	}

	/**
	 * Obtém a lista de métricas.
	 * 
	 * @return List<Metrica> métricas.
	 */
	public List<Metrica> obterMetricas() {
		WebTarget target = client.target(this.urlSonarMetrics);

		List<Metrica> metricas = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Metrica>>() {
		});

		return metricas;
	}

	/**
	 * Obtém as medidas dos recursos.
	 * 
	 * @param metricas
	 *            - Medidas a serem obtidas.
	 * @param recurso
	 *            - Recurso a ser medido.
	 * @return List<Medida> medidas.
	 */
	public List<Medida> obterMedidasDoRecurso(List<Metrica> metricas, Recurso recurso) {

		String metricasComVirgula = this.joinMetricas(metricas);

		WebTarget target = client.target(this.urlSonarResources).queryParam("metrics", metricasComVirgula)
				.queryParam("resource", recurso.getChave());

		List<Recurso> recursos = target.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Recurso>>() {
		});

		return recursos.get(0).getMedidas();
	}

	/**
	 * Une as métricas com vírgula. Ex: nloc,coverage,comments
	 * 
	 * @param metricas
	 *            a serem unidas.
	 * @return Métricas com vírgula.
	 */
	private String joinMetricas(List<Metrica> metricas) {

		StringBuilder sb = new StringBuilder();

		String loopDelim = "";

		for (Metrica m : metricas) {

			sb.append(loopDelim);
			sb.append(m.getChave());

			loopDelim = ",";
		}

		return sb.toString();
	}

    /**
     * Cria as Medidas do SonarQube no banco de dados da SoMeSPC.
     * Não atribui valores, somente cria as definições das medidas.
     * 
     * @param medidasSonar
     *            - Lista com as medidas do SonarQube a serem criadas na SoMeSPC.
     * @return List<Medida> - Medidas criadas na SoMeSPC.
     * @throws Exception
     */
    public List<org.somespc.model.entidades_e_medidas.Medida> criarMedidasSoMeSPC(List<MedidasSonarQube> medidasSonar) throws Exception
    {

	EntityManager manager = XPersistence.createManager();
	List<org.somespc.model.entidades_e_medidas.Medida> medidasCadastradas = new ArrayList<org.somespc.model.entidades_e_medidas.Medida>();

	//Obtem o tipo de medida base.
	String query1 = "SELECT mb FROM TipoMedida mb WHERE mb.nome='Medida Base'";
	TypedQuery<TipoMedida> typedQuery1 = manager.createQuery(query1, TipoMedida.class);
	TipoMedida medidaBase = typedQuery1.getSingleResult();

	//Obtem a escala racional.
	String query2 = "SELECT e FROM Escala e WHERE e.nome='Escala formada pelos números reais'";
	TypedQuery<Escala> typedQuery2 = manager.createQuery(query2, Escala.class);
	Escala escala = typedQuery2.getSingleResult();

	//Obtem o tipo de Entidade Mensurável Projeto.
	String query5 = "SELECT e FROM TipoDeEntidadeMensuravel e WHERE e.nome='Projeto'";
	TypedQuery<TipoDeEntidadeMensuravel> typedQuery5 = manager.createQuery(query5, TipoDeEntidadeMensuravel.class);
	TipoDeEntidadeMensuravel tipoProjeto = typedQuery5.getSingleResult();

	//Obtem o ElementoMensuravel Desempenho.
	String queryDesempenho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Desempenho'";
	TypedQuery<ElementoMensuravel> typedQueryDesempenho = manager.createQuery(queryDesempenho, ElementoMensuravel.class);
	ElementoMensuravel desempenho = typedQueryDesempenho.getSingleResult();

	//Obtem o ElementoMensuravel Tamanho.
	String queryTamanho = "SELECT e FROM ElementoMensuravel e WHERE e.nome='Tamanho'";
	TypedQuery<ElementoMensuravel> typedQueryTamanho = manager.createQuery(queryTamanho, ElementoMensuravel.class);
	ElementoMensuravel tamanho = typedQueryTamanho.getSingleResult();


	manager.close();

	//Define a medida de acordo com a lista informada.
	for (MedidasSonarQube medidaSonar : medidasSonar)
	{
	    org.somespc.model.entidades_e_medidas.Medida medida = new org.somespc.model.entidades_e_medidas.Medida();
	    medida.setNome(medidaSonar.toString());

	    switch (medidaSonar)
	    {
		case MEDIA_COMPLEXIDADE_CICLOMATICA_MEDIA:
		    medida.setMnemonico("MCCM");
		    medida.setElementoMensuravel(desempenho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case TAXA_DUPLICACAO_CODIGO:
		    medida.setMnemonico("TDC");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		case PERCENTUAL_DIVIDA_TECNICA:
		    medida.setMnemonico("PDT");
		    medida.setElementoMensuravel(tamanho);
		    medida.setTipoDeEntidadeMensuravel(new ArrayList<TipoDeEntidadeMensuravel>(Arrays.asList(tipoProjeto)));
		    break;
		
		default:
		    System.out.println(String.format("Medida %s inexistente no SonarQube.", medidaSonar.toString()));
	    }

	    medida.setDescricao("Medida obtida pela API SonarQube conforme a documentação: http://docs.sonarqube.org/display/DEV/Web+Service+API");
	    medida.setTipoMedida(medidaBase);
	    medida.setEscala(escala);

	    DefinicaoOperacionalDeMedida definicao = new DefinicaoOperacionalDeMedida();

	    definicao.setNome("Definição operacional da medida do SonarQube - " + medida.getNome());
	    Calendar cal = Calendar.getInstance();
	    definicao.setData(cal.getTime());
	    definicao.setDescricao("Definição operacional criada automaticamente.");
	    definicao.setMedida(medida);
	  
	    try
	    {
		if (!manager.isOpen())
		    manager = XPersistence.createManager();

		manager.getTransaction().begin();
		manager.persist(medida);
		manager.persist(definicao);
		manager.getTransaction().commit();
	    }
	    catch (Exception ex)
	    {
		if (manager.getTransaction().isActive())
		    manager.getTransaction().rollback();

		manager = XPersistence.createManager();

		if ((ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) ||
			(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException))
		{
		    System.out.println(String.format("A Medida %s já existe.", medida.getNome()));

		    String queryMedida = String.format("SELECT m FROM Medida m WHERE m.nome='%s'", medida.getNome());
		    TypedQuery<org.somespc.model.entidades_e_medidas.Medida> typedQueryMedida = manager.createQuery(queryMedida, org.somespc.model.entidades_e_medidas.Medida.class);

		    medida = typedQueryMedida.getSingleResult();
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

	    medidasCadastradas.add(medida);
	}

	return medidasCadastradas;
    }
	
}
