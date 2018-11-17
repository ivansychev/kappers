angular
    .module('profile')
    .controller('profileController', function ($scope, $http, $location, $rootScope, $route, $timeout, profileService, lastweekService) {

        $scope.message = 'Личный кабинет.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
        $scope.fixturesLoaded = false;

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

        $scope.loadLastTwoWeeks = function () {
            if ($rootScope.currentRole.role != 'ROLE_ADMIN') {
                return;
            }
            lastweekService.getLastTwoWeeks(
                function (fixtures) {
                    $scope.fixturesLoaded = true;
                    $scope.fixtures = fixtures;
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

