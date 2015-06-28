'use strict';

angular.module('rumblrsadminApp')
    .controller('BikeController', function($scope, Bike, ParseLinks) {
        $scope.bikes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Bike.query({
                page: $scope.page,
                per_page: 20
            }, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.bikes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.bikes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.showDetails = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;
                $scope.bikeDetail = result.bikeDetail;
                var viewBikeModal = $('#viewBikeModal');
                viewBikeModal.on('hide.bs.modal', function() {
                    $scope.clear();
                });
                viewBikeModal.modal('show');
            });
        };

        $scope.delete = function(id) {
            Bike.get({
                id: id
            }, function(result) {
                $scope.bike = result.bike;
                $('#deleteBikeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            Bike.delete({
                    id: id
                },
                function() {
                    $scope.reset();
                    $('#deleteBikeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function() {
            $scope.reset();
            $('#viewBikeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function() {
            $scope.bike = {
                brand: null,
                name: null,
                engineCapacity: null,
                yearOfManufacture: null,
                location: null,
                kms: null,
                owners: null,
                price: null,
                score: null,
                type: null,
                thumbnail: null,
                reserved: null,
                sold: null,
                detailId: null,
                id: null
            };
            $scope.bikeDetail = {
                owner: null,
                features: null,
                documents: null,
                report: null,
                performance: null,
                id: null
            };
        };
        $scope.loadAll();
    });