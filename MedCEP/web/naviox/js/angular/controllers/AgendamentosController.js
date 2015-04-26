/**
 * Controlador da aba de Agendamentos do Painel de Controle
 */
app.controller('AgendamentosController', function($scope, $interval, dialogs,
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
			$scope.mensagem = 'Agendamento ' + agendamento.nome_agendamento + ' excluído com sucesso!';
		});
	}

	/**
	 * Abre um modal para confirmar a exclusão do agendamento.
	 */
	$scope.confirmarExclusao = function confirmarExclusao(linha) {		

		$scope.linhaSelecionada = linha;			
		
		var cabecalho = 'Atenção!';
		var mensagem = 'Os agendamentos são criados apenas durante o wizard para criar um Novo Plano de Medição.' 
						+ ' Deseja prosseguir com a exclusão do agendamento "'+ linha.nome_agendamento +'" ?';
		
		var dlg = dialogs.confirm(cabecalho, mensagem);
		
		dlg.result.then(function(btn) {
			$scope.excluirAgendamento($scope.linhaSelecionada);			
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

})
.config(function(dialogsProvider,$translateProvider){
		dialogsProvider.useBackdrop('static');
		dialogsProvider.useEscClose(false);
		dialogsProvider.useCopy(false);
		dialogsProvider.setSize('sm');

		/**
		 * Traduções pt-BR do angular-dialog-service.
		 */
		$translateProvider.translations('pt-BR',{
			DIALOGS_ERROR: "Erro",
			DIALOGS_ERROR_MSG: "Ocorreu um erro.",
			DIALOGS_CLOSE: "Fechar",
			DIALOGS_CONFIRMATION: "Atenção",
			DIALOGS_CONFIRMATION_MSG: "Essa operação é irreversível. Deseja realmente continuar?",
			DIALOGS_OK: "OK",
			DIALOGS_YES: "Sim",
			DIALOGS_NO: "Não"
		});

		$translateProvider.preferredLanguage('pt-BR');
});