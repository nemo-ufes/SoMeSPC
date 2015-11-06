
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

<!DOCTYPE html>
<html lang="pt-br">

  <head>
    <meta charset='utf-8' />
    <meta http-equiv="X-UA-Compatible" content="chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<!-- CSS Modules -->
	<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" media="screen" href="angularWizard/stylesheet.css">
    <link rel="stylesheet" type="text/css" href="angularWizard/angular-wizard.css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">	    
	<link href="<%=request.getContextPath()%>/naviox/style/naviox.css" rel="stylesheet" type="text/css">
	<link href="style/wizard.css" rel="stylesheet"  type="text/css">
	
	<%-- Naviox --%>
	<script type='text/javascript' src='<%=request.getContextPath()%>/xava/js/dwr-engine.js?ox=<%=oxVersion%>'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Modules.js?ox=<%=oxVersion%>'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/Folders.js?ox=<%=oxVersion%>'></script>
    
	<!-- Angular Modules - Wizard and Angular JS -->
    <script type="text/javascript" src="js/angular/shared/angular.min.js"></script>
    <script type="text/javascript" src="angularWizard/lodash.js"></script>
    <script type="text/javascript" src="angularWizard/angular-wizard.js"></script>    
    <script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
	<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
	<script type='text/javascript' src='js/angular/shared/angular-resource.min.js'></script>
	<script type='text/javascript' src='js/angular/shared/angular-filter.js'></script>
	
	<!-- Custom Modules -->
	<script type="text/javascript" src="js/angular/angularWizard.js"></script>
	<!-- Controllers -->
	<script type="text/javascript" src="js/angular/controllers/IntegratorController.js"></script>
	<!-- Services -->
	<script type="text/javascript" src="js/angular/services/IntegratorService.js"></script>

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

    <title>SoMeSPC Wizard</title>
  </head>

  <body <%=NaviOXStyle.getBodyClass(request)%>>

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
				
				<div class="container">
				
				    <!-- HEADER -->
				    <div id="header_wrap">
				        <header class="text-center">
				          <h2>Novo Plano de Medição Integrado</h2>				
				        </header>
				    </div>
				
				    <!-- MAIN CONTENT -->
				    <div id="main_content_wrap">
				      <section id="main_content" ng-app="SoMeSPCWizardApp" ng-controller="WizardCtrl">
				        <wizard on-finish="finished()">
					          <wz-step title="Introdução">
					          <fieldset class="wizard-content">
					            <h3>Criando um Plano de Medição</h3>
					            <p>Para utilizar este wizard, siga os seguintes passos:</p>
					            <p>
					            	<ul style="list-style-type:disc">
										<li>Selecione a periodicidade da coleta e os objetivos que deseja alcançar com o plano.</li>
										<li>De acordo com os objetivos selecionados, uma tela para realização do login nas ferramentas integradas irá aparecer, preencha os dados e siga em frente.</li>
										<li>Selecione os projetos para os quais deseja criar os planos de medição e siga em frente para a tela de resumo</li>
										<li>Avalie se tudo foi preenchido corretamente e cliquei em concluir.</li>
									</ul>  
					            </p>
					          </fieldset>
					         	<nav>
								  <div class="pager pager-size">							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>	
				          </wz-step>
				          <wz-step title="Objetivos"  >
				            <fieldset class="wizard-content">				            
				            	<label for="selectPeriodicidades">Periodicidade</label>
								<select class="form-control" id="selectPeriodicidades" 
										ng-model="periodicidade_selected" ng-options="periodicidades[periodicidades.indexOf(p)].nome for p in periodicidades"> 
									</select>
									<br />
									<div class="row">
										<p class="row">
											<b>Objetivos:</b>
										</p>
										<p class="row">										
											<i>OE - {{itens[0].nome_ObjetivoEstrategico}}</i>
										</p>
										<div class="bg-wizard">
											<div id="itens" class="row" ng-repeat="(obj, item) in itens | groupBy: 'nome_ObjetivoDeMedicao'">									
												<div class="row fonte">
													<input class="col-md-1" style="margin-top: 15px; margin-left: -5px !important; margin-right: 5px !important;"  
															id="{{i.nome_ObjetivoDeMedicao}}" 
															type="checkbox" ng-checked="itens_selected.indexOf(item) > -1"
															ng-click="toggleSelectionItem(item)" />
													<span class="col-md-11 objetivo-medicao">
														<b>OM - {{obj}}</b>									
													</span>
												</div>
												<div class="row fonte">
													<div class="col-md-12" ng-repeat="i in item">
														<span class="row">	
															<span class="necessidade-informacao">											
																NI - {{i.nome_NecessidadeDeInformacao}}
															</span>												
														</span>											
														<span class="row">											
															<span class="medida">												
																<i>ME - {{i.nome_Medida}}</i>
															</span>
														</span>										
													</div>
												</div>
											</div>
										</div>
										<br />
										<br />
									</div>
								</fieldset>
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>						
				          </wz-step>
				          <wz-step title="Conexão Taiga">
							<fieldset class="wizard-content">
								<form name="loginFormTaiga" novalidate>
									<div style="margin-left: 180px; margin-top: 50px;">
										<label for="url">
											<strong>URL *</strong>
										</label>
										<input placeholder="URL do servidor do Taiga" type="url" name="url" ng-model="loginTaiga.url" required>
										<span style="color: red" ng-show="loginFormTaiga.url.$dirty && loginFormTaiga.url.$invalid">
											<span ng-show="loginFormTaiga.url.$error.required">Digite a URL.</span>
											<span ng-show="loginFormTaiga.url.$error.url">Endereço de URL inválido.</span>
										</span>
										<br />
										<br />
										<label for="usuario">
											<strong>Usuário *</strong>
										</label>
										<input placeholder="Usuário do Taiga" type="text" name="usuario" ng-model="loginTaiga.usuario" required>
										<span style="color: red" ng-show="loginFormTaiga.usuario.$dirty && loginFormTaiga.usuario.$invalid">
											<span ng-show="loginFormTaiga.usuario.$error.required">Digite o Nome do Usuário.</span>
										</span>
										<br />
										<br />
										<label for="password">
											<strong>Senha *</strong>
										</label>
										<input type="password" name="password" ng-model="loginTaiga.senha" required>
										<span style="color: red" ng-show="loginFormTaiga.password.$dirty && loginFormTaiga.password.$invalid">
											<span ng-show="loginFormTaiga.password.$error.required">Digite a Senha.</span>
										</span>
									</div>
								</form>
							</fieldset>
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>	
				          </wz-step>
				          <wz-step title="Projetos Taiga">
								<fieldset class="wizard-content">
									<form name="formProjetosTaiga">
										<div id="projetosTaiga" class="row bg-wizard" ng-repeat="projeto in projetosTaiga">
											<div class="col-md-12">
												<label class="checkbox" for="{{projeto}}">
													<div class="row">
														<input id="{{projeto}}" class="col-md-2" type="checkbox"
															ng-checked="projetosSelecionados_taiga.indexOf(projeto) > -1"
															ng-click="toggleSelectionProjeto_Taiga(projeto)" />
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
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>
				          </wz-step>
				          <wz-step title="Conexão Sonar">
								<fieldset class="wizard-content">
									<form name="loginFormSonar" novalidate>
										<div style="margin-left: 180px; margin-top: 50px;">
											<label for="url">
												<strong>URL *</strong>
											</label>
											<input placeholder="URL do servidor Sonar" type="url" name="url" ng-model="loginSonar.url" required>
											<span style="color: red" ng-show="loginFormSonar.url.$dirty && loginFormSonar.url.$invalid">
												<span ng-show="loginFormSonar.url.$error.required">Digite a URL.</span>
												<span ng-show="loginFormSonar.url.$error.url">Endereço de URL inválido.</span>
											</span>
										</div>
									</form>
								</fieldset>
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>
				          </wz-step>
				          <wz-step title="Projetos Sonar">
								<fieldset class="wizard-content">
									<form name="formProjetosSonar">
										<div id="projetosSonar" class="row bg-wizard" ng-repeat="projeto in projetosSonar">
											<div class="col-md-12">
												<label class="checkbox" for="{{projeto}}">
													<div class="row">
														<input id="{{projeto}}" class="col-md-2" type="checkbox"
															ng-checked="projetosSelecionados_sonar.indexOf(projeto) > -1"
															ng-click="toggleSelectionProjeto_Sonar(projeto)" />
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
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>
				          </wz-step>
				           <wz-step title="Resumo">
								<fieldset class="wizard-content">
									
								</fieldset>
								<nav>
								  <div class="pager pager-size">
								  	<input type="button" class="nav navbar-nav navbar-left"  wz-previous value="Anterior" />							  	
				            		<input type="button" class="nav navbar-nav navbar-right" wz-next="logStep()" value="Proximo" />					    
								  </div>
							  	</nav>
				          </wz-step>
				          
				        </wizard>
				      </section>
				    </div>  
  				</div>		
  
  				</div>
  			</td>
  		</tr>
  	</table>
  	
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
			else if (moduloAtual == 'testWizard.jsp')
				$('#menu_tree').jstree('select_node','testWizard');
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