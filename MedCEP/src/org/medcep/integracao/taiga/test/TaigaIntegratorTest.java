package org.medcep.integracao.taiga.test;

import org.junit.*;
import org.medcep.integracao.taiga.*;

public class TaigaIntegratorTest
{

    @Test
    public void testObterAuthToken()
    {
	TaigaIntegrator integrator = new TaigaIntegrator("https://api.taiga.io/", "vinnysoft", "teste123");
	integrator.obterAuthToken();
    }

}
