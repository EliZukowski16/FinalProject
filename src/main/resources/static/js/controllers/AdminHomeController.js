angular
	.module("liquorStore")
	.controller("AdminHomeController", homeCtrl)
homeCtrl.$inject = [ '$http' ]

function homeCtrl($http) {
	var ctrl = this;
	ctrl.pending = [];

	ctrl.sortOrderP = '';
	ctrl.sortReverseOP = false;


	ctrl.getPending = function() {

		$http({
			url : location.pathname + "/orders/pending",
			method : 'GET',
		}).then(function(response) {

			ctrl.pending = response.data.success;
			console.log(ctrl.pending);
			ctrl.convertDates();

		})
	};

	ctrl.convertDates = function() {
		for (let i = 0; i < ctrl.pending.length; i++) {


			ctrl.pending[i].deliveryDate = new Date(ctrl.pending[i].date.year, ctrl.pending[i].date.monthValue - 1, ctrl.pending[i].date.dayOfMonth);
			ctrl.pending[i].orderDate = new Date(ctrl.pending[i].timeOfOrder.year, ctrl.pending[i].timeOfOrder.monthValue - 1, ctrl.pending[i].timeOfOrder.dayOfMonth, ctrl.pending[i].timeOfOrder.hour, ctrl.pending[i].timeOfOrder.minute, ctrl.pending[i].timeOfOrder.second)
		}
	}

	ctrl.getPending();

	ctrl.status = [];
	ctrl.statusPopup = [];

	ctrl.checkedRadio = function(order, value) {



		ctrl.stat = {
			[order.id] : value
		}





		ctrl.statPopup = {
			id : order.id,
			value : value
		}



		ctrl.bol = false

		if (ctrl.statusPopup.length == 0) {
			console.log("pushed")
			ctrl.statusPopup.push(ctrl.statPopup)
			ctrl.status.push(ctrl.stat);
		} else {
			for (let i = 0; i < ctrl.statusPopup.length; i++) {
				console.log('length' + ctrl.statusPopup.length)
				if (ctrl.statusPopup[i].id == order.id) {
					ctrl.bol = true;
					console.log('contains')
					ctrl.status.splice(i, 1)
					ctrl.statusPopup.splice(i, 1)
					ctrl.statusPopup.push(ctrl.statPopup)
					ctrl.status.push(ctrl.stat);
					break;
				}

			}

			if (ctrl.bol == false) {
				console.log('does not contrain')
				ctrl.statusPopup.push(ctrl.statPopup)
				ctrl.status.push(ctrl.stat);
			}



		}

		console.log(ctrl.status);
		console.log(ctrl.statusPopup)



	}


	ctrl.submitStatus = function() {
		console.log(ctrl.status)
		console.log(ctrl.statusPopup)



		for (let i = 0; i < ctrl.statusPopup.length; i++) {
			if (ctrl.statusPopup[i].value == "pending") {
				ctrl.statusPopup.splice(i, 1);
				ctrl.status.splice(i, 1);
			}
		}

		if (ctrl.status.length == 0) {
			$('#successAddPendO').addClass('hide');
			$('#failedAddPendO').removeClass('hide');
		} else {



			console.log(ctrl.status)
			console.log(ctrl.statusPopup)

			$http({
				url : location.pathname + "/orders/pending",
				method : 'POST',
				data : ctrl.status,
			}).then(function(response) {
				console.log('it worked' + response.data)

			})
		}

	}

	ctrl.reload = function() {
		location.reload()
	}



}