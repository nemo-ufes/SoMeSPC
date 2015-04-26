app.controller('MainController', function($scope, Entry) {
	

	$scope.projetos = Entry.query({ entidade: 'Projetos' });
	
	$scope.medidas = Entry.query({ entidade: 'Medidas' });
	
	$scope.projeto_selected;
	
	$scope.medida_selected = [];
	
	$scope.get_projeto = function (index) {
		$scope.projeto_selected = $scope.projetos[index];
		console.log($scope.projeto_selected.nome);
	};
	
	$scope.get_medidas = function() {
        for(medida in $scope.medida_selected) {
        	if($scope.medida_selected[medida] != false){
        	//$scope.medida_selected.push($scope.medidas[indice]);
        		console.log(medida);
        	}
        }                
    }
	
	/*$scope.entry = new Entry(); //You can instantiate resource class
	 
	$scope.entry.data = 'some data';
	
	Entry.save($scope.entry, function() {
	    //data saved. do something here.
	  }); //saves an entry. Assuming $scope.entry is the Entry object*/
	
});