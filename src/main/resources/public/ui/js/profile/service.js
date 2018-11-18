angular
    .module('profile')
    .factory('profileService', ['$resource', '$http', function ($resource, $http) {
        var service = {};
        service.resource = $resource(
            '/rest/profile/:_action:_id'
            , {
                id: "@_id",
                action: "@_action"
            }
            , {
                get: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                }
            });
        service.get = function (id, onSuccess, onFailure) {
            this.resource.get({_id : id}, {}, function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        service.userResource = $resource(
            '/rest/user/:_action:_id'
            , {
                id: "@_id",
                action: "@_action"
            }
            , {
                getCurrentAuth: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform,
                    params: {
                        _action : 'get-current-authorized'
                    }
                }
            });

        service.getCurrentAuthorizedUser = function (onSuccess, onFailure) {
            this.userResource.getCurrentAuth(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        return service;
    }])