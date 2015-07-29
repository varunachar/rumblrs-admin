angular.module('rumblrsadminApp').controller('BikeModalInstanceCtrl',
    function($scope, $modalInstance, items, Model, Brand, Body) {

        $scope.bike = items ? items.bike : {};
        $scope.bikeDetail = items ? items.bikeDetail : {};
        $scope.owner = items ? items.owner : {};

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };
    });