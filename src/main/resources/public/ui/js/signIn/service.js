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

        return service;
    }])