myApp.service( 'sideListService', [ '$rootScope','$http', function( $rootScope,  $http) {
	return {
		getSideList: function(callback) {
			$http.get('../front/cc/getleftSide.sdo?cids=9,7,8,1').success(function(data){
				callback(data);
			});
		}
	}
}]);