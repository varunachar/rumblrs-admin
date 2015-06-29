angular.module('rumblrsadminApp').controller('BrandModalInstanceCtrl', function($scope, $modalInstance, items, Brand) {

    $scope.brand = items;

    $scope.save = function() {
        if ($scope.brand.id != null) {
            Brand.update($scope.brand,
                function() {
                    $modalInstance.close();
                });
        } else {
            Brand.save($scope.brand,
                function() {
                    $modalInstance.close();
                });
        }
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
});