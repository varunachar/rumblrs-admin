'use strict';

angular.module('rumblrsadminApp')
    .factory('Body', function ($resource, DateUtils) {
        return $resource('api/bodys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
