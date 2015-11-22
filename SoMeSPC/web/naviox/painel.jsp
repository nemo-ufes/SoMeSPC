
<%
    Servlets.setCharacterEncoding(request, response);
%>

<%@include file="../xava/imports.jsp"%>

<%@page import="org.openxava.web.servlets.Servlets"%>
<%@page import="org.openxava.util.Locales"%>
<%@page import="com.openxava.naviox.web.NaviOXStyle"%>

<jsp:useBean id="context" class="org.openxava.controller.ModuleContext" scope="session" />
<jsp:useBean id="modules" class="com.openxava.naviox.Modules" scope="session" />

<%
    String oxVersion = org.openxava.controller.ModuleManager
					.getVersion();
%>
<!doctype html>
<html lang="pt-br">
<head>

<meta charset="utf-8">
<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />


<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="style/angular-chart.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-locale_pt-br.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='js/Chart.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-chart.min.js'></script>
<script type='text/javascript' src='js/angular/shared/smart-table.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-sanitize.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-translate.min.js'></script>

<title>Painel de Controle - SoMeSPC</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body id="PainelApp" ng-app="PainelApp" <%=NaviOXStyle.getBodyClass(request)%> style="font-size: 12px; line-height: 15px;">

	<div id="main_navigation" style="box-sizing: content-box !important;">
		<jsp:include page="mainNavigation.jsp" />
	</div>
	<table width="100%">
		<tr>

			<td id="modules_list">
				<div id="modules_list_popup">
					<img id="modules_list_corner" src="<%=request.getContextPath()%>/naviox/images/corner.png" />
					<div id="modules_list_outbox">
						<table id="modules_list_box">
							<tr id="modules_list_content">
								<td><jsp:include page="modulesMenu.jsp" /></td>
							</tr>
						</table>
					</div>
				</div>
			</td>

			<td valign="top" class="container">
				<div class="module-wrapper">	
					<div class="row" style="margin-left: -30px;">										
						<div id="tree-row" class="col-md-3">
							<jsp:include page="menus.jsp" />							
						</div>					
					
						<div id="content-row" class="col-md-8">	
							<ul class="nav nav-tabs" role="tablist" id="painel_tab">
								<li role="presentation" class="active"><a href="#instrucoes" aria-controls="instrucoes" role="tab" data-toggle="tab">Instruções</a></li>
								<li role="presentation"><a href="#medicoes" aria-controls="medicoes" role="tab" data-toggle="tab">Análise de Medições</a></li>
								<li role="presentation"><a href="#agendamentos" aria-controls="agendamentos" role="tab" data-toggle="tab">Agendamentos de Medições</a></li>
							</ul>
		
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="instrucoes">
									<div ng-include="'painel_instrucoes.jsp'"></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="medicoes">
									<div ng-include="'painel_medicoes.jsp'"></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="agendamentos">
									<div ng-include="'painel_agendamentos.jsp'"></div>
								</div>
							</div>
		
							<%-- Modules --%>
							<script type="text/javascript" src="js/angular/painelApp.js"></script>
		
							<%-- Controllers --%>
							<script type="text/javascript" src="js/angular/controllers/MedicoesController.js"></script>
							<script type="text/javascript" src="js/angular/controllers/AgendamentosController.js" charset="UTF-8"></script>
		
							<%-- Services --%>
							<script type="text/javascript" src="js/angular/services/MedicaoService.js"></script>
							<script type="text/javascript" src="js/angular/services/AgendamentoService.js"></script>
												
							<%-- JQuery --%>
							<script type='text/javascript' src="js/jquery.js"></script>
		
							<%-- Bootstrap --%>
							<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>
		
							<%-- Paginator --%>
							<script type='text/javascript' src='js/ui-bootstrap-tpls-0.14.3.min.js'></script>
							
							<%-- Modal --%>
							<script type='text/javascript' src='js/angular/shared/dialogs-default-translations.min.js'></script>
							<script type='text/javascript' src='js/angular/shared/dialogs.min.js'></script>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/navs.css' />
	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/style/dialogs.min.css' />	
	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/js/themes/default/style.min.css' />
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/typewatch.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/naviox.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jstree.min.js'></script>

	<script>
		$(function() {
			naviox.init();

			$('#menu_tree').jstree({
				"core" : {
					"themes" : {
						"dots" : false
					}
				},
				"plugins" : [ "state", "types" ]
			});

			var partesUrl = window.location.href.split("/");
			var moduloAtual = partesUrl[partesUrl.length - 1];

			if (moduloAtual == 'painel.jsp')
				$('#menu_tree').jstree('select_node', 'PainelControle');
			else
				$('#menu_tree').jstree('select_node', '#' + moduloAtual);

			$('#menu_tree').on("changed.jstree", function(e, data) {
				var href = data.node.a_attr.href;
				window.location = href;
			});
		});
	</script>
</body>
</html>
