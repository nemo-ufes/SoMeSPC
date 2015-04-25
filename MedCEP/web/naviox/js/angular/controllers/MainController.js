app.controller('MainController', function($scope, Entry) {
	

	$scope.projetos = Entry.query({ entidade: 'Projetos' });
	
	$scope.medidas = Entry.query({ entidade: 'Medidas' });
	
	$scope.projeto_selected;
	
	$scope.medida_selected = [];
	
	$scope.verificador_medidas = new Array();
	
	$scope.add_index = function (index) {
		console.log(index);
		if($scope.verificador_medidas == "undefined" && $scope.verificador_medidas == null ){
			$scope.verificador_medidas.push(index);
			console.log(index);
		}
		for (i in $scope.verificador_medidas){
			if($scope.verificador_medidas[i] != index){
				$scope.verificador_medidas.push(index);
				console.log(index);
			}
		}
	};
	
	$scope.get_projeto = function (index) {
		$scope.projeto_selected = $scope.projetos[index];
		console.log($scope.projeto_selected.nome);
	};
	
	$scope.get_medidas = function() {
        for(i in $scope.verificador_medidas) {
            var indice = $scope.verificador_medidas[i];
        	$scope.medida_selected.push($scope.medidas[indice]);
            console.log($scope.medidas[indice]);
        }                
    }
	
	/*$scope.entry = new Entry(); //You can instantiate resource class
	 
	$scope.entry.data = 'some data';
	
	Entry.save($scope.entry, function() {
	    //data saved. do something here.
	  }); //saves an entry. Assuming $scope.entry is the Entry object*/
	
});