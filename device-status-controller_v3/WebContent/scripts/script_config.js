
    // create the controller and inject Angular's $scope
    iotapp.controller('configController',function($scope,$http, $timeout) {
		// create a message to display in our view
    	$scope.greetingText;
    	var appUrl='http://localhost:8080/';
		$scope.value = 1;
		var config ={
		};
		var timer;
		var getStreamConfig = function() {
			var value = $http({
				method : 'GET',
				url : appUrl+'config?streamId='+encodeURIComponent($scope.appId)
			});

			value.success(function(data, status, headers, config) {
				$scope.greetingText = data;
				$scope.updateGreeting(false);
			});

		};
		var deleteStreamConfig = function() {
			var value = $http({
				method : 'DELETE',
				url : appUrl+'config?streamId='+encodeURIComponent($scope.appId)
			});

			value.success(function(data, status, headers, config) {
				$scope.greetingText = data;
				$scope.updateGreeting(false);
			});

		};



		$scope.getConfig= function(){
			getStreamConfig();
		};
		$scope.postConfig=function(){
			var data = $scope.ajaxText;
			console.log('processing config create' + '\n');
			$http.post(appUrl+'config',data).success(function(data){
				$scope.greetingText = data;
				$scope.updateGreeting(false);
				console.log('posted config to server' + '\n');
			}).error(function(data){
				$scope.greetingText = data.statusCode;
				$scope.updateGreeting(true);
				console.log('failed to load config to server' + '\n');
			});
		};


		$scope.deleteConfig= function(){
		    greetingText="";
		    deleteStreamConfig();
		}
		$scope.updateGreeting =  function(error){
			$scope.stompResponse= $scope.greetingText;
			$scope.stompError=error;
		}
		
    });