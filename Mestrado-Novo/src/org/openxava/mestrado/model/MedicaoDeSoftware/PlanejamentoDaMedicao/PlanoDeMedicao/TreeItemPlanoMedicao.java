package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao;

import javax.persistence.*;
import javax.xml.ws.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.actions.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;

@Entity
@Views({
	@View(members=
			//"path;"
			" item;"
			//+ " path;"
			//+ " treeOrder;"
	)
})
public class TreeItemPlanoMedicao {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	//@SearchAction("PlanoTreeItem.searchPlanoTreeItemNome")//Seria o ideal algo do tipo sem ter que fazer um ReferenceSearch2Action
	private TreeItemPlanoMedicaoBase item;

	public TreeItemPlanoMedicaoBase getItem() {
		return item;
	}

	public void setItem(TreeItemPlanoMedicaoBase item) {
		this.item = item;
	}

	
	@Column(length=700)
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	@ManyToOne
	private PlanoDeMedicao planoDeMedicaoContainer;

	public PlanoDeMedicao getPlanoDeMedicaoContainer() {
		return planoDeMedicaoContainer;
	}

	public void setPlanoDeMedicaoContainer(PlanoDeMedicao planoDeMedicaoContainer) {
		this.planoDeMedicaoContainer = planoDeMedicaoContainer;
	}


	@PreCreate
	@PreUpdate
	public void ajustes()
	{
		if(item != null) {
			if(item instanceof ObjetivoEstrategico)
			{
				this.nome = "OE - " + item.getNome();		
			}
			else if(item instanceof ObjetivoDeSoftware)
			{
				this.nome = "OS - " + item.getNome();
			}
			else if(item instanceof ObjetivoDeMedicao)
			{
				this.nome = "OM - " + item.getNome();
			}
			else if(item instanceof NecessidadeDeInformacao)
			{
				this.nome = "NI - " + item.getNome();
			}
			else if(item instanceof Medida)
			{
				this.nome = "ME - " + item.getNome();
			}
/*			else if(item instanceof DefinicaoOperacionalDeMedida)
			{
				this.nome = "DE - " + item.getNome();
			}
*/			else
			{
				this.nome = item.getNome();
			}
		}
		if(NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject != null)
		{
			TreeItemPlanoMedicao pti = (TreeItemPlanoMedicao)NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject;
			path = pti.getPath()+ "/" + pti.getId();
		}
		NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject = null;
	}
	
	
	@Override
	public String toString() {
		return id.toString();
	}
}
