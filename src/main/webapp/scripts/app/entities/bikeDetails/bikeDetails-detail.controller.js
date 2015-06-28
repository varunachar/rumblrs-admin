'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeDetailsDetailController', function ($scope, $stateParams, BikeDetails) {
        $scope.bikeDetails = {};
        $scope.load = function (id) {
            BikeDetails.get({id: id}, function(result) {
              $scope.bikeDetails = result;
            });
        };
        $scope.load($stateParams.id);
    });
