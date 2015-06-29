angular.module('rumblrsadminApp').controller('ModalDeleteInstanceCtrl', function($scope, $modalInstance, Model, items) {

    $scope.model = items;


    $scope.confirmDelete = function(id) {
        Model.delete({
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