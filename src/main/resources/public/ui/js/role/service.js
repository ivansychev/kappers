angular
    .module('role')
    .factory('roleService', ['$resource', '$http', function ($resource, $http) {
        var service = {};
        service.resource = $resource(
            '/rest/dict/role/:_action:_id'
            , {
                id: "@_id",
                action: "@_action"
            }
            , {
                get: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                },
                getAll: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                },
            });
        service.getAll = function (onSuccess, onFailure) {
            this.resource.getAll(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };
        return service;
    }])