//URL da API da MedCEP
var url = 'http://localhost:8080/MedCEP/api/';

app.factory('MedCEP', function($http) {
		return $http.get(url + 'Medicao?entidade=2433&medida=6284&projeto=1671')
		 .success(function(data) {
			 return data;
		 }).error(function(err) {
			 return err;
		 });  
  });