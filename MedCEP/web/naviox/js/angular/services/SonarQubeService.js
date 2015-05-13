app.service("SonarQubeService", function($http, $q, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	var url = host + '/MedCEP/api/SonarQubeIntegrator';

	return ({
		obterProjetos : obterProjetos,
		obterMetricas : obterMetricas
	});

	function obterProjetos(urlSonar) {

		var request = $http({
			method : "post",
			url : url + "/Projetos",
			data : urlSonar
		});

		return (request.then(handleSuccess, handleError));
	}

	function obterMetricas(urlSonar) {

		var request = $http({
			method : "post",
			url : url + "/Metricas",
			data : urlSonar
		});

		return (request.then(handleSuccess, handleError));
	}

	function handleError(response) {
		if (!angular.isObject(response.data) || !response.data.message) {
			return ($q.reject("Ocorreu um erro."));
		}
		return ($q.reject(response.data.message));
	}

	function handleSuccess(response) {
		return (response.data);
	}

});