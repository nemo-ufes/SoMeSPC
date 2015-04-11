package org.medcep.integracao.taiga.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.junit.*;
import org.medcep.inicializacao.*;
import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.medicao.*;
import org.medcep.model.organizacao.*;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.json.*;

public class TaigaIntegratorTest
{
    @Before
    public void init() throws Exception
    {
	MedCEPStarter.inicializarMedCEP();
    }

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
    }

    @Test
    public void testObterProjetoJson()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	String projeto = integrator.obterProjetoTaigaJson("paflopes-sincap");

	assertNotNull(projeto);

	System.out.println(projeto);
    }

    @Test
    public void testObterAndamentoProjetoJson()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	String projeto = integrator.obterAndamentoProjetoTaigaJson("paflopes-sincap");

	assertNotNull(projeto);

	System.out.println(projeto);
    }

    @Test
    public void testObterEstadoProjetoTaiga()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	EstadoProjeto estado = integrator.obterEstadoProjetoTaiga("paflopes-sincap");

	assertNotNull(estado);

	dump(estado);
    }

    @Test
    public void testObterProjetos()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	List<Projeto> projetos = integrator.obterProjetosTaiga();

	assertNotNull(projetos);
	assertNotEquals(projetos.size(), 0);

	dump(projetos);
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

    @Test
    public void testCriarProjetoMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Projeto sincap = integrator.obterProjetoTaiga("paflopes-sincap");

	assertNotNull(sincap);
	assertNotEquals(sincap.getId(), 0);

	dump(sincap);

	org.medcep.model.organizacao.Projeto projeto = integrator.criarProjetoMedCEP(sincap);

	assertNotNull(projeto);
	assertNotEquals(projeto.getId(), "");

	dump(projeto);
    }

    @Test
    public void testCriarMedidasMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");

	List<EstadoProjeto.Medidas> medidasTaiga = new ArrayList<EstadoProjeto.Medidas>();
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_ALOCADOS);
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_ALOCADOS_POR_PAPEL);
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_DEFINIDOS);
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_DEFINIDOS_POR_PAPEL);
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_FECHADOS);
	medidasTaiga.add(EstadoProjeto.Medidas.PONTOS_FECHADOS_POR_PAPEL);
	medidasTaiga.add(EstadoProjeto.Medidas.TOTAL_MARCOS);
	medidasTaiga.add(EstadoProjeto.Medidas.TOTAL_PONTOS);
	medidasTaiga.add(EstadoProjeto.Medidas.VELOCIDADE);

	List<Medida> medidas = integrator.criarMedidasMedCEP(medidasTaiga);

	assertNotNull(medidas);
	assertNotEquals(medidas.size(), 0);
	assertEquals(medidas.size(), 9);

	dump(medidas);
    }

    private void dump(Object object)
    {
	XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
	System.out.println(xstream.toXML(object));
    }

}
