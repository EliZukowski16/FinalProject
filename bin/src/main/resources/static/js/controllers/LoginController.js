angular.module('liquorStore').controller('LoginController', loginCtrl)
		
		
	loginCtrl.$inject = ['$http', '$location', 'authentication']
	function loginCtrl($http, $location, authentication) {
	 
	var controller = this;
	  controller.login = function() {
	    if (controller.username === 'admin' && controller.password === 'pass') {
	      console.log('successful')
	      authentication.isAuthenticated = true;
	      authentication.user = { name: controller.username };
<<<<<<< HEAD
	      $location.url("/customer");
=======
	      $location.url("/customer.html");
>>>>>>> b088a857180722688a04a59972446cc18e050127
	    } else {
	    	controller.loginError = "Invalid username/password combination. Try again.";
	      console.log('Login failed.');
	    };
	  };
<<<<<<< HEAD
	};	
=======
	});	
>>>>>>> b088a857180722688a04a59972446cc18e050127

/*
app.controller('LoginController', function($scope, authentication){
	$scope.templates = 
		[
		 {url: 'index.html'},
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



	app.run(function(authentication, $rootScope, $location) {
	  $rootScope.$on('$routeChangeStart', function(evt) {
	    if(!authentication.isAuthenticated){ 
	      $location.url("/customer.html");
	    }
	    event.preventDefault();
	  });
	})
	
	
*/









