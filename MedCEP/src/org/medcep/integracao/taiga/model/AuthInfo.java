/*
 * MedCEP - A powerful tool for measure
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
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
package org.medcep.integracao.taiga.model;

import javax.xml.bind.annotation.*;

/**
 * Classe para mapear as informações de autenticação com o Taiga.
 * 
 * @author Vinicius
 *
 */
@XmlRootElement
public class AuthInfo
{
    //TODO: Avaliar implementação do tipo Github.
    private String type = "normal";
    private String username;
    private String password;

    public AuthInfo()
    {

    }

    public AuthInfo(String username, String password)
    {
	this.username = username;
	this.password = password;
    }

    public String getType()
    {
	return type;
    }

    public String getUsername()
    {
	return username;
    }

    public void setUsername(String username)
    {
	this.username = username;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

}