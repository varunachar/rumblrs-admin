'use strict';

angular.module('rumblrsadminApp')
    .config(function($stateProvider) {
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
                resolve: {}
            })
            .state('bikeEdit', {
                parent: 'entity',
                url: '/bike/edit/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bike Edit'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bike/bike-edit.html',
                        controller: 'BikeEditController'
                    }
                },
                resolve: {}
            });
    });