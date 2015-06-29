'use strict';

angular.module('rumblrsadminApp', ['LocalStorageModule',
    'ngResource', 'ui.router', 'ngCookies', 'ngCacheBuster',
    'infinite-scroll', 'ui.bootstrap',
    'ui-notification'
])

.run(function($rootScope, $location, $window, $http, $state, Auth, Principal, ENV, VERSION) {
    $rootScope.ENV = ENV;
    $rootScope.VERSION = VERSION;
    $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;

        if (Principal.isIdentityResolved()) {
            Auth.authorize();
        }

    });

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        var titleKey = 'Axlery Admin';

        $rootScope.previousStateName = fromState.name;
        $rootScope.previousStateParams = fromParams;

        // Set the page title key to the one configured in state or use default one
        if (toState.data.pageTitle) {
            titleKey = toState.data.pageTitle;
        }
        $window.document.title = titleKey;
    });

    $rootScope.back = function() {
        // If previous state is 'activate' or do not exist go to 'home'
        if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
            $state.go('home');
        } else {
            $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
        }
    };
})
    .factory('authInterceptor', function($rootScope, $q, $location, localStorageService) {
        return {
            // Add authorization token to headers
            request: function(config) {
                config.headers = config.headers || {};
                var token = localStorageService.get('token');

                if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                    config.headers.Authorization = 'Bearer ' + token.access_token;
                }

                return config;
            }
        };
    })
    .factory('authExpiredInterceptor', function($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function(response) {
                // token has expired
                if (response.status === 401 && (response.data.error == 'invalid_token' || response.data.error == 'Unauthorized')) {
                    localStorageService.remove('token');
                    var Principal = $injector.get('Principal');
                    if (Principal.isAuthenticated()) {
                        var Auth = $injector.get('Auth');
                        Auth.authorize(true);
                    }
                }
                return $q.reject(response);
            }
        };
    })
    .config(function($stateProvider, $urlRouterProvider, $httpProvider,
        $locationProvider, httpRequestInterceptorCacheBusterProvider,
        NotificationProvider) {

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function(Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });




        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        NotificationProvider.setOptions({
            delay: 10000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'top'
        })
    });