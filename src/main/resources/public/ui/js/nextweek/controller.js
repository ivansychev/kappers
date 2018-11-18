angular
    .module('nextweek')
    .controller('nextweekController', function ($scope, $http, $location, $rootScope, $route, mainService, nextweekService, $timeout) {
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
        $scope.search = {};
        $scope.fixtures = [];

        $scope.load = function() {
            nextweekService.getAll(
                function (fixtures) {
                    $scope.fixtures = fixtures;
                },
                function (error) {
                    alert("Error occured.");
                    $scope.error = error;
                });
        }

        $scope.loadCard = function () {
            if (($scope.mode == 'view' || $scope.mode == 'edit') && $scope.params.id) {
                nextweekService.getById($scope.params.id,
                    function (fixture) {
                        $scope.fixture = fixture;
                    },
                    function (error) {
                        alert("Error occured.");
                        $scope.error = error;
                    });
            }
        }

        $timeout(function(){
            $scope.load();
            $scope.loadCard();
        });

        $scope.clearSearch = function () {
            $scope.search = {};
        }

        $scope.card = function (mode, id) {
            $location.path("/nextweek/card").search({mode: mode, id: id});
        }
    });

