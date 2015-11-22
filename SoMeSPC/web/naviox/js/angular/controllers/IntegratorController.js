//Definição do Controlador - AngularJS
app.controller('WizardCtrl', function($scope, $resource, $window, WizardHandler,
		TaigaIntegratorProjeto, IntegratorPlanoMedicao, SoMeSPCResourcePlano, SonarIntegratorProjeto) {

	// ---------------------------- Teste ------------------------------------------------
	
    $scope.logStep = function() {
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
	$scope.mensagem_Objetivos = '';
	$scope.mensagem_Projetos = '';
	$scope.periodicidade = {};
	$scope.itens_selected = [];
	$scope.disabledTaiga = false;
	$scope.disabledSonar = false;
	
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
		
    $scope.validacao_Dados = function(){
    	if ($scope.itens_selected == undefined || $scope.itens_selected.length == 0){
    		$scope.mensagem_Objetivos = "Escolha ao menos um objetivo para o plano!";
    		$scope.disabledTaiga = false;
    		$scope.disabledSonar = false;
    		return false;
    	}
    	else if ($scope.periodicidade == undefined ||  $scope.periodicidade.selecionada == undefined){
    		$scope.mensagem_Objetivos = "Escolha uma periodicidade para a coleta!";
    		$scope.disabledTaiga = false;
    		$scope.disabledSonar = false;
    		return false;
    	}    		
    	else{
    		$scope.mensagem_Objetivos = "";
    		return true;
    	}
    }
    
    $scope.validacao_DadosProjetoTaiga = function(){
    	if ($scope.projetosSelecionados_taiga == undefined || $scope.projetosSelecionados_taiga.length == 0){
    		$scope.mensagem_Projetos = "Escolha pelo menos um projeto!";
    		return false;
    	}   		
    	else{
    		$scope.mensagem_Projetos ="";
    		return true;
    	}
    }
    
    $scope.validacao_DadosProjetoSonar = function(){
    	if ($scope.projetosSelecionados_sonar == undefined || $scope.projetosSelecionados_sonar.length == 0){
    		$scope.mensagem_Projetos = "Escolha pelo menos um projeto!";
    		return false;
    	}   		
    	else{
    		$scope.mensagem_Projetos ="";
    		return true;
    	}
    }
    
    $scope.validacao_DadosTaiga = function(){
    	var temObjetivoTaiga = false;
    	var temObjetivoSonar = false;
    	
    	for(idx in $scope.itens_selected){
    		var item = $scope.itens_selected[idx];
    		if (item.nome_FerramentaColetora == "Taiga"){    			
    			temObjetivoTaiga = true;
    		}
    		else {
    			temObjetivoSonar = true;
    		}   			
    	}
    	if (!temObjetivoSonar){
    		$scope.disabledSonar = true;
    	}
    	if (!temObjetivoTaiga){
    		$scope.disabledTaiga = true;
    		WizardHandler.wizard().goTo(4);
    	}    	
    }
        
    $scope.validacao_DadosTaigaRetorno = function(){
    	var temObjetivoTaiga = false;
		$scope.disabledTaiga = false;
    	
    	for(idx in $scope.itens_selected){
    		var item = $scope.itens_selected[idx];
    		if (item.nome_FerramentaColetora == "Taiga"){    			
    			temObjetivoTaiga = true;
    			break;
    		}
    	}
    	
    	if (!temObjetivoTaiga){
    		WizardHandler.wizard().goTo(1);	
    	}     
    }
    
    $scope.validacao_DadosSonar = function(){
    	var temObjetivoSonar = false;
    	
    	for(idx in $scope.itens_selected){
    		var item = $scope.itens_selected[idx];
    		if (item.nome_FerramentaColetora == "SonarQube"){  	
    			temObjetivoSonar = true;
    		}   			
    	}
    	
    	if (!temObjetivoSonar){    		
    		WizardHandler.wizard().goTo(4);	
    	}
    }
    
    $scope.validacao_RetornoResumo = function(){
    	$window.location.href = '/SoMeSPC/naviox/wizard.jsp';	
    }


	$scope.post_plano = function() {

		$scope.toggleLoading();
		$scope.entry = new SoMeSPCResourcePlano();

		$scope.entry.projetos_Taiga = [];
		$scope.entry.projetos_Sonar = [];
		$scope.entry.nome_Periodicidade = $scope.periodicidade.selecionada.nome;
		$scope.entry.nome_Itens = [];

		for (idx in $scope.projetosSelecionados_taiga) {
			$scope.entry.projetos_Taiga
					.push($scope.projetosSelecionados_taiga[idx].apelido);
		}
		
		for (idx in $scope.projetosSelecionados_sonar) {
			$scope.entry.projetos_Sonar
					.push($scope.projetosSelecionados_sonar[idx].name);
		}

		for (idx in $scope.itens_selected) {
			$scope.entry.nome_Itens.push($scope.itens_selected[idx]);
		}

		$scope.entry.taiga_Login = $scope.loginTaiga;
		$scope.entry.sonar_Login = $scope.loginSonar;
		
		$scope.entry.$save(function sucesso(plano) {
			$scope.toggleLoading();
			alert("Plano(s) de Medição criado com sucesso!");
	    	$window.location.href = '/SoMeSPC/naviox/wizard.jsp';	
		}, function erro(err) {
			$scope.toggleLoading();
			console.log(err);
			alert("Ocorreu um erro ao criar o(s) Plano(s) de Medição!");		
		});
	}
	
	//-------------------------------------- Funções Taiga e Sonar -------------------------------------

	$scope.filterFunction = function(element) {
		return element.name.match(/^Ma/) ? true : false;
	};
	
	$scope.post_projetoTaiga = function() {
		$scope.toggleLoading();
		TaigaIntegratorProjeto.save($scope.loginTaiga).$promise.then(
				function sucesso(result) {
					$scope.projetosTaiga = result;
					$scope.projetosSelecionados_taiga = [];
					$scope.toggleLoading();
				}, function erro(err) {
					console.log(err);
					alert("Erro ao estabelecer a conexão com o Taiga! Verifique se os dados de login estão corretos e tente novamente.")
					$scope.toggleLoading();
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
	
	$scope.post_projetoSonar = function() {
		$scope.toggleLoading();
		SonarIntegratorProjeto.save($scope.loginSonar).$promise.then(
				function sucesso(result) {
					$scope.projetosSonar = result;
					$scope.projetosSelecionados_sonar = [];
					$scope.toggleLoading();
				}, function erro(err) {
					console.log(err);
					alert("Erro ao estabelecer a conexão com o SonarQube! Verifique se os dados de login estão corretos e tente novamente.")
					$scope.toggleLoading();
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