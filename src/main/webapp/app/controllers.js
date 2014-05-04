var MyAppControllers = angular.module('MyAppControllers', []);

MyAppControllers.controller('MenuListController', ['$scope', '$http','$location',
	function($scope, $http, $location){
		$scope.activeId = 0;
		$scope.menuList = [{id:0,name: '首页'},{id:9,name: '我的博客'},{id:1,name: '科技动态'}];
		$scope.onClickMenu = function(menu) {
			$scope.activeId = menu.id;
			
			if(menu.id === 0) {
				$location.path('/home');
			}
			else {
				$location.path('/list/' + menu.id);
			}
			
			//$scope.$root.$broadcast("onClickMenu", menu);//触发事件
			return false;
		};
		
		$scope.navClass = function(id) {
			return id === $scope.activeId ? 'active' : '';
		};
		
		$scope.onClickSysName = function() {
			$scope.activeId = 0;
		}
	}
]);

MyAppControllers.controller('HomeController', ['$scope','sideListService','$location',function($scope, sideListService, $location) {
	$scope.myValue = true;
	sideListService.getSideList(function(data){
		$scope.sideList = data.sides;
	});
//	$scope.$on("onClickMenu", function (event, menu) {//监听事件
//	});
}]);

MyAppControllers.controller('DetailController', ['$scope','$http','$sce','$routeParams',function($scope,$http,$sce,$routeParams) {
	$http({
		method: 'GET', 
		url: '../front/cc/dtailN.sdo',
		params: {itemId: $routeParams.itemId}
	}).success(function(data){
		$scope.detail = $sce.trustAsHtml(data.detail);
	});
}]);

MyAppControllers.controller('ListController', ['$scope','$http','$sce','$routeParams',function($scope,$http,$sce,$routeParams) {
	$scope.limit = 20;
	$scope.nowPage = 0;
	$http({
		method: 'GET', 
		url: '../front/cc/getContentItemList.sdo',
		params: {contentId: $routeParams.itemId,'extLimit.start':0,'extLimit.limit':$scope.limit,'extLimit.sort':'itemOrder','extLimit.dir':'DESC'}
	}).success(function(data){
		$scope.data = data;
		
		//计算页数
//		$scope.total

		var pageCount = 0;
		pageCount = $scope.data.total / $scope.limit;
		var mod = $scope.data.total % $scope.limit;
		if(mod > 0) {
			pageCount ++;
		}

		//以nowpage为中心,两侧各取5个
		var centerPage = $scope.nowPage;
		if(centerPage - 5 < 0) {

		}

		$scope.pageData = {count: pageCount, nowPage: $scope.nowPage, pages: [1,2,3,4,5,6,7,8,9,10]};

	});

	$scope.onClickPage = function(page) {
		alert('page');
	}
}]);

