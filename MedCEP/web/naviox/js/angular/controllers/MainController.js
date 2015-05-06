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

	$scope.projeto_selected;

	$scope.periodicidade_selected;

	$scope.medidas_selected = [];

	$scope.toggleSelection = function toggleSelection(medida) {
		var idx = $scope.medidas_selected.indexOf(medida);

		// is currently selected
		if (idx > -1) {
			$scope.medidas_selected.splice(idx, 1);
		}

		// is newly selected
		else {
			$scope.medidas_selected.push(medida);
		}
	};

	$scope.get_periodicidade = function(index) {
		$scope.periodicidade_selected = $scope.periodicidades[index];
	}

	$scope.post_plano = function() {
		$scope.entry = new TaigaIntegratorPlano(); // You can instantiate
													// resource class

		$scope.entry.apelido_Projeto = $scope.projeto_selected.apelido;
		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
		$scope.entry.nome_Medidas = [];
		for (medida in $scope.medida_selected) {
			$scope.entry.nome_Medidas.push(medida);
		}
		$scope.entry.taiga_Login = $scope.login;
		$scope.entry.$save();
	}

	$scope.post_projeto = function(retorno) {
		TaigaIntegratorProjeto.save($scope.login).$promise.then(
				function sucesso(result) {
					$scope.projetos = result;
					return retorno(true);
				}, function erro(err) {
					return retorno(false);
				});
	}
});