angular
  .module("liquorStore", ["ui.router"])
  .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']
function configure($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('customerHome', {
      url: '/TopSellers',
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
      url: '/orders/pending',
      controller: 'AdminHomeController',
      controllerAs: 'HC',
      templateUrl: '/html/admin-home.html'
    })
        .state('adminOrder', {
      url: '/order/unfulfilled',
      controller: 'AdminOrderController',
      controllerAs: 'OC',
      templateUrl: '/html/admin-order.html'
    })
        .state('adminInventorySales', {
      url: '/inventory/sales',
      controller: 'AdminInventorySalesController',
      controllerAs: 'AIS',
      templateUrl: '/html/admin-inventory-sales.html'
    })


  $urlRouterProvider.otherwise(function(){
	  
	  console.log(location.pathname)
	  
	  var path = location.pathname
	  
	  if(path.includes("customer"))
	  {
		  return "/TopSellers";
	  }
	  else if(path.includes("admin"))
	  {
		  return "/orders/pending";
	  }
	  else
	  {
		  return "/error";
	  }
	  
  })

  console.log("setting up", $stateProvider)
}