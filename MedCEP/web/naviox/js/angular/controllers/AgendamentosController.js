/**
 * Controlador da aba de Agendamentos do Painel de Controle
 */
app.controller('AgendamentosController', function($scope, $interval,
		AgendadorService) {

	/**
	 * Obtem a lista de agendamentos.
	 */
	$scope.obterAgendamentos = function obterAgendamentos() {
		AgendadorService.obterAgendamentos().then(function(agendamentos) {
			$scope.agendamentos = agendamentos;
		});
	}

	/**
	 * Inicia o agendamento selecionado na linha.
	 */
	$scope.iniciarAgendamento = function iniciarAgendamento(linha) {
		var index = $scope.agendamentos.indexOf(linha);
		var agendamento = $scope.agendamentos[index];

		AgendadorService.iniciarAgendamento(agendamento.nome_agendamento,
				agendamento.grupo_agendamento).then(function() {
			$scope.obterAgendamentos();
		});
	}

	/**
	 * Pausa o agendamento selecionado na linha.
	 */
	$scope.pausarAgendamento = function pausarAgendamento(linha) {
		var index = $scope.agendamentos.indexOf(linha);
		var agendamento = $scope.agendamentos[index];

		AgendadorService.pausarAgendamento(agendamento.nome_agendamento,
				agendamento.grupo_agendamento).then(function() {
			$scope.obterAgendamentos();
		});
	}

	/**
	 * Exclui o agendamento selecionado na linha.
	 */
	$scope.excluirAgendamento = function excluirAgendamento(linha) {
		var index = $scope.agendamentos.indexOf(linha);
		var agendamento = $scope.agendamentos[index];

		AgendadorService.excluirAgendamento(agendamento.nome_agendamento,
				agendamento.grupo_agendamento).then(function() {
			$scope.obterAgendamentos();
		});
	}

	/**
	 * Inicializa a lista de agendamentos.
	 */
	$scope.obterAgendamentos();

	/**
	 * Atualiza a lista de agendamentos a cada X segundos;
	 */
	$interval(function() {
		$scope.obterAgendamentos();
	}.bind(this), 5000);

});