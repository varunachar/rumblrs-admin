'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeController', function($scope, Bike, ParseLinks, $modal) {
        $scope.bikes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Bike.query({
                page: $scope.page,
                per_page: 20
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.bikes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.bikes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.view = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                var modalInstance = $modal.open({
                    animation: true,
                    templateUrl: 'bike-modal.html',
                    controller: 'BikeModalInstanceCtrl',
                    resolve: {
                        items: function() {
                            return result;
                        }
                    }
                });
                modalInstance.result.then(function() {
                    $scope.refresh();
                });
            });
        };


        $scope.delete = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;

                var modalInstance = $modal.open({
                    animation: true,
                    templateUrl: 'bike-delete.html',
                    controller: 'BikeDeleteInstanceCtrl',
                    resolve: {
                        items: function() {
                            return result;
                        }
                    }
                });
                modalInstance.result.then(function() {
                    $scope.refresh();
                });
            });
        };

        $scope.refresh = function() {
            $scope.reset();
        };

        $scope.loadAll();
    });