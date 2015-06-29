angular.module('rumblrsadminApp')
    .controller('BrandDeleteModalInstanceCtrl', function($scope, $modalInstance, Brand, items, Notification) {

        $scope.brand = items;


        $scope.confirmDelete = function(id) {
            Brand.delete({
                    id: id
                },
                function() {
                    $modalInstance.close();
                }, function(response) {
                    $modalInstance.dismiss('cancel');
                    if (response.status = 412) {
                        Notification.error('Cannot delete Brand since Models exists with this brand');
                    } else {
                        Notification.error('There was an issue during processing');
                    }
                });
        };

        $scope.cancel = function(response) {
            $modalInstance.dismiss('cancel');
        };
    });