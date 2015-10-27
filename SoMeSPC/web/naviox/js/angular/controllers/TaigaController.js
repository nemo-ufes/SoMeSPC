//Definição do Controlador - AngularJS
app.controller('TaigaController', function($scope, $resource,
		TaigaIntegratorProjeto, SoMeSPCResource, TaigaIntegratorPlano,
		TaigaIntegrator) {

	// Objeto Login
	$scope.login = {
		url : '',
		usuario : '',
		senha : ''
	};
	
	// Objeto Lista de Itens - GET através da Web Service do Taiga, usando
	// $Resource do Angular JS
	$scope.itens = TaigaIntegrator.query({
		entidade : 'ItensPlanoDeMedicao'
	});

	// Objeto Lista de Periodicidades - GET através da Web Service do Taiga,
	// usando $Resource do Angular JS
	$scope.periodicidades = SoMeSPCResource.query({
		periodicidades_med_cep : 'Periodicidade'
	});

	$scope.loading = false;
	$scope.projetos;
	$scope.projetosSelecionados = [];
	$scope.periodicidade_selected;
	$scope.itens_selected = [];

	$scope.toggleLoading = function toggleLoading(){
		$scope.loading = !$scope.loading;
	}
	
	$scope.toggleSelectionProjeto = function toggleSelectionProjeto(projeto) {
		var idx = $scope.projetosSelecionados.indexOf(projeto);

		if (idx > -1) {
			$scope.projetosSelecionados.splice(idx, 1);
		} else {
			$scope.projetosSelecionados.push(projeto);
		}
	};
	
	$scope.toggleSelectionMedida = function toggleSelectionMedida(item) {
		console.log(item);
		
		var idx = $scope.itens_selected.indexOf(item);
		
		if (idx > -1) {
			$scope.itens_selected.splice(idx, 1);
		} else {
			$scope.itens_selected.push(item);
		}
	};

	$scope.get_periodicidade = function(index) {
		$scope.periodicidade_selected = $scope.periodicidades[index];
	}

	$scope.post_plano = function(retorno) {
			
		$scope.toggleLoading();
		$scope.entry = new TaigaIntegratorPlano(); 

		$scope.entry.apelido_Projetos = [];
		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
		$scope.entry.nome_Itens = [];
		
		for (idx in $scope.projetosSelecionados) {
			$scope.entry.apelido_Projetos.push($scope.projetosSelecionados[idx].apelido);
		}
		
		for (idx in $scope.itens_selected) {
			$scope.entry.nome_Itens.push($scope.itens_selected[idx]);
		}
		
		console.log($scope.entry.nome_Itens);
		
		$scope.entry.taiga_Login = $scope.login;
		console.log($scope.entry);
		$scope.entry.$save(function sucesso(plano){
			$scope.toggleLoading();
			console.log(plano);
			return retorno(plano);
		}, function erro(err){
			$scope.toggleLoading();
			console.log(err);
			return retorno(err);			
		});
	}
	
//	$scope.post_planoTeste = function(retorno) {
//		
//		$scope.toggleLoading();
//		$scope.entry = new TaigaIntegratorPlanoTeste(); 
//
//		$scope.entry.apelido_Projetos = [];
//		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
//		
//		for (idx in $scope.projetosSelecionados) {
//			$scope.entry.apelido_Projetos.push($scope.projetosSelecionados[idx].apelido);
//		}
//		
//		$scope.entry.taiga_Login = $scope.login;
//		console.log($scope.entry);
//		$scope.entry.$save(function sucesso(plano){
//			$scope.toggleLoading();
//			console.log(plano);
//			return retorno(plano);
//		}, function erro(err){
//			$scope.toggleLoading();
//			console.log(err);
//			return retorno(err);			
//		});
//	}

	$scope.post_projeto = function(retorno) {
		$scope.toggleLoading();
		TaigaIntegratorProjeto.save($scope.login).$promise.then(
				function sucesso(result) {
					$scope.projetos = result;
					$scope.projetosSelecionados = [];
					$scope.toggleLoading();
					return retorno(true);
				}, function erro(err) {
					$scope.toggleLoading();
					return retorno(false);
				});
	}
});