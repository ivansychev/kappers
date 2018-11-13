angular
    .module('main', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: './ui/view/main/main.html',
                controller: 'mainController'
            })
        ;
    });