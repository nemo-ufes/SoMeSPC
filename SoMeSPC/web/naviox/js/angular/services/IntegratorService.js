//SoMeSPCResouce - Serviço para retornar lista de dados pela URL da Web Service
app.factory('IntegratorPlanoMedicao', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	return $resource(host +'/SoMeSPC/api/:entidade');
	});

//------------------------------------ Serviço Taiga ----------------------------------------------

//TaigaIntegratorProjeto - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorProjeto', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/Projetos', {},{save: { method: 'POST', isArray: true}});
	});


//------------------------------------ Serviço SonarQube ------------------------------------------

//SonarIntegratorProjeto - Serviço para enviar dados pela URL da Web Service
app.factory('SonarIntegratorProjeto', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/SonarQubeIntegrator/Projetos', {},{save: { method: 'POST', isArray: true}});
	});

//------------------------------------- Serviço Plano de Medição ------------------------------------------

//SoMeSPCResourcePlano - Serviço para enviar dados pela URL da Web Service
app.factory('SoMeSPCResourcePlano', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/Plano');
	});
