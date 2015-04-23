/*
 * MedCEP - A powerful tool for measure
 * 
 * import java.util.*;
 * 
 * import javax.persistence.*;
 * import javax.persistence.Entity;
 * 
 * import org.hibernate.annotations.*;
 * import org.medcep.actions.*;
 * import org.medcep.model.medicao.planejamento.*;
 * import org.medcep.model.processo.*;
 * import org.openxava.annotations.*;
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.medcep.model.processo.comportamento;

import java.util.*;

import javax.persistence.*;

import org.medcep.actions.*;
import org.medcep.model.medicao.*;
import org.medcep.model.processo.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members =
		"capacidade, resultado;"
			+ "data; "
			+ "desempenhoDeProcessoEspecificado; "
	//+ "processoPadrao; " 
	//+ "medida; "
	//+ "Procedimento { procedimentoDeDeterminacaoDeCapacidadeDeProcesso; }"
	//+ "procedimento"
	)
})
public class CapacidadeDeProcesso
{

    @Id
    @SequenceGenerator(name="pk_sequence",sequenceName="seq_capacidade_processo", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
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

    private Date data;

    //@Required
    //@Stereotype("NO_CHANGE")
    //@ReadOnly
    @OnChange(OnChangePropertyDoNothingValorMedido.class)
    //@OnChangeSearch(OnChangeSearchDoNothing.class)
    //@NoSearch
    private float valor;

    //TODO: remover se não usar
    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @Required
    @ReferenceView("Simple")
    @NoCreate
    @SearchAction("AnaliseDeComportamentoDeProcesso.SearchWhereProcessoPadraoEMedidaForCEP")
    //so funciona dentro do analiseDeComportamentoDeProcesso
    private DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado;

    @OneToOne
    //@PrimaryKeyJoinColumn
    private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;

    @ManyToOne
    //@Required
    @ReferenceView("Simple")
    private ProcessoPadrao processoPadrao;


    @ManyToOne
    //@Required
    @ReferenceView("Simple")
    private Medida medida;

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    public float getValor()
    {
	getCapacidade();
	return valor;
    }

    public void setValor(float valor)
    {
	this.valor = valor;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public DesempenhoDeProcessoEspecificado getDesempenhoDeProcessoEspecificado()
    {
	return desempenhoDeProcessoEspecificado;
    }

    public void setDesempenhoDeProcessoEspecificado(
	    DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado)
    {
	this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
    }

    public BaselineDeDesempenhoDeProcesso getBaselineDeDesempenhoDeProcesso()
    {
	return baselineDeDesempenhoDeProcesso;
    }

    public void setBaselineDeDesempenhoDeProcesso(
	    BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso)
    {
	this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
    }

    public ProcessoPadrao getProcessoPadrao()
    {
	return processoPadrao;
    }

    public void setProcessoPadrao(ProcessoPadrao processoPadrao)
    {
	this.processoPadrao = processoPadrao;
    }

  
    public Medida getMedida()
    {
	return medida;
    }

    public void setMedida(Medida medida)
    {
	this.medida = medida;
    }

    /*
     * índice de capacidade, dado por Cp = (LSb – LIb)/(LSe – LIe), onde Cp =
     * Índice de Capacidade, LSb = Limite Superior da Baseline de
     * Desempenho, LIb = Limite Inferior da Baseline de Desempenho, LSe
     * = Limite Superior de Especificação, LIe = Limite Inferior de
     * Especificação. Nesse caso, Cp menor ou igual a 1 indica um processo
     * capaz e Cp maior que 1 indica um processo não capaz.
     * 
     * Cp = (LSb - LIb)/(LSe - LIe);
     * 
     * Cp <= 1 CAPAZ
     * Cp > 1 NÃO CAPAZs
     */

    //@Depends("product.number, quantity")
    @Depends("desempenhoDeProcessoEspecificado")
    public float getCapacidade()
    {
	if (baselineDeDesempenhoDeProcesso != null
		&& desempenhoDeProcessoEspecificado != null
		&& baselineDeDesempenhoDeProcesso.getLimiteDeControle() != null
		&& desempenhoDeProcessoEspecificado.getLimiteDeControle() != null)
	{
	    float LSb = baselineDeDesempenhoDeProcesso.getLimiteDeControle().getLimiteSuperior();
	    float LIb = baselineDeDesempenhoDeProcesso.getLimiteDeControle().getLimiteInferior();

	    float LSe = desempenhoDeProcessoEspecificado.getLimiteDeControle().getLimiteSuperior();
	    float LIe = desempenhoDeProcessoEspecificado.getLimiteDeControle().getLimiteInferior();

	    //valor = (LSb - LIb)/(LSe - LIe);
	    valor = (LSe - LIe) / (LSb - LIb);

	    return valor;
	}

	return valor;
    }

    public String getResultado()
    {
	getCapacidade();
	if (baselineDeDesempenhoDeProcesso != null
		&& desempenhoDeProcessoEspecificado != null
		&& baselineDeDesempenhoDeProcesso.getLimiteDeControle() != null
		&& desempenhoDeProcessoEspecificado.getLimiteDeControle() != null)
	{
	    //if(valor <= 1)
	    if (valor >= 1)
	    {
		return "É capaz.";
	    }
	    return "Não é capaz.";
	}
	return "Salve os dados para saber o resultado.";

    }

    public String getCapaz()
    {
	//if(valor <= 1)
	if (valor >= 1)
	{
	    return "É capaz.";
	}
	return "Não é capaz.";
    }
}
