var app = angular.module('liquorStore', ['ngRoute']);

	app.run(function(authentication, $rootScope, $location) {
	  $rootScope.$on('$routeChangeStart', function(evt) {
	    if(!authentication.isAuthenticated){ 
	      $location.url("/customer");
	    }
	    event.preventDefault();
	  });
	})

	
app.controller('loginCtrl', function($scope, $http, $location, authentication) {
  $scope.login = function() {
    if ($scope.username === 'admin' && $scope.password === 'pass') {
      console.log('successful')
      authentication.isAuthenticated = true;
      authentication.user = { name: $scope.username };
      $location.url("/customer");
    } else {
      $scope.loginError = "Invalid username/password combination ";
      console.log('Login failed..');
    };
  };
});	
	
	

app.controller('loginCtrl', function($scope, authentication){
	$scope.templates = 
		[
		 {url: 'login.html'},
		 {url: 'customer.html'},
		 {url: 'admin.html'}
		];
	$scope.template = $scope.templates[0];
	$scope.login = function(username, password){
		if ( username === 'admin' && password === '1234') {
			authentication.isAuthenticated = true;
		    $scope.template = $scope.templates[1];
		    $scope.user = username;
		} else {
		    $scope.loginError = "Invalid username/password combination. Try again.";
		    };
		  };
})








