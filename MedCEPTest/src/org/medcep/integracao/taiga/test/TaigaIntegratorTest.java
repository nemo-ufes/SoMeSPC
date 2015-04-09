package org.medcep.integracao.taiga.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.*;
import org.medcep.integracao.taiga.*;

public class TaigaIntegratorTest
{

    @Test
    public void testObterAuthToken()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("https://api.taiga.io/", "vinnysoft", "teste123");
	String token = integrator.obterAuthToken();
	System.out.println("token: " + token);
	assertNotNull(token);
	assertNotEquals(token, "");
	
    }

}
