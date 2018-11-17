angular
    .module('signUp')
    .controller('signUpController', function ($scope, $http, $location, $rootScope, $route, mainService, signUpService) {
        $scope.message = 'Зарегистрироваться.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;

        $scope.save = function () {
            if (!$scope.user || !$scope.user.userName || !$scope.user.password || !$scope.user.name || !$scope.user.role || !$scope.user.role.id) {
                alert("Fill all parameters please.<br>" + JSON.stringify($scope.user));
                return;
            }
            signUpService.save($scope.user,
                function (user) {
                    $scope.user = user;
                    alert("Create success!");
                },
                function (error) {
                    $scope.error = error;
                });
        }


    });

