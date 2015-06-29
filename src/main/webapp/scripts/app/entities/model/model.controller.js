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
                    templateUrl: 'scripts/app/entities/model/model-delete.html',
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
            $scope.clear();
        };

        $scope.clear = function() {
            $scope.model = {
                name: null,
                engineCapacity: null,
                brandId: null,
                id: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });