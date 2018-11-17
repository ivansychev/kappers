angular
    .module('signUp')
    .controller('signUpController', function ($scope, $http, $location, $rootScope, $route, mainService, signUpService, roleService, $timeout) {
        $scope.message = 'Зарегистрироваться.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;

        $scope.getAllRoles = function () {
            roleService.getAll(
                function (roles) {
                    $scope.roles = roles;
                },
                function (error) {
                    $scope.error = error;
                });
        }

        $timeout(function () {
            $scope.getAllRoles();
        });

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

