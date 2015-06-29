'use strict';

angular.module('rumblrsadminApp')
    .controller('BrandController', function($scope, $modal, Brand) {
        $scope.brands = [];
        $scope.loadAll = function() {
            Brand.query(function(result) {
                $scope.brands = result;
            });
        };
        $scope.loadAll();

        $scope.open = function(result) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'brand-modal.html',
                controller: 'BrandModalInstanceCtrl',
                resolve: {
                    items: function() {
                        return result;
                    }
                }
            });

            modalInstance.result.then(function() {
                $scope.refresh();
            });
        }

        $scope.edit = function(id) {
            Brand.get({
                id: id
            }, function(result) {
                $scope.open(result);
            });
        };

        $scope.delete = function(id) {
            Brand.get({
                id: id
            }, function(result) {
                var modalDeleteInstance = $modal.open({
                    animation: true,
                    templateUrl: 'brand-delete.html',
                    controller: 'BrandDeleteModalInstanceCtrl',
                    resolve: {
                        items: function() {
                            return result;
                        }
                    }
                });

                modalDeleteInstance.result.then(function() {
                    $scope.refresh();
                });
            });
        };

        $scope.refresh = function() {
            $scope.loadAll();
        };
    });