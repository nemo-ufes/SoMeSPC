/**
 * Controlador da aba de Medições do Painel de Controle.
 */
app.controller('MedicoesController', function($scope, MedicaoService) {
	
	carregarEntidades();
	inicializar();

	
	function carregarEntidades() {
		MedicaoService.obterEntidades().then(function(entidades) {
			$scope.entidades = entidades;
		});
	}
	
	function inicializar(){
		$scope.paginaAtual = 1;
		$scope.numPerPage = 5;
		$scope.dados = [[0,0,0,0,0]];
		$scope.labels = ["0","0","0","0","0"];	
		$scope.series = [''];
		$scope.colours = [{fillColor : "#e9ebed", strokeColor : "#2C3E50", pointColor : "#2C3E50", pointStrokeColor : "#fff"}];
	}
	
	$scope.onSelecionarEntidade = function onSelecionarEntidade($item, $model, $label) {
		$scope.entidadeSelecionada = $item;
		console.log("Entidade 1: " + $scope.entidadeSelecionada.id);
		$scope.obterMedidas();
	};

	$scope.onSelecionarEntidade2 = function onSelecionarEntidade2($item, $model, $label) {
		$scope.entidadeSelecionada2 = $item;
		console.log("Entidade 2: " + $scope.entidadeSelecionada2.id);
		$scope.obterMedidas();
	};
	
	$scope.configurarPaginator = function configurarPaginator(numPerPage)	{
		MedicaoService.obterTotalMedicoes($scope.entidadeSelecionada.id, $scope.medidaSelecionada.id).then(function (total) {
			 $scope.totalItems = total;
			 $scope.numPerPage = numPerPage;
		});
	}

	$scope.obterMedidas = function obterMedidas() {
		inicializar();
		
		MedicaoService.obterMedidas($scope.entidadeSelecionada.id).then(
				function(medidas) {
					$scope.medidas = medidas;
				});
	}
	
	$scope.obterMedicoes = function (paginaAtual) {		
		
		if ($scope.entidadeSelecionada == undefined){
			console.warn("Nenhuma entidade selecionada.");
			return;
		}
		
		if ($scope.medidaSelecionada == undefined){
			console.warn("Nenhuma medida selecionada.");
			return;
		}
		
		MedicaoService.obterMedicoes($scope.entidadeSelecionada.id, $scope.medidaSelecionada.id, paginaAtual, $scope.numPerPage).then(function(valores) {			
			var dados = new Array();
			var labels = new Array();
			
			for (var medicao of valores){
				dados.push(medicao.valor_medido);
				labels.push(medicao.data)
			};
				
			$scope.paginaAtual = paginaAtual;
			$scope.dados = [dados];
			$scope.labels = labels;			
			$scope.series = [$scope.entidadeSelecionada.nome + ' / ' + $scope.medidaSelecionada.nome];
			
			$scope.configurarPaginator($scope.numPerPage);
		});
	}
	
});