app.controller('PainelController', function($scope, medicaoService) {

	// Dados do ChartJS
	$scope.labels = [ "January", "February", "March", "April", "May", "June",
			"July" ];
	$scope.series = [ 'Series A' ];
	$scope.data = [ [ 65, 59, 80, 81, 56, 55, 40 ] ];
	$scope.colours = [ {
		fillColor : "#bcdb99",
		strokeColor : "#3d5b19",
		pointColor : "#3d5b19",
		pointStrokeColor : "#fff",
	} ]

	// Dados dos comboboxes
	$scope.projetoSelecionado;
	$scope.medidaSelecionada;

	// Inicializa os projetos.
	carregarProjetos();

	function carregarProjetos() {
		medicaoService.obterProjetos().then(function(projetos) {
			$scope.projetos = projetos;
		});
	}

	$scope.obterMedidas = function obterMedidas() {
		// Reset na medida selecionada.
		$scope.medidaSelecionada = null;

		medicaoService.obterMedidas($scope.projetoSelecionado.id).then(
				function(medidas) {
					$scope.medidas = medidas;
				});
	}

	$scope.obterValoresMedicao = function obterValoresMedicao() {
		medicaoService.obterValoresMedicao($scope.projetoSelecionado.id,
				$scope.medidaSelecionada.id).then(function(valores) {
			$scope.data = valores;
		});
	}

});