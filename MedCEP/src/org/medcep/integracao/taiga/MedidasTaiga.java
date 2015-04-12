package org.medcep.integracao.taiga;

public enum MedidasTaiga
{
    //Medidas do Projeto
    PONTOS_ALOCADOS_PROJETO,    
    PONTOS_DEFINIDOS_PROJETO,    
    PONTOS_FECHADOS_PROJETO,    
    TOTAL_SPRINTS_PROJETO,
    TOTAL_PONTOS_PROJETO,
    VELOCIDADE_PROJETO,

    //Medidas do Recurso Humano na Equipe (Mede a Alocação)
    PONTOS_ALOCADOS_POR_PAPEL_PROJETO,
    PONTOS_DEFINIDOS_POR_PAPEL_PROJETO,
    PONTOS_FECHADOS_POR_PAPEL_PROJETO,
    
    //Medidas da Sprint
    DOSES_IOCAINE_SPRINT,
    DURACAO_SPRINT, //Data Fim - Data Inicio
    ESTORIAS_COMPLETADAS_SPRINT,
    PONTOS_COMPLETADOS_SPRINT,
    TAREFAS_COMPLETADAS_SPRINT,
    TOTAL_ESTORIAS_SPRINT,
    TOTAL_PONTOS_SPRINT,
    TOTAL_TAREFAS_SPRINT,
        
    //Medidas da Estória
    DURACAO_ESTORIA, //Data Fim - Data Inicio
    PONTOS_ESTORIA
    
};