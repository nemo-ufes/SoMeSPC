<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
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
<body id="PainelApp" ng-app="PainelApp">

	<div id="painel" class="container" ng-controller=PainelController>

		<h1 class="text-primary text-center">Painel de Controle</h1>

		<div class="row">

			<div class="col-md-3">

				<label for="selectProjeto">Projeto:</label> <select class="form-control" id="selectProjeto" ng-model="projetoSelecionado" ng-options="projetos[projetos.indexOf(projeto)].nome  for projeto in projetos" ng-change="obterMedidas()">
				</select>

				<div>
					<p>ID do Projeto: {{projetoSelecionado.id}}</p>
				</div>

				<label for="selectMedida">Medida:</label> <select class="form-control" id="selectMedida" ng-model="medidaSelecionada" ng-options="medidas[medidas.indexOf(medida)].nome  for medida in medidas" ng-change="obterMedicoes()">
				</select>

				<div>
					<p>ID da Medida: {{medidaSelecionada.id}}</p>
				</div>

			</div>

			<div class="col-md-9">
				<canvas id="line" class="chart chart-line" data="dados" labels="labels" legend="true" series="series" colours="colours" click="onClick">
				</canvas>
			</div>

		</div>
	</div>

	<!-- Modules -->
	<script type="text/javascript" src="js/angular/painelApp.js"></script>

	<!-- Controllers -->
	<script type="text/javascript" src="js/angular/controllers/PainelController.js"></script>

	<!-- Services -->
	<script type="text/javascript" src="js/angular/services/MedCEPService.js"></script>

	<!-- JQuery -->
	<script type='text/javascript' src="js/jquery.js"></script>

	<!-- Bootstrap -->
	<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>

</body>
</html>
