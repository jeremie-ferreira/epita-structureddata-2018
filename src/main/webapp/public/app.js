"use strict";

var app = angular.module('app', [ 'ngResource',
		'ngMaterial', 'ngMessages', 'ngSanitize', 'ngRoute',
		'ngAnimate']);

app.factory("Station", ["$resource", function($resource) {
	return $resource("api/station/:id", {id: '@id'}, {update: {method:'PUT'}}, {delete: {method:'DELETE'}});
}]);

app.controller('PricerController', function($scope, Station) {
    $scope.stations = Station.query();

    $scope.origin = null;
    $scope.destination = null;

});