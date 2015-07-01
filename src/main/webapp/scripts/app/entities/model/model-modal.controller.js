angular.module('rumblrsadminApp').controller('ModalInstanceCtrl',
    function($scope, $modalInstance, items, Model, Brand, Body) {

        $scope.model = items ? items : {};
        $scope.brands = [];
        $scope.bodies = [];

        Body.query(function(result) {
            $scope.bodies = result;
            if (!$scope.model.body) {
                result.forEach(function(body) {
                    if (body.type === 'City') {
                        $scope.model.body = body;
                    }
                });
            } else {
                result.forEach(function(body) {
                    if (body.id === $scope.model.body.id) {
                        $scope.model.body = body;
                    }
                })
            }
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