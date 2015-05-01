app.factory('TaigaIntegrator', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/:entidade');
	});

app.factory('MedCEPResource', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/:periodicidades_med_cep');
	});

app.factory('TaigaIntegratorPost', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/Plano');
	});
