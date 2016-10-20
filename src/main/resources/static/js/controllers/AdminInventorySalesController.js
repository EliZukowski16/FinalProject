angular
	.module("liquorStore")
	.controller("AdminInventorySalesController", InvSalesCtrl)	
	homeCtrl.$inject=['$http','CartService']

	function InvSalesCtrl($http, CartService)
	{
		var ctrl = this;
		
		
		ctrl.product1 = {
				product : 'budlight6pk',
				ystSales : 10,
				ystCount : 5,
				weekSales : 10,
				weekCount : 5,
				monthSales : 10,
				monthCount : 5,
				yearSales : 10,
				yearCount : 5,
		}
		
		productA = [ctrl.product1];
		
		ctrl.coreProduct1 = {
				cp : 'budlight',
				products : productA,
				ystSales : 50,
				ystCount : 10,
				weekSales : 50,
				weekCount : 10,
				monthSales : 50,
				monthCount : 10,
				yearSales : 50,
				yearCount : 10,
		}
		
		coreProductA = [ctrl.coreProduct1];
		
		ctrl.type = {
			
				type : 'BEER',
				coreProducts : coreProductA,
				ystSales : 200,
				ystCount : 50,
				weekSales : 200,
				weekCount : 50,
				monthSales : 200,
				monthCount : 50,
				yearSales : 200,
				yearCount : 50,
				
		}
		
		ctrl.sales = [ctrl.type];
		
		
		
		
		
		
		ctrl.getSales = function()
		{
			$('.lowInventoryContainer').hide();
			$('.searchProductContainer').hide();
			console.log(ctrl.product1)
			console.log(ctrl.coreProduct1)
			console.log(ctrl.type)
			console.log(ctrl.sales)
			
		}
	
//		ctrl.getSales = function()
//		{	
//	
//			$http({
//				url: location.pathname +"/inventory/sales",
//				method: 'GET',
//		}).then(function(response) {		
//		
//			console.log(response)
//			ctrl.unOrders= response.data.success;
//			console.log(ctrl.unOrders);
//  		
//		})
//		};
//	
		ctrl.getSales();
		
		
		ctrl.hideCPD = function()
		{
			console.log("hiding CP")
			$('.coreProductDetail').hide()
		}
		
		ctrl.hidePD = function()
		{
			console.log("hiding P")
			$('.productDetail').hide()
		}
		
		ctrl.lowInv = [];
		
		ctrl.showInventory = function()
		{
			console.log('showing inventory')
			$('.fullTableContainer').hide();
			$('.searchProductContainer').hide();
			$('.lowInventoryContainer').show();
			
			console.log(location.pathname + "/inventory/lowInventory")
			
			$http({
				url: location.pathname +"/inventory/lowInventory",
				method: 'GET',
			}).then(function(response) {		
		
			console.log(response)
			ctrl.lowInv= response.data.success;
			console.log(ctrl.lowInv);
  		
		})
		}
		
		ctrl.types = ['Beer', 'Wine', 'Spirits'];
		ctrl.selection = CartService.getSelection;
		ctrl.keyword = CartService.getKeyword;
		
		ctrl.showSearch = function()
		{
			$('.fullTableContainer').hide();
			$('.lowInventoryContainer').hide();
			$('.searchProductContainer').show();
		}
		
		
		ctrl.active = false;
		ctrl.types = ['Beer', 'Wine', 'Spirits'];
	    ctrl.orderDetails = [];
	    ctrl.orderResponse = [];
	    
	   
	    
	    ctrl.searchResults = CartService.getSearchResults;
	    ctrl.keyword = "";
	    ctrl.selection = CartService.getSelection;
	    
	    //Checkbox search
	    ctrl.toggleSelection = function(type){
	    	CartService.toggleSelection(type);
	    }
	    
	    //Submit search to controller
	    ctrl.search = function(){
	    	CartService.search(ctrl.keyword);
	    
	    }   
	    
	    
		
	}