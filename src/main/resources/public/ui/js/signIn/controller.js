angular
    .module('signIn')
    .controller('signInController', function ($scope, $http, $location, $rootScope, $route, mainService, $window) {
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
            });
        };


    });

