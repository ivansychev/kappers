angular
    .module('profile', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/profile', {
                templateUrl: './ui/view/profile/profile.html',
                controller: 'profileController'
            })
        ;
    });