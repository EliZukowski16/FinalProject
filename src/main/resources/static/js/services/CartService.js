angular
  .module("liquorStore")
  .factory("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {

var service = this;


service.selection = [];
service.searchResults = [];
service.productsIds = [];
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

		
		
		
		if(service.selection.length == 0)
		{

			service.selection = ["BEER","WINE","SPIRITS","CIDERS","ACCESSORIES","NON_ALCOHOL"]

		}
		
		console.log(service.selection)
		console.log(key)
		

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
			
			console.log(service.searchResults)

		})
		},
		
		//Add product to cart	
		addToCart : function(product){
			
			if(service.cart.indexOf(product) == -1){
				var tempCart = [];
				service.cart.push(product);
				//service.productIds.push(product.id);
			}
		},
		
	    //Remove product from cart
	    remove : function(product){
	    	var index = service.cart.indexOf(product)
	    	service.cart.splice(index,1);  
	    },
	    

	    getProductIds: service.productsIds,
	
		getSelection: service.selection,
	
		getSearchResults: service.searchResults,
		
		getCart: service.cart
		  
	}
}

