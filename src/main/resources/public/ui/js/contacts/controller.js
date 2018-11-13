angular
    .module('contacts')
    .controller('contactsController', function ($scope, $http, $location, $rootScope, $route, mainService) {
        $scope.message = 'Это сообщение о контактах.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
    });

