angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)	
	productCtrl.$inject=['$http', '$state', 'CartService']

	function productCtrl($http, $state, CartService)
	{
	
	var ctrl = this;	
	ctrl.active = false;
	ctrl.types = ['Beer', 'Wine', 'Spirits', 'Ciders', 'Accessories', 'Non-Alcohol'];
    ctrl.orderDetails = [];
    ctrl.orderResponse = [];
    ctrl.orderList = "";
    ctrl.inCart = CartService.getProductIds;
    
    ctrl.month = "";
    ctrl.day = "";
    ctrl.year = "";
    
    ctrl.searchResults = CartService.getSearchResults;
    ctrl.keyword = "";
    ctrl.selection = CartService.getSelection;
    ctrl.cart = CartService.getCart;
    
    
    //Checkbox search
    ctrl.toggleSelection = function(type){
    	CartService.toggleSelection(type);
    }
    
    //Submit search to controller
    ctrl.search = function(){
    	CartService.search(ctrl.keyword);
    }   
    
    //Add product to cart 
    ctrl.addToCart = function(product)
    {
    	ctrl.active = true;
    	CartService.addToCart(product);
    	console.log(ctrl.cart);
	}
    
    //Remove product from cart
    ctrl.remove = function(product){
    	CartService.remove(product);
    };
    

    //Calculate cart grand total
    ctrl.grandTotal = function()
    {
    	var total = 0;
    	for(var i = 0; i<ctrl.cart.length; i++){
    		var product = ctrl.cart[i];
    		total += (product.price * product.qty)
    	}
    	return total;
    };
	
	//Send search to controller
//	ctrl.search = function()
//	{	
//		
//		var queryParams = {
//				keywords: ctrl.keyword,
//				types: ctrl.selection
//		}
//	
//	$http({
//		url: location.pathname +"/search",
//		method: 'GET',
//		params: queryParams
//	}).then(function(response) {		
//		
//		ctrl.searchResults= response.data.success;
//		
//  		for(let i = 0;i < ctrl.searchResults.length;i++)
//  		{
//  			var p = ctrl.searchResults[i];
//  			
//  			var full = new Image();
//  			var thumb = new Image();
//  			
//  			
//  			
//  			full.src = "/pictures/products/" + p.coreProduct.fullSizeImage + "/images/thumb.png.png";
//  			thumb.src = "/pictures/products/" + p.coreProduct.thumbnail + "/images/thumb.png.png";
//  			
//  			
//  			
//  			if(ctrl.UrlExists(full.src))
//  			{
//  				console.log("has an image")
//  			}
//  			else
//  			{
//  				console.log("no image" + p.coreProduct.fullSizeImage);
//  				p.coreProduct.fullSizeImage = "noimage";
//  				
//  			}
//  			if(ctrl.UrlExists(thumb.src))
//  			{
//  				
//  			}
//  			else
//  			{
//  				p.coreProduct.thumbnail = "noimage";
//  			}
  				
  			
  		//}
//  		
//  		
//  		
//	})
//	};
    
	
	ctrl.UrlExists = function(url) {
			var http = new XMLHttpRequest();
		    http.open('HEAD', url, false);
		    http.send();
		    return http.status!=404;
		}
    

    //Submit Order    
    ctrl.submitOrder = function()
    {
    	var products = [];
    	for(var i = 0; i<ctrl.cart.length; i++){
    		var tempProduct = {
    				'id': ctrl.cart[i].id,
    				'qty': ctrl.cart[i].qty,
    				'price': ctrl.cart[i].price
    		}
    		products.push(tempProduct);
    	}


    	$http({
        	url: location.pathname +"/placeOrder",
        	method: 'POST',
        	data: {
        		'orderYear': ctrl.year,
        		'orderMonth': ctrl.month,
        		'orderDay': ctrl.day,
        		'total': ctrl.grandTotal(),
        		'products': products
        	}
        }).then(function(response) {	
        	console.log(response);
        	ctrl.orderResponse = response.data;
        	
        	if(ctrl.orderResponse.success){
        		console.log("success");
        	} else if(ctrl.orderResponse.error){
        		console.log("error");
        	} else if(ctrl.orderResponse.outofstock){
        		console.log("out of stock");
        		//returns out of stock items
        	}else if(ctrl.orderResponse.pricechange){
        		console.log("price change");
        		//returns price change items
        	}
        	
    		console.log(ctrl.orderResponse);
        })
    };

    //Redirect after closing receipt
    ctrl.redirect = function(){
    	$state.go("customerOrders");
    	$(".modal-backdrop").hide();
    };
    
    
    
    
    
    
    
}