angular
    .module('signIn')
    .controller('signInController', function ($scope, $http, $location, $rootScope, $route, mainService, $window, signInService) {
        $scope.message = 'Войти.';
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;

        $scope.vm = {};
        $scope.vm.login = $scope.login;

        $window.localStorage.setItem('token', '');

        $scope.login = function() {
            $http({
                url: 'http://localhost:8080/login',
                method: "POST",
                data: {
                    'userName': $scope.vm.username,
                    'password': $scope.vm.password
                }
            }).then(function (response) {
                console.log("response=" + JSON.stringify(response));
                if (response.data) {
                    var token
                        = $window.btoa($scope.vm.username + ':' + $scope.vm.password);
                    var userData = {
                        userName: $scope.vm.username,
                        authData: token
                    }
                    $window.sessionStorage.setItem(
                        'userData', JSON.stringify(userData)
                    );
                    $http.defaults.headers.common['Authorization']
                        = 'Basic ' + token;
                    console.log("Autorized");
                    console.log("userData = " + JSON.stringify(userData));
                    $location.path('/main');
                } else {
                    alert("Authentication failed.")
                }
            }, function errorCallback(response) {
                alert("Authentication failed.");
                console.log("error response=" + JSON.stringify(response));
            });
        };

        $scope.logout = function() {
            $window.sessionStorage.setItem(
                'userData', {}
            );
            $http.defaults.headers.common['Authorization'] = undefined;
            //$rootScope.currentRole = 'ROLE_ANONYMOUS';

            signInService.logout(
                function (role) {
                    console.log("ROLE = " + role);
                    $rootScope.currentRole = role;
                },
                function (error) {
                    $rootScope.currentRole = 'ROLE_ANONYMOUS';
                    console.error(error);
                });
        }

    });

