'use strict';

angular.module('rumblrsadminApp')
    .controller('ModelController', function($scope, $modal, Model) {
        $scope.models = [];
        $scope.loadAll = function() {
            Model.query(function(result) {
                $scope.models = result;
            });
        };

        $scope.loadAll();

        $scope.open = function(result) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'model-modal.html',
                controller: 'ModalInstanceCtrl',
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
            Model.get({
                id: id
            }, function(result) {
                $scope.open(result);
            });
        };

        $scope.delete = function(id) {
            Model.get({
                id: id
            }, function(result) {
                var modalDeleteInstance = $modal.open({
                    animation: true,
                    templateUrl: 'model-delete.html',
                    controller: 'ModalDeleteInstanceCtrl',
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