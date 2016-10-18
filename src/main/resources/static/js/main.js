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
    
    .state('adminHome', {
      url: '/TheBeerGuys',
      controller: 'AdminHomeController',
      controllerAs: 'HC',
      templateUrl: '/html/admin-home.html'
    })
        .state('adminOrder', {
      url: '/order',
      controller: 'AdminOrderController',
      controllerAs: 'OC',
      templateUrl: '/html/admin-order.html'
    })
        .state('adminSearch', {
      url: '/search',
      controller: 'AdminSearchController',
      controllerAs: 'SC',
      templateUrl: '/html/admin.html'
    })

  $urlRouterProvider.otherwise('/TheBeerGuys')

  console.log("setting up", $stateProvider)
}