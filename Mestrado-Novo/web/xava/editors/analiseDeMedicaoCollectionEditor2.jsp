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

<%@page import="java.lang.String"%>
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

<% 
	String viewObject2 = request.getParameter("viewObject");
	View collectionView2 = (View) context.get(request, viewObject2);
	View rootView2 = collectionView2.getRoot();
	Map key2 = rootView2.getKeyValues();
	if (Is.empty(key2) == false) 
	{
		AnaliseDeMedicao analiseDeMedicao = null;
		try {
			analiseDeMedicao = (AnaliseDeMedicao) MapFacade.findEntity("AnaliseDeMedicao", key2);
		}catch(Exception e){}
		
		//se a escala for numerica e tiver alguma medicao
		if(analiseDeMedicao != null && analiseDeMedicao.getMedicao().size() > 0 
		   && analiseDeMedicao.getMedidaPlanoDeMedicao().getMedida().getEscala().isNumerico())
		{
			String datas = "[";
			String valores = "[";
			String toolTips = "[";

			//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locales.getCurrent());
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
			
			//Para ajustar no liferay a data foi adicionada a seguinte linha no arquivo: \liferay-portal-6.0.6\tomcat-6.0.29\webapps\ROOT\html\portlet\asset_publisher\init.jsp
			//Format dateFormatDate = FastDateFormatFactoryUtil.getSimpleDateFormat("dd-MMM-yyyy");
			
			ArrayList<Medicao> lstMedicao = new ArrayList<Medicao>(analiseDeMedicao.getMedicao());
			
			Collections.sort(lstMedicao);
						
			for(Medicao medicao : lstMedicao)
			{
				datas += "'" + df.format(medicao.getData()) + " ', ";
				valores += medicao.getValorMedido().getValorMedido().replace(',', '.') + ", ";
				toolTips += "'" + medicao.getValorMedido().getValorMedido() + "', ";
			}
			
			datas += "]";
			valores += "]";
			toolTips += "]";
			
			datas = datas.replaceAll(", ]", "]");
			valores = valores.replaceAll(", ]", "]");
			toolTips = toolTips.replaceAll(", ]", "]");
		
%>	

<script>

	var datas = <%= datas %>;
	var valores = <%= valores %>;
	var toolTips = <%= toolTips %>;
	
	var graficoLinha = false;
	
	var fGerarGrafico = 
		function() {
			if(valores)
			{
				if(document.getElementById('canvasGrafico'))
				{
					if(graficoLinha)
					{
			        	var line = new RGraph.Line('canvasGrafico', valores);
				        line.Set('chart.labels', <%= datas %>);
				        line.Set('chart.tooltips', <%= toolTips %>);
						
				        line.Set('chart.linewidth', 4);
						line.Set('chart.scale.decimals', 2);
						line.Set('chart.hmargin', 2);
						line.Set('chart.text.angle', 50);
						line.Set('chart.gutter.left', 50);
						line.Set('chart.gutter.bottom', 65);
						line.Set('chart.colors', ['rgba(164, 222, 0, 255)']);
						
						//line.Set('chart.tickmarks', 'dot');
	
				        RGraph.Effects.Line.Trace2(line);
						//line.Draw();
					}
					else
					{
			            var bar4 = new RGraph.Bar('canvasGrafico', valores)
		                .Set('colors', ['rgba(164, 222, 0, 255)'])
		                .Set('chart.labels', <%= datas %>)
		                .Set('chart.tooltips', <%= toolTips %>)
		                .Set('chart.text.angle', 50)
		                .Set('chart.gutter.left', 50)
		                .Set('chart.gutter.bottom', 75)
		                .Set('chart.scale.decimals', 2)
		                .Set('chart.hmargin', 3)
		                //.Set('labels.above', true)
		                .Set('numyticks', 5)
		                .Set('ylabels.count', 5)
		                .Set('gutter.left', 35)
		                .Set('variant', '3d')
		                .Set('strokestyle', 'transparent')
		                //.Set('hmargin.grouped', 0)
		                //.Set('scale.round', true)
		                .Draw();
					}
				}
				
				
			}//valores
		};
		
		var fMudaGrafico = 
			function() {
				graficoLinha = !graficoLinha;
				RGraph.ObjectRegistry.Clear();
				RGraph.Clear(document.getElementById("canvasGrafico"));
				fGerarGrafico();
			};

	fGerarGrafico();

	//para funcionar o F5 a função é adicionada no init do openxava	
	openxava.addEditorInitFunction(fGerarGrafico);

</script>

<canvas id="canvasGrafico" width="850" height="280">[No canvas support]</canvas>
<br />
<input type="button" value="Trocar gráfico" onClick="fMudaGrafico()"/>
<br />
  
<br />

<%
		}//if(analiseDeMedicao.getMedicao().size() > 0)
	}//if (Is.empty(key) == false) 
%>



