'use strict';

angular.module('rumblrsadminApp')
    .controller('BodyDetailController', function($scope, $stateParams, Body) {
        $scope.body = {};
        $scope.load = function(id) {
            Body.get({
                id: id
            }, function(result) {
                $scope.body = result;
            });
        };
        $scope.load($stateParams.id);
    });