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