angular
  .module("liquorStore")
  .factory("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {

var service = this;


service.selection = [];
service.searchResults = [];

service.cart = [];


return {

	//Checkbox search
	toggleSelection : function(type){
		var index = service.selection.indexOf(type);
		
		if(index >-1){
			service.selection.splice(index, 1);
		} else {
			service.selection.push(type);
		}
	},	
	
	//Submit search to controller
	search : function (key){

		
		console.log(service.selection)
		
		if(service.selection.length == 0)
		{
			service.selection = ["BEER","WINE","SPIRITS"]
		}
		
		console.log(service.selection)
		

		var queryParams = {
				keywords: key,
				types: service.selection
		}
		
		

		$http({
			url: location.pathname + "/search",
			method: 'GET',
			params: queryParams
			
		}).then(function(response) {		
			
			var tempArray = [];
			tempArray = response.data.success;
			angular.copy(tempArray, service.searchResults);

		})
		},
		
		//Add product to cart	
		addToCart : function(product){
			
			if(service.cart.indexOf(product) == -1){
				var tempCart = [];
				service.cart.push(product);
			}
		},
		
	    //Remove product from cart
	    remove : function(product){
	    	var index = service.cart.indexOf(product)
	    	service.cart.splice(index,1);  
	    },
	    
	
<<<<<<< HEAD
		
=======
>>>>>>> 91d9518d8f458a7ec12d6b7ad3f3dd3906d79ecf
	
		getSelection: service.selection,
	
		getSearchResults: service.searchResults,
		
		getCart: service.cart
		  
	}
}

