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
	    	ctrl.load=true;
			$http({
				url: location.pathname +"/Orders",
				method: 'GET',
			}).then(function(response) {		
				ctrl.load=false;
				ctrl.orders= response.data.success;
		  		console.log(ctrl.orders);
				ctrl.convertDates();

			})
			
		};
		
		ctrl.getOrders();
		
		ctrl.convertDates = function() {
			for (let i = 0; i < ctrl.orders.length; i++) {


				ctrl.orders[i].deliveryDate = new Date(ctrl.orders[i].date.year, ctrl.orders[i].date.monthValue - 1, ctrl.orders[i].date.dayOfMonth);
				ctrl.orders[i].orderDate = new Date(ctrl.orders[i].timeOfOrder.year, ctrl.orders[i].timeOfOrder.monthValue - 1, ctrl.orders[i].timeOfOrder.dayOfMonth, ctrl.orders[i].timeOfOrder.hour, ctrl.orders[i].timeOfOrder.minute, ctrl.orders[i].timeOfOrder.second)
			}
	
		
	}


}