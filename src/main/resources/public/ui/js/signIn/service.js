angular
    .module('signIn')
    .factory('signInService', ['$resource', '$http', function ($resource, $http) {

        var service = {};

        service.resource = $resource(
            '/sign-in/:_action'
            , {
                action: "@_action"
            }
            , {
                get: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                },
                getUserRole: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform,
                    params: {
                        _action : 'get-authority'
                    }
                },
                logout: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform,
                    params: {
                        _action : 'perform-logout'
                    }
                }
            });
        service.get = function (onSuccess, onFailure) {
            service.resource.get(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        service.getUserRole = function (onSuccess, onFailure) {
            service.resource.getUserRole({}, {}, function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        service.logout = function (onSuccess, onFailure) {
            service.resource.logout({}, {}, function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        /*service.loginResource = $resource(
            '/login'
            , {}
            , {
                login: {
                    method: 'POST',
                    transformResponse: http.response.defaultTransform,
                }
            });

        service.login = function (user, onSuccess, onFailure) {
            service.loginResource.login({}, {userName : user.userName, password : user.password}, function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };*/

        return service;
    }])