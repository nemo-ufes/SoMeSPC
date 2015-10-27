//TaigaIntegrator - Serviço para retornar lista de dados pela URL da Web Service
app.factory('TaigaIntegrator', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/:entidade');
	});

//SoMeSPCResouce - Serviço para retornar lista de dados pela URL da Web Service
app.factory('SoMeSPCResource', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	return $resource(host +'/SoMeSPC/api/:periodicidades_med_cep');
	});

//TaigaIntegratorProjeto - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorProjeto', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/Projetos', {},{save: { method: 'POST', isArray: true}});
	});

//TaigaIntegratorPlano - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorPlano', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/Plano');
	});

//TaigaIntegratorPlanoTeste - Serviço para enviar dados pela URL da Web Service
//app.factory('TaigaIntegratorPlanoTeste', function($resource, $location) {
//	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
//	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/Plano_Teste');
//	});
