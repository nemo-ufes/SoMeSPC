package org.medcep.wizard;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class WizardHelper extends HttpServlet
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String nome;

    private String URL;

    private String password;

    public WizardHelper()
    {

    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getURL()
    {
	return URL;
    }

    public void setURL(String uRL)
    {
	URL = uRL;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException
    {
	
	String nome = req.getParameter("parametroNome").toString();
	String password = req.getParameter("parametroSenha").toString();
	
	WizardHelper helper = new WizardHelper();
	helper.setNome(nome);	
	helper.setPassword(password);
	
	resp.setContentType("text/plain");  
	resp.setCharacterEncoding("UTF-8"); 
	PrintWriter out = resp.getWriter();
	out.println("Deu certo!");
	out.close();
	
	 //req.getRequestDispatcher().forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
	PrintWriter out = resp.getWriter();
        out.println("Servlet do Wizard funcionando!");        
    }
    
}
