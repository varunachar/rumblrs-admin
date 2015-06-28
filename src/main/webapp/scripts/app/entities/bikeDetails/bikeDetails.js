'use strict';

angular.module('rumblrsadminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bikeDetails', {
                parent: 'entity',
                url: '/bikeDetails',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BikeDetailss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bikeDetails/bikeDetailss.html',
                        controller: 'BikeDetailsController'
                    }
                },
                resolve: {
                }
            })
            .state('bikeDetailsDetail', {
                parent: 'entity',
                url: '/bikeDetails/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BikeDetails'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bikeDetails/bikeDetails-detail.html',
                        controller: 'BikeDetailsDetailController'
                    }
                },
                resolve: {
                }
            });
    });