<%
String collectionName = request.getParameter("collectionName");
String viewObject = request.getParameter("viewObject");
String listEditor = request.getParameter("listEditor");  
View view = (View) context.get(request, viewObject);
View collectionView = view.getSubview(collectionName);
if (!Is.emptyString(listEditor)) {
	collectionView.setDefaultListActionsForCollectionsIncluded(false);
	collectionView.setDefaultRowActionsForCollectionsIncluded(false); 
}
View subview = view.getSubview(collectionName);
MetaReference ref = view.getMetaModel().getMetaCollection(collectionName).getMetaReference();
String viewName = viewObject + "_" + collectionName;
String propertyPrefixAccumulated = request.getParameter("propertyPrefix");
String idCollection = org.openxava.web.Collections.id(request, collectionName); 
boolean collectionEditable = subview.isCollectionEditable();
boolean collectionMembersEditables = subview.isCollectionMembersEditables();
boolean hasListActions = subview.hasListActions();
String lineAction = ""; 
if (collectionEditable || collectionMembersEditables) {
	lineAction = subview.getEditCollectionElementAction();
}
else {
	lineAction = subview.getViewCollectionElementAction();
}
String propertyPrefix = propertyPrefixAccumulated == null?collectionName + ".":propertyPrefixAccumulated + collectionName + "."; 
%>
<table width="100%" class="<%=style.getList()%>" <%=style.getListCellSpacing()%>>
<% if (XavaPreferences.getInstance().isDetailOnBottomInCollections()) { %>
<tr><td>
<% try { %>
	<% if (!Is.emptyString(listEditor)) { %> 		
		<jsp:include page="<%=listEditor%>">
			<jsp:param name="rowAction" value="<%=lineAction%>"/>	
			<jsp:param name="viewObject" value="<%=viewName%>"/>
		</jsp:include>
	<% } else if (collectionView.isCollectionCalculated()) { %>
		<%@include file="../calculatedCollectionList.jsp" %>
	<% } else { %>
		<%@include file="../collectionList.jsp" %>
	<% } %>
<% } catch (Exception ex) { %>
</td></tr>
<tr><td class='<%=style.getErrors()%>'>
<%=ex.getLocalizedMessage()%>
<% } %>
</td></tr>
<% } // of: if (XavaPreferences...  %>
<%
// New
if (view.displayDetailInCollection(collectionName)) {
	context.put(request, viewName, collectionView);
%>
<tr class=<%=style.getCollectionListActions()%>><td colspan="<%=subview.getMetaPropertiesList().size()+1%>" class=<%=style.getCollectionListActions()%>>
<% if (collectionEditable) { %>
<jsp:include page="../barButton.jsp">
	<jsp:param name="action" value="<%=subview.getNewCollectionElementAction()%>"/>
	<jsp:param name="argv" value='<%="viewObject="+viewName%>'/>
</jsp:include>
<jsp:include page="../barButton.jsp">
	<jsp:param name="action" value="<%=subview.getRemoveSelectedCollectionElementsAction()%>"/>
	<jsp:param name="argv" value='<%="viewObject="+viewName%>'/>
</jsp:include>

<% } %>
<% 
Iterator itListActions = subview.getActionsNamesList().iterator();
while (itListActions.hasNext()) {
%>
<jsp:include page="../barButton.jsp">
	<jsp:param name="action" value="<%=itListActions.next().toString()%>"/>
	<jsp:param name="argv" value='<%="viewObject="+viewName%>'/>
</jsp:include>
<%	
} // while list actions
%>


</td></tr>
<%		
}
else {
%>
<td></td>
<%
	String argv = "collectionName=" + collectionName;
	Iterator it = subview.getMetaPropertiesList().iterator();
	String app = request.getParameter("application");
	String module = request.getParameter("module");
	while (it.hasNext()) {
		MetaProperty p = (MetaProperty) it.next(); 
		String propertyKey= propertyPrefix + p.getName();
		String valueKey = propertyKey + ".value";
		request.setAttribute(propertyKey, p);
		request.setAttribute(valueKey, subview.getValue(p.getName()));		
		String script = "";
		if (it.hasNext()) {
			if (subview.throwsPropertyChanged(p)) {			
				script = "onchange='openxava.throwPropertyChanged(\"" + 
						app + "\", \"" + 
						module + "\", \"" +
						propertyKey + "\")'";
			}
		}
		else {
			script = "onblur='openxava.executeAction(\"" + app + "\", \"" + module + "\", \"\", false, \"" + subview.getSaveCollectionElementAction() + "\", \"" + argv + "\")'";
		}
		Object value = request.getAttribute(propertyKey + ".value");
		if (WebEditors.mustToFormat(p, view.getViewName())) {
			String fvalue = WebEditors.format(request, p, value, errors, view.getViewName());
			request.setAttribute(propertyKey + ".fvalue", fvalue);
		}		
	%>
	<td>
		<jsp:include page="<%=WebEditors.getUrl(p, view.getViewName())%>">
			<jsp:param name="propertyKey" value="<%=propertyKey%>"/>
			<jsp:param name="script" value="<%=script%>"/>
			<jsp:param name="editable" value="true"/>
		</jsp:include>
	</td>
	<%
	}
}
%>

</tr>
<% if (!XavaPreferences.getInstance().isDetailOnBottomInCollections()) { %>
<tr><td>
<% try { %>
	<% if (!Is.emptyString(listEditor)) { %> 		
		<jsp:include page="<%=listEditor%>">
			<jsp:param name="rowAction" value="<%=lineAction%>"/>	
			<jsp:param name="viewObject" value="<%=viewName%>"/>
		</jsp:include>
	<% } else if (collectionView.isCollectionCalculated()) { %>
		<%@include file="../calculatedCollectionList.jsp" %>
	<% } else { %>
		<%@include file="../collectionList.jsp" %>
	<% } %>
<% } catch (Exception ex) { %>
</td></tr>
<tr><td class='<%=style.getErrors()%>'>
<%=ex.getLocalizedMessage()%>
<% } %>
</td></tr>
<% } // of: if (!XavaPreferences... %>
</table>