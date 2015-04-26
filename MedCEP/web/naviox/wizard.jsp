<jsp:useBean id="wizardHelper" class="org.medcep.wizard.WizardHelper" scope="session" />


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
<script type='text/javascript' src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript' src='js/angular/shared/angular-resource.min.js'></script>

<title>Wizard - MedCEP</title>
</head>
<body ng-app="MedCEPWizardApp">
	<div style="margin: 40px auto; width: 800px;">
		<div id="medcep-wizard" ng-controller="MainController">

			<h2>Projetos</h2>
			<fieldset>
				<h2 class="text-primary text-center">Selecione os Projetos do Taiga</h2>
				<br />
				<form name="formProjetos">
					<div id="projetos" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary">
						<div class="radio">
							<label ng-repeat="projeto in projetos"><input type="radio" ng-model="$parent.projeto_selected" ng-value="projeto">
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
				<br />
				<div id="medidas" style="margin-top: 10px; margin-left: 10px;" class="row bg-primary" ng-repeat="medida in medidas">
					<div class="col-md-1">
						<p class="radio">
							<input type="checkbox" ng-click="add_index($index)" />
						</p>
					</div>
					<div class="col-md-11">
						<div>
							<p>
								<b>{{medida}}</b>
							</p>
						</div>
					</div>
				</div>
				<button type="button" class="btn btn-primary btn-md" ng-click="get_medidas()">Selecionar</button>
			</fieldset>

			<h2>Agendamento</h2>
			<fieldset></fieldset>

		</div>
	</div>

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

	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$("#checkNow")
									.click(
											function(event) {

												var $mySelection = $("input[name='check'][type='radio']:checked");
												if ($mySelection.length > 0) {
													alert("You have selected: "
															+ $mySelection
																	.get().nome);
												} else {
													alert("Please select the car you Drive");
												}

											});
						});

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
