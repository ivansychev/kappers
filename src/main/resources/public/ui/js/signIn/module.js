angular
    .module('signIn', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/sign-in', {
                templateUrl: './ui/view/signIn/signIn.html',
                controller: 'signInController'
            })
        ;
    });