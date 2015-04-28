app.controller('MainController', function($scope, TaigaIntegrator, Api_Med_Resource) {
	

	$scope.projetos = TaigaIntegrator.query({ entidade: 'Projetos' });
	
	$scope.medidas = TaigaIntegrator.query({ entidade: 'Medidas' });
	
	$scope.periodicidades = Api_Med_Resource.query({ periodicidades_med_cep: 'Periodicidade' });
	
	$scope.projeto_selected;
	
	$scope.periodicidade_selected;
	
	$scope.medida_selected = [];
	
	$scope.get_projeto = function (index) {
		$scope.projeto_selected = $scope.projetos[index];
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
	
	/*$scope.entry = new Entry(); //You can instantiate resource class
	 
	$scope.entry.data = 'some data';
	
	Entry.save($scope.entry, function() {
	    //data saved. do something here.
	  }); //saves an entry. Assuming $scope.entry is the Entry object*/
	
});