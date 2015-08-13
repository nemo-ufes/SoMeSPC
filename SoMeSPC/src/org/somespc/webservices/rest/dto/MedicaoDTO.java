package org.somespc.webservices.rest.dto;

import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.*;

import org.somespc.webservices.rest.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicaoDTO
{
    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "data")
    @XmlJavaTypeAdapter(DataHoraAdapter.class)
    private Date data;

    @XmlElement(name = "valor_medido")
    private String valorMedido;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    public String getValorMedido()
    {
	return valorMedido;
    }

    public void setValorMedido(String valorMedido)
    {
	this.valorMedido = valorMedido;
    }

}
