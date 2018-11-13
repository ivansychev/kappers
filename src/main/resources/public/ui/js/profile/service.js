angular
    .module('profile')
    .factory('profileService', ['$resource', '$http', function ($resource, $http) {

        var service = {};

        service.resource = $resource(
            '/kappers/rest/profile/:_action:_id'
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
        return service;
    }])