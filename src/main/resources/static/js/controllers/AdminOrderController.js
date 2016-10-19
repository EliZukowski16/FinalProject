angular
	.module("liquorStore")
	.controller("AdminOrderController", orderCtrl)	
	homeCtrl.$inject=['$http']

	function orderCtrl($http)
	{
		var ctrl = this;
		ctrl.unOrders = [];
	
	
		ctrl.getUnOrders = function()
		{	
	
			$http({
				url: location.pathname +"/orders/unfulfilled",
				method: 'GET',
		}).then(function(response) {		
		
			console.log(response)
			ctrl.unOrders= response.data.success;
			console.log(ctrl.unOrders);
  		
		})
		};
	
		ctrl.getUnOrders();
	}