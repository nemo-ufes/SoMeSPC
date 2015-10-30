package org.somespc.integracao.taiga.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.somespc.integracao.util.TaigaDateAdapter;

/**
 * Projeto do Taiga.
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class Projeto {

	@XmlElement(name = "name")
	private String nome;

	@XmlElement(name = "slug")
	private String apelido;

	@XmlElement(name = "description")
	private String descricao;

	@XmlElement(name = "memberships")
	private List<Membro> equipe;

	@XmlElement(name = "created_date")
	@XmlJavaTypeAdapter(TaigaDateAdapter.class)
	private Date dataCriacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Membro> getEquipe() {
		return equipe;
	}

	public void setEquipe(List<Membro> equipe) {
		this.equipe = equipe;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
