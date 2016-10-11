angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)	
	productCtrl.$inject=['$http']

	function productCtrl($http)
	{
	
	var ctrl = this;	
	ctrl.searchResults = [];
	ctrl.cart = [];
	ctrl.tags = [];
	ctrl.types = [];
	ctrl.keyword = "";
	

	ctrl.search = function()
	{		
		var queryParams = {
				keywords: ctrl.keyword,
				types: ctrl.types
		}
	
	$http({
		url: location.pathname +"/search",
		method: 'GET',
		params: queryParams
	}).then(function(response) {		
		
		ctrl.searchResults= response.data.success;
  		console.log(ctrl.searchResults);
	})
	}
	
     
    
    ctrl.addToCart = function(product){
		if(ctrl.cart.indexOf(product) == -1) 
			ctrl.cart.push(product);
	}
    
}