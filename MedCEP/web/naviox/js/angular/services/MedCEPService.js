app.service("MedCEPService", function($http, $q, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	var url = host + '/MedCEP/api';

	return ({
		obterPeriodicidades : obterPeriodicidades,
	});

	function obterPeriodicidades() {

		var request = $http({
			method : "get",
			url : url + "/Periodicidade",
			params : {}
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