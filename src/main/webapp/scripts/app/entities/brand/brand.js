'use strict';

angular.module('rumblrsadminApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('brand', {
                parent: 'entity',
                url: '/brand',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Brands'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/brand/brands.html',
                        controller: 'BrandController'
                    }
                },
                resolve: {
                }
            })
            .state('brandDetail', {
                parent: 'entity',
                url: '/brand/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Brand'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/brand/brand-detail.html',
                        controller: 'BrandDetailController'
                    }
                },
                resolve: {
                }
            });
    });
