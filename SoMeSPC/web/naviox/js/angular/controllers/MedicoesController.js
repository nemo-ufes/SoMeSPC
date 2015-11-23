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
	carregarEntidades();
	inicializar();
	
	/**
	 * Carrega a lista de entidades.
	 */
	function carregarEntidades() {
		MedicaoService.obterEntidades().then(function(entidades) {
			$scope.entidades = entidades;
		});
	}
	
	/**
	 * Inicializa o gráfico.
	 */
	function inicializar(){
		$scope.medidas = new Array();
		$scope.itensParaSelecao = new Array();
		inicializarGrafico();
	}
	
	function inicializarGrafico(){
		$scope.paginaAtual = 1;
		$scope.numPerPage = 5;
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
	
	// Objeto Lista de Itens - GET através da Web Service do IntegratorPlanoMedicao,
	// usando $Resource do Angular JS
	$scope.itens = IntegratorPlanoMedicao.query({
		entidade : 'ItensPlanoDeMedicao'
	});

	/**
	 * Busca as medidas comuns entre a Entidade Mensurável 1 e 2.
	 VERIFICAR ESSA FUNÇÃO!*/
	$scope.obterMedidas = function obterMedidas() {
		inicializar();
	
		// Se as duas entidades foram selecionadas, aguarda o retorno das
		// medidas das duas.
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada) && !angular.isUndefinedOrNull($scope.entidade.Selecionada2)){			
		
			$q.all([MedicaoService.obterMedidas($scope.entidade.Selecionada.id), MedicaoService.obterMedidas($scope.entidade.Selecionada2.id)])
			.then(function(responses){					
				$scope.medidasEntidade1 = responses[0];
				$scope.medidasEntidade2 = responses[1];				
				$scope.selecionarMedidasParaExibicao();
			})
		} else {			
			// Senão, busca da que foi selecionada.
			if (!angular.isUndefinedOrNull($scope.entidade.Selecionada)){			
				MedicaoService.obterMedidas($scope.entidade.Selecionada.id).then(
						function(medidas) {
							$scope.medidasEntidade1 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}
			
			if (!angular.isUndefinedOrNull($scope.entidade.Selecionada2)){			
				MedicaoService.obterMedidas($scope.entidade.Selecionada2.id).then(
						function(medidas) {
							$scope.medidasEntidade2 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}			
		}	
		
		
	}
	
	$scope.selecionarMedidasParaExibicao = function selecionarMedidasParaExibicao(){
		// Se somente uma entidade foi selecionada, usa apenas as medidas dela.
		if (angular.isUndefinedOrNull($scope.entidade.Selecionada) != angular.isUndefinedOrNull($scope.entidade.Selecionada2)){			
			
			
			if (!angular.isUndefinedOrNull($scope.medidasEntidade1)){
				$scope.medidas = $scope.medidasEntidade1
			}
			else{
				$scope.medidas = $scope.medidasEntidade2
			}
			
			for (idxMedida in $scope.medidas) {
				var medida = $scope.medidas[idxMedida];
								
				for (idxItem in $scope.itens){								
					var item = $scope.itens[idxItem];
					
					if (medida.nome == item.nome_Medida){								
						var objetivoMedida = { objetivo : item.nome_ObjetivoDeMedicao, medida : medida };									
						$scope.itensParaSelecao.push(objetivoMedida);
					}								
				}							
			} 			
		}
		else {			
			
			if (!angular.isUndefinedOrNull($scope.medidasEntidade1) && !angular.isUndefinedOrNull($scope.medidasEntidade2)){
				
				$q.all([MedicaoService.obterMedidas($scope.entidade.Selecionada.id), MedicaoService.obterMedidas($scope.entidade.Selecionada2.id)])
					.then(function(responses){					
						$scope.medidasEntidade1 = responses[0];
						$scope.medidasEntidade2 = responses[1];
						
						$scope.medidasComuns = [];
						// Senão, seleciona as medidas em comum.
						// Primeiro, testa as medidas do array 1 no array 2.
						for (idx in $scope.medidasEntidade1) {				
							
							var item = $scope.medidasEntidade1[idx];
							
							for(idx_2 in $scope.medidasEntidade2){
								
								if($scope.medidasEntidade1[idx].nome == $scope.medidasEntidade2[idx_2].nome){
									$scope.medidasComuns.push(item);
								}
							}
						}	
						
						$scope.medidas = $scope.medidasComuns;		
						
						for (idxMedida in $scope.medidas) {
							var medida = $scope.medidas[idxMedida];							
							
							for (idxItem in $scope.itens){								
								var item = $scope.itens[idxItem];
								
								if (medida.nome == item.nome_Medida){								
									var objetivoMedida = { objetivo : item.nome_ObjetivoDeMedicao, medida : medida };									
									$scope.itensParaSelecao.push(objetivoMedida);
								}								
							}							
						} 
					}); 
			}				 	 
		}	
	}
	
	/**
	 * Busca os valores das medições de acordo com a página atual do paginator e
	 * das entidades selecionadas.
	 */
	$scope.obterMedicoes = function (paginaAtual) {		
		
		if (paginaAtual == 1){
			inicializarGrafico();
		}
		
		if (angular.isUndefinedOrNull($scope.entidade.Selecionada) && angular.isUndefinedOrNull($scope.entidade.Selecionada2)){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if (angular.isUndefinedOrNull($scope.itemSelecionado.medida)){
			console.warn("Nenhuma medida selecionada.");
			return;
		}
				
		//So funciona nesse padrão de data...
		var dataInicioFormatada = $filter('date')($scope.dataInicio,'yyyy-MM-dd');
		var dataFimFormatada = $filter('date')($scope.dataFim,'yyyy-MM-dd');
						
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada)){
			
			MedicaoService.obterMedicoes($scope.entidade.Selecionada.id, $scope.itemSelecionado.medida.id, paginaAtual, $scope.numPerPage, dataInicioFormatada, dataFimFormatada).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
								
				$scope.paginaAtual = paginaAtual;
				$scope.dados[0] = dados;
				$scope.labels = labels;			
				$scope.series[0] = [$scope.entidade.Selecionada.nome + ' / ' + $scope.itemSelecionado.medida.nome];
				
				$scope.configurarPaginator($scope.numPerPage);
			});
		}
		
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada2)){
								
			MedicaoService.obterMedicoes($scope.entidade.Selecionada2.id, $scope.itemSelecionado.medida.id, paginaAtual, $scope.numPerPage, dataInicioFormatada, dataFimFormatada).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
									
				$scope.paginaAtual = paginaAtual;
				$scope.dados[1] = dados;
				$scope.labels = labels;			
				$scope.series[1] = [$scope.entidade.Selecionada2.nome + ' / ' + $scope.itemSelecionado.medida.nome];
			});
		}
	}	
	
	
	/**
	 * Configurações do DatePicker
	 */
    $scope.dataInicio = new Date();
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