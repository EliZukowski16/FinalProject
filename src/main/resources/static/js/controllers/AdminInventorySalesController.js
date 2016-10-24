angular
	.module("liquorStore")
	.controller("AdminInventorySalesController", InvSalesCtrl)
homeCtrl.$inject = [ '$http', 'CartService' ]

function InvSalesCtrl($http, CartService) {
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
	
	ctrl.sortType = '';
    ctrl.sortReverse = false;
    
    ctrl.sortCP = '';
    ctrl.sortReverseCP = false;
    
    ctrl.sortP = '';
    ctrl.sortReverseP = false;
    
    ctrl.sortIP = '';
    ctrl.sortReverseIP = false;
    
    ctrl.sortSP = '';
    ctrl.sortReverseSP = false;

	ctrl.getSales = function() {
		$('.lowInventoryContainer').hide();
		$('.searchProductContainer').hide();

		$http({
			url : location.pathname + "/inventory/sales",
			method : 'GET',
		}).then(function(response) {

			console.log(response)
			ctrl.sales = response.data.success;

			console.log(ctrl.sales);

			ctrl.getYearSales();

		})

	};


	ctrl.convertDate = function(year, month, day) {
		var date = new Date(year, month, day)
		return date;
	}


	ctrl.getSales();

	ctrl.getYearSales = function() //getting the first year sales
	{
		var today = new Date();
		today.setHours(0, 0, 0, 0);

		var yesterday = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 1));

		var lastWeek = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 8));

		var lastMonth = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 30));

		var lastYear = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 365));


		for (let i = 0; i < ctrl.sales.length; i++) {
			var type = ctrl.sales[i];

			type.yesterdayNumberSold = 0;
			type.yesterdayTotalValue = 0;

			type.lastWeekNumberSold = 0;
			type.lastWeekTotalValue = 0;

			type.lastMonthNumberSold = 0;
			type.lastMonthTotalValue = 0;

			type.lastYearNumberSold = 0;
			type.lastYearTotalValue = 0;

			for (let j = 0; j < type.dailySalesData.length; j++) {
				let salesData = type.dailySalesData[j];

				let year = salesData.date.year;
				let month = salesData.date.monthValue - 1;
				let day = salesData.date.dayOfMonth;
				let salesDate = ctrl.convertDate(year, month, day)

				if (salesDate.getTime() == yesterday.getTime()) {
					type.yesterdayNumberSold = type.yesterdayNumberSold + salesData.numberSold;
					type.yesterdayTotalValue = type.yesterdayTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastWeek) {
					type.lastWeekNumberSold = type.lastWeekNumberSold + salesData.numberSold;
					type.lastWeekTotalValue = type.lastWeekTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastMonth) {
					type.lastMonthNumberSold = type.lastMonthNumberSold + salesData.numberSold;
					type.lastMonthTotalValue = type.lastMonthTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastYear) {

					type.lastYearNumberSold = type.lastYearNumberSold + salesData.numberSold;
					type.lastYearTotalValue = type.lastYearTotalValue + salesData.totalValue;
				}
			}

			for (let k = 0; k < type.coreProductSales.length; k++) //core product 1 year sales
			{
				var coreProduct = type.coreProductSales[k];

				coreProduct.yesterdayNumberSold = 0;
				coreProduct.yesterdayTotalValue = 0;

				coreProduct.lastWeekNumberSold = 0;
				coreProduct.lastWeekTotalValue = 0;

				coreProduct.lastMonthNumberSold = 0;
				coreProduct.lastMonthTotalValue = 0;

				coreProduct.lastYearNumberSold = 0;
				coreProduct.lastYearTotalValue = 0;

				for (let j = 0; j < coreProduct.dailySalesData.length; j++) {
					let salesData = coreProduct.dailySalesData[j];

					let year = salesData.date.year;
					let month = salesData.date.monthValue - 1;
					let day = salesData.date.dayOfMonth;
					let salesDate = ctrl.convertDate(year, month, day)


					if (salesDate.getTime() == yesterday.getTime()) {
						coreProduct.yesterdayNumberSold = coreProduct.yesterdayNumberSold + salesData.numberSold;
						coreProduct.yesterdayTotalValue = coreProduct.yesterdayTotalValue + salesData.totalValue;
					}

					if (salesDate <= yesterday && salesDate >= lastWeek) {
						coreProduct.lastWeekNumberSold = coreProduct.lastWeekNumberSold + salesData.numberSold;
						coreProduct.lastWeekTotalValue = coreProduct.lastWeekTotalValue + salesData.totalValue;
					}

					if (salesDate <= yesterday && salesDate >= lastMonth) {
						coreProduct.lastMonthNumberSold = coreProduct.lastMonthNumberSold + salesData.numberSold;
						coreProduct.lastMonthTotalValue = coreProduct.lastMonthTotalValue + salesData.totalValue;
					}

					if (salesDate <= yesterday && salesDate >= lastYear) {

						coreProduct.lastYearNumberSold = coreProduct.lastYearNumberSold + salesData.numberSold;
						coreProduct.lastYearTotalValue = coreProduct.lastYearTotalValue + salesData.totalValue;
					}
				}

				for (let n = 0; n < coreProduct.productSales.length; n++) {
					product = coreProduct.productSales[n];

					product.yesterdayNumberSold = 0;
					product.yesterdayTotalValue = 0;

					product.lastWeekNumberSold = 0;
					product.lastWeekTotalValue = 0;

					product.lastMonthNumberSold = 0;
					product.lastMonthTotalValue = 0;

					product.lastYearNumberSold = 0;
					product.lastYearTotalValue = 0;

					for (let j = 0; j < product.dailySalesData.length; j++) {
						let salesData = product.dailySalesData[j];

						let year = salesData.date.year;
						let month = salesData.date.monthValue - 1;
						let day = salesData.date.dayOfMonth;
						let salesDate = ctrl.convertDate(year, month, day)


						if (salesDate.getTime() == yesterday.getTime()) {
							product.yesterdayNumberSold = product.yesterdayNumberSold + salesData.numberSold;
							product.yesterdayTotalValue = product.yesterdayTotalValue + salesData.totalValue;
						}

						if (salesDate <= yesterday && salesDate >= lastWeek) {
							product.lastWeekNumberSold = product.lastWeekNumberSold + salesData.numberSold;
							product.lastWeekTotalValue = product.lastWeekTotalValue + salesData.totalValue;
						}

						if (salesDate <= yesterday && salesDate >= lastMonth) {
							product.lastMonthNumberSold = product.lastMonthNumberSold + salesData.numberSold;
							product.lastMonthTotalValue = product.lastMonthTotalValue + salesData.totalValue;
						}

						if (salesDate <= yesterday && salesDate >= lastYear) {

							product.lastYearNumberSold = product.lastYearNumberSold + salesData.numberSold;
							product.lastYearTotalValue = product.lastYearTotalValue + salesData.totalValue;
						}
					}
				}
			}
		}
	}

	ctrl.hideCPD = function() {
		console.log("hiding CP")
		$('.typeDetail').hide()
		$('.typeDiv').hide()
	}

	ctrl.hidePD = function() {
		console.log("hiding P")
		$('.coreProductproductDetail').hide()
	}

	ctrl.showSales = function() {
		$('.fullTableContainer').show();
		$('.searchProductContainer').hide();
		$('.lowInventoryContainer').hide();
	}

	ctrl.lowInv = [];

	ctrl.showInventory = function() {
		console.log('showing inventory')
		$('.fullTableContainer').hide();
		$('.searchProductContainer').hide();
		$('.lowInventoryContainer').show();

		console.log(location.pathname + "/inventory/lowInventory")

		$http({
			url : location.pathname + "/inventory/lowInventory",
			method : 'GET',
		}).then(function(response) {

			console.log(response)

			ctrl.lowInv = response.data.success;


			console.log(ctrl.lowInv)
			console.log(ctrl.lowInv)

			ctrl.lowInvSales();


		})
	}

	ctrl.lowInvSales = function() {

		var today = new Date();
		today.setHours(0, 0, 0, 0);

		var yesterday = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 1));

		var lastWeek = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 8));

		var lastMonth = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 30));

		var lastYear = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 365));

		console.log(ctrl.lowInv)
		for (let i = 0; i < ctrl.lowInv.length; i++) {
			var product = ctrl.lowInv[i];

			product.yesterdayNumberSold = 0;
			product.yesterdayTotalValue = 0;

			product.lastWeekNumberSold = 0;
			product.lastWeekTotalValue = 0;

			product.lastMonthNumberSold = 0;
			product.lastMonthTotalValue = 0;

			product.lastYearNumberSold = 0;
			product.lastYearTotalValue = 0;

			for (let j = 0; j < product.dailySalesData.length; j++) {
				let salesData = product.dailySalesData[j];

				let year = salesData.date.year;
				let month = salesData.date.monthValue - 1;
				let day = salesData.date.dayOfMonth;
				let salesDate = ctrl.convertDate(year, month, day)


				if (salesDate.getTime() == yesterday.getTime()) {
					product.yesterdayNumberSold = product.yesterdayNumberSold + salesData.numberSold;
					product.yesterdayTotalValue = product.yesterdayTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastWeek) {
					product.lastWeekNumberSold = product.lastWeekNumberSold + salesData.numberSold;
					product.lastWeekTotalValue = product.lastWeekTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastMonth) {
					product.lastMonthNumberSold = product.lastMonthNumberSold + salesData.numberSold;
					product.lastMonthTotalValue = product.lastMonthTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastYear) {

					product.lastYearNumberSold = product.lastYearNumberSold + salesData.numberSold;
					product.lastYearTotalValue = product.lastYearTotalValue + salesData.totalValue;
				}
			}
		}

	}
	
	ctrl.searchSales = function() {

		var today = new Date();
		today.setHours(0, 0, 0, 0);

		var yesterday = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 1));

		var lastWeek = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 8));

		var lastMonth = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 30));

		var lastYear = new Date(today.getFullYear(), today.getMonth(), (today.getDate() - 365));

		console.log("Search results " + ctrl.searchResults)
		for (let i = 0; i < ctrl.searchResults.length; i++) {
			var product = ctrl.searchResults[i];

			product.yesterdayNumberSold = 0;
			product.yesterdayTotalValue = 0;

			product.lastWeekNumberSold = 0;
			product.lastWeekTotalValue = 0;

			product.lastMonthNumberSold = 0;
			product.lastMonthTotalValue = 0;

			product.lastYearNumberSold = 0;
			product.lastYearTotalValue = 0;

			for (let j = 0; j < product.dailySalesData.length; j++) {
				let salesData = product.dailySalesData[j];

				let year = salesData.date.year;
				let month = salesData.date.monthValue - 1;
				let day = salesData.date.dayOfMonth;
				let salesDate = ctrl.convertDate(year, month, day)


				if (salesDate.getTime() == yesterday.getTime()) {
					product.yesterdayNumberSold = product.yesterdayNumberSold + salesData.numberSold;
					product.yesterdayTotalValue = product.yesterdayTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastWeek) {
					product.lastWeekNumberSold = product.lastWeekNumberSold + salesData.numberSold;
					product.lastWeekTotalValue = product.lastWeekTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastMonth) {
					product.lastMonthNumberSold = product.lastMonthNumberSold + salesData.numberSold;
					product.lastMonthTotalValue = product.lastMonthTotalValue + salesData.totalValue;
				}

				if (salesDate <= yesterday && salesDate >= lastYear) {

					product.lastYearNumberSold = product.lastYearNumberSold + salesData.numberSold;
					product.lastYearTotalValue = product.lastYearTotalValue + salesData.totalValue;
				}
			}
		}

	}


	ctrl.showSearch = function() {
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
	    	
	    	CartService.search(ctrl.keyword)
	    		.then(function() {
	    			ctrl.searchSales();
	    	})
	    
	    	
	    	
	    	console.log(ctrl.searchResults)
	    	
	    	
	    	
	    
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
