app.controller('PainelController', function($scope, medicaoService) {

	// Dados do ChartJS
	 $scope.colours = [ {
	 fillColor : "#bcdb99",
	 strokeColor : "#3d5b19",
	 pointColor : "#3d5b19",
	 pointStrokeColor : "#fff",
	 } ]

	// Paginação
	$scope.inicio = 0;
	$scope.quantidade = 10; 
	 
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
	
	$scope.obterMedicoesIniciais = function () {
		$scope.inicio = 0;
		
		medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, $scope.inicio, $scope.quantidade).then(function(valores) {					
					var dados = new Array();
					var labels = new Array();
					
					for (var medicao of valores){
							dados.push(medicao.valor_medido);
							labels.push(medicao.data)
						};
			$scope.series = ['Valores Medidos'];
			$scope.dados = [dados];
			$scope.labels = labels;						
		});
	}

	$scope.obterProximasMedicoes = function () {
		medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, $scope.inicio, $scope.quantidade).then(function(valores) {					
					var dados = new Array();
					var labels = new Array();
					
					for (var medicao of valores){
							dados.push(medicao.valor_medido);
							labels.push(medicao.data)
						};
			$scope.series = ['Valores Medidos'];
			$scope.dados = [dados];
			$scope.labels = labels;
			$scope.inicio = $scope.inicio + $scope.quantidade;
		});
	}
	

	$scope.obterMedicoesAnteriores = function () {
		if ($scope.inicio > 0) {
				medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, $scope.inicio, $scope.quantidade).then(function(valores) {					
				var dados = new Array();
				var labels = new Array();
					
				for (var medicao of valores){
						dados.push(medicao.valor_medido);
						labels.push(medicao.data)
						};
			$scope.series = ['Valores Medidos'];
			$scope.dados = [dados];
			$scope.labels = labels;
			$scope.inicio = $scope.inicio - $scope.quantidade;
		});
	}
	}

});