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
			
			
			
			
			
			ctrl.statPopup = {
				id : order.id,
				value : value
			}

			
			
			
				
			if(ctrl.statusPopup.length == 0)
			{
				console.log("pushed")
				ctrl.statusPopup.push(ctrl.statPopup)
				ctrl.status.push(ctrl.stat);
			}
			else
			{
				for (let i = 0; i < ctrl.statusPopup.length; i++) 
				{
					
					
					console.log('length' + ctrl.statusPopup.length)
					if(ctrl.statusPopup[i].id == order.id)
					{
						ctrl.status.splice(i,1)
						ctrl.statusPopup.splice(i,1)
						ctrl.statusPopup.push(ctrl.statPopup)
						ctrl.status.push(ctrl.stat);
					}
					else
					{
						ctrl.statusPopup.push(ctrl.statPopup)
						ctrl.status.push(ctrl.stat);
					}
				}
				
				
			}

			console.log(ctrl.status);
			console.log(ctrl.statusPopup)
			
		

		}
		
		
		ctrl.submitStatus = function()
		{
			console.log(ctrl.status)
			$http({
	        	url: location.pathname +"/orders/pending",
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
