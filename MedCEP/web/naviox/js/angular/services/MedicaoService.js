app.service("MedicaoService", function($http, $q) {

	var url = 'http://localhost:8080/MedCEP/api/Medicao';
	
	return ({
		obterProjetos : obterProjetos,
		obterMedidas : obterMedidas,
		obterMedicoes : obterMedicoes,
		obterValoresMedicao : obterValoresMedicao,
		obterDataHoraMedicao : obterDataHoraMedicao,
		obterPaginas : obterPaginas,
		obterTotalMedicoes : obterTotalMedicoes
	});

	function obterProjetos() {

		var request = $http({
			method : "get",
			url : url + "/Projeto",
			params : {}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedidas(idProjeto) {

		var request = $http({
			method : "get",
			url : url + "/Medida",
			params : {
				projeto : idProjeto
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function obterMedicoes(idProjeto, idMedida, indiceAtual, tamanhoPagina) {
		var request = $http({
			method : "get",
			url : url,
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
			url : url + "/Valor",
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
			url : url + "/DataHora",
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
			url : url + "/Pagina",
			params : {
				tamanhoPagina : tamanhoPagina,
				projeto : idProjeto,
				medida : idMedida
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function obterTotalMedicoes(idProjeto, idMedida) {

		var request = $http({
			method : "get",
			url : url + "/Total",
			params : {				
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