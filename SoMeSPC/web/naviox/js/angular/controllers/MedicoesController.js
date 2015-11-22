/**
 * Controlador da aba de Medições do Painel de Controle.
 */
app.controller('MedicoesController', function($scope, MedicaoService, $q) {
	
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
		$scope.medidas = "";
		$scope.paginaAtual = 1;
		$scope.numPerPage = 5;
		$scope.dados = new Array();
		$scope.labels = new Array();	
		$scope.series = [''];
		$scope.colours = [{fillColor : "#e9ebed", strokeColor : "#2C3E50", pointColor : "#2C3E50", pointStrokeColor : "#fff"},
		                  {fillColor : "#c5cabf", strokeColor : "#3E502C", pointColor : "#3E502C", pointStrokeColor : "#fff"}];
	}
	
	/**
	 * Listener executado ao selecionar Entidade Mensurável 1.
	$scope.onSelecionarEntidade = function onSelecionarEntidade($item, $model, $label) {
		$scope.entidadeSelecionada = $item;
		$scope.obterMedidas();
	};*/

	/**
	 * Listener executado ao selecionar a Entidade Mensurável 2.
	 */
	$scope.onSelecionarEntidade2 = function onSelecionarEntidade2($item, $model, $label) {
		$scope.entidadeSelecionada2 = $item;
		$scope.obterMedidas();
	};
	
	/**
	 * Configura o paginator com as páginas corretas.
	 */
	$scope.configurarPaginator = function configurarPaginator(numPerPage)	{
		MedicaoService.obterTotalMedicoes($scope.entidade.Selecionada.id, $scope.medidaSelecionada.id).then(function (total) {
			 $scope.totalItems = total;
			 $scope.numPerPage = numPerPage;
		});
	}

	/**
	 * Busca as medidas comuns entre a Entidade Mensurável 1 e 2.
	 VERIFICAR ESSA FUNÇÃO!*/
	$scope.obterMedidas = function obterMedidas() {
		inicializar();
	
		// Se as duas entidades foram selecionadas, aguarda o retorno das
		// medidas das duas.
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada) && !angular.isUndefinedOrNull($scope.entidadeSelecionada2)){			
		
			$q.all([MedicaoService.obterMedidas($scope.entidade.Selecionada.id), MedicaoService.obterMedidas($scope.entidadeSelecionada2.id)])
			.then(function(responses){					
				$scope.medidasEntidade1 = responses[0].data;
				$scope.medidasEntidade2 = responses[1].data;
				$scope.selecionarMedidasParaExibicao();
			})
		}
		else{			
			// Senão, busca da que foi selecionada.
			if (!angular.isUndefinedOrNull($scope.entidade.Selecionada)){			
				MedicaoService.obterMedidas($scope.entidade.Selecionada.id).then(
						function(medidas) {
							$scope.medidasEntidade1 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}
			
			if (!angular.isUndefinedOrNull($scope.entidadeSelecionada2)){			
				MedicaoService.obterMedidas($scope.entidadeSelecionada2.id).then(
						function(medidas) {
							$scope.medidasEntidade2 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}			
		}
		
	}
	
	$scope.selecionarMedidasParaExibicao = function selecionarMedidasParaExibicao(){
		// Se somente uma entidade foi selecionada, usa apenas as medidas dela.
		if (angular.isUndefinedOrNull($scope.entidade.Selecionada) != angular.isUndefinedOrNull($scope.entidadeSelecionada2)){			
		
			console.log("Entrou xor");
			if (!angular.isUndefinedOrNull($scope.medidasEntidade1)){
				$scope.medidas = $scope.medidasEntidade1
			}
			else{
				$scope.medidas = $scope.medidasEntidade2
			}
		}
		else {			
			console.log("Entrou else");
			
			if (angular.isUndefinedOrNull($scope.medidasEntidade1) && angular.isUndefinedOrNull($scope.medidasEntidade2)){
				
				$q.all([MedicaoService.obterMedidas($scope.entidade.Selecionada.id), MedicaoService.obterMedidas($scope.entidadeSelecionada2.id)])
					.then(function(responses){					
						$scope.medidasEntidade1 = responses[0];
						$scope.medidasEntidade2 = responses[1];
						
						console.log("medidas1: " + responses[0]);
						console.log("medidas2: " + responses[1]);
						
						// Senão, seleciona as medidas em comum.
						// Primeiro, testa as medidas do array 1 no array 2.
						for (var i = 0; i < $scope.medidasEntidade1.length; i++) {				
							var index = $.inArray($scope.medidasEntidade1[i], $scope.medidasEntidade2)
							
							// Se não tiver a medida (indice -1), remove do
							// array.
							if (index == -1){
								$scope.medidasEntidade1 = $scope.medidasEntidade1.splice(index, 1);
							}
						 }	
						
						// Agora inverte, buscando as medidas do array 2 no
						// array 1.
						for (var i = 0; i < $scope.medidasEntidade2.length; i++) {				
							var index = $.inArray($scope.medidasEntidade2[i], $scope.medidasEntidade1)
							
							// Se não tiver a medida (indice -1), remove do
							// array.
							if (index == -1){
								$scope.medidasEntidade2= $scope.medidasEntidade2.splice(index, 1);
							}
						 }	
						
						console.log("medidas1 pós: " + responses[0]);
						console.log("medidas2 pós: " + responses[1]);
						
						// Agora que os arrays são iguais, usa qualquer um
						// para exibir as medidas.
						$scope.medidas = $scope.medidasEntidade1;			
					}); 
			}				 	 
		}
	}
	
	/**
	 * Busca os valores das medições de acordo com a página atual do paginator e
	 * das entidades selecionadas.
	 */
	$scope.obterMedicoes = function (paginaAtual) {		
		
		if (angular.isUndefinedOrNull($scope.entidade.Selecionada) && angular.isUndefinedOrNull($scope.entidadeSelecionada2)){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if (angular.isUndefinedOrNull($scope.medidaSelecionada)){
			console.warn("Nenhuma medida selecionada.");
			return;
		}
		
		if (!angular.isUndefinedOrNull($scope.entidade.Selecionada)){
			MedicaoService.obterMedicoes($scope.entidade.Selecionada.id, $scope.medidaSelecionada.id, paginaAtual, $scope.numPerPage).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
								
				$scope.paginaAtual = paginaAtual;
				$scope.dados[0] = dados;
				$scope.labels = labels;			
				$scope.series[0] = [$scope.entidade.Selecionada.nome + ' / ' + $scope.medidaSelecionada.nome];
				
				$scope.configurarPaginator($scope.numPerPage);
			});
		}
		
		if (!angular.isUndefinedOrNull($scope.entidadeSelecionada2)){
			MedicaoService.obterMedicoes($scope.entidadeSelecionada2.id, $scope.medidaSelecionada.id, paginaAtual, $scope.numPerPage).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
									
				$scope.paginaAtual = paginaAtual;
				$scope.dados[1] = dados;
				$scope.labels = labels;			
				$scope.series[1] = [$scope.entidadeSelecionada2.nome + ' / ' + $scope.medidaSelecionada.nome];
			});
		}
	}	
});