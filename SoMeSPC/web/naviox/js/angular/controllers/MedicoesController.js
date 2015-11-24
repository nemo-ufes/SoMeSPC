/**
 * Controlador da aba de Medições do Painel de Controle.
 */
app.controller('MedicoesController', function($scope, $resource, MedicaoService, IntegratorPlanoMedicao, $q, $filter) {
	
	/**
	 * Função auxiliar.
	 */
	angular.isUndefinedOrNull = function(val) {
	    return angular.isUndefined(val) || val === null || val === ""
	}
	
	/**
	 * Inicialização.
	 */
	inicializar();
	
	/**
	 * Carrega a lista de itens.
	 */
	$scope.itens = IntegratorPlanoMedicao.query({
		entidade : 'ItensPlanoDeMedicao'
	}, function sucesso(itens){
		$scope.itensParaSelecao = new Array();
		
		for (idx in itens){
			
			var item = itens[idx];
			
			if (!angular.isUndefinedOrNull(item) && !angular.isUndefinedOrNull(item.nome_Medida)){
				var objetivoMedida = { objetivo : item.nome_ObjetivoDeMedicao, medida : item.nome_Medida };									
				$scope.itensParaSelecao.push(objetivoMedida);	
			}
			
		}	
		
	});
	
	/**
	 * Carrega a lista de entidades.
	 */
	$scope.obterEntidades = function obterEntidades() {
		MedicaoService.obterEntidades($scope.itemSelecionado.medida).then(function(entidades) {
			$scope.entidades = entidades;
		});
		
		inicializarGrafico();
	}
	
	/**
	 * Inicializa a tela.
	 */
	function inicializar(){
		$scope.medidas = new Array();
		$scope.numPerPage = 5;		
		inicializarGrafico();
	}
	
	/**
	 * Inicializa o gráfico.
	 */
	function inicializarGrafico(){
		$scope.paginaAtual = 1;
		$scope.dados = new Array();
		$scope.labels = new Array();	
		$scope.series = [''];
		$scope.colours = [{fillColor : "rgba(233,235,237,0.4)", strokeColor : "#2C3E50", pointColor : "#2C3E50", pointStrokeColor : "#fff"},
		                  {fillColor : "rgba(197,202,191,0.4)", strokeColor : "#3E502C", pointColor : "#3E502C", pointStrokeColor : "#fff"}];
	}
	
	/**
	 * Configura o paginator com as páginas corretas.
	 */
	$scope.configurarPaginator = function configurarPaginator(numPerPage)	{
		var dataInicioFormatada = $filter('date')($scope.dataInicio,'yyyy-MM-dd');
		var dataFimFormatada = $filter('date')($scope.dataFim,'yyyy-MM-dd');
				
		MedicaoService.obterTotalMedicoes($scope.entidade.Selecionada.id, $scope.itemSelecionado.medida.id, dataInicioFormatada, dataFimFormatada).then(function (total) {
			 $scope.totalItems = total;
			 $scope.numPerPage = numPerPage;
		});
	}	
				
	/**
	 * Busca os valores das medições de acordo com a página atual do paginator e
	 * das entidades selecionadas.
	 */
	$scope.obterMedicoes = function (paginaAtual) {		
		
		if (paginaAtual == 1){
			inicializarGrafico();
		}
		
		if (angular.isUndefinedOrNull($scope.entidade)){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if (angular.isUndefinedOrNull($scope.entidade.Selecionada) && angular.isUndefinedOrNull($scope.entidade.Selecionada2)){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if (angular.isUndefinedOrNull($scope.itemSelecionado)){
			console.warn("Nenhuma medida selecionada.");
			return;
		}
				
		//So funciona nesse padrão de data...
		var dataInicioFormatada = $filter('date')($scope.dataInicio,'yyyy-MM-dd');
		var dataFimFormatada = $filter('date')($scope.dataFim,'yyyy-MM-dd');
						
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada)){
			
			MedicaoService.obterMedicoes($scope.entidade.Selecionada.id, $scope.itemSelecionado.medida, paginaAtual, $scope.numPerPage, dataInicioFormatada, dataFimFormatada).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
								
				$scope.paginaAtual = paginaAtual;
				$scope.dados[0] = dados;
				$scope.labels = labels;			
				$scope.series[0] = [$scope.entidade.Selecionada.nome + ' / ' + $scope.itemSelecionado.medida];
				
				$scope.configurarPaginator($scope.numPerPage);
			});
		}
		
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada2)){
								
			MedicaoService.obterMedicoes($scope.entidade.Selecionada2.id, $scope.itemSelecionado.medida, paginaAtual, $scope.numPerPage, dataInicioFormatada, dataFimFormatada).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
									
				$scope.paginaAtual = paginaAtual;
				$scope.dados[1] = dados;
				$scope.labels = labels;			
				$scope.series[1] = [$scope.entidade.Selecionada2.nome + ' / ' + $scope.itemSelecionado.medida];
			});
		}
	}	
	
	
	/**
	 * Configurações do DatePicker
	 */
    $scope.dataInicio = new Date();
    $scope.dataInicio.setDate( $scope.dataInicio.getDate()-30);
    
    $scope.dataFim = new Date();
	 
	$scope.statusDataInicio = {
		opened: false
	};
	
	$scope.statusDataFim = {
		opened: false
	};
	 
	$scope.openDataInicio = function($event) {
		$scope.statusDataInicio.opened = true;
	};
	
	$scope.openDataFim = function($event) {
		$scope.statusDataFim.opened = true;
	};
		
});