//URL da API da MedCEP
var url = 'http://localhost:8080/MedCEP/api/';

app.factory('TaigaIntegrator', function($http) {
		return $http.get(url + 'TaigaIntegrator/Projetos')
		 .success(function(data) {
			 return data;
		 }).error(function(err) {
			 return err;
		 });  
  });