angular
	.module("liquorStore")
	.controller("CustomerHomeController", homeCtrl)	
	homeCtrl.$inject=['$http']

	function homeCtrl($http)
	{
		var ctrl = this;
		ctrl.topSellers = [];
		ctrl.beers = [];
		ctrl.wines = [];
		ctrl.spirits = [];
		
		ctrl.getTop = function()
		{	
		
		$http({
			url: location.pathname +"/TopSellers",
			method: 'GET',
		}).success(function(response) {		
			
			ctrl.topSellers= response.data.success;
			ctrl.beers = ctrl.topSellers.beer;
			ctrl.wines = ctrl.topSellers.wine;
			ctrl.spirits = ctrl.topSellers.spirits;
	  		console.log(ctrl.topSellers);
	  		console.log(ctrl.beers);
	  		console.log(ctrl.wines);
		}).error(function(response){
			console.log("http get error");
		})
		};
		
		ctrl.getTop();
	
	
	
	}
