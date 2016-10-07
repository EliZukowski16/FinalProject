angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)
	
	productCtrl.$inject=['$http']
	function productCtrl($http){
	
	var ctrl = this;
	var product;
	$http.get(location.pathname +"/products").then(function(response) {
		
      var product = response.data.success
      ctrl.products = response.data.success
      console.log(product)
    })
    
    ctrl.product = product;
	console.log(ctrl.product)
    
}