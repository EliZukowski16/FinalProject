angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('customer',{
		url: '/TheBeerGuys/customer/{customerID}',
    	views: {
    		'home@': {
    			controller: 'CustomerHomeController',
    			controllerAs: 'HC',
    			templateUrl: 'customer-home.html',
    		},
			'products@': {
    			controller: 'CustomerProductsController',
    			controllerAs: 'PC',
    			templateUrl: 'customer-products.html',
			},
			'account@': {
    			controller: 'CustomerAccountController',
    			controllerAs: 'AC',
    			templateUrl: 'customer-account.html',
			},
			'cart@': {
    			controller: 'CustomerCartController',
    			controllerAs: 'CC',
    			templateUrl: 'customer-cart.html',
			}
    	}
    })
    
    
    
    .state('admin', {
      url: '/TheBeerGuys/admin',
      controller: 'AdminController',
      controllerAs: 'AC',
      templateUrl: 'admin.html'
    })

  $urlRouterProvider.otherwise('/TheBeerGuys')

  console.log("setting up", $stateProvider)
}