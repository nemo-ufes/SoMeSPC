//Definição do Controlador - AngularJS
app.controller('WizardCtrl', function($scope, $resource, WizardHandler,
		TaigaIntegratorProjeto, IntegratorPlanoMedicao, SoMeSPCResourcePlano, SonarIntegratorProjeto) {

	// ---------------------------- Teste ------------------------------------------------
	
	$scope.finished = function() {
        alert("Wizard finished :)");
    }

    $scope.logStep = function() {
        console.log("Step continued");
    }

    $scope.goBack = function() {
        WizardHandler.wizard().goTo(0);
    }
    $scope.goFinish = function() {
        WizardHandler.wizard().goTo(5);
    }
	
	// ---------------------------- Objetos Taiga ----------------------------------------
	
	//Login Taiga
	$scope.loginTaiga = {
		url : '',
		usuario : '',
		senha : ''
	};
	
	//Projetos Taiga
	$scope.projetosTaiga;
	$scope.projetosSelecionados_taiga = [];
	
	// ---------------------------- Objetos Sonar ----------------------------------------
	
	//Login Sonar
	$scope.loginSonar = {
		url : ''
	};
	
	//Projetos Sonar
	$scope.projetosSonar;
	$scope.projetosSelecionados_sonar = [];
	
	// ---------------------------- Objetos Integrator ----------------------------------------
	
	//Variaveis auxiliares para o Plano de Medição
	$scope.loading = false;
	$scope.periodicidade_selected;
	$scope.itens_selected = [];
	
	//------------------------------------ Funções Integrator ---------------------------------------
	
	// Objeto Lista de Itens - GET através da Web Service do IntegratorPlanoMedicao,
	// usando $Resource do Angular JS
	$scope.itens = IntegratorPlanoMedicao.query({
		entidade : 'ItensPlanoDeMedicao'
	});

	// Objeto Lista de Periodicidades - GET através da Web Service do
	// IntegratorPlanoMedicao, usando $Resource do Angular JS
	$scope.periodicidades = IntegratorPlanoMedicao.query({
		entidade : 'Periodicidade'
	});
	
	$scope.toggleLoading = function toggleLoading() {
		$scope.loading = !$scope.loading;
	}
	

	$scope.toggleSelectionItem = function toggleSelectionItem(itemsSelecionados) {					
		for(idxItem in itemsSelecionados) {					
			var item = itemsSelecionados[idxItem];
									
			if ($scope.itens_selected.indexOf(item) > -1){
				var idxParaRemover = $scope.itens_selected.indexOf(item);
				$scope.itens_selected.splice(idxParaRemover, 1);					
			} else {				
				$scope.itens_selected.push(item);
			}				
		}			
	};
	
	$scope.get_periodicidade = function(index) {
		console.log(index);
		
		$scope.periodicidade_selected = $scope.periodicidades[index];
		console.log($scope.periodicidade_selected);
	}

	$scope.post_plano = function(retorno) {

		$scope.toggleLoading();
		$scope.entry = new SoMeSPCResourcePlano();

		$scope.entry.projetos_Taiga = [];
		$scope.entry.projetos_Sonar = [];
		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
		$scope.entry.nome_Itens = [];

		for (idx in $scope.projetosSelecionados_taiga) {
			$scope.entry.projetos_Taiga
					.push($scope.projetosSelecionados_taiga[idx].apelido);
		}
		
		for (idx in $scope.projetosSelecionados_sonar) {
			$scope.entry.projetos_Sonar
					.push($scope.projetosSelecionados_sonar[idx].apelido);
		}

		for (idx in $scope.itens_selected) {
			$scope.entry.nome_Itens.push($scope.itens_selected[idx]);
		}

		console.log($scope.entry.nome_Itens);

		$scope.entry.taiga_Login = $scope.login;
		console.log($scope.entry);
		$scope.entry.$save(function sucesso(plano) {
			$scope.toggleLoading();
			console.log(plano);
			return retorno(true);
		}, function erro(err) {
			$scope.toggleLoading();
			console.log(err);
			return retorno(false);
		});
	}
	
	//-------------------------------------- Funções Taiga e Sonar -------------------------------------

	$scope.post_projetoTaiga = function(retorno) {
		$scope.toggleLoading();
		TaigaIntegratorProjeto.save($scope.loginTaiga).$promise.then(
				function sucesso(result) {
					console.log("sucesso");
					$scope.projetosTaiga = result;
					$scope.projetosSelecionados_taiga = [];
					$scope.toggleLoading();
					return retorno(true);
				}, function erro(err) {
					$scope.toggleLoading();
					return retorno(false);
				});
	}
	
	$scope.toggleSelectionProjeto_Taiga = function toggleSelectionProjeto_Taiga(projeto) {
		var idx = $scope.projetosSelecionados_taiga.indexOf(projeto);

		if (idx > -1) {
			$scope.projetosSelecionados_taiga.splice(idx, 1);
		} else {
			$scope.projetosSelecionados_taiga.push(projeto);
		}
	};
	
	$scope.post_projetoSonar = function(retorno) {
		$scope.toggleLoading();
		SonarIntegratorProjeto.save($scope.loginSonar).$promise.then(
				function sucesso(result) {
					console.log("sucesso");
					$scope.projetosSonar = result;
					$scope.projetosSelecionados_sonar = [];
					$scope.toggleLoading();
					return retorno(true);
				}, function erro(err) {
					$scope.toggleLoading();
					return retorno(false);
				});
	}
	
	$scope.toggleSelectionProjeto_Sonar = function toggleSelectionProjeto_Sonar(projeto) {
		var idx = $scope.projetosSelecionados_sonar.indexOf(projeto);

		if (idx > -1) {
			$scope.projetosSelecionados_sonar.splice(idx, 1);
		} else {
			$scope.projetosSelecionados_sonar.push(projeto);
		}
	};
	
	
});