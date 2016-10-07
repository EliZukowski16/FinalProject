angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('customerHome', {
      url: '/',
      controller: 'CustomerHomeController',
      controllerAs: 'HC',
      templateUrl: 'html/customer-home.html'
    })
      .state('customerProducts', {
      url: '/products',
      controller: 'CustomerProductsController',
      controllerAs: 'PC',
      templateUrl: '/html/customer-products.html'
    })
      .state('customerAccount', {
      url: '/account',
      controller: 'CustomerAccountController',
      controllerAs: 'AC',
      templateUrl: 'html/customer-account.html'
    })
      .state('customerCart', {
      url: '/cart',
      controller: 'CustomerCartController',
      controllerAs: 'CC',
      templateUrl: 'html/customer-cart.html'
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