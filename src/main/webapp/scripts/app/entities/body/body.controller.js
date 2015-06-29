'use strict';

angular.module('rumblrsadminApp')
    .controller('BodyController', function ($scope, Body) {
        $scope.bodys = [];
        $scope.loadAll = function() {
            Body.query(function(result) {
               $scope.bodys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Body.get({id: id}, function(result) {
                $scope.body = result;
                $('#saveBodyModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.body.id != null) {
                Body.update($scope.body,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Body.save($scope.body,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Body.get({id: id}, function(result) {
                $scope.body = result;
                $('#deleteBodyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Body.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBodyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBodyModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.body = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
