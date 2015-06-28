'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeDetailsController', function ($scope, BikeDetails) {
        $scope.bikeDetailss = [];
        $scope.loadAll = function() {
            BikeDetails.query(function(result) {
               $scope.bikeDetailss = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            BikeDetails.get({id: id}, function(result) {
                $scope.bikeDetails = result;
                $('#saveBikeDetailsModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bikeDetails.id != null) {
                BikeDetails.update($scope.bikeDetails,
                    function () {
                        $scope.refresh();
                    });
            } else {
                BikeDetails.save($scope.bikeDetails,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            BikeDetails.get({id: id}, function(result) {
                $scope.bikeDetails = result;
                $('#deleteBikeDetailsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BikeDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBikeDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBikeDetailsModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bikeDetails = {owner: null, features: null, documents: null, report: null, performance: null, detailId: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
