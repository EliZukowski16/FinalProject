angular
	.module("liquorStore")
	.controller("CustomerOrdersController", orderCtrl)	
	orderCtrl.$inject=['$http']

	function orderCtrl($http)
	{
		var ctrl = this;
		
		ctrl.getOrders = function(){
			
		}
		
	
	}
	