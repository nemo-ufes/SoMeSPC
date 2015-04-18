app.controller('MainController', [ "$scope", 'TaigaIntegrator',function($scope, TaigaIntegrator) {
	
	TaigaIntegrator.success(function(data){
		$scope.projetos = data
	});
	
	TaigaIntegrator.error(function(err){
		alert(err);
	});
	
} ]);