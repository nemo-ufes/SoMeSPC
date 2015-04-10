package org.medcep.integracao.taiga.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.junit.*;
import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.organizacao.*;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.json.*;

public class TaigaIntegratorTest
{

    @Test
    public void testObterAuthToken()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	String token = integrator.obterAuthToken();
	System.out.println("token: " + token);
	assertNotNull(token);
	assertNotEquals(token, "");
    }

    @Test
    public void testObterProjeto()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Projeto projeto = integrator.obterProjetoTaiga("paflopes-sincap");
	assertNotNull(projeto);
	assertNotEquals(projeto.getId(), 0);

	dump(projeto);

	System.out.println("Membros do projeto " + projeto.getNome());
	for (Membro membro : projeto.getMembros())
	{
	    dump(membro);
	}
	System.out.println("----------------------------------------");

    }

    @Test
    public void testObterProjetos()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	List<Projeto> projetos = integrator.obterProjetosTaiga();
	assertNotNull(projetos);
	assertNotEquals(projetos.size(), 0);

	for (Projeto proj : projetos)
	{
	    dump(proj);
	    System.out.println("----------------------------------------");
	}
    }

    @Test
    public void testObterMembro()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);

	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);

	dump(membro);
    }

    @Test
    public void testCriarRecursoHumanoMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);

	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);

	dump(membro);

	RecursoHumano recurso = integrator.criarRecursoHumanoMedCEP(membro);

	assertNotNull(recurso);

	dump(recurso);
    }

    @Test
    public void testCriarPapelRecursoHumanoMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);

	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);

	dump(membro);

	PapelRecursoHumano papel = integrator.criarPapelRecursoHumanoMedCEP(membro);

	assertNotNull(papel);

	dump(papel);
    }

    @Test
    public void testCriarEquipeMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Projeto sincap = integrator.obterProjetoTaiga("paflopes-sincap");

	assertNotNull(sincap);
	assertNotEquals(sincap.getId(), 0);
	assertNotNull(sincap.getMembros());

	dump(sincap);

	Equipe equipe = integrator.criarEquipeMedCEP(sincap.getNome() + " Team", sincap.getMembros());

	assertNotNull(equipe);

	dump(equipe);
    }

    private void dump(Object object)
    {
	XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
	System.out.println(xstream.toXML(object));
    }

}
