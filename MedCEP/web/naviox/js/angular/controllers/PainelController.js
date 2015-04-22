app.controller('PainelController', function($scope, medicaoService) {
	 
	// Inicializacao.
	carregarProjetos();
	inicializar();

	function carregarProjetos() {
		medicaoService.obterProjetos().then(function(projetos) {
			$scope.projetos = projetos;
		});
	}
	
	function inicializar(){
		$scope.paginaAtual = 1;
		$scope.tamanhoPagina = 10; 
		$scope.dados = [[0,0,0,0,0]];
		$scope.labels = ["0","0","0","0","0"];	
		$scope.series = ['Valores Medidos'];
		$scope.colours = [ {
			 fillColor : "#bcdb99",
			 strokeColor : "#3d5b19",
			 pointColor : "#3d5b19",
			 pointStrokeColor : "#fff",
			 } ]
	}

	$scope.obterMedidas = function obterMedidas() {
		inicializar();
		
		medicaoService.obterMedidas($scope.projetoSelecionado.id).then(
				function(medidas) {
					$scope.medidas = medidas;
				});
	}
	
	// Paginacao
	$scope.obterPaginas = function () {
		if ($scope.projetoSelecionado != undefined && $scope.medidaSelecionada != undefined) {			
			medicaoService.obterPaginas($scope.tamanhoPagina, $scope.projetoSelecionado.id, $scope.medidaSelecionada.id).then(function(valores) {
					$scope.paginas = valores;
					
					if ($scope.paginaAtual != 1)
						$scope.paginaAtual = 1;					
			});			
			
			$scope.obterMedicoesIniciais();
		}
	}	
	
	// Buscar Medidas
	$scope.obterMedicoesIniciais = function () {				
		medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, 1, $scope.tamanhoPagina).then(function(valores) {			
			//$scope.obterPaginas();
			var dados = new Array();
			var labels = new Array();
			
			for (var medicao of valores){
				dados.push(medicao.valor_medido);
				labels.push(medicao.data)
			};
				
			$scope.dados = [dados];
			$scope.labels = labels;						
		});
	}

//	$scope.obterProximasMedicoes = function () {
//		medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, $scope.paginaAtual, $scope.tamanhoPagina).then(function(valores) {					
//					var dados = new Array();
//					var labels = new Array();
//					
//					for (var medicao of valores){
//							dados.push(medicao.valor_medido);
//							labels.push(medicao.data)
//						};
//						
//			$scope.dados = [dados];
//			$scope.labels = labels;
//			$scope.paginaAtual = $scope.paginaAtual + 1;
//		});
//	}
//	
//
//	$scope.obterMedicoesAnteriores = function () {
//			if ($scope.inicio > 0) {
//					medicaoService.obterMedicoes($scope.projetoSelecionado.id, $scope.medidaSelecionada.id, $scope.paginaAtual, $scope.tamanhoPagina).then(function(valores) {					
//					var dados = new Array();
//					var labels = new Array();
//						
//					for (var medicao of valores){
//							dados.push(medicao.valor_medido);
//							labels.push(medicao.data)
//							};
//							
//				$scope.dados = [dados];
//				$scope.labels = labels;
//				$scope.paginaAtual = $scope.paginaAtual - 1;
//			});
//		}
//	}

});