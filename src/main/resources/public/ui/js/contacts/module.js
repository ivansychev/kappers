angular
    .module('contacts', ['ngRoute', 'ngResource', 'ngAnimate'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/contacts', {
                templateUrl: './ui/view/contacts/contacts.html',
                controller: 'contactsController'
            })
        ;
    });