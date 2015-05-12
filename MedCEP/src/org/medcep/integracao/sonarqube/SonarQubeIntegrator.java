/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.integracao.sonarqube;

import java.util.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import org.medcep.integracao.sonarqube.model.*;

/**
 * Classe para a integração da MedCEP com o SonarQube.
 * 
 * @author Vinicius
 *
 */
public class SonarQubeIntegrator
{

    private final String urlSonarResources;
    private final String urlSonarMetrics;
    private static Client client;

    /**
     * Construtor
     * 
     * @param urlTaiga
     *            - URL base do Taiga.
     * @param usuario
     *            - login do Taiga.
     * @param senha
     *            - senha do Taiga.
     */
    public SonarQubeIntegrator(String urlSonar)
    {
	if (urlSonar.endsWith("/"))
	{
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
    public List<Recurso> obterProjetos()
    {
	//Busca informações dos projetos.
	WebTarget target = client.target(this.urlSonarResources);

	List<Recurso> projetos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.get(new GenericType<List<Recurso>>() {
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
    public List<Recurso> obterRecursosDoProjeto(String chaveProjeto)
    {
	//Busca informações dos recursos do projeto.
	WebTarget target = client.target(this.urlSonarResources).queryParam("resource", chaveProjeto).queryParam("depth", "-1");

	List<Recurso> projetos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.get(new GenericType<List<Recurso>>() {
		});

	return projetos;
    }

    /**
     * Obtém um recurso pela chave.
     * @param chaveRecurso - Chave do recurso.
     * @return Recurso obtido.
     */
    public Recurso obterRecurso(String chaveRecurso)
    {
	WebTarget target = client.target(this.urlSonarResources).queryParam("resource", chaveRecurso);

	List<Recurso> recursos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.get(new GenericType<List<Recurso>>() {
		});

	return recursos.get(0);
    }

    /**
     * Obtém a lista de métricas.
     * 
     * @return List<Metrica> métricas.
     */
    public List<Metrica> obterMetricas()
    {
	WebTarget target = client.target(this.urlSonarMetrics);

	List<Metrica> metricas = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.get(new GenericType<List<Metrica>>() {
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
    public List<Medida> obterMedidasDoRecurso(List<Metrica> metricas, Recurso recurso)
    {

	String metricasComVirgula = this.joinMetricas(metricas);

	WebTarget target = client.target(this.urlSonarResources).queryParam("metrics", metricasComVirgula).queryParam("resource", recurso.getChave());

	List<Recurso> recursos = target
		.request(MediaType.APPLICATION_JSON_TYPE)
		.get(new GenericType<List<Recurso>>() {
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
    private String joinMetricas(List<Metrica> metricas)
    {

	StringBuilder sb = new StringBuilder();

	String loopDelim = "";

	for (Metrica m : metricas)
	{

	    sb.append(loopDelim);
	    sb.append(m.getChave());

	    loopDelim = ",";
	}

	return sb.toString();
    }

}
