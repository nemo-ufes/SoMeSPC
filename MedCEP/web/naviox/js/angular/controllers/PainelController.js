app.controller('PainelController', function($scope, medicaoService) {
	
	$scope.projetoSelecionado;
	
	carregarProjetos();
	
	function carregarProjetos() {
		medicaoService.obterProjetos().then(function(projetos) {
			$scope.projetos = projetos;
		});
	}
	
	$scope.obterMedidas = function obterMedidas() {
		medicaoService.obterMedidas($scope.projetoSelecionado.id).then(function(medidas) {
			$scope.medidas = medidas;
		});
	}

});