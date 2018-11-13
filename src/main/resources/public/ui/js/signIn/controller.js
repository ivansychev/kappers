angular
    .module('signIn')
    .controller('signInController', function ($scope, $http, $location, $rootScope, $route, mainService) {
        $scope.message = 'Войти.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
    });

