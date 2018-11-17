angular
    .module('nextweek', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/nextweek/list', {
                templateUrl: './ui/view/nextweek/list.html',
                controller: 'nextweekController'
            })
            .when('/nextweek/card', {
                templateUrl: './ui/view/nextweek/fixtureCard.html',
                controller: 'nextweekController'
            })
        ;
    });