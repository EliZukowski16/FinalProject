angular
	.module("liquorStore")
	.controller("CustomerProductsController", productCtrl)	
	productCtrl.$inject=['$http', '$state']

	function productCtrl($http, $state)
	{
	
	var ctrl = this;	
	ctrl.active = false;
	ctrl.searchResults = [];
	ctrl.cart = [];
	ctrl.keyword = "";
	ctrl.types = ['Beer', 'Wine', 'Spirits'];
	ctrl.selection = [];
    ctrl.orderDetails = [];
    ctrl.orderResponse = [];
     
	
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
	
    //Add product to cart 
    ctrl.addToCart = function(product)
    {
    	ctrl.active = true;
    	
    	if(ctrl.cart.indexOf(product) == -1) 
			ctrl.cart.push(product);
	}
    
    
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
        		'orderYear': "2016",
        		'orderMonth': "12",
        		'orderDay': "15",
        		'total': ctrl.grandTotal(),
        		'products': products
        	}
        }).then(function(response) {	
        	
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
    	
    }
    
}