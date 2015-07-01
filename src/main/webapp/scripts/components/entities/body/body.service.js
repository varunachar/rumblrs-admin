'use strict';

angular.module('rumblrsadminApp')
    .factory('Body', function($resource, DateUtils) {
        return $resource('api/bodies/:id', {}, {
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
                url: 'api/bodies/search',
                isArray: true
            }
        });
    });