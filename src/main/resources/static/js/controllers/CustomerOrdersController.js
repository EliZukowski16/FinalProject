angular
	.module("liquorStore")
	.controller("CustomerOrdersController", orderCtrl)	
	orderCtrl.$inject=['$http']

	function orderCtrl($http)
	{
		var ctrl = this;
		ctrl.isCollapsed = true;
		ctrl.orders = [];	
	    ctrl.sortType = '';
	    ctrl.sortReverse = false;
		ctrl.ordersExpanded = true;
		
		ctrl.getOrders = function()
		{
	    	
			$http({
				url: location.pathname +"/Orders",
				method: 'GET',
			}).then(function(response) {		
				
				ctrl.orders= response.data.success;
		  		console.log(ctrl.orders);
			})
			
		};
		
		ctrl.getOrders();
	
		
	}
	