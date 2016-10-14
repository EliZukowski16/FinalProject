/*angular
  .module("liquorStore")
  .factory("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {


var service = this;

service.keyword = "";
service.searchResults = [];
service.searchTerms = [];
service.selection = [];


return {

searchResults : function (key, select){
	service.keyword = key;
	service.selection = select;
	
	
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
	
	
	
	
	
}










CartService.$inject = ['$http']
function CartService($http) {

  return {
    all: function() {
      return $http.get("http://localhost:4567/products").then(function(res) {
        return res.data
      })
    },
    save: function(data) {
      console.log('should save', data)
      return Promise.resolve({
        id: 5
      })
    }


*/

/**
 * 
 *//*
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