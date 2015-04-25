app.service("AgendadorService", function($http, $q) {
	//URL da API da MedCEP
	var url = 'http://localhost:8080/MedCEP/api/Agendador';
	
	return ({
		obterJobs : obterJobs,
		obterAgendamentos  :obterAgendamentos
	});

	function obterJobs() {

		var request = $http({
			method : "get",
			url : url + "/Job",
			params : {}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function obterAgendamentos() {

		var request = $http({
			method : "get",
			url : url + "/Agendamento",
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