//Definição do Controlador - AngularJS
app.controller('MainController', function($scope, $resource,
		TaigaIntegratorProjeto, MedCEPResource, TaigaIntegratorPlano,
		TaigaIntegrator) {

	// Objeto Login
	$scope.login = {
		url : 'http://ledsup.sr.ifes.edu.br/',
		usuario : 'vinnysoft',
		senha : 'teste123'
	};

	// Objeto Lista de Medidas - GET através da Web Service do Taiga, usando
	// $Resource do Angular JS
	$scope.medidas = TaigaIntegrator.query({
		entidade : 'Medidas'
	});

	// Objeto Lista de Periodicidades - GET através da Web Service do Taiga,
	// usando $Resource do Angular JS
	$scope.periodicidades = MedCEPResource.query({
		periodicidades_med_cep : 'Periodicidade'
	});

	$scope.projetos;
	$scope.projetosSelecionados = [];
	$scope.periodicidade_selected;
	$scope.medidas_selected = [];

	$scope.toggleSelectionProjeto = function toggleSelectionProjeto(projeto) {
		var idx = $scope.projetosSelecionados.indexOf(projeto);

		if (idx > -1) {
			$scope.projetosSelecionados.splice(idx, 1);
		} else {
			$scope.projetosSelecionados.push(projeto);
		}
	};
	
	$scope.toggleSelectionMedida = function toggleSelectionMedida(medida) {
		var idx = $scope.medidas_selected.indexOf(medida);

		if (idx > -1) {
			$scope.medidas_selected.splice(idx, 1);
		} else {
			$scope.medidas_selected.push(medida);
		}
	};

	$scope.get_periodicidade = function(index) {
		$scope.periodicidade_selected = $scope.periodicidades[index];
	}

	$scope.post_plano = function(retorno) {
			
		$scope.entry = new TaigaIntegratorPlano(); 

		$scope.entry.apelido_Projetos = [];
		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
		$scope.entry.nome_Medidas = [];
		
		for (idx in $scope.projetosSelecionados) {
			$scope.entry.apelido_Projetos.push($scope.projetosSelecionados[idx].apelido);
		}
		
		for (idx in $scope.medidas_selected) {
			$scope.entry.nome_Medidas.push($scope.medidas_selected[idx]);
		}
		
		$scope.entry.taiga_Login = $scope.login;
		$scope.entry.$save(function sucesso(plano){
			return retorno(plano);
		}, function erro(err){
			return retorno(err);
		});
	}

	$scope.post_projeto = function(retorno) {
		TaigaIntegratorProjeto.save($scope.login).$promise.then(
				function sucesso(result) {
					$scope.projetos = result;
					$scope.projetosSelecionados = [];
					return retorno(true);
				}, function erro(err) {
					return retorno(false);
				});
	}
});