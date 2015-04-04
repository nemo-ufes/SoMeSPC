/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */
package org.medcep.model.medicao;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

/**
 * N�meros inteiros positivos; baixo, medio, alto;
 */
@Entity
@Views({ @View(members = "valor; numerico"),
		@View(name = "Simple", members = "nome") })
@Tabs({ @Tab(properties = "valor, numerico", defaultOrder = "${valor} asc") })
public class ValorDeEscala {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@Hidden
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(length = 500, unique = true)
	@Required
	private String valor;

	private boolean numerico;

	@ManyToMany
	@JoinTable(name = "escala_valorDeEscala", joinColumns = { @JoinColumn(name = "valorDeEscala_id") }, inverseJoinColumns = { @JoinColumn(name = "escala_id") })
	private Collection<Escala> escala;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isNumerico() {
		return numerico;
	}

	public void setNumerico(boolean numerico) {
		this.numerico = numerico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<Escala> getEscala() {
		return escala;
	}

	public void setEscala(Collection<Escala> escala) {
		this.escala = escala;
	}

}
