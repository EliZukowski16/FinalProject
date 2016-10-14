angular
	.module("liquorStore")
	.controller("CustomerHomeController", homeCtrl)	
	homeCtrl.$inject=['$http']

	function homeCtrl($http)
	{
		var ctrl = this;
		ctrl.topSellers = [];
		
		ctrl.getTop = function()
		{	
		
		$http({
			url: location.pathname +"/TopSellers",
			method: 'GET',
		}).then(function(response) {		
			
			ctrl.topSellers= response.data.success;
	  		console.log(ctrl.topSellers);
		})
		};
		
		ctrl.getTop();
	
	
	
	}
