'use strict';

angular.module('rumblrsadminApp')
    .controller('BrandController', function ($scope, Brand) {
        $scope.brands = [];
        $scope.loadAll = function() {
            Brand.query(function(result) {
               $scope.brands = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Brand.get({id: id}, function(result) {
                $scope.brand = result;
                $('#saveBrandModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.brand.id != null) {
                Brand.update($scope.brand,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Brand.save($scope.brand,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Brand.get({id: id}, function(result) {
                $scope.brand = result;
                $('#deleteBrandConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Brand.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBrandConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBrandModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.brand = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
