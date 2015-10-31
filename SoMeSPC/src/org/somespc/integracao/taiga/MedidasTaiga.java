/*
 * SoMeSPC - powerful tool for measurement
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
package org.somespc.integracao.taiga;

import java.util.*;

public enum MedidasTaiga
{
    //OM - Monitorar a conclusão de pontos de estória nos projetos - DESEMPENHO
			
		    PONTOS_ESTORIA_PLANEJADOS_PROJETO("ME - Pontos de Estória Planejados para o Projeto"),
		    PONTOS_ESTORIA_ALOCADOS_PROJETO("ME - Pontos de Estória Alocados no Projeto"),
		    PONTOS_ESTORIA_CONCLUIDOS_PROJETO_CONCLUSAO("ME - Pontos de Estória Concluídos no Projeto"),
		    TAXA_CONCLUSAO_PONTOS_ESTORIA_PROJETO("ME – Taxa de Conclusão de Pontos de Estória no Projeto"),
		    
	//OM – Monitorar a realização de sprints nos projetos TAMANHO
		    
		   	NUMERO_SPRINTS_PLANEJADAS_PROJETO("ME - Número de Sprints Planejadas para o Projeto"),
		   	NUMERO_SPRINTS_REALIZADA_PROJETO("ME - Número de Sprints Realizadas no Projeto"),
		   	TAXA_CONCLUSAO_SPRINTS_PROJETO("ME - Taxa de Conclusão de Sprints no Projeto"),
		   	
    //OM – Monitorar desempenho na sprint - DESEMPENHO
    		
    		NUMERO_ESTORIAS_PLANEJADAS_SPRINT("ME - Número de Estórias Planejadas para a Sprint"),
    		NUMERO_ESTORIAS_CONCLUIDAS_SPRINT("ME - Número de Estórias Concluídas na Sprint"),
    		TAXA_CONCLUSAO_ESTORIAS_SPRINT("ME - Taxa de Conclusão de Estórias na Sprint"),
    		PONTOS_ESTORIAS_PLANEJADOS_SPRINT("ME - Pontos de Estória Planejados para a Sprint"),
    		PONTOS_ESTORIAS_CONCLUIDAS_SPRINT("ME – Pontos de Estória Concluídos na Sprint"),
    		TAXA_CONCLUSAO_PONTOS_ESTORIAS_SPRINT("ME - Taxa de Conclusão de Pontos de Estórias na Sprint"),
    		NUMERO_TAREFAS_PLANEJADAS_SPRINT("ME - Número de Tarefas Planejadas para a Sprint"),
    		NUMERO_TAREFAS_CONCLUIDAS_SPRINT("ME - Número de Tarefas Concluídas na Sprint"),
    		TAXA_CONCLUSAO_TAREFAS_SPRINT("ME - Taxa de Conclusão de Tarefas na Sprint"),
 
    	    //OM – Monitorar desempenho no projeto - DESEMPENHO
        	
    		NUMERO_SPRINTS_REALIZADAS_PROJETO("ME - Número de Sprints Realizadas no Projeto"),
    		NUMERO_ESTORIAS_CONCLUIDAS_PROJETO("ME - Número de Estórias Concluídas para o Projeto"),
    		MEDIA_ESTORIAS_CONCLUIDAS_SPRINT_PROJETO("ME - Média de Estórias Concluídas por Sprint do Projeto"),
    		PONTOS_ESTORIA_CONCLUIDOS_PROJETO_DESEMPENHO("ME - Pontos de Estória Concluídos no Projeto"),
    		VELOCIDADE_PROJETO("ME - Velocidade da Equipe no Projeto"),
	
	//OM – Monitorar quantidade de doses de locaine nas sprints -TAMANHO

			NUMERO_IOCAINE_SPRINT("ME - Número de Doses de Iocaine na Sprint"),		
			NUMERO_TAREFAS_REALIZADAS_SPRINT("ME - Número de Tarefas Realizadas na Sprint"),
			TAXA_IOCAINE_SPRINT("ME - Taxa de Doses de Iocaine na Sprint"),

	//OM – Monitorar velocidade da equipe no projeto - DESEMPENHO
	
			VELOCIDADE_EQUIPE_PROJETO("ME - Velocidade da Equipe no Projeto");

    private final String name;

    private MedidasTaiga(String s)
    {
	name = s;
	this.descricao = s;
    }

    public boolean equalsName(String otherName)
    {
	return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString()
    {
	return name;
    }

    private final String descricao;
    private static final Map<String, MedidasTaiga> lookup = new HashMap<String, MedidasTaiga>();
    static
    {
	for (MedidasTaiga d : MedidasTaiga.values())
	    lookup.put(d.getDescricao(), d);
    }

    public String getDescricao()
    {
	return descricao;
    }

    public static MedidasTaiga get(String descricao)
    {
	return lookup.get(descricao);
    }

};