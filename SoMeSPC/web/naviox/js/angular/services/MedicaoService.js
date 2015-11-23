app.service("MedicaoService", function($http, $q, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	var url = host + '/SoMeSPC/api/Medicao';

	return ({
		obterEntidades : obterEntidades,
		obterMedidas : obterMedidas,
		obterMedicoes : obterMedicoes,
		obterTotalMedicoes : obterTotalMedicoes
	});

	function obterEntidades() {

		var request = $http({
			method : "get",
			url : url + "/Entidade",
			params : {}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedidas(idEntidade) {

		var request = $http({
			method : "get",
			url : url + "/Entidade/" + idEntidade + "/Medida"
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedicoes(idEntidade, idMedida, indiceAtual, tamanhoPagina, dataInicio, dataFim) {
		var request = $http({
			method : "get",
			url : url,
			params : {
				entidade : idEntidade,
				medida : idMedida,
				indiceAtual : indiceAtual,
				tamanhoPagina : tamanhoPagina,
				dataInicio : dataInicio,
				dataFim : dataFim
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	function obterTotalMedicoes(idEntidade, idMedida, dataInicio, dataFim) {

		var request = $http({
			method : "get",
			url : url + "/Total",
			params : {
				entidade : idEntidade,
				medida : idMedida,
				dataInicio : dataInicio,
				dataFim : dataFim
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