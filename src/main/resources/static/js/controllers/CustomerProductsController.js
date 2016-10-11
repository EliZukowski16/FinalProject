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
	
	console.log(location.pathname)
	$http.get(location.pathname +"/search").then(function(response) {
		
		console.log("hello")
		ctrl.tags = ctrl.keyword.split(" ");
		ctrl.types = $("input[name='filter']:checked").val();
 
		ctrl.searchResults= response.data.success;
  		console.log(ctrl.searchResults);
/*	for(i=0; i<response.data.success.length; i++){
	var temp = {}
	ctrl.searchResults.push(temp);*/
/*	}*/
	})
	}
    
    
    
    ctrl.addToCart = function(product){
		if(ctrl.cart.indexOf(product) == -1) 
			ctrl.cart.push(product);
	}
    
}