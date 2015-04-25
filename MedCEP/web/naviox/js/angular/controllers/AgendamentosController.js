/**
 * Controlador da aba de Agendamentos do Painel de Controle
 */
app.controller('AgendamentosController', function($scope, AgendadorService) {

	obterJobs();

	function obterJobs() {
		AgendadorService.obterJobs().then(function(jobs) {
			$scope.jobs = jobs;
		});
	}

});