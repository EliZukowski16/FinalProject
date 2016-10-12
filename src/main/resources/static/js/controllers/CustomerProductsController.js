angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)	
	productCtrl.$inject=['$http']

	function productCtrl($http)
	{
	
	var ctrl = this;	
	ctrl.searchResults = [];
	
	ctrl.cart = [];
	ctrl.keyword = "";
	
	ctrl.types = ['Beer', 'Wine', 'Spirits'];
	ctrl.selection = [];
	
	//Checkbox search
	ctrl.toggleSelection = function toggleSelection(type){
		var index = ctrl.selection.indexOf(type);
		
		if(index >-1){
			ctrl.selection.splice(index, 1);
		} else {
			ctrl.selection.push(type);
		}
	};
	
	//Send search to controller
	ctrl.search = function()
	{	
		
		var queryParams = {
				keywords: ctrl.keyword,
				types: ctrl.selection
		}
	
	$http({
		url: location.pathname +"/search",
		method: 'GET',
		params: queryParams
	}).then(function(response) {		
		
		ctrl.searchResults= response.data.success;
  		console.log(ctrl.searchResults);
	})
	};
	
     
    
    ctrl.addToCart = function(product){
   		if(ctrl.cart.indexOf(product) == -1) 
			ctrl.cart.push(product);
	}
    
    
    
    
/*    ctrl.order = [
                  {
                	  date:
                	  products: [
                	             {
                	            	 id:
                	            	 qty:
                    	             price:
                	             },
                	             {
                	            	 id:
                	            	 qty:
                	            	 price:
                	             }
                  }]*/
    
    
    
}