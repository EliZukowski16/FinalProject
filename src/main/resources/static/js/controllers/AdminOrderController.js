angular
	.module("liquorStore")
	.controller("AdminOrderController", orderCtrl)
orderCtrl.$inject = [ '$http' ]

function orderCtrl($http) {
	var ctrl = this;
	ctrl.unOrders = [];

	ctrl.sortO = '';
	ctrl.sortReverseO = false;

	ctrl.getUnOrders = function() {

		$http({
			url : location.pathname + "/orders/unfulfilled",
			method : 'GET',
		}).then(function(response) {

			console.log(response)
			ctrl.unOrders = response.data.success;
			console.log(ctrl.unOrders);
			ctrl.convertDates();

		})
	};

	ctrl.getUnOrders();

	ctrl.convertDates = function() {
		for (let i = 0; i < ctrl.unOrders.length; i++) {


			ctrl.unOrders[i].deliveryDate = new Date(ctrl.unOrders[i].date.year, ctrl.unOrders[i].date.monthValue - 1, ctrl.unOrders[i].date.dayOfMonth);
			ctrl.unOrders[i].orderDate = new Date(ctrl.unOrders[i].timeOfOrder.year, ctrl.unOrders[i].timeOfOrder.monthValue - 1, ctrl.unOrders[i].timeOfOrder.dayOfMonth, ctrl.unOrders[i].timeOfOrder.hour, ctrl.unOrders[i].timeOfOrder.minute, ctrl.unOrders[i].timeOfOrder.second)
		}
	}
}