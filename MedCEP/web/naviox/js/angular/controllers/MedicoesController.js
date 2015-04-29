/**
 * Controlador da aba de Medições do Painel de Controle.
 */
app.controller('MedicoesController', function($scope, MedicaoService, $q) {
	
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
		$scope.paginaAtual = 1;
		$scope.numPerPage = 5;
		$scope.dados = [[0,0,0,0,0]];
		$scope.labels = ["0","0","0","0","0"];	
		$scope.series = [''];
		$scope.colours = [{fillColor : "#e9ebed", strokeColor : "#2C3E50", pointColor : "#2C3E50", pointStrokeColor : "#fff"},
		                  {fillColor : "#c5cabf", strokeColor : "#3E502C", pointColor : "#3E502C", pointStrokeColor : "#fff"}];
	}
	
	/**
	 * Listener executado ao selecionar Entidade Mensurável 1.
	 */
	$scope.onSelecionarEntidade = function onSelecionarEntidade($item, $model, $label) {
		$scope.entidadeSelecionada = $item;
		console.log("Entidade 1: " + $scope.entidadeSelecionada.id);
		$scope.obterMedidas();
	};

	/**
	 * Listener executado ao selecionar a Entidade Mensurável 2.
	 */
	$scope.onSelecionarEntidade2 = function onSelecionarEntidade2($item, $model, $label) {
		$scope.entidadeSelecionada2 = $item;
		console.log("Entidade 2: " + $scope.entidadeSelecionada2.id);
		$scope.obterMedidas();
	};
	
	/**
	 * Configura o paginator com as páginas corretas.
	 */
	$scope.configurarPaginator = function configurarPaginator(numPerPage)	{
		MedicaoService.obterTotalMedicoes($scope.entidadeSelecionada.id, $scope.medidaSelecionada.id).then(function (total) {
			 $scope.totalItems = total;
			 $scope.numPerPage = numPerPage;
		});
	}

	/**
	 * Busca as medidas comuns entre a Entidade Mensurável 1 e 2.
	 */
	$scope.obterMedidas = function obterMedidas() {
		inicializar();
				
		//Se as duas entidades foram selecionadas, aguarda o retorno das medidas das duas.
		if ($scope.entidadeSelecionada != undefined && $scope.entidadeSelecionada != null
				&& $scope.entidadeSelecionada2 != undefined && $scope.entidadeSelecionada2 != null){			
		
			$q.all([MedicaoService.obterMedidas($scope.entidadeSelecionada.id), MedicaoService.obterMedidas($scope.entidadeSelecionada2.id)])
			.then(function(responses){
				$scope.medidasEntidade1 = responses[0].data;
				$scope.medidasEntidade2 = responses[1].data;
				$scope.selecionarMedidasParaExibicao();
			})
		}
		else{
			
			//Senão, busca da que foi selecionada.
			if ($scope.entidadeSelecionada != undefined && $scope.entidadeSelecionada != null){			
				MedicaoService.obterMedidas($scope.entidadeSelecionada.id).then(
						function(medidas) {
							$scope.medidasEntidade1 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}
			
			if ($scope.entidadeSelecionada2 != undefined && $scope.entidadeSelecionada2 != null){			
				MedicaoService.obterMedidas($scope.entidadeSelecionada2.id).then(
						function(medidas) {
							$scope.medidasEntidade2 = medidas;
							$scope.selecionarMedidasParaExibicao();
						});
			}			
		}
		
	}
	
	$scope.selecionarMedidasParaExibicao = function selecionarMedidasParaExibicao(){
		//Se somente uma entidade foi selecionada, usa apenas as medidas dela.
		if (($scope.entidadeSelecionada != undefined && $scope.entidadeSelecionada != null)
				!=($scope.entidadeSelecionada2 != undefined && $scope.entidadeSelecionada2 != null)){
			
			if ($scope.medidasEntidade1 != undefined){
				$scope.medidas = $scope.medidasEntidade1
			}
			else{
				$scope.medidas = $scope.medidasEntidade2
			}
		}
		else {
			//Senão, seleciona as medidas em comum.
			//Primeiro, testa as medidas do array 1 no array 2.
			for (var i = 0; i < $scope.medidasEntidade1.length; i++) {				
				var index = $.inArray($scope.medidasEntidade1[i], $scope.medidasEntidade2)
				
				//Se não tiver a medida (indice -1), remove do array.
				if (index == -1){
					$scope.medidasEntidade1 = $scope.medidasEntidade1.splice(index, 1);
				}
			 }	
			
			//Agora inverte, buscando as medidas do array 2 no array 1.
			for (var i = 0; i < $scope.medidasEntidade2.length; i++) {				
				var index = $.inArray($scope.medidasEntidade2[i], $scope.medidasEntidade1)
				
				//Se não tiver a medida (indice -1), remove do array.
				if (index == -1){
					$scope.medidasEntidade2= $scope.medidasEntidade2.splice(index, 1);
				}
			 }	
			
			//Agora que os arrays são iguais, usa qualquer um para exibir as medidas.
			$scope.medidas = $scope.medidasEntidade1;			
		}	 	 
	}
	
	/**
	 * Busca os valores das medições de acordo com a página atual do paginator e das entidades selecionadas.
	 */
	$scope.obterMedicoes = function (paginaAtual) {		
		
		if ($scope.entidadeSelecionada == undefined){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if ($scope.medidaSelecionada == undefined){
			console.warn("Nenhuma medida selecionada.");
			return;
		}
		
		if ($scope.entidadeSelecionada != undefined && $scope.entidadeSelecionada != null){
			console.log("entidade 1: " + $scope.entidadeSelecionada.nome);
			MedicaoService.obterMedicoes($scope.entidadeSelecionada.id, $scope.medidaSelecionada.id, paginaAtual, $scope.numPerPage).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
					
				$scope.paginaAtual = paginaAtual;
				$scope.dados[0] = [dados];
				$scope.labels = labels;			
				$scope.series = [$scope.entidadeSelecionada.nome + ' / ' + $scope.medidaSelecionada.nome];
				
				$scope.configurarPaginator($scope.numPerPage);
			});
		}
		
		if ($scope.entidadeSelecionada2 != undefined && $scope.entidadeSelecionada2 != null){
			console.log("entidade 2: " +$scope.entidadeSelecionada2.nome);
			MedicaoService.obterMedicoes($scope.entidadeSelecionada2.id, $scope.medidaSelecionada.id, paginaAtual, $scope.numPerPage).then(function(valores) {			
				var dados = new Array();
				var labels = new Array();
				
				for (var medicao of valores){
					dados.push(medicao.valor_medido);
					labels.push(medicao.data)
				};
					
				$scope.paginaAtual = paginaAtual;
				$scope.dados[1] = [dados];
				$scope.series[1] = [$scope.entidadeSelecionada2.nome + ' / ' + $scope.medidaSelecionada.nome];
				
			});
		}
	}
	
});