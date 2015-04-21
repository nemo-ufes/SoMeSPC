app.controller('PainelController', [ "$scope", 'MedCEP',function($scope, MedCEP) {
		
	MedCEP.success(function(data){
		$scope.medicoes = data
	});	
		
} ]);