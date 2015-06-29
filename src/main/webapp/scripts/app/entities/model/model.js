'use strict';

angular.module('rumblrsadminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('model', {
                parent: 'entity',
                url: '/model',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Models'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/model/models.html',
                        controller: 'ModelController'
                    }
                },
                resolve: {
                }
            })
            .state('modelDetail', {
                parent: 'entity',
                url: '/model/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Model'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/model/model-detail.html',
                        controller: 'ModelDetailController'
                    }
                },
                resolve: {
                }
            });
    });
