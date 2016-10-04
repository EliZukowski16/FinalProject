angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('login', {
      url: '/',
      controller: 'LoginController',
      controllerAs: 'LC',
      templateUrl: 'login.html'
    })
    .state('customer', {
      url: '/customer/:id',
      controller: 'CustomerController',
      controllerAs: 'CC',
      templateUrl: 'customer.html'
    })
    .state('admin', {
      url: '/admin/:id',
      controller: 'AdminController',
      controllerAs: 'AC',
      templateUrl: 'admin.html'
    })

  $urlRouterProvider.otherwise('/')

  console.log("setting up", $stateProvider)
}