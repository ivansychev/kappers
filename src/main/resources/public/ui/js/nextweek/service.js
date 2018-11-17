angular
    .module('nextweek')
    .factory('nextweekService', ['$resource', '$http', function ($resource, $http) {

        var service = {};

        service.resource = $resource(
            '/rest/fixture/:_action:_id'
            ,  {
                id: "@_id",
                action: "@_action"
            }
            , {
                getAll: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform,
                    params: {
                        _action : 'nextweek'
                    }
                },
                getById: {
                    method: 'GET',
                    transformResponse:  http.response.defaultTransform
                }
            });
        service.getAll = function (onSuccess, onFailure) {
            this.resource.getAll(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        service.getById = function (id, onSuccess, onFailure) {
            this.resource.getById({_id : id}, {}, function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };
        return service;
    }])