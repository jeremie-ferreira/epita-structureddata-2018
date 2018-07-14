"use strict";

/* Define the application */
var app = angular.module('app', [ 'ngResource',
		'ngMaterial', 'ngMessages', 'ngSanitize', 'ngRoute',
		'ngAnimate']);

/* Define the Station Resource (used to query Station API) */
app.factory("Station", ["$resource", function($resource) {
	return $resource("api/station/:id", {id: '@id'}, {update: {method:'PUT'}}, {delete: {method:'DELETE'}});
}]);

/* Define the application main controller */
app.controller('PricerController', function($scope, $http, Station) {
	//station list used in the autocomplete inputs
    $scope.stations = Station.query();

    //selected stations for origin and destination
    $scope.origin = null;
    $scope.destination = null;

    //search text used inside the autocomplete component
    $scope.originSearchText = "";
    $scope.destinationSearchText = "";

    //resulting path
    $scope.path = null;

    //function used to update the result according to the input
    $scope.updateResult = function() {
    	//call the result over the api
		$http.get('/api/line/' + $scope.origin.code + '/' + $scope.destination.code).then(function(res) {
			$scope.path = res.data;
		})
	}

	//query used to filter result according to the user input text
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