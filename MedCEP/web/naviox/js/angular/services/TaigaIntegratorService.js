app.factory('Entry', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/:entidade');
	});