
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


<link href="style/jquery-ui.css" rel="stylesheet">
<link href="style/normalize.css" rel="stylesheet">
<link href="style/main.css" rel="stylesheet">
<link href="style/jquery.steps.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="style/angular-chart.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-resource.min.js'></script>
<script type='text/javascript' src='js/Chart.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-chart.min.js'></script>
<script type='text/javascript' src='js/angular/shared/smart-table.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-sanitize.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-translate.min.js'></script>

<title>Novo Plano de Medição Integrado - MedCEP</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body ng-app="MedCEPWizardApp" <%=NaviOXStyle.getBodyClass(request)%> style="font-size: 12px; line-height: 15px;">

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
					<div id="medcep-wizard" ng-controller="MainController">
						<h2>Conexão</h2>
						<fieldset>
							<h3 class="text-center">Conexão com o Taiga</h3>
						</fieldset>

						<h2>Projetos</h2>
						<fieldset>
							<h2 class="text-primary text-center">Selecione os Projetos do Taiga</h2>
							<br />
							<form name="formProjetos">
								<div id="projetos" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary">
									<div class="radio">
										<label ng-repeat="projeto in projetos"><input type="radio" ng-model="$parent.projeto_selected" ng-value="projeto" ng-click="mostra_projeto()">
											<div>
												<p>
													<b>Projeto: {{projeto.nome}} ({{projeto.apelido}})</b>
												</p>
											</div>
											<div>
												<p>{{projeto.descricao}}</p>
											</div> </label>
									</div>
								</div>
							</form>
						</fieldset>

						<h2>Medidas</h2>
						<fieldset style="overflow: scroll; border: 1px solid #0000FF;">
							<h2 class="text-primary text-center">Selecione as Medidas</h2>
							<br /> <label for="selected">Selecione a Periodicidade:</label> <select class="form-control" id="selected">
								<option ng-repeat="periodicidade in periodicidades" ng-click="get_periodicidade($index)">{{periodicidade.nome}}</option>
							</select> <br />
							<div id="medidas" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary" ng-repeat="medida in medidas">
								<div class="col-md-1">
									<label class="checkbox" for="{{medida}}"><input type="checkbox" ng-model="medida_selected[medida]" name="group" id="{{medida}}" /></label>
								</div>
								<div class="col-md-11">
									<div>
										<p>
											<b>{{medida}}</b>
										</p>
									</div>
								</div>
							</div>
							<br /> <br />
							<div align="center">
								<button type="button" class="btn btn-primary btn-md" ng-click="get_medidas()">Selecionar</button>
							</div>
						</fieldset>

						<h2>Confirmação</h2>
						<fieldset>
						</fieldset>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<!-- Modules -->
	<script type="text/javascript" src="js/angular/app.js"></script>

	<!-- Controllers -->
	<script type="text/javascript" src="js/angular/controllers/MainController.js"></script>

	<!-- Services -->
	<script type="text/javascript" src="js/angular/services/TaigaIntegratorService.js"></script>

	<!-- JQuery -->
	<script type='text/javascript' src="js/jquery.js"></script>
	<script type='text/javascript' src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.ui.checkbox.js"></script>
	<script type='text/javascript' src='js/jquery.steps.js'></script>

	<!-- Bootstrap -->
	<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>


	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/js/themes/default/style.min.css' />
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/typewatch.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/naviox.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jstree.min.js'></script>

	<script type="text/javascript">
		//Wizard Steps
		$("#medcep-wizard").steps({
			headerTag : "h2",
			bodyTag : "fieldset",
			transitionEffect : "slideLeft",			
			autoFocus : true,
			labels: {
		        cancel: "Cancelar",
		        current: "Passo atual:",
		        pagination: "Paginação",
		        finish: "Concluir",
		        next: "Próximo",
		        previous: "Anterior",
		        loading: "Carregando ..."
		    },
			onFinished : function(event, currentIndex) {
				// get scope from the DOM element
				e = document.getElementById('medcep-wizard');
				scope = angular.element(e).scope();
				// update the model with a wrap in $apply(fn) which will refresh the view for us
				scope.POST();
				//alert("Wizard concluido com Sucesso!");
			}

		});

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
			else if (moduloAtual == 'wizard.jsp')
				$('#menu_tree')
						.jstree('select_node', 'PlanoDeMedicaoIntegrado');
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
