angular.module('rumblrsadminApp').controller('BikeDeleteInstanceCtrl', function($scope, $modalInstance, Bike, items) {

    $scope.bike = items ? items.bike : {};


    $scope.confirmDelete = function(id) {
        Bike.delete({
                id: id
            },
            function() {
                $modalInstance.close();
            });
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
});