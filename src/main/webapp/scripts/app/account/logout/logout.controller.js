'use strict';

angular.module('rumblrsadminApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
