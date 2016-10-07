angular
	.module("liquorStore")
	.controller("CustomerHomeController", homeCtrl)
	
	homeCtrl.$inject=['$state']
	function homeCtrl($state){
	var ctrl = this;
	console.log("state is ", $state);
	ctrl.print = "hello";
}