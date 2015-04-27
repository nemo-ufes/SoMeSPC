app.factory('TaigaIntegrator', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/:entidade');
	});

app.factory('Api_Med_Resource', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/:periodicidades_med_cep');
	});
