/**
 * Controlador da aba de Agendamentos do Painel de Controle
 */
app.controller('AgendamentosController', function($scope, AgendadorService) {

	$scope.obterAgendamentos = function obterAgendamentos() {
		AgendadorService.obterAgendamentos().then(function(agendamentos) {
			$scope.agendamentos = agendamentos;
		});
	}

	$scope.pausarAgendamento = function pausarAgendamento(linha) {
		var index = $scope.agendamentos.indexOf(linha);
		var agendamento = $scope.agendamentos[index];

		AgendadorService.pausarAgendamento(agendamento.nome_agendamento,
				agendamento.grupo_agendamento).then(function() {
			$scope.obterAgendamentos();
		});
	}
	
	$scope.iniciarAgendamento = function iniciarAgendamento(linha) {
		var index = $scope.agendamentos.indexOf(linha);
		var agendamento = $scope.agendamentos[index];

		AgendadorService.iniciarAgendamento(agendamento.nome_agendamento,
				agendamento.grupo_agendamento).then(function() {
			$scope.obterAgendamentos();
		});
	}

	$scope.obterAgendamentos();

});