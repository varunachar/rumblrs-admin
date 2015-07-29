/* globals $ */
'use strict';

angular.module('rumblrsadminApp').directive('bikeRating',
    function() {
        // Runs during compile
        return {
            // name: '',
            // priority: 1,
            // terminal: true,
            scope: {
                report: "=",
                detail: "=",
                radios: "="
            }, // {} = isolate, true = child, false/undefined = no change
            // controller: function($scope, $element, $attrs, $transclude) {},
            // require: 'ngModel', // Array = multiple requires, ? = optional, ^ = check parent elements
            restrict: 'E', // E = Element, A = Attribute, C = Class, M = Comment
            templateUrl: '/scripts/app/entities/bike/bike-rating-template.html',
            // replace: true,
            // transclude: true,
            // compile: function(tElement, tAttrs, function transclude(function(scope, cloneLinkingFn){ return function linking(scope, elm, attrs){}})),
            link: function($scope, iElm, iAttrs, controller) {

            }
        };
    }
);