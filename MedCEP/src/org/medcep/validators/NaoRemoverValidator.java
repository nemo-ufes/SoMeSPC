package org.medcep.validators;

import org.medcep.model.medicao.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class NaoRemoverValidator implements IRemoveValidator
{
    private static final long serialVersionUID = 1L;

    private String nome;

    @Override
    public void setEntity(Object entity) throws Exception
    {
	this.setNome(((TipoDeEntidadeMensuravel) entity).getNome());	
    }

    @Override
    public void validate(Messages errors) throws Exception
    {	
	if (getNome().equalsIgnoreCase("Artefato")
		|| getNome().equalsIgnoreCase("Atividade de Projeto")
		|| getNome().equalsIgnoreCase("Atividade de Padrão")
		|| getNome().equalsIgnoreCase("Ocorrência de Atividade")
		|| getNome().equalsIgnoreCase("Ocorrência de Processo de Software")
		|| getNome().equalsIgnoreCase("Processo de Software em Projeto")
		|| getNome().equalsIgnoreCase("Processo de Software Padrão")
		|| getNome().equalsIgnoreCase("Projeto")
		|| getNome().equalsIgnoreCase("Tipo de Artefato"))
	    errors.add("entidade_nao_pode_ser_removida");
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

}
