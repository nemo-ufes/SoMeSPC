package org.medcep.integracao.conversores;

import org.medcep.integracao.taiga.model.*;
import org.medcep.model.organizacao.*;

public class TaigaConverter
{
    public static RecursoHumano converterMembroParaRecursoHumano(Membro membro)
    {
	RecursoHumano recursoHumano = new RecursoHumano();
	recursoHumano.setNome(membro.getNome());
	return recursoHumano;
    }

}
