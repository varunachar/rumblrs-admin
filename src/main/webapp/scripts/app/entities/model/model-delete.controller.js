angular.module('rumblrsadminApp').controller('ModalDeleteInstanceCtrl', function($scope, $modalDeleteInstance, items) {

    $scope.model = items;


    $scope.confirmDelete = function(id) {
        Model.delete({
                id: id
            },
            function() {
                $modalDeleteInstance.close();
            });
    };

    $scope.cancel = function() {
        $modalDeleteInstance.dismiss('cancel');
    };
});