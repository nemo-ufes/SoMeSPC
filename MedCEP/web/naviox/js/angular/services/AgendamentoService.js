app.service("AgendadorService", function($http, $q) {
	// URL da API da MedCEP
	var url = 'http://localhost:8080/MedCEP/api/Agendador';

	return ({
		obterJobs : obterJobs,
		obterAgendamentos : obterAgendamentos,
		iniciarAgendamento : iniciarAgendamento,
		pausarAgendamento : pausarAgendamento,
		excluirAgendamento : excluirAgendamento,
		executarAgendamento : executarAgendamento
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

	function iniciarAgendamento(nomeAgendamento, grupoAgendamento) {
		var request = $http({
			method : "post",
			url : url + "/Agendamento/Comando",
			data : {
				nome_agendamento : nomeAgendamento,
				grupo_agendamento : grupoAgendamento,
				comando : "Iniciar"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function pausarAgendamento(nomeAgendamento, grupoAgendamento) {
		var request = $http({
			method : "post",
			url : url + "/Agendamento/Comando",
			data : {
				nome_agendamento : nomeAgendamento,
				grupo_agendamento : grupoAgendamento,
				comando : "Pausar"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function excluirAgendamento(nomeAgendamento, grupoAgendamento) {
		var request = $http({
			method : "post",
			url : url + "/Agendamento/Comando",
			data : {
				nome_agendamento : nomeAgendamento,
				grupo_agendamento : grupoAgendamento,
				comando : "Excluir"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function executarAgendamento(nomeJob, grupoJob, nomeAgendamento, grupoAgendamento) {
		var request = $http({
			method : "post",
			url : url + "/Agendamento/Comando",
			data : {
				nome_job : nomeJob,
				grupo_job : grupoJob,
				nome_agendamento : nomeAgendamento,
				grupo_agendamento : grupoAgendamento,
				comando : "ExecutarAgora"
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