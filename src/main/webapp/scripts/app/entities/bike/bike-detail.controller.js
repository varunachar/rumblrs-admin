'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeDetailController', function ($scope, $stateParams, Bike) {
        $scope.bike = {};
        $scope.load = function (id) {
            Bike.get({id: id}, function(result) {
              $scope.bike = result;
            });
        };
        $scope.load($stateParams.id);
    });
