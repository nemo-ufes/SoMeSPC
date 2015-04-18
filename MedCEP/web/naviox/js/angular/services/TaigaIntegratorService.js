app.factory('TaigaIntegrator', ['$http', function($http) { 
  return $http.get('http://localhost:8080/MedCEP/api/TaigaIntegrator/Projetos') 
            .success(function(data) { 
              return data; 
            }) 
            .error(function(err) { 
              return err; 
            }); 
}]);