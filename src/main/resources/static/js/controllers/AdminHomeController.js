angular
	.module("liquorStoreAdmin")
	.controller("AdminHomeController", homeCtrl)	
	homeCtrl.$inject=['$http']

	function homeCtrl($http)
	{
		var ctrl = this;
		ctrl.pending = [];
		
		
		ctrl.getPending = function()
		{	
		
		$http({
			url: location.pathname +"/orders/pending",
			method: 'GET',
		}).success(function(response) {		
			
			ctrl.pending= response.data.success;
	  		console.log(ctrl.pending);
	  		
		}).error(function(response){
			console.log("http get error");
		})
		};
		
		ctrl.getPending();
		
		
		
	}
