app.controller('PainelController', function($scope, medicaoService) {

	// Dados do ChartJS
	// $scope.labels;
	// $scope.series;
	// $scope.dados;
	// $scope.colours = [ {
	// fillColor : "#bcdb99",
	// strokeColor : "#3d5b19",
	// pointColor : "#3d5b19",
	// pointStrokeColor : "#fff",
	// } ]

	// Dados do Morris
	$scope.xLabels = function(x) {
		return moment(x).format('L');
	}

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

		$scope.series = $scope.projetoSelecionado.nome;

		// Reset na medida selecionada.
		$scope.medidaSelecionada = null;

		medicaoService.obterMedidas($scope.projetoSelecionado.id).then(
				function(medidas) {
					$scope.medidas = medidas;
				});
	}

	$scope.obterMedicoes = function obterMedicoes() {
		medicaoService.obterMedicoes($scope.projetoSelecionado.id,
				$scope.medidaSelecionada.id).then(function(valores) {
			$scope.dados = valores;
		});
	}

});