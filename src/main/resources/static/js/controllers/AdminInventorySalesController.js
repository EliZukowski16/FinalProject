angular
	.module("liquorStore")
	.controller("AdminInventorySalesController", InvSalesCtrl)	
	homeCtrl.$inject=['$http','CartService']

	function InvSalesCtrl($http, CartService)
	{
		var ctrl = this;
//		
//		
//		ctrl.product1 = {
//				product : 'budlight6pk',
//				ystSales : 10,
//				ystCount : 5,
//				weekSales : 10,
//				weekCount : 5,
//				monthSales : 10,
//				monthCount : 5,
//				yearSales : 10,
//				yearCount : 5,
//		}
//		
//		productA = [ctrl.product1];
//		
//		ctrl.coreProduct1 = {
//				cp : 'budlight',
//				products : productA,
//				ystSales : 50,
//				ystCount : 10,
//				weekSales : 50,
//				weekCount : 10,
//				monthSales : 50,
//				monthCount : 10,
//				yearSales : 50,
//				yearCount : 10,
//		}
//		
//		coreProductA = [ctrl.coreProduct1];
//		
//		ctrl.type = {
//			
//				type : 'BEER',
//				coreProducts : coreProductA,
//				ystSales : 200,
//				ystCount : 50,
//				weekSales : 200,
//				weekCount : 50,
//				monthSales : 200,
//				monthCount : 50,
//				yearSales : 200,
//				yearCount : 50,
//				
//		}
//		
//		ctrl.sales = [ctrl.type];
//		
//		
//		
//		
//		
//		
//		ctrl.getSales = function()
//		{
//			$('.lowInventoryContainer').hide();
//			$('.searchProductContainer').hide();
//			console.log(ctrl.product1)
//			console.log(ctrl.coreProduct1)
//			console.log(ctrl.type)
//			console.log(ctrl.sales)
//			
//		}
		
		ctrl.sales = [];
		ctrl.salesStatsData = [];
	
		ctrl.getSales = function()
		{	
			$('.lowInventoryContainer').hide();
			$('.searchProductContainer').hide();
	
			$http({
				url: location.pathname +"/inventory/sales",
				method: 'GET',
			}).then(function(response) {		
		
				console.log(response)
				ctrl.sales= response.data.success;
				console.log(ctrl.sales);
			
				ctrl.getYearSales();

			})

		};
		
		
		
		ctrl.getSales();
		
		ctrl.getYearSales = function()//getting the first year sales
		{
			for(let i = 0;i < ctrl.sales.length;i++)
			{
				var typeYear1 = 0;
				var cpYear1 = 0;
				var pYear1 = 0;
				
				var typeCountSold = 0;
				var cpCountSold = 0;
				var pCountSold = 0;
				
				var cpCount = 0;
				var pCount = 0;

				for(let j = 0; j < ctrl.sales[i].monthlySalesData.length;j++)//type 1 year sales
				{
					//if(ctrl.sales[i].monthlySalesData[j].year)
					//{
						typeYear1 = typeYear1 + ctrl.sales[i].monthlySalesData[j].totalValue
						typeCountSold = typeCountSold + ctrl.sales[i].monthlySalesData[j].numberSold;
						
						cpCount = ctrl.sales[i].coreProductSales.length;
						ctrl.sales[i].cpCount = cpCount;
					//}	
				}
				for(let k = 0; k < ctrl.sales[i].coreProductSales.length;k++)//core product 1 year sales
				{
					for(let s = 0; s < ctrl.sales[i].coreProductSales[k].monthlySalesData.length;s++)
					{
						cpYear1 = cpYear1 + ctrl.sales[i].coreProductSales[k].monthlySalesData[s].totalValue;
						ctrl.sales[i].coreProductSales[k].cpYear1 = cpYear1;
						cpCountSold = cpCountSold + ctrl.sales[i].coreProductSales[k].monthlySalesData[s].numberSold;
						ctrl.sales[i].coreProductSales[k].cpCountSold = cpCountSold
						
						pCount = ctrl.sales[i].coreProductSales[k].productSales.length;
						ctrl.sales[i].coreProductSales[k].pCount = pCount;
					}
					for(let q = 0;q <ctrl.sales[i].coreProductSales[k].productSales.length;q++)//product 1 year sales
					{
						for(let a = 0;a < ctrl.sales[i].coreProductSales[k].productSales[q].monthlySalesData.length;a++)
						{
							pYear1 = pYear1 + ctrl.sales[i].coreProductSales[k].productSales[q].monthlySalesData[a].totalValue;
							ctrl.sales[i].coreProductSales[k].productSales[q].pYear1 = pYear1;
							pCountSold = pCountSold + ctrl.sales[i].coreProductSales[k].productSales[q].monthlySalesData[a].numberSold;
							ctrl.sales[i].coreProductSales[k].productSales[q].pCountSold = pCountSold;
							
							
							
							
						}
					}
				}
				ctrl.sales[i].typeYear1 = typeYear1;
				ctrl.sales[i].typeCountSold = typeCountSold
			}
		}
		
		ctrl.hideCPD = function()
		{
			console.log("hiding CP")
			$('.typeDetail').hide()
			$('.typeDiv').hide()
		}
		
		ctrl.hidePD = function()
		{
			console.log("hiding P")
			$('.coreProductproductDetail').hide()
		}
		
		ctrl.showSales = function()
		{
			$('.fullTableContainer').show();
			$('.searchProductContainer').hide();
			$('.lowInventoryContainer').hide();
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
		
  		
		})
		}
		
		
		ctrl.selection = CartService.getSelection;
		ctrl.keyword = CartService.getKeyword;
		
		ctrl.showSearch = function()
		{
			$('.fullTableContainer').hide();
			$('.lowInventoryContainer').hide();
			$('.searchProductContainer').show();
			
			
		}
		
		
		ctrl.active = false;
		ctrl.types = ['Beer', 'Wine', 'Spirits',"Ciders","Accessories","Non_Alcoholic"];
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
	    	console.log('search Results' + ctrl.searchResults)
	    	
	    	for(let i = 0; i < ctrl.searchResults.length;i++)
	    	{
	    		var coreProductSales = ctrl.searchResults[i].coreProductSales
	    		console.log('core Product Sales' + coreProductSales)
	    		
	    		
	    	}
	    
	    }   
	    
	    ctrl.addInventory = [];
	    ctrl.addInventoryPop = [];
	    
	    
	    ctrl.addInv = function(id) {
	    
	    	console.log(ctrl.lowInv)
	    	
	    	for(let i = 0;i < ctrl.lowInv.length;i++)
	    	{
	    		var id = ctrl.lowInv[i].id;
	    		var value = angular.element('#' + id).val()
	    		
	    		if(value == "")
	    		{
	    			
	    		}
	    		else
	    		{
	    			ctrl.addI = {
	    					[id] : value
	    			}
	    			
	    			ctrl.addIPop = {
	    					id : id,
	    					name : ctrl.lowInv[i].coreProduct.name,
	    					baseUnit : ctrl.lowInv[i].baseUnit,
	    					qty : ctrl.lowInv[i].quantity,
	    					value : value
	    			}
	    			
	    			ctrl.addInventory.push(ctrl.addI);
	    			ctrl.addInventoryPop.push(ctrl.addIPop);
	    			
	    		}
	    	}
	    	
	    	console.log(ctrl.addInventory)
	    	console.log(ctrl.addInventoryPop)
	    	
	    	console.log(location.pathname)

			$http({
	        	url: location.pathname +"/inventory",
	        	method: 'POST',
	        	data: ctrl.addInventory,
	        }).then(function(response) {	
	        	console.log('it worked' + response.data)
	        	
	        })
	    	
	    	
	    	
	    }
	    
	    ctrl.reload = function()
	    {
	    	location.reload()
	    }
	    

	    
	    
		
	}