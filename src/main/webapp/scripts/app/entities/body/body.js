'use strict';

angular.module('rumblrsadminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('body', {
                parent: 'entity',
                url: '/body',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bodys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/body/bodys.html',
                        controller: 'BodyController'
                    }
                },
                resolve: {
                }
            })
            .state('bodyDetail', {
                parent: 'entity',
                url: '/body/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Body'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/body/body-detail.html',
                        controller: 'BodyDetailController'
                    }
                },
                resolve: {
                }
            });
    });
