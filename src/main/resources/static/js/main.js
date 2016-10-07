angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('customerHome', {
      url: '/TheBeerGuys/customer/{customerID}',
      controller: 'CustomerHomeController',
      controllerAs: 'HC',
      templateUrl: 'customer-home.html'
    })
      .state('customerProducts', {
      url: '/TheBeerGuys/customer/{customerID}',
      controller: 'CustomerProductsController',
      controllerAs: 'PC',
      templateUrl: 'customer-products.html'
    })
      .state('customerAccount', {
      url: '/TheBeerGuys/customer/{customerID}',
      controller: 'CustomerAccountController',
      controllerAs: 'AC',
      templateUrl: 'customer-account.html'
    })
      .state('customerCart', {
      url: '/TheBeerGuys/customer/{customerID}',
      controller: 'CustomerCartController',
      controllerAs: 'CC',
      templateUrl: 'customer-cart.html'
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