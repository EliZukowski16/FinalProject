angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('login', {
      url: '/TheBeerGuys',
      controller: 'LoginController',
      controllerAs: 'LC',
      templateUrl: 'index.html'
    })
    .state('customer', {
      url: '/TheBeerGuys/customer',
      controller: 'CustomerController',
      controllerAs: 'CC',
      templateUrl: 'customer.html'
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