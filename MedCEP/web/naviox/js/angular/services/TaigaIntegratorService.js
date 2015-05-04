//TaigaIntegrator - Serviço para retornar lista de dados pela URL da Web Service
app.factory('TaigaIntegrator', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/:entidade');
	});

//MedCEPResouce - Serviço para retornar lista de dados pela URL da Web Service
app.factory('MedCEPResource', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/:periodicidades_med_cep');
	});

//TaigaIntegratorProjeto - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorProjeto', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/Projetos', {},{save: { method: 'POST', isArray: true}});
	});

//TaigaIntegratorPlano - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorPlano', function($resource) {
	  return $resource('http://localhost:8080/MedCEP/api/TaigaIntegrator/Plano');
	});
