'use strict';

angular.module('rumblrsadminApp')
    .controller('ModelDetailController', function ($scope, $stateParams, Model) {
        $scope.model = {};
        $scope.load = function (id) {
            Model.get({id: id}, function(result) {
              $scope.model = result;
            });
        };
        $scope.load($stateParams.id);
    });
