app.service("MedicaoService", function($http, $q, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	var url = host + '/SoMeSPC/api/Medicao';

	return ({
		obterEntidades : obterEntidades,
		obterMedicoes : obterMedicoes,
		obterTotalMedicoes : obterTotalMedicoes
	});

	function obterEntidades(nomeMedida) {

		var request = $http({
			method : "get",
			url : url + "/Entidade",
			params : {
				medida: nomeMedida
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedicoes(idEntidade, nomeMedida, indiceAtual, tamanhoPagina, dataInicio, dataFim) {
		var request = $http({
			method : "get",
			url : url,
			params : {
				entidade : idEntidade,
				medida : nomeMedida,
				indiceAtual : indiceAtual,
				tamanhoPagina : tamanhoPagina,
				dataInicio : dataInicio,
				dataFim : dataFim
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	function obterTotalMedicoes(idEntidade, nomeMedida, dataInicio, dataFim) {

		var request = $http({
			method : "get",
			url : url + "/Total",
			params : {
				entidade : idEntidade,
				medida : nomeMedida,
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