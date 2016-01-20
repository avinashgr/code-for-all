
    // create the controller and inject Angular's $scope
    iotapp.controller('ajaxController',function($scope,$http, $timeout) {
		// create a message to display in our view
		var greetingText;
		var appUrl='https://device-status-webapp.run.covapp.io/';
		$scope.value = 1;
		var config ={
		};
		var timer;
		var polling = function() {
			var value = $http({
				method : 'GET',
				url : appUrl+'devicelog?topic='+encodeURIComponent($scope.topicToPost)+'&appId='+encodeURIComponent($scope.appId)
			});

			value.success(function(data, status, headers, config) {
				greetingText = data;
				$scope.updateGreeting();
			});

			timer = $timeout(function() {
				$scope.value++;
				polling();
			}, 1000);
		};



		$scope.getMessages= function(){
			polling();
		};
		$scope.postMessage=function(){
			var data = {
						message: $scope.ajaxText,
						appId:$scope.appId,
						publishToTopic: $scope.topicToPost
						};
			console.log('processing ajax message' + '\n');
			$http.post(appUrl+'publish',data).success(function(data){
				console.log('posted ajax message' + '\n');
			});
		};
        $scope.stopMessageLogs=function(){
			var data = {
						publishToTopic: encodeURIComponent($scope.topicToPost)
				};
//      	$http.post('http://localhost:8080/device-status-webapp//stopLog',data).success(function(data){
//						console.log('posted stop message' + '\n');
//			});
        }

		$scope.stopMessages= function(){
		    greetingText="";
		    $timeout.cancel(timer);
		    $scope.stopMessageLogs();
		    $scope.updateGreeting();

		}
		$scope.updateGreeting =  function(){
			$scope.ajaxResponse= greetingText;
		}

    });