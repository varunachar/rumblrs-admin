'use strict';

angular.module('rumblrsadminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bike', {
                parent: 'entity',
                url: '/bike',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bikes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bike/bikes.html',
                        controller: 'BikeController'
                    }
                },
                resolve: {
                }
            })
            .state('bikeDetail', {
                parent: 'entity',
                url: '/bike/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bike'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bike/bike-detail.html',
                        controller: 'BikeDetailController'
                    }
                },
                resolve: {
                }
            });
    });
