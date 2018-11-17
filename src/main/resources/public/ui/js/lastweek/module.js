angular
    .module('lastweek', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/lastweek/list', {
                templateUrl: './ui/view/lastweek/list.html',
                controller: 'lastweekController'
            })
            .when('/lastweek/card', {
                templateUrl: './ui/view/lastweek/fixtureCard.html',
                controller: 'lastweekController'
            })
        ;
    });