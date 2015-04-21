//URL da API da MedCEP
var url = 'http://localhost:8080/MedCEP/api/';

app.service("medicaoService", function($http, $q) {

	return ({
		obterProjetos : obterProjetos,
		obterMedidas : obterMedidas
	});

	function obterProjetos() {

		var request = $http({
			method : "get",
			url : url + "Medicao/Projeto",
			params : {}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedidas(idProjeto) {

		var request = $http({
			method : "get",
			url : url + "Medicao/Medida",
			params : {
				projeto : idProjeto
			}
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