angular
  .module("liquorStore")
  .factory("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {


var service = this;

service.keyword = {'key': null};
service.searchResults = [];
service.selection = [];
service.cart = [];
service.types = ['Beer', 'Wine', 'Spirits'];


return {

searchResults : function (key, select){
	service.keyword.key = key;
	service.selection.select = select;
	
	
var queryParams = {
	keywords: ctrl.keyword,
	types: ctrl.selection
}
	
	$http({
	method: "POST",
	url: location.pathname + "/search",
	dataType: "json",
	params: queryParams
	}).success(function(response) {		
		
		ctrl.searchResults= response.data.success;
  		console.log(ctrl.searchResults);
  		
	}).error(function(response){
	
		console.log("http error");
		})
	},
	
	//Checkbox search
	toggleSelection : function(type){
		var index = service.selection.indexOf(type);
		
		if(index >-1){
			service.selection.splice(index, 1);
		} else {
			service.selection.push(type);
		}
	};


/*
	
	addToCart : function(product){
		
		if(service.cart.indexOf(product) == -1) {
		service.cart.push(product);
	}
	
	


 *
 *app.service = ('CartService', )
var service = this;
var searchResults = [];
var searchTerms = [];

this.currentSearch = function(){
	return searchTerms;
}

this.addToCart = function(evt, productId){
	var element = angular.element(evt.target);
	if(element.hasClass('selected'))
		return void(0);
	element.addClass('selected');
	element.attr('title', '');
	console.log("addToCart(" + productId + ")");
	
	var match = searchResults.find(function(productId){
		if(productId === product.id)
			return true;
	});
	if(match)
		contents.
		store.cart = function(){
		return CartService.contents();
	}
	
	this.search = function(query){
		if(searchResults.length)
	}
}

function CartController($scope, CartService){
	var store = $scope.store = { };
	
	store.results = [];
	store.query = CartService.currentSearch();
	
	CartService.search().then(function(response){
	store.results = response;
	})
	
	store.addToCart = function (evt, productId){
		CartService.addToCart(evt, productId);
	}
	
	store.cart = function(){
	return CartService.contents();
	}
}*/