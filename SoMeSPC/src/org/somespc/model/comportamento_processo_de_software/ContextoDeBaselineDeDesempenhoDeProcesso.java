/*
 * SoMeSPC - powerful tool for measurement
 * 
 * import javax.persistence.*;
 * import javax.persistence.Entity;
 * 
 * import org.hibernate.annotations.*;
 * import org.openxava.annotations.*;
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
package org.somespc.model.comportamento_processo_de_software;

import javax.persistence.*;

import org.openxava.annotations.*;

/**
 * Primeira baseline de desempenho estabelecida para o
 * processo de Gerência de Requisitos, tendo sido o processo
 * executado em 6 projetos pequenos, cujas equipes foram
 * compostas pelos mesmos recursos humanos, sob
 * condições usuais tendo sido desconsiderados dois pontos
 * fora dos limites de controle, por caracterizarem situações
 * de ocorrência excepcional.
 */
@Entity
public class ContextoDeBaselineDeDesempenhoDeProcesso
{

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="CTX_BASELINE_DES_PROC_ID", valueColumnName="ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GENERATOR")
     @Hidden
    private Integer id;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

}
