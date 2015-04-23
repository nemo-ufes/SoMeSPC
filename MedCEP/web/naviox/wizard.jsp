<jsp:useBean id="wizardHelper" class="org.medcep.wizard.WizardHelper"
	scope="session" />


<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<link href="style/jquery-ui.css" rel="stylesheet">
<link href="style/normalize.css" rel="stylesheet">
<link href="style/main.css" rel="stylesheet">
<link href="style/jquery.steps.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">

<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript'
	src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript'
	src='js/angular/shared/angular-resource.min.js'></script>
	
<title>Wizard - MedCEP</title>
</head>
<body ng-app="MedCEPWizardApp">
	<div style="margin: 40px auto; width: 800px;">
		<div id="medcep-wizard" ng-controller="MainController">

			<h2>Projetos</h2>
			<fieldset>
				<h2 class="text-primary text-center">Selecione os Projetos do Taiga</h2>
				<br/>
				<div id="projetos" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary" ng-repeat="projeto in projetos">
					<div class="col-md-1">
						<p class="radio">
							<input type="radio" id="check"/>
						</p>
					</div>
					<div class="col-md-11" >
						<div>
							<p><b>Projeto: {{projeto.nome}} ({{projeto.apelido}})</b></p>
						</div>
						<div>
							<p>{{projeto.descricao}}</p>
						</div>
					</div>
				</div>
			</fieldset>

			<h2>Medidas</h2>
			<fieldset>
				<h2 class="text-primary text-center">Selecione as Medidas</h2>
				<br/>
				<div id="medidas" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary" ng-repeat="medida in medidas">
					<div class="col-md-1">
						<p class="radio">
							<input type="radio" id="check"/>
						</p>
					</div>
					<div class="col-md-11" >
						<div>
							<p><b>{{medida}}</b></p>
						</div>
					</div>
				</div>
			</fieldset>

			<h2>Agendamento</h2>
			<fieldset>
			
			</fieldset>
			
		</div>
	</div>

	<!-- Modules -->
	<script type="text/javascript" src="js/angular/app.js"></script>

	<!-- Controllers -->
	<script type="text/javascript"
		src="js/angular/controllers/MainController.js"></script>

	<!-- Services -->
	<script type="text/javascript"
		src="js/angular/services/TaigaIntegratorService.js"></script>

	<!-- JQuery -->
	<script type='text/javascript' src="js/jquery.js"></script>
	<script type='text/javascript' src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery.ui.checkbox.js"></script>
	<script type='text/javascript' src='js/jquery.steps.js'></script>

	<!-- Bootstrap -->
	<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>

	<script>
		$('input [type=checkbox]').button();

		//Wizard Steps
		$("#medcep-wizard").steps({
			headerTag : "h2",
			bodyTag : "fieldset",
			transitionEffect : "slideLeft",
			autoFocus : true

		});
	</script>


</body>
</html>
