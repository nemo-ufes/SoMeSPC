<!doctype html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="style/angular-chart.css" rel="stylesheet">

<script type='text/javascript' src='js/angular/shared/angular.min.js'></script>
<script type='text/javascript'
	src='js/angular/shared/angular-route.min.js'></script>
<script type='text/javascript'
	src='js/angular/shared/angular-resource.min.js'></script>
<script type='text/javascript' src='js/Chart.min.js'></script>
<script type='text/javascript'
	src='js/angular/shared/angular-chart.min.js'></script>


<title>Painel de Controle - MedCEP</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body ng-app="PainelApp">
	<div id="painel" class="container" ng-controller="LineChartController">

		<h1 class="text-primary text-center">Painel de Controle</h1>

		<canvas id="line" class="chart chart-line" data="data" labels="labels"
			legend="true" series="series" click="onClick">
		</canvas>

	</div>


	<!-- Modules -->
	<script type="text/javascript" src="js/angular/painelApp.js"></script>

	<!-- Controllers -->
	<script type="text/javascript"
		src="js/angular/controllers/PainelController.js"></script>
	<script type="text/javascript"
		src="js/angular/controllers/LineChartController.js"></script>

	<!-- Services -->
	<script type="text/javascript"
		src="js/angular/services/MedCEPService.js"></script>

	<!-- JQuery -->
	<script type='text/javascript' src="js/jquery.js"></script>

	<!-- Bootstrap -->
	<script type='text/javascript' src='bootstrap/js/bootstrap.min.js'></script>

	<script>
		
	</script>

</body>
</html>
