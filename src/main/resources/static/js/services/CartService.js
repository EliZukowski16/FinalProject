/*angular
  .module("liquorStore")
  .service("CartService", CartService)
  
CartService.$inject = ['$http']
function CartService($http) {


var service = this;
var contents = [];
var searchResults = [];
var searchTerms = [];






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
var contents = [];
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
		ShopC.cart = function(){
		return CartService.contents();
	}
	
	this.search = function(query){
		if(searchResults.length)
	}
}

function CartController($scope, CartService){
	var ShopC = $scope.ShopC = { };
	
	ShopC.results = [];
	ShopC.query = CartService.currentSearch();
	
	CartService.search().then(function(response){
	ShopC.results = response;
	})
	
	ShopC.addToCart = function (evt, productId){
		CartService.addToCart(evt, productId);
	}
	
	ShopC.cart = function(){
	return CartService.contents();
	}
}*/