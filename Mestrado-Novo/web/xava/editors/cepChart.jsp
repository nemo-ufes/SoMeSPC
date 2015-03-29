<%@page import="java.util.Locale"%>
<%@ include file="../imports.jsp"%>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>
<jsp:useBean id="errors" class="org.openxava.util.Messages" scope="request"/>
<jsp:useBean id="style" class="org.openxava.web.style.Style" scope="request"/>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.openxava.util.Maps" %>
<%@ page import="org.openxava.util.Is" %>
<%@ page import="org.openxava.util.XavaPreferences" %>
<%@ page import="org.openxava.view.View" %>
<%@ page import="org.openxava.model.meta.MetaProperty" %>
<%@ page import="org.openxava.model.meta.MetaReference" %>
<%@ page import="org.openxava.web.WebEditors" %>

<%@page import="java.lang.Float"%>
<%@page import="java.lang.Exception"%>
<%@page import="org.openxava.util.Locales"%>
<%@page import="org.openxava.view.View"%>
<%@page import="org.openxava.model.MapFacade"%>
<%@page import="java.util.Map"%>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%@page import="java.text.DateFormat"%>
<%@page import="org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.Medicao"%>
<%@page import="org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao.AnaliseDeMedicao"%>
<%@page import="org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.AnaliseDeComportamentoDeProcesso"%>
<%@page import="org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.LimiteDeControle"%>

