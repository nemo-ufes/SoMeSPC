/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

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

package org.openxava.mestrado.model.MedicaoDeSoftware.Medicao;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members="valorMedido")
public class ValorNumerico extends ValorMedido {
    
	private float valorNumerico;
	
	public float getValorNumerico() {
		return valorNumerico;
	}

	public void setValorNumerico(float valorNumerico) {
		this.valorNumerico = valorNumerico;
	}

	public String getValorMedido()
	{
		return this.valorMedido;	
	}
	
	public void setValorMedido(String valorMedido)
	{
		super.setValorMedido(valorMedido);
		valorNumerico = (Float.parseFloat(valorMedido.replace(',', '.')));
	}
	
}
 
