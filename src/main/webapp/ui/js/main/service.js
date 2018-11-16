angular
    .module('main')
    .factory('mainService', ['$resource', '$http', function ($resource, $http) {

        var mainService = {};

        mainService.MainResource = $resource(
            '/main/about'
            , {}
            , {
                get: {
                    method: 'GET',
                    transformResponse: http.response.defaultTransform
                }
            });
        mainService.get = function (onSuccess, onFailure) {
            this.MainResource.get(function (response) {
                http.response.defaultResolve(response, onSuccess, onFailure);
            });
        };

        return mainService;
    }])