<% 
	String viewObject2 = request.getParameter("viewObject");
	View collectionView2 = (View) context.get(request, viewObject2);
	View rootView2 = collectionView2.getRoot();
	Map key2 = rootView2.getKeyValues();
	if (Is.empty(key2) == false) 
	{
		AnaliseDeComportamentoDeProcesso analiseDeComportamentoDeProcesso = null;
		try {
			analiseDeComportamentoDeProcesso = (AnaliseDeComportamentoDeProcesso) MapFacade.findEntity("AnaliseDeComportamentoDeProcesso", key2);
		}catch(Exception e){}
		
		//se a escala for numerica e tiver alguma medicao
		if(analiseDeComportamentoDeProcesso != null 
		   && analiseDeComportamentoDeProcesso.getAnaliseDeMedicao() != null 
		   && analiseDeComportamentoDeProcesso.getAnaliseDeMedicao().getMedicao().size() > 0 
		   && analiseDeComportamentoDeProcesso.getAnaliseDeMedicao().getMedidaPlanoDeMedicao().getMedida().getEscala().isNumerico())
		{
			String datas = "[";
			String limiteSuperiorBaseline = "[";
			String limiteInferiorBaseline = "[";
			String limiteSuperiorDesempenhoEspecificado = "[";
			String limiteInferiorDesempenhoEspecificado = "[";
			String lc = "[";
			String valores = "[";
			String toolTips = "[";

			//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locales.getCurrent());
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
			
			//Para ajustar no liferay a data foi adicionada a seguinte linha no arquivo: \liferay-portal-6.0.6\tomcat-6.0.29\webapps\ROOT\html\portlet\asset_publisher\init.jsp
			//Format dateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("dd-MMM-yyyy");
			
			ArrayList<Medicao> lstMedicao = new ArrayList<Medicao>(analiseDeComportamentoDeProcesso.getAnaliseDeMedicao().getMedicao());
			
			Collections.sort(lstMedicao);
						
			for(Medicao medicao : lstMedicao)
			{
				datas += "'" + df.format(medicao.getData()) + "', ";
				valores += medicao.getValorMedido().getValorMedido().replace(',', '.') + ", ";
				toolTips += "'" + medicao.getValorMedido().getValorMedido() + "', ";
			}
			
			LimiteDeControle l = (LimiteDeControle)analiseDeComportamentoDeProcesso.retornaLimites();
			
			String auxLS = Float.toString(l.getLimiteSuperior());
			auxLS = auxLS.replace(',', '.');
			String auxLI = Float.toString(l.getLimiteInferior());
			auxLI = auxLI.replace(',', '.');
			String auxLC = Float.toString(l.getLimiteCentral());
			auxLC = auxLC.replace(',', '.');
			
			for(Medicao medicao : lstMedicao)
			{
				limiteSuperiorBaseline += auxLS + ", ";
				limiteInferiorBaseline += auxLI + ", ";
				lc += auxLC + ", ";
			}
			
			/*
			if(analiseDeComportamentoDeProcesso.getBaselineDeDesempenhoDeProcesso() != null
				&& analiseDeComportamentoDeProcesso.getBaselineDeDesempenhoDeProcesso().getLimiteDeControle() != null)
			{
				String auxLS = Float.toString(analiseDeComportamentoDeProcesso.getBaselineDeDesempenhoDeProcesso().getLimiteDeControle().getLimiteSuperior());
				auxLS = auxLS.replace(',', '.');
				String auxLI = Float.toString(analiseDeComportamentoDeProcesso.getBaselineDeDesempenhoDeProcesso().getLimiteDeControle().getLimiteInferior());
				auxLI = auxLI.replace(',', '.');
				
				for(Medicao medicao : lstMedicao)
				{
					limiteSuperiorBaseline += auxLS + ", ";
					limiteInferiorBaseline += auxLI + ", ";
				}					
			}
			
			if(analiseDeComportamentoDeProcesso.getCapacidadeDeProcesso() != null
				&& analiseDeComportamentoDeProcesso.getCapacidadeDeProcesso().getDesempenhoDeProcessoEspecificado() != null
				&& analiseDeComportamentoDeProcesso.getCapacidadeDeProcesso().getDesempenhoDeProcessoEspecificado().getLimiteDeControle() != null)
			{
				String auxLS = Float.toString(analiseDeComportamentoDeProcesso.getCapacidadeDeProcesso().getDesempenhoDeProcessoEspecificado().getLimiteDeControle().getLimiteSuperior());
				auxLS = auxLS.replace(',', '.');
				String auxLI = Float.toString(analiseDeComportamentoDeProcesso.getCapacidadeDeProcesso().getDesempenhoDeProcessoEspecificado().getLimiteDeControle().getLimiteInferior());
				auxLI = auxLI.replace(',', '.');
				
				for(Medicao medicao : lstMedicao)
				{
					limiteSuperiorDesempenhoEspecificado += auxLS + ", ";
					limiteInferiorDesempenhoEspecificado += auxLI + ", ";
				}					
			}*/
			
			String center = "false";
			if(l.getLimiteInferior() < 0)
			{
				center = "true";
			}
			
			datas += "]";
			valores += "]";
			limiteSuperiorBaseline += "]";
			limiteInferiorBaseline += "]";
			limiteSuperiorDesempenhoEspecificado += "]";
			limiteInferiorDesempenhoEspecificado += "]";
			lc += "]";
			toolTips += "]";
			
			datas = datas.replaceAll(", ]", "]");
			valores = valores.replaceAll(", ]", "]");
			limiteSuperiorBaseline = limiteSuperiorBaseline.replaceAll(", ]", "]");
			limiteInferiorBaseline = limiteInferiorBaseline.replaceAll(", ]", "]");
			limiteSuperiorDesempenhoEspecificado = limiteSuperiorDesempenhoEspecificado.replaceAll(", ]", "]");
			limiteInferiorDesempenhoEspecificado = limiteInferiorDesempenhoEspecificado.replaceAll(", ]", "]");
			lc = lc.replaceAll(", ]", "]");
			toolTips = toolTips.replaceAll(", ]", "]");
		
%>	

<script>

	var datas = <%= datas %>;
	var valores = <%= valores %>;
	var limiteSuperiorBaseline = <%= limiteSuperiorBaseline %>;
	var limiteInferiorBaseline = <%= limiteInferiorBaseline %>;
	var limiteSuperiorDesempenhoEspecificado = <%= limiteSuperiorDesempenhoEspecificado %>;
	var limiteInferiorDesempenhoEspecificado = <%= limiteInferiorDesempenhoEspecificado %>;
	var lc = <%= lc %>;
	var toolTips = <%= toolTips %>;
	var display = false;//para evitar que execute duas vezes (1 script 2 load event)
	var center = <%= center %>;
	
	var fGerarGrafico = 
		function() {
			if(valores && display==false){
				if(document.getElementById('canvasGraficoCEP'))
				{
		        	//var line = new RGraph.Line('canvasGrafico', valores, limiteSuperiorBaseline, limiteInferiorBaseline, limiteSuperiorDesempenhoEspecificado, limiteInferiorDesempenhoEspecificado);
	        	
		        	var line = new RGraph.Line('canvasGraficoCEP', valores, limiteSuperiorBaseline, limiteInferiorBaseline, limiteSuperiorDesempenhoEspecificado, limiteInferiorDesempenhoEspecificado, lc);
		        	line.Set('chart.labels', <%= datas %>);
			        line.Set('chart.tooltips', <%= toolTips %>);
			        line.Set('chart.linewidth', 4);
					line.Set('chart.scale.decimals', 2);
					line.Set('chart.hmargin', 2);
					line.Set('chart.text.angle', 50);
					line.Set('chart.gutter.left', 50);
					line.Set('chart.gutter.bottom', 65);
					line.Set('chart.colors', ['rgba(164, 222, 0, 255)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)']);
			        
					var line2 = new RGraph.Line('canvasGraficoCEP', valores, limiteSuperiorBaseline, limiteInferiorBaseline, limiteSuperiorDesempenhoEspecificado, limiteInferiorDesempenhoEspecificado, lc);
					//var line2 = new RGraph.Line('canvasGraficoCEP', valores, limiteSuperiorDesempenhoEspecificado, limiteInferiorDesempenhoEspecificado);
					line2.Set('chart.labels', <%= datas %>);
					line2.Set('chart.tooltips', <%= toolTips %>);
			        line2.Set('chart.linewidth', 0.5);
			        line2.Set('chart.background.grid', false);
			        line2.Set('chart.scale.decimals', 2);
					line2.Set('chart.hmargin', 2);
					line2.Set('chart.text.angle', 50);
					line2.Set('chart.gutter.left', 50);
					line2.Set('chart.gutter.bottom', 65);
					//line2.Set('chart.colors', ['rgba(0, 0, 0, 0)', '#00f', '#00f', '#f00', '#f00']);
					line2.Set('chart.colors', ['rgba(0, 0, 0, 0)', '#f00', '#f00', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', '#00f']);

					if(center == true)
					{
						line.Set('xaxispos', 'center');
						line2.Set('xaxispos', 'center');
					}
					
/* 		            line.ondraw = function (obj)
		            {
		                //var ylimiteSuperiorBaseline = Math.round(obj.getYCoord(limiteSuperiorBaseline));
		                var ylimiteSuperiorBaseline = obj.getYCoord(0.5);
		                
		                //var ylimiteInferiorBaseline = Math.round(obj.getYCoord(limiteInferiorBaseline));
		                var ylimiteInferiorBaseline = obj.getYCoord(limiteInferiorBaseline);
		                //var ylimiteInferiorBaseline = 40;
		                var x1 = obj.Get('chart.gutter.left');
		                var x2 = obj.canvas.width - obj.Get('chart.gutter.right');

		                obj.context.beginPath();
		                    obj.context.strokeStyle = 'orange';
		                    RGraph.DashedLine(obj.context, x1, ylimiteSuperiorBaseline, x2, ylimiteSuperiorBaseline);
		                    //RGraph.DashedLine(obj.context, x1, ylimiteInferiorBaseline, x2, ylimiteInferiorBaseline);
		                obj.context.stroke();
		                
		                obj.context.beginPath();
	                    	obj.context.strokeStyle = 'red';
	                    	//RGraph.DashedLine(obj.context, x1, ylimiteSuperiorBaseline, x2, ylimiteSuperiorBaseline);
	                    	RGraph.DashedLine(obj.context, x1, ylimiteInferiorBaseline, x2, ylimiteInferiorBaseline);
	                	obj.context.stroke();
		            } */
					
					//line.Set('chart.tickmarks', 'dot');
			        //RGraph.Effects.Line.Trace2(line2);
			        //RGraph.Effects.Line.Trace2(line);
			        line.Draw();
			        line2.Draw();
			        
			        display=true;
				}
			}
		};

	fGerarGrafico();

	//para funcionar o F5 a função é adicionada no init do openxava	
	openxava.addEditorInitFunction(fGerarGrafico);

</script>

<canvas id="canvasGraficoCEP" width="850" height="280">[No canvas support]</canvas>

<br />
<input type="button" value="Atualizar gráfico com dados salvos" onClick="window.location.href=window.location.href"/>    
<br />

<%
		}//if(analiseDeMedicao.getMedicao().size() > 0)
	}//if (Is.empty(key) == false) 
%>
