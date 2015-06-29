'use strict';

angular.module('rumblrsadminApp')
    .controller('BrandDetailController', function ($scope, $stateParams, Brand) {
        $scope.brand = {};
        $scope.load = function (id) {
            Brand.get({id: id}, function(result) {
              $scope.brand = result;
            });
        };
        $scope.load($stateParams.id);
    });
