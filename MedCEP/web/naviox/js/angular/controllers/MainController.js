app.controller('MainController', [ "$scope", 'TaigaIntegrator',function($scope, TaigaIntegrator) {
		
	TaigaIntegrator.success(function(data){
		$scope.projetos = data
	});	
		
} ]);