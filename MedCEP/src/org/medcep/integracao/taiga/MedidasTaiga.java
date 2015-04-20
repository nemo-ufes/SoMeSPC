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
    PONTOS_ALOCADOS_PROJETO("Pontos Alocados no Projeto"),    
    PONTOS_DEFINIDOS_PROJETO("Pontos Definidos no Projeto"),    
    PONTOS_FECHADOS_PROJETO("Pontos Fechados no Projeto"),    
    TOTAL_SPRINTS_PROJETO("Total de Sprints do Projeto"),
    TOTAL_PONTOS_PROJETO("Total de Pontos do Projeto"),
    VELOCIDADE_PROJETO("Velocidade do Projeto"),

    //Medidas do Recurso Humano na Equipe (Mede a Alocação)
    PONTOS_ALOCADOS_POR_PAPEL_PROJETO("Pontos Alocados por Papel no Projeto"),
    PONTOS_DEFINIDOS_POR_PAPEL_PROJETO("Pontos Definidos por Papel no Projeto"),
    PONTOS_FECHADOS_POR_PAPEL_PROJETO("Pontos Fechados por Papel no Projeto"),
    
    //Medidas da Sprint
    DOSES_IOCAINE_SPRINT("Doses de Iocaine da Sprint"),
    DURACAO_SPRINT("Duração da Sprint"), //Data Fim - Data Inicio
    ESTORIAS_COMPLETADAS_SPRINT("Estórias Completadas na Sprint"),
    PONTOS_COMPLETADOS_SPRINT("Pontos Completados na Sprint"),
    TAREFAS_COMPLETADAS_SPRINT("Tarefas Completadas na Sprint"),
    TOTAL_ESTORIAS_SPRINT("Total de Estórias da Sprint"),
    TOTAL_PONTOS_SPRINT("Total de Pontos da Sprint"),
    TOTAL_TAREFAS_SPRINT("Total de Tarefas da Sprint"),
        
    //Medidas da Estória
    DURACAO_ESTORIA("Duração da Estória"), //Data Fim - Data Inicio
    PONTOS_ESTORIA("Pontos da Estória");
    
    private final String name;       

    private MedidasTaiga(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
    
};