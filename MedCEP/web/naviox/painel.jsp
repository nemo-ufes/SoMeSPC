
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

<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="style/angular-chart.css" rel="stylesheet">

<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='js/Chart.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-chart.min.js'></script>


<title>Painel de Controle - MedCEP</title>
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

			<td valign="top"><jsp:include page="menus.jsp" /></td>

			<td valign="top">
				<div class="module-wrapper">

					<%-- O Painel de Controle começa aqui --%>
					<div id="painel" class="container" ng-controller=PainelController>

						<div class="panel panel-primary" style="margin-top: 20px;">

							<div class="panel-heading">
								<h1 class="panel-title text-center">
									<b>Painel de Controle</b>
								</h1>
							</div>

							<div class="row" style="margin: 20px;">
								<div class="col-md-6">
									<label for="selectProjeto">Projeto: {{projetoSelecionado.id}}</label> <select class="form-control" id="selectProjeto" ng-model="projetoSelecionado" ng-options="projetos[projetos.indexOf(projeto)].nome  for projeto in projetos" ng-change="obterMedidas()">
									</select>
								</div>
								<div class="col-md-6">
									<label for="selectMedida">Medida: {{medidaSelecionada.id}}</label> <select class="form-control" id="selectMedida" ng-model="medidaSelecionada" ng-options="medidas[medidas.indexOf(medida)].nome  for medida in medidas" ng-change="obterMedicoes(1)">
									</select>
								</div>
							</div>

							<div class="row" style="margin: 20px;">
								<div class="col-md-12">
									<canvas id="line" style="height: 500px;" class="chart chart-line" data="dados" labels="labels" legend="true" series="series" colours="colours" click="onClick">
									</canvas>

									<div class="text-center">
										<pagination boundary-links="true" total-items="totalItems" ng-model="paginaAtual" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;" ng-change="obterMedicoes(paginaAtual)"> </pagination>
									</div>
								</div>
							</div>

						</div>

					</div>

					<%-- Modules --%>
					<script type="text/javascript" src="js/angular/painelApp.js"></script>

					<%-- Controllers --%>
					<script type="text/javascript" src="js/angular/controllers/PainelController.js"></script>

					<%-- Services --%>
					<script type="text/javascript" src="js/angular/services/MedCEPService.js"></script>

					<%-- JQuery --%>
					<script type='text/javascript' src="js/jquery.js"></script>

					<%-- Bootstrap --%>
					<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>

					<%-- Paginator --%>
					<script type='text/javascript' src='js/ui-bootstrap-tpls-0.12.1.js'></script>

					<%-- O Painel de Controle termina aqui --%>
				</div>
			</td>
		</tr>
	</table>

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

			if (moduloAtual =='painel.jsp')
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
