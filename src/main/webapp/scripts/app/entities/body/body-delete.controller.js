angular.module('rumblrsadminApp')
    .controller('BodyDeleteModalInstanceCtrl', function($scope, $modalInstance, Body, items, Notification) {

        $scope.body = items;


        $scope.confirmDelete = function(id) {
            Body.delete({
                    id: id
                },
                function() {
                    $modalInstance.close();
                }, function(response) {
                    $modalInstance.dismiss('cancel');
                    if (response.status = 412) {
                        Notification.error('Cannot delete Body since Models exists with this Body type');
                    } else {
                        Notification.error('There was an issue during processing');
                    }
                });
        };

        $scope.cancel = function(response) {
            $modalInstance.dismiss('cancel');
        };
    });