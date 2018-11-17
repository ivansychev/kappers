var kappersApp = angular.module('kappersApp',
    [
        'ngRoute'
        , 'ngResource'
        , 'ngAnimate'
        , 'main'
        , 'about'
        , 'contacts'
        , 'profile'
        , 'signIn'
        , 'signUp'
        , 'role'
    ]
);

kappersApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: './ui/view/main/main.html',
            controller: 'mainController'
        })
        .otherwise({
            templateUrl: './ui/view/404.html'
        })
    ;
});

kappersApp.run(['$rootScope', '$location', '$window', 'signInService', '$http', function ($rootScope, $location, $window, signInService, $http) {
    $rootScope.routeTo = function (path) {
        switch (path) {
            case '/':
                $rootScope.currentNavigation = 'main';
                break;
            case '/about':
                $rootScope.currentNavigation = 'about';
                break;
            case '/contacts':
                $rootScope.currentNavigation = 'contacts';
                break;
            case '/profile':
                $rootScope.currentNavigation = 'profile';
                break;
            case '/sign-in':
                $rootScope.currentNavigation = 'sign-in';
                break;
            case '/sign-up':
                $rootScope.currentNavigation = 'sign-up';
                break;
            default :
                path = '404';
                $rootScope.currentNavigation = '404';
        }
        $location.path(path).search({});
    }

    $rootScope.currentPage = 'main';

    $rootScope.DEFAULT_STATES = [STATE.ACTIVE, STATE.DELETED];

    $rootScope.stateFilter = function (entity) {
        if (entity && entity.state == STATE.ACTIVE) {
            return true;
        }
        return false;
    }

    $rootScope.toInt = function (id) {
        return parseInt(id, 10);
    };

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // var restrictedPage
        //     = $.inArray($location.path(), ['/login']) === -1;
        var loggedIn = $window.sessionStorage.getItem('userData');
        console.log("userData = " + JSON.stringify(loggedIn));
        signInService.getUserRole(
            function (role) {
                console.log("ROLE = " + JSON.stringify(role));
                $rootScope.currentRole = role;
            },
            function (error) {
                $rootScope.currentRole = {role : 'ROLE_ANONYMOUS'};
                console.error(error);
            });
        // if (!loggedIn) {
        //     $location.path('/sign-in');
        // }
    });

    $rootScope.logout = function() {
        $window.sessionStorage.setItem(
            'userData', {}
        );
        $http.defaults.headers.common['Authorization'] = undefined;
        //$rootScope.currentRole = 'ROLE_ANONYMOUS';

        signInService.logout(
            function (role) {
                console.log("ROLE = " + role);
                $rootScope.currentRole = role;
                $rootScope.routeTo("/");
            },
            function (error) {
                console.error(error);
                $rootScope.currentRole = {role : 'ROLE_ANONYMOUS'};
                $rootScope.routeTo("/");
            });
    }

    $rootScope.currentRole = {role : 'ROLE_ANONYMOUS'};
    $rootScope.routeTo("/");
}]);


kappersApp.directive('restrictRole', function($log, $rootScope) {
    return {
        restrict: 'A',
        link: function(scope, element, attr){
            //console.log("attr=" + JSON.stringify(attr.restrictRole));
            if (attr.restrictRole) {
                var roles = attr.restrictRole.split(",");
                if (roles.indexOf($rootScope.currentRole.role) < 0) {
                    element.css({
                        display : 'none'
                    });
                }
            }
        }
    }
});