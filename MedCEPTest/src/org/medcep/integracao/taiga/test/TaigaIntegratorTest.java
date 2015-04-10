package org.medcep.integracao.taiga.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.junit.*;
import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;
import org.medcep.integracao.taiga.model.Projeto;
import org.medcep.model.organizacao.*;

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

	System.out.println("Id: " + projeto.getId());
	System.out.println("Nome: " + projeto.getNome());
	System.out.println("Descricao: " + projeto.getDescricao());

	System.out.println("Membros do projeto " + projeto.getNome());
	for (Membro membro : projeto.getMembros())
	{
	    System.out.println("Id do Membro: " + membro.getId());
	    System.out.println("Nome do Membro: " + membro.getNome());
	    System.out.println("Papel do Membro: " + membro.getPapel());
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
	    System.out.println("Id: " + proj.getId());
	    System.out.println("Nome: " + proj.getNome());
	    System.out.println("Descricao: " + proj.getDescricao());
	    System.out.println("----------------------------------------");
	}
    }

    @Test
    public void testObterMembro()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);

	System.out.println("Id do Membro: " + membro.getId());
	System.out.println("Nome do Membro: " + membro.getNome());
	System.out.println("Papel do Membro: " + membro.getPapel());

	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);
    }
    
    @Test
    public void testCriarRecursoHumanoMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);
	
	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);
	
	System.out.println("Id do Membro: " + membro.getId());
	System.out.println("Nome do Membro: " + membro.getNome());
	System.out.println("Papel do Membro: " + membro.getPapel());
	
	RecursoHumano recurso = integrator.criarRecursoHumanoMedCEP(membro);
	
	assertNotNull(recurso);
	
	System.out.println("Id do Recurso Humano: " + recurso.getId());
    }
    
    @Test
    public void testCriarPapelRecursoHumanoMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Membro membro = integrator.obterMembroTaiga(4);
	
	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);
	
	System.out.println("Id do Membro: " + membro.getId());
	System.out.println("Nome do Membro: " + membro.getNome());
	System.out.println("Papel do Membro: " + membro.getPapel());
	
	PapelRecursoHumano papel = integrator.criarPapelRecursoHumanoMedCEP(membro);
	
	assertNotNull(papel);
	
	System.out.println("Id do Papel de Recurso Humano: " + papel.getId());
    }
    
    @Test
    public void testCriarEquipeMedCEP() throws Exception
    {
	TaigaIntegrator integrator = new TaigaIntegrator("http://ledsup.sr.ifes.edu.br/", "vinnysoft", "teste123");
	Projeto sincap = integrator.obterProjetoTaiga("paflopes-sincap");
	
	assertNotNull(sincap);
	assertNotEquals(sincap.getId(), 0);
	assertNotNull(sincap.getMembros());
	
	System.out.println("Id: " + sincap.getId());
	System.out.println("Nome: " + sincap.getNome());
	System.out.println("Descricao: " + sincap.getDescricao());

	Equipe equipe = integrator.criarEquipeMedCEP(sincap.getNome() + " Team", sincap.getMembros());
	
	assertNotNull(equipe);
	
	System.out.println("Id da Equipe : " + equipe.getId());
    }
    

}
