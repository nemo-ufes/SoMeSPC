app.controller('MainController', function($scope, Entry) {
	

	$scope.projetos = Entry.query({ entidade: 'Projetos' });
	
	$scope.medidas = Entry.query({ entidade: 'Medidas' });
	
	$scope.entry = new Entry(); //You can instantiate resource class
	 
	$scope.entry.data = 'some data';
	
	Entry.save($scope.entry, function() {
	    //data saved. do something here.
	  }); //saves an entry. Assuming $scope.entry is the Entry object 
	
});