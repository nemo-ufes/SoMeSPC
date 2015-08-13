app.controller('SonarQubeController', function($scope, SonarQubeService,
		SoMeSPCService) {

	/**
	 * Variáveis.
	 */
	$scope.url = "http://ledszeppellin.sr.ifes.edu.br:9000/";
	$scope.projetos;
	$scope.projetoSelecionado;
	$scope.metricas;
	$scope.metricasSelecionadas = [];
	
	/**
	 * Inicialização.
	 */	
	function inicializar(){
		obterPeriodicidades();
	}
	
	inicializar();

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

	/**
	 * Busca as métricas do SonarQube.
	 */
	$scope.obterMetricas = function obterMetricas() {
		SonarQubeService.obterMetricas($scope.url).then(function(metricas) {
			$scope.metricas = metricas;
		}, function erro(err) {
			console.log("Erro ao obter as métricas do SonarQube: " + err);
		});
	}

	/**
	 * Obtém as periodicidades.
	 */
	function obterPeriodicidades() {
		SoMeSPCService.obterPeriodicidades().then(function(periodicidades) {
			$scope.periodicidades = periodicidades;
		})
	}
	
	/**
	 * Inclui/Remove uma métrica de acordo com o checkbox.
	 */
	$scope.toggleMetrica = function toggleMetrica(metrica) {
		var idx = $scope.metricasSelecionadas.indexOf(metrica);

		if (idx > -1) {
			$scope.metricasSelecionadas.splice(idx, 1);
		} else {
			$scope.metricasSelecionadas.push(metrica);
		}
	};

});