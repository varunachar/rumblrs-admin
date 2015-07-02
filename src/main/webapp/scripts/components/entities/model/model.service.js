'use strict';

angular.module('rumblrsadminApp')
    .factory('Model', function($resource, DateUtils) {
        return $resource('api/models/:id', {}, {
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
                url: '/api/models/search',
                method: 'GET',
                isArray: true
            }
        });
    });