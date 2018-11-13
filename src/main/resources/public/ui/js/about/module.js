angular
    .module('about', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/about', {
                templateUrl: './ui/view/about/about.html',
                controller: 'aboutController'
            })
        ;
    });