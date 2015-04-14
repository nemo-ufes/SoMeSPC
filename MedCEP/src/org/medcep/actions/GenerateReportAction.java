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
package org.medcep.actions;

import org.apache.commons.logging.*;
import org.openxava.actions.*;
import org.openxava.hibernate.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

/**
 * @author Javier Paniza
 */

public class GenerateReportAction extends TabBaseAction implements IForwardAction
{

    private static Log log = LogFactory.getLog(GenerateReportAction.class);
    private String type;

    @SuppressWarnings("deprecation")
    public void execute() throws Exception
    {
	if (!("pdf".equals(getType()) || "csv".equals(getType())))
	{
	    throw new XavaException("report_type_not_supported", getType(), "pdf, csv");
	}
	
	getRequest().getSession().setAttribute("xava_reportTab", getTab());
	getRequest().getSession().setAttribute("xava_selectedRowsReportTab", getTab().getSelected());
	
	String hibernateDefaultSchema = getHibernateDefaultSchema();
	if (!Is.emptyString(hibernateDefaultSchema))
	{
	    getRequest().getSession().setAttribute("xava_hibernateDefaultSchemaTab", hibernateDefaultSchema);
	}
	if (!Is.emptyString(XPersistence.getDefaultSchema()))
	{
	    getRequest().getSession().setAttribute("xava_jpaDefaultSchemaTab", XPersistence.getDefaultSchema());
	}
	getRequest().getSession().setAttribute("xava_user", Users.getCurrent());
    }

    private String getHibernateDefaultSchema()
    {
	try
	{
	    return XHibernate.getDefaultSchema();
	}
	catch (Exception ex)
	{
	    log.warn(XavaResources.getString("hibernate_default_schema_warning", "__UNKNOWN__"));
	    return "__UNKNOWN__"; // Not null in order to avoid security holes
	}
    }

    public boolean inNewWindow()
    {
	return true;
    }

    public String getForwardURI()
    {
	return "/xava/list." + getType() +
		"?application=" + getRequest().getParameter("application") +
		"&module=" + getRequest().getParameter("module") +
		"&time=" + System.currentTimeMillis();
    }

    public String getType()
    {
	return type;
    }

    public void setType(String string)
    {
	type = string;
    }

}
