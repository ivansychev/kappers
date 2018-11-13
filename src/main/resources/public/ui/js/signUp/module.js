angular
    .module('signUp', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/sign-up', {
                templateUrl: './ui/view/signUp/signUp.html',
                controller: 'signUpController'
            })
        ;
    });