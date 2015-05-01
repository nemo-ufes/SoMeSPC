app.controller('MainController', function($scope, TaigaIntegrator, MedCEPResource, TaigaIntegratorPost) {
	

	$scope.projetos = TaigaIntegrator.query({ entidade: 'Projetos' });
	
	$scope.medidas = TaigaIntegrator.query({ entidade: 'Medidas' });
	
	$scope.periodicidades = MedCEPResource.query({ periodicidades_med_cep: 'Periodicidade' });
	
	$scope.projeto_selected;
	
	$scope.periodicidade_selected;
	
	$scope.medida_selected = [];
	
	$scope.mostra_projeto = function () {
		console.log($scope.projeto_selected.nome);
	}
	
	$scope.get_medidas = function() {
        for(medida in $scope.medida_selected) {
        	if($scope.medida_selected[medida] != false){
        		console.log(medida);
        	}
        }                
    }
	
	$scope.get_periodicidade = function(index) {
        $scope.periodicidade_selected = $scope.periodicidades[index];
		console.log($scope.periodicidade_selected.nome);   
    }
	
	
	
	$scope.POST = function(){
		$scope.entry = new TaigaIntegratorPost(); //You can instantiate resource class
		 
		$scope.entry.nome_Projeto = $scope.projeto_selected.nome;
		$scope.entry.nome_Periodicidade = $scope.periodicidade_selected.nome;
		$scope.entry.nome_Medidas = [];
	    for(medida in $scope.medida_selected) {
	    	$scope.entry.nome_Medidas.push(medida);
	    }	
		$scope.entry.$save();
	}
});