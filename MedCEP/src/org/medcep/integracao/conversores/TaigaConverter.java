package org.medcep.integracao.conversores;

import org.medcep.integracao.taiga.model.*;
import org.medcep.model.organizacao.*;

public class TaigaConverter
{
    /**
     * Converte um Membro do Taiga para um RecursoHumano da MedCEP.
     * @param membro - Membro a ser convertido.
     * @return RecursoHumano convertido.
     */
    public static RecursoHumano converterMembroParaRecursoHumano(Membro membro)
    {
	RecursoHumano recursoHumano = new RecursoHumano();
	recursoHumano.setNome(membro.getNome());
	return recursoHumano;
    }
    
    /**
     * Converte um Membro do Taiga para um PapelRecursoHumano da MedCEP.
     * @param membro - Membro a ser convertido.
     * @return PapelRecursoHumano convertido.
     */
    public static PapelRecursoHumano converterMembroParaPapelRecursoHumano(Membro membro)
    {
	PapelRecursoHumano papel = new PapelRecursoHumano();
	papel.setNome(membro.getPapel());
	return papel;
    }

}
