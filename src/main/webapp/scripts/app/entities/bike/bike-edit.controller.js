'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeEditController', function($scope, $stateParams, $state, Bike) {
        $scope.bike = {};
        $scope.bikeDetail = {};
        $scope.load = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;
                $scope.bikeDetail = result.bikeDetail;
            });
        };

        $scope.save = function() {
            if ($scope.bike.id != null) {
                Bike.update({
                        bike: $scope.bike,
                        bikeDetail: $scope.bikeDetail
                    },
                    function() {
                        $state.go('bike');
                    });
            } else {
                Bike.save({
                        bike: $scope.bike,
                        bikeDetail: $scope.bikeDetail
                    },
                    function() {
                        // navigate back to listing page
                        $state.go('bike');
                    });
            }
        };

        if ($stateParams.id) {
            $scope.load($stateParams.id);
        }
    });