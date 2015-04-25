/**
 * Controlador da aba de Agendamentos do Painel de Controle
 */
app.controller('AgendamentosController', function($scope, AgendadorService) {

	obterAgendamentos();

	function obterAgendamentos() {
		AgendadorService.obterAgendamentos().then(function(agendamentos) {
			$scope.agendamentos = agendamentos;
		});
	}

});