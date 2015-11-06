
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
<html>

  <head>
    <meta charset='utf-8' />
    <meta http-equiv="X-UA-Compatible" content="chrome=1" />
    <meta name="description" content="Angular-wizard : Easy to use Wizard library for AngularJS" />
    
	<!-- CSS Modules -->
    <link rel="stylesheet" type="text/css" media="screen" href="angularWizard/stylesheet.css">
    <link rel="stylesheet" type="text/css" href="angularWizard/angular-wizard.css">
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    
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



    <title>SoMeSPC Wizard</title>
  </head>

  <body>

    <!-- HEADER -->
    <div id="header_wrap" class="outer">
        <header class="inner">

          <h1 id="project_title">SoMeSPC Wizard</h1>
          <h2 id="project_tagline">Novo Plano de Medição Integrado</h2>

        </header>
    </div>

    <!-- MAIN CONTENT -->
    <div id="main_content_wrap" class="outer">
      <section id="main_content" class="inner main-inner" ng-app="SoMeSPCWizardApp" ng-controller="WizardCtrl">
        <wizard on-finish="finished()">
          <wz-step title="StartWizard">
            <h1>Criando um Plano de Medição</h1>
            <p>Ao inicializar a Wizard, siga os seguintes passos:</p>
            <p>
            	<ul style="list-style-type:disc">
					<li>Seleciona a periodicidade da coleta e os objetivos que deseja alcançar com o plano.</li>
					<li>De acordo com os objetivos selecionados, uma tela para realização do login irá aparecer, preencha os dados e siga em frente.</li>
					<li>Selecione os projetos que deseja criar e segui em frente para a tela de resumo</li>
					<li>Avalie se tudo foi preenchido corretamente e cliquei em finalizar.</li>
				</ul>  
            </p>
            <input type="submit" wz-next value="Proximo"/>
          </wz-step>
          <wz-step title="Objetivos">
            <h1>Periodicidade da coleta:</h1>
            <label for="selectPeriodicidades">Periodicidade</label>
				<select class="form-control" id="selectPeriodicidades" ng-model="periodicidade_selected" ng-options="periodicidades[periodicidades.indexOf(p)].nome for p in periodicidades"> 
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
						<br />
						<br />
					</div>
            <input type="button" wz-next="logStep()" value="Proximo" />
          </wz-step>
          <wz-step title="Conexao Taiga">
          <h1>Conexão Taiga</h1>
			<fieldset>
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
			<input type="button" wz-next value="Proximo" />
          </wz-step>
          <wz-step title="Projetos Taiga">
           <h1>Projetos Taiga</h1>
				<fieldset style="overflow: scroll;">
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
            <input type="button" wz-next value="Proximo" />
          </wz-step>
          <wz-step title="Conexao Sonar">
           <h1>Conexão Sonar</h1>
				<fieldset>
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
            <input type="button" wz-next value="Proximo" />
          </wz-step>
          <wz-step title="Projetos Sonar">
           <h1>Projetos Taiga</h1>
				<fieldset>
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
            <input type="button" wz-next value="Proximo" />
          </wz-step>
        </wizard>
      </section>
    </div>

<!--     FOOTER  -->
<!--     <div id="footer_wrap" class="outer"> -->
<!--       <footer class="inner"> -->
<!--         <p class="copyright">Angular-wizard maintained by <a href="https://github.com/mgonto">mgonto</a></p> -->
<!--         <p>Published with <a href="http://pages.github.com">GitHub Pages</a></p> -->
<!--       </footer> -->
<!--     </div> -->

    

  </body>
</html>