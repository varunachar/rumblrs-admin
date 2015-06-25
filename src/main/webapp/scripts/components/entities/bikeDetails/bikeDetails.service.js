'use strict';

angular.module('rumblrsadminApp')
    .factory('BikeDetails', function ($resource, DateUtils) {
        return $resource('api/bikeDetailss/:id', {}, {
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
