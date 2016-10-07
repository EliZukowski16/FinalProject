angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)
	
	productCtrl.$inject=['$http']
	function productCtrl($http){
	
	var ctrl = this;
	var product;
	$http.get(location.pathname +"/products").then(function(response) {
		
      
      ctrl.product = response.data.success
      ctrl.id = response.data.success[0].id
      ctrl.type = response.data.success[0].coreProduct.type
      ctrl.subType = response.data.success[0].coreProduct.subType
      ctrl.name = response.data.success[0].coreProduct.name
      ctrl.desc = response.data.success[0].coreProduct.description
      ctrl.baseUnit = response.data.success[0].baseUnit
      ctrl.quantity = response.data.success[0].quantity
      ctrl.price = response.data.success[0].price
      console.log(ctrl.product)
    })
    

    
}