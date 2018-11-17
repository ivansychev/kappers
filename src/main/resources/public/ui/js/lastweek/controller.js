angular
    .module('lastweek')
    .controller('lastweekController', function ($scope, $http, $location, $rootScope, $route, mainService, lastweekService, $timeout) {
        $scope.params = $location.search();
        $scope.mode = $scope.params.mode;
        $scope.search = {};
        $scope.fixtures = [];
        $scope.fixture = {};

        $scope.load = function () {
            lastweekService.getAll(
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
                lastweekService.getById($scope.params.id,
                    function (fixture) {
                        $scope.fixture = fixture;
                    },
                    function (error) {
                        alert("Error occured.");
                        $scope.error = error;
                    });
            }
        }

        $timeout(function () {
            $scope.load();
            $scope.loadCard();
        });

        $scope.clearSearch = function () {
            $scope.search = {};
        }

        $scope.card = function (mode, id) {
            $location.path("/lastweek/card").search({mode: mode, id: id});
        }
    });

