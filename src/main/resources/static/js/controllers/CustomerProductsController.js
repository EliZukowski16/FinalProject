angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)
	
	productCtrl.$inject=['$http']
	function productCtrl($http){
	
	var ctrl = this;
	var product;
	$http.get(location.pathname +"/products").then(function(response) {
		
      ctrl.product = response.data.success
      console.log(ctrl.product)
    })
    
    ctrl.addToCart = function(){
		
		
	}
    
}