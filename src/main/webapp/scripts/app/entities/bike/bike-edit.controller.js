'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeEditController', function($scope, $stateParams, $state, Bike, Model) {
        $scope.bike = {};
        $scope.bikeDetail = {};
        $scope.models = [];
        $scope.load = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;
                $scope.bikeDetail = result.bikeDetail;
            });
        };

        $scope.save = function() {
            $scope.bike.brand = $scope.bike.model.brandName;
            $scope.bike.name = $scope.bike.model.name;
            $scope.bike.engineCapacity = $scope.bike.model.engineCapacity;
            $scope.bike.brandId = $scope.bike.model.brandId;
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

        $scope.getModels = function(value) {
            Model.search({
                name: value
            }, function(result) {
                $scope.models = result;
            });
            return $scope.models;
        };

        $scope.formatValue = function(value) {
            if (value) {
                return value.brandName + ' - ' + value.name + ' ' + value.engineCapacity + 'cc';
            }
        };

        if ($stateParams.id) {
            $scope.load($stateParams.id);
        }


    });