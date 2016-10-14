angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('customerHome', {
      url: '/TheBeerGuys',
      controller: 'CustomerHomeController',
      controllerAs: 'HC',
      templateUrl: '/html/customer-home.html'
    })
      .state('customerProducts', {
      url: '/products',
      controller: 'CustomerProductsController',
      controllerAs: 'PC',
      templateUrl: '/html/customer-products.html'
    })
      .state('customerOrders', {
      url: '/orders',
      controller: 'CustomerOrdersController',
      controllerAs: 'OC',
      templateUrl: '/html/customer-orders.html'
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