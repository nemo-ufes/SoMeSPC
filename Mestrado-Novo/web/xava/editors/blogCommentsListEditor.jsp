<%@ include file="../imports.jsp"%>

<%@page import="org.openxava.view.View"%>
<%@page import="org.openxava.model.MapFacade"%>
<%@page import="org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.Blog"%>
<%@page import="org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.Comentario"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.openxava.util.Locales"%>
<%@page import="org.openxava.util.Is"%>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session"/>

<br/>
<br/>
<script>

var data = [280, 45, 133, 166, 84, 259, 266, 960, 219, 311, 67, 89];

var fGerarGrafico = 
	function() {
		if(data){
			if(document.getElementById('canvasGrafico'))
			{
	        	var line = new RGraph.Line('canvasGrafico', data);
		        line.Set('chart.labels', ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']);
		        line.Set('chart.tooltips', ['280', '45', '133', '166', '84', '259', '266', '960', '219', '311', '67', '89']);
		        RGraph.Effects.Line.Trace2(line);
			}
		}
	};

fGerarGrafico();

//para funcionar o F5 a função é adicionada no init do openxava	
openxava.addEditorInitFunction(fGerarGrafico);

</script>
<canvas id="canvasGrafico" width="600" height="250">[No canvas support]</canvas>
<br/>
<br/>

<% 
String viewObject = request.getParameter("viewObject");
View collectionView = (View) context.get(request, viewObject);
View rootView = collectionView.getRoot();
Map key = rootView.getKeyValues();
if (Is.empty(key)) {
%>	
There are no comments	222
<%
} else {
	
Blog blog = (Blog) MapFacade.findEntity("Blog", key);
String action = request.getParameter("rowAction"); 
String actionArgv = ",viewObject=" + viewObject;  
%>

These are the comments:<br/>
<%

int f=0;
for (Iterator it = blog.getComentarios().iterator(); it.hasNext(); f++) {
	Comentario comment = (Comentario) it.next();	

%>
<i><b><big>Comment at XX/XX/XX</big></b></i>
<xava:action action='<%=action%>' argv='<%="row=" + f + actionArgv%>'/>
<p>
<i><%=comment.getTexto()%></i>
</p>
<!-- <script type="text/javascript">
f();
</script> -->
<hr/>
<%
}

}
%>


