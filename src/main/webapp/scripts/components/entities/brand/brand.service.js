'use strict';

angular.module('rumblrsadminApp')
    .factory('Brand', function($resource, DateUtils) {
        return $resource('api/brands/:id', {}, {
            'query': {
                method: 'GET',
                isArray: true
            },
            'get': {
                method: 'GET',
                transformResponse: function(data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {
                method: 'PUT'
            },
            'search': {
                method: 'GET',
                url: 'api/brands/search',
                isArray: true
            }
        });
    });