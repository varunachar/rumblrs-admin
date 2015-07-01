'use strict';

angular.module('rumblrsadminApp')
    .controller('BodyController', function($scope, $modal, Body) {
        $scope.bodies = [];
        $scope.loadAll = function() {
            Body.query(function(result) {
                $scope.bodies = result;
            });
        };
        $scope.loadAll();

        $scope.open = function(result) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'body-modal.html',
                controller: 'BodyModalInstanceCtrl',
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
            Body.get({
                id: id
            }, function(result) {
                $scope.open(result);
            });
        };

        $scope.delete = function(id) {
            Body.get({
                id: id
            }, function(result) {
                var modalDeleteInstance = $modal.open({
                    animation: true,
                    templateUrl: 'body-delete.html',
                    controller: 'BodyDeleteModalInstanceCtrl',
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