'use strict';

angular.module('rumblrsadminApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


