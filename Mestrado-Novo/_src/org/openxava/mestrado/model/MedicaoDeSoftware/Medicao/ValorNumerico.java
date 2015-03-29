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
 
