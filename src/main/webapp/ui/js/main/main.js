angular
    .module('kappersApp')
    .controller('mainController', function ($scope, $http, $location, $rootScope, $route, mainService) {
        $scope.message = 'Это главная страница.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
    });

