//URL da API da MedCEP
var url = 'http://localhost:8080/MedCEP/api/';

app.service("medicaoService", function($http, $q) {

	return ({
		obterProjetos : obterProjetos,
		obterMedidas : obterMedidas,
		obterMedicoes : obterMedicoes,
		obterValoresMedicao : obterValoresMedicao,
		obterDataHoraMedicao : obterDataHoraMedicao,
		obterPaginas : obterPaginas
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

	function obterMedicoes(idProjeto, idMedida, indiceAtual, tamanhoPagina) {
		var request = $http({
			method : "get",
			url : url + "Medicao",
			params : {
				projeto : idProjeto,
				medida : idMedida,
				indiceAtual : indiceAtual,
				tamanhoPagina: tamanhoPagina		
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterValoresMedicao(idProjeto, idMedida) {

		var request = $http({
			method : "get",
			url : url + "Medicao/Valor",
			params : {
				projeto : idProjeto,
				medida : idMedida
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterDataHoraMedicao(idProjeto, idMedida) {

		var request = $http({
			method : "get",
			url : url + "Medicao/DataHora",
			params : {
				projeto : idProjeto,
				medida : idMedida
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function obterPaginas(tamanhoPagina, idProjeto, idMedida) {

		var request = $http({
			method : "get",
			url : url + "Medicao/Pagina",
			params : {
				tamanhoPagina : tamanhoPagina,
				projeto : idProjeto,
				medida : idMedida
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