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
package org.medcep.integracao.sonarqube.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.junit.*;
import org.medcep.inicializacao.*;
import org.medcep.integracao.sonarqube.*;
import org.medcep.integracao.sonarqube.model.*;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.json.*;

public class SonarQubeIntegratorTest
{

    private SonarQubeIntegrator integrator;

    @Before
    public void init() throws Exception
    {
	MedCEPStarter.inicializarMedCEP();
	integrator = new SonarQubeIntegrator("http://ledszeppellin.sr.ifes.edu.br:9000/");
    }

    @Test
    public void testObterProjetos()
    {
	List<Recurso> projetos = integrator.obterProjetos();

	assertNotNull(projetos);
	assertNotEquals(projetos.size(), 0);

	dump(projetos);
    }

    @Test
    public void testObterRecursosDoProjeto()
    {
	List<Recurso> recursos = integrator.obterRecursosDoProjeto("br.ifes.leds.sincap:SincapEntities");

	assertNotNull(recursos);
	assertNotEquals(recursos.size(), 0);

	dump(recursos);
    }

    @Test
    public void testObterMetricas()
    {
	List<Metrica> metricas = integrator.obterMetricas();

	assertNotNull(metricas);
	assertNotEquals(metricas.size(), 0);

	dump(metricas);
    }

    @Test
    public void testObterMedidasDoRecurso()
    {
	List<Metrica> metricas = integrator.obterMetricas();
	Recurso recurso = integrator.obterRecurso("br.ifes.leds.sincap:SincapEntities");

	List<Medida> medidas = integrator.obterMedidasDoRecurso(metricas, recurso);

	assertNotNull(medidas);
	assertNotEquals(medidas.size(), 0);

	dump(medidas);
    }

    private void dump(Object object)
    {
	XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
	System.out.println(xstream.toXML(object));
    }

}
