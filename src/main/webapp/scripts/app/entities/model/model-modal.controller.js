angular.module('rumblrsadminApp').controller('ModalInstanceCtrl', function($scope, $modalInstance, items, Model, Brand) {

    $scope.model = items;
    $scope.brands = [];

    Brand.get({
        id: items.brandId
    }, function(result) {
        $scope.model.brandName = result.name;
    });

    $scope.save = function() {
        if ($scope.model.id != null) {
            Model.update($scope.model,
                function() {
                    $modalInstance.close();
                });
        } else {
            Model.save($scope.model,
                function() {
                    $modalInstance.close();
                });
        }
    };

    $scope.itemSelected = function($item, $model, $label) {
        $scope.model.brandId = $item.id;
    };

    $scope.getBrands = function(value) {
        Brand.search({
            name: value
        }, function(result) {
            $scope.brands = result;
        })
        return $scope.brands;
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
});