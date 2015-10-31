//TaigaIntegrator - Serviço para retornar lista de dados pela URL da Web Service
app.factory('TaigaIntegrator', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/:entidade');
	});

//SoMeSPCResouce - Serviço para retornar lista de dados pela URL da Web Service
app.factory('SoMeSPCResource', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	return $resource(host +'/SoMeSPC/api/:entidade');
	});

//TaigaIntegratorProjeto - Serviço para enviar dados pela URL da Web Service
app.factory('TaigaIntegratorProjeto', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/TaigaIntegrator/Projetos', {},{save: { method: 'POST', isArray: true}});
	});

//SoMeSPCResourcePlano - Serviço para enviar dados pela URL da Web Service
app.factory('SoMeSPCResourcePlano', function($resource, $location) {
	var host = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	  return $resource(host +'/SoMeSPC/api/Plano');
	});