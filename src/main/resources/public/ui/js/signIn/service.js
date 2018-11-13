angular
    .module('signIn')
    .factory('signInService', ['$resource', '$http', function ($resource, $http) {

        var service = {};

        service.resource = $resource(
            '/sign-in'
            , {}
            , {
                get: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                }
            });
        service.get = function (onSuccess, onFailure) {
            this.resource.get(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        return service;
    }])