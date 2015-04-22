<!doctype html>
<html lang="pt-br">
<head>

<meta charset="utf-8">
<link rel="icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/naviox/images/favicon.ico" type="image/x-icon" /> 

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

		<div class="panel panel-primary" style="margin-top: 20px;">

			<div class="panel-heading">
				<h1 class="panel-title text-center"><b>Painel de Controle</b></h1>
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
					<canvas id="line" class="chart chart-line" data="dados" labels="labels" legend="true" series="series" colours="colours" click="onClick">
					</canvas>

					<div class="text-center">
						<pagination boundary-links="true" total-items="totalItems" ng-model="paginaAtual" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;" ng-change="obterMedicoes(paginaAtual)"> </pagination>
					</div>
				</div>
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

	<!-- Paginator -->
	<script type='text/javascript' src='js/ui-bootstrap-tpls-0.12.1.js'></script>

</body>
</html>
