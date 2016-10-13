angular
	.module("liquorStore")
	.controller("CustomerOrdersController", orderCtrl)	
	orderCtrl.$inject=['$http']

	function orderCtrl($http)
	{
		var ctrl = this;
		var orders = [];
		
		ctrl.getOrders = function()
		{
			$('#myModal').modal('hide');
	    	$('body').removeClass('modal-open');
	    	$('.modal-backdrop').remove();
	    	
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
	