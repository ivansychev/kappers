angular
    .module('about')
    .controller('aboutController', function ($scope, $http, $location, $rootScope, $route, mainService) {
        $scope.message = 'Это сообщение о проекте.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
    });

