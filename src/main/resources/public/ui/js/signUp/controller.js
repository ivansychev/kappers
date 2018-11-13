angular
    .module('signUp')
    .controller('signUpController', function ($scope, $http, $location, $rootScope, $route, mainService) {
        $scope.message = 'Зарегистрироваться.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
    });

