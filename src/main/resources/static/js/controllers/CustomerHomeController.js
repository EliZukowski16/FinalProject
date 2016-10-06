angular
	.module("liquorStore")
	.controller("CustomerHomeController", homeCtrl)
	
	homeCtrl.$inject=['$state', 'Test']
	function testCtrl($state, Test){
	var ctrl = this;
	console.log("state is ", $state);
	ctrl.print = "hello";
}