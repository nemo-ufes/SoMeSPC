package org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

//@Entity
/*@Views({
	@View (members="nome, versao, data; planoDeMedicaoDaOrganizacao; projeto; descricao; recursoHumano; objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name="Simple", members="nome")
})*/
/*@Tabs({
	@Tab(properties="nome, versao")	
})*/
public class Premissa {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; 
	
	private String descricao;
	 
	private Collection<CriterioDeDecisao > criterioDeDecisao ;
	 
}
 
