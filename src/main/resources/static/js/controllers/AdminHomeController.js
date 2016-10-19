angular
	.module("liquorStore")
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
		}).then(function(response) {		
			
			ctrl.pending= response.data.success;
	  		console.log(ctrl.pending);
	  		
		})
		};
		
		ctrl.getPending();
		
		ctrl.status = [];
		ctrl.statusPopup = [];
		
		ctrl.checkedRadio = function(order, value)
		{
			ctrl.stat = {
				[order.id] : value
			}
			
			ctrl.status.push(ctrl.stat);
			
			ctrl.statPopup = {
				id : order.id,
				value : value
			}
			console.log(ctrl.status);
			ctrl.statusPopup.push(ctrl.statPopup)

		}
		
		
		ctrl.submitStatus = function()
		{
			console.log(ctrl.status)
			$http({
	        	url: location.pathname +"/UnfulfilledOrders/pending",
	        	method: 'POST',
	        	data: ctrl.status,
	        }).then(function(response) {	
	        	console.log('it worked' + response.data)
	        	
	        })

		}
		
		ctrl.reload = function()
		{
			location.reload()
		}
		
		
		
	}
