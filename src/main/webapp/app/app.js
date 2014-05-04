var myApp = angular.module('myApp', []);
var myApp = angular.module('myApp', [
	'ngRoute',
	'MyAppControllers'
]);

myApp.config([
	'$routeProvider',
	function($routeProvider) {
		$routeProvider.
			when('/home', {
				templateUrl: 'home.html',
		        controller: 'HomeController'
			}).
			when('/detail/:itemId', {
				templateUrl: 'detail.html',
				controller: 'DetailController'
			}).
			when('/list/:itemId', {
				templateUrl: 'list.html',
				controller: 'ListController'
			}).
			otherwise({redirectTo: '/home'});
	}
]);
                    
