angular
	.module("liquorStore")
	.controller("CustomerProductController", productCtrl)
	
	productCtrl.$inject=['$http']
	function productCtrl($http){
	
	var ctrl = this;
	
	$http.get("/products").then(function(response) {
      ctrl.products = response.success.data
    })
    
    console.log(ctrl.products);
}