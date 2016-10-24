angular
  .module("liquorStore")
  .factory("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {

var service = this;


service.selection = [];
service.keyword = "";
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
		
		service.keyword = key;
		service.noResults = false;
		
		if(service.selection.length == 0)
		{

			service.selection = ["BEER","WINE","SPIRITS","CIDERS"]

		}
		
		console.log(service.selection);
		console.log(service.keyword);
		

		var queryParams = {
				keywords: service.keyword,
				types: service.selection
		}
		
		

		return $http({
			url: location.pathname + "/search",
			method: 'GET',
			params: queryParams
			
		}).then(function(response) {		
			
			var tempArray = [];
			tempArray = response.data.success;
			angular.copy(tempArray, service.searchResults);
			
			console.log(service.searchResults)
			
			
            if(service.searchResults.length == 0){
            	$('#noResults').removeClass('hide');
            }

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
	    
	    getKeyword: service.keyword,
	
		getSelection: service.selection,
	
		getSearchResults: service.searchResults,
		
		getCart: service.cart,
		
		getSearchReturn: service.noResults
		  
	}
}

