package org.medcep.integracao.taiga.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.junit.*;
import org.medcep.integracao.taiga.*;
import org.medcep.integracao.taiga.model.*;

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
	Projeto projeto = integrator.obterProjeto("paflopes-sincap");
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
	List<Projeto> projetos = integrator.obterProjetos();
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
	Membro membro = integrator.obterMembro(4);

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
	Membro membro = integrator.obterMembro(4);
	
	assertNotNull(membro);
	assertNotEquals(membro.getId(), 0);
	
	System.out.println("Id do Membro: " + membro.getId());
	System.out.println("Nome do Membro: " + membro.getNome());
	System.out.println("Papel do Membro: " + membro.getPapel());
	
	String id = integrator.criarRecursoHumanoMedCEP(membro);
	
	assertNotNull(id);
	
	System.out.println("Id do Recurso Humano: " + id);
    }

}
