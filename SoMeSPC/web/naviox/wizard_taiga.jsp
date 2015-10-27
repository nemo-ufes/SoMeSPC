
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
<style>
input {
	width: 300px;
	height: 20px;
	vertical-align: middle;
}
</style>
<meta charset="utf-8">
<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link href="style/jquery-ui.css" rel="stylesheet">
<link href="style/wizard_taiga.css" rel="stylesheet">
<link href="style/loading.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>
<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-resource.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-filter.js'></script>
<title>Novo Plano de Medição Integrado - SoMeSPC</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="SoMeSPCWizardApp" ng-controller="TaigaController" <%=NaviOXStyle.getBodyClass(request)%>
	style="font-size: 12px; line-height: 15px;">

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

					<h2 class="text-center">
						<b>Novo Plano de Medição Integrado (Taiga)</b>
					</h2>

					<div id="SoMeSPC-wizard">

						<h2>Conexão</h2>
						<fieldset>
							<form name="loginForm" novalidate>
								<div style="margin-left: 180px; margin-top: 50px;">
									<label for="url">
										<strong>URL *</strong>
									</label>
									<input placeholder="URL do servidor do Taiga" type="url" name="url" ng-model="login.url" required>
									<span style="color: red" ng-show="loginForm.url.$dirty && loginForm.url.$invalid">
										<span ng-show="loginForm.url.$error.required">Digite a URL.</span>
										<span ng-show="loginForm.url.$error.url">Endereço de URL inválido.</span>
									</span>
									<br />
									<br />
									<label for="usuario">
										<strong>Usuário *</strong>
									</label>
									<input placeholder="Usuário do Taiga" type="text" name="usuario" ng-model="login.usuario" required>
									<span style="color: red" ng-show="loginForm.usuario.$dirty && loginForm.usuario.$invalid">
										<span ng-show="loginForm.usuario.$error.required">Digite o Nome do Usuário.</span>
									</span>
									<br />
									<br />
									<label for="password">
										<strong>Senha *</strong>
									</label>
									<input type="password" name="password" ng-model="login.senha" required>
									<span style="color: red" ng-show="loginForm.password.$dirty && loginForm.password.$invalid">
										<span ng-show="loginForm.password.$error.required">Digite a Senha.</span>
									</span>
								</div>
							</form>
						</fieldset>

						<h2>Projetos</h2>
						<fieldset style="overflow: scroll;">
							<form name="formProjetos">
								<div id="projetos" class="row bg-wizard" ng-repeat="(index, projeto) in projetos">
									<div class="col-md-12">
										<label class="checkbox" for="{{projeto}}">
											<div class="row">
												<input id="{{projeto}}" class="col-md-2" type="checkbox"
													ng-checked="projetosSelecionados.indexOf(projeto) > -1"
													ng-click="toggleSelectionProjeto(projeto)" />
												<div class="col-md-10">
													<p>
														<b>Projeto: {{projeto.nome}} ({{projeto.apelido}})</b>
													</p>
													<p style="width: 600px; font-weight: normal !important;">{{projeto.descricao}}</p>
												</div>
											</div>
										</label>
									</div>
								</div>
							</form>
						</fieldset>

						<h2>Objetivos</h2>
						<fieldset style="overflow: scroll;">
							<label for="selectPeriodicidades">Periodicidade da coleta:</label>
							<select class="form-control" id="selectPeriodicidades" ng-model="periodicidade_selected"
								ng-options="periodicidades[periodicidades.indexOf(p)].nome for p in periodicidades">
							</select>
							<br />
							<div class="row">
								<p>
									<b>Objetivos:</b>
								</p>
								<br />
								<p>
									<i>{{itens[0].nome_ObjetivoEstrategico}}</i>
								</p>
								<br />
								<div id="itens" class="row bg-wizard" ng-repeat="(obj, item) in itens | groupBy: 'nome_ObjetivoDeMedicao'">
									<label>
										<span style="padding-bottom: 10px !important; ">
												<br/>
												{{obj}}
												<br/>
										</span>
									</label>
									<div class="col-md-12" ng-repeat="i in item">
										<label class="checkbox" for="{{i.nome_Medida}}">
											<span style="vertical-align: super !important;">
												<br/>
												{{i.nome_NecessidadeDeInformacao}}
												<br/>
											</span>
											<input id="{{i.nome_Medida}}" type="checkbox" ng-checked="itens_selected.indexOf(i) > -1"
												ng-click="toggleSelectionMedida(i)" />
											<span style="vertical-align: bottom !important;">
												{{i.nome_Medida}}
											</span>
										</label>
									</div>
								</div>
								<br />
								<br />
							</div>
						</fieldset>

						<h2>Resumo</h2>
						<fieldset style="overflow: scroll;">
							<p>
								<b>Projeto(s):</b>
								<br />
							<ul style="font-size: 16px; margin-left: 20px;">
								<span ng-repeat="projeto in projetosSelecionados">
									<li style="margin-bottom: 5px;">{{projeto.nome}}</li>
								</span>
							</ul>
							</p>
							<p>
								<b>Periodicidade da coleta:</b>
								{{periodicidade_selected.nome}}
							</p>
							<p>
								<b>Medida(s):</b>
								<br />
							<ul style="font-size: 16px; margin-left: 20px;">
								<span ng-repeat="medida in itens_selected">
									<li style="margin-bottom: 5px;">{{medida.nome_Medida}}</li>
								</span>
							</ul>
							</p>
						</fieldset>

					</div>
				</div>
			</td>
		</tr>
	</table>

	<%-- Loading --%>
	<div class="overlay" ng-show="loading"></div>
	<div class="modal-message" ng-show="loading">
		<h5>
			<b>Processando...</b>
		</h5>
		<div id="squaresWaveG">
			<div id="squaresWaveG_1" class="squaresWaveG"></div>
			<div id="squaresWaveG_2" class="squaresWaveG"></div>
			<div id="squaresWaveG_3" class="squaresWaveG"></div>
			<div id="squaresWaveG_4" class="squaresWaveG"></div>
			<div id="squaresWaveG_5" class="squaresWaveG"></div>
			<div id="squaresWaveG_6" class="squaresWaveG"></div>
			<div id="squaresWaveG_7" class="squaresWaveG"></div>
			<div id="squaresWaveG_8" class="squaresWaveG"></div>
		</div>
	</div>

	<%--  Modules --%>
	<script type="text/javascript" src="js/angular/app.js"></script>
	<%--  Controllers --%>
	<script type="text/javascript" src="js/angular/controllers/TaigaController.js"></script>
	<%--  Services --%>
	<script type="text/javascript" src="js/angular/services/TaigaIntegratorService.js"></script>
	<%--  JQuery --%>
	<script type='text/javascript' src="js/jquery.js"></script>
	<script type='text/javascript' src="js/jquery-ui.js"></script>
	<script type='text/javascript' src='js/jquery.steps.js'></script>
	<%--  Bootstrap --%>
	<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>
	<link rel='stylesheet' href='<%=request.getContextPath()%>/naviox/js/themes/default/style.min.css' />
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/typewatch.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/naviox.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/naviox/js/jstree.min.js'></script>

	<script type="text/javascript">
		function toggleButton(buttonId, enable) {
			if (enable) {
				// Enable disabled button
				var button = $("#SoMeSPC-wizard").find(
						'a[href="#' + buttonId + '-disabled"]');
				button.attr("href", '#' + buttonId);
				button.parent().removeClass();
			} else {
				// Disable enabled button
				var button = $("#SoMeSPC-wizard").find(
						'a[href="#' + buttonId + '"]');
				button.attr("href", '#' + buttonId + '-disabled');
				button.parent().addClass("disabled");
			}
		}
		//Wizard Steps

		$("#SoMeSPC-wizard")
				.steps(
						{
							headerTag : "h2",
							bodyTag : "fieldset",
							transitionEffect : "slideLeft",
							autoFocus : true,
							labels : {
								cancel : "Cancelar",
								current : "Passo atual:",
								pagination : "Paginação",
								finish : "Concluir",
								next : "Próximo",
								previous : "Anterior",
								loading : "Carregando ..."
							},
							loadingTemplate : '<span class="spinner"></span> #text#',
							onStepChanging : function(event, currentIndex,
									newIndex) {
								if (newIndex === 1) {
									e = document
											.getElementById('SoMeSPC-wizard');
									scope = angular.element(e).scope();
									scope
											.post_projeto(function resultado(
													conexao) {
												if (!conexao) {
													alert("Erro ao estabelecer a conexão! Verifique se os dados de login estão corretos e tente novamente.")
												}
											});

									return true;
								} else if (newIndex === 2) {
									if (scope.projetosSelecionados === undefined
											|| scope.projetosSelecionados.length === 0) {
										return false;
									} else {
										return true;
									}
								} else if (newIndex === 3) {
									if (scope.periodicidade_selected === undefined
											|| scope.itens_selected === undefined
											|| scope.itens_selected.length === 0) {
										return false;
									} else {
										return true;
									}
								} else {
									return true;
								}

							},
							onFinished : function(event, currentIndex) {
								toggleButton('finish', false);

								e = document.getElementById('SoMeSPC-wizard');
								scope = angular.element(e).scope();

								if (scope.projetosSelecionados === undefined
										|| scope.projetosSelecionados.length === 0)
									alert("Selecione ao menos um projeto antes de concluir!");
								else if (scope.periodicidade_selected === undefined)
									alert("Selecione uma periodicidade de coleta antes de concluir!");
								else {
									scope
											.post_plano(function retorno(result) {
												alert("Plano(s) de Medição criado com sucesso!");
											});
								}
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
				$('#menu_tree').jstree('select_node',
						'PlanoDeMedicaoIntegradoTaiga');
			else
				$('#menu_tree').jstree('select_node', '#' + moduloAtual);

			$('#menu_tree').on("changed.jstree", function(e, data) {
				var href = data.node.a_attr.href;
				window.location = href;
			});
		});
	</script>
	</ body>
</html>
