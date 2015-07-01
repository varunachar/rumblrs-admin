angular.module('rumblrsadminApp').controller('BodyModalInstanceCtrl', function($scope, $modalInstance, items, Body) {

    $scope.body = items;

    $scope.save = function() {
        if ($scope.body.id != null) {
            Body.update($scope.body,
                function() {
                    $modalInstance.close();
                });
        } else {
            Body.save($scope.body,
                function() {
                    $modalInstance.close();
                });
        }
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
});