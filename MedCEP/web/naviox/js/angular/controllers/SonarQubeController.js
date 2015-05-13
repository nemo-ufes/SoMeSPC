app.controller('SonarQubeController', function($scope, SonarQubeService) {

	/**
	 * Variáveis.
	 */
	$scope.url = "http://ledszeppellin.sr.ifes.edu.br:9000/";
	$scope.projeto_selected;

	/**
	 * Busca os projetos do SonarQube.
	 */
	$scope.obterProjetos = function obterProjetos(retorno) {
		SonarQubeService.obterProjetos($scope.url).then(function(projetos) {
			$scope.projetos = projetos;
			return retorno(true);
		}, function erro(err) {
			console.log("Erro ao obter os projetos do SonarQube: " + err);
			return retorno(false);
		});
	}
	
});