"use strict";

var app = angular.module('app', [ 'ngResource',
		'ngMaterial', 'ngMessages', 'ngSanitize', 'ngRoute',
		'ngAnimate']);

app.factory("Station", ["$resource", function($resource) {
	return $resource("api/station/:id", {id: '@id'}, {update: {method:'PUT'}}, {delete: {method:'DELETE'}});
}]);

app.controller('PricerController', function($scope, $http, Station) {
    $scope.stations = Station.query();

    $scope.origin = null;
    $scope.destination = null;

    $scope.originSearchText = "";
    $scope.destinationSearchText = "";

    $scope.path = null;

    $scope.updateResult = function() {
		console.log("here");
		$http.get('/api/line/' + $scope.origin.code + '/' + $scope.destination.code).then(function(res) {
			console.log(res.data);
			$scope.path = res.data;
		})
	}

	$scope.querySearch = function(searchText) {
		var lowercaseQuery = angular.lowercase(searchText);
		console.log($scope.stations);
		return $scope.stations.filter(function(station) {
			var value = station.name;
			var	lowercaseItem = angular.lowercase(value);
			return lowercaseItem.indexOf(lowercaseQuery) >= 0;		
		});
	}

	
});