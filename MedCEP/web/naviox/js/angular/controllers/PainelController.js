app.controller('PainelController', function($scope, medicaoService) {

	carregarProjetos();
	carregarMedidas();
	
	function carregarProjetos() {
		medicaoService.obterProjetos().then(function(projetos) {
			$scope.projetos = projetos;
		});
	}
	
	function carregarMedidas() {
		medicaoService.obterMedidas(1671).then(function(medidas) {
			$scope.medidas = medidas;
		});
	}

});