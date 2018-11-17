angular
    .module('profile')
    .controller('profileController', function ($scope, $http, $location, $rootScope, $route, $timeout, profileService) {
        $scope.message = 'Личный кабинет.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;

        $scope.loadUser = function () {
            profileService.getCurrentAuthorizedUser(
                function (user) {
                    $scope.user = user;
                },
                function (error) {
                    console.error(error);
                    $scope.error = error;
                });
        }
        $timeout(function () {
            $scope.loadUser();
        })
    });

