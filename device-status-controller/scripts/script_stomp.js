    // create the controller and inject Angular's $scope
    iotapp.controller('stompController',function($stomp,$scope) {
        // create a message to display in our view
		var greetingText;

		$stomp.connect('http://device-status-webapp.run.covapp.io/stomp/device', {}).then(function(){
			console.log('endpoint connected' + '\n');
			$stomp.setDebug(function (args) {
			     console.log('initiating stomp' + '\n');
            });

		});
        
        $scope.getMessages=function(){
            var data = {
                message: $scope.stompText,
                appId:$scope.appId,
                publishToTopic: $scope.topicToPost
            };
			console.log('processing stomp message' + '\n');
			$stomp.send("/app/stomp/device/subscribe/"+$scope.topicToPost, data);
            var subscription  = $stomp.subscribe('/topic/'+$scope.topicToPost,function(greeting){
				console.log('processing stomp message greeting' + greeting.content);
				greetingText = greeting.content;
				 $scope.$apply(function(){
					   $scope.initiator = true;
					   $scope.updateGreeting();
				   });
			});
        }

		$scope.processMessage= function(){
            var data = {
						message: $scope.stompText,
						appId:$scope.appId,
						publishToTopic: $scope.topicToPost
            };
			console.log('processing stomp message' + '\n');
			$stomp.send("/app/stomp/device/publish", data);
		};

		$scope.updateGreeting =  function(){
			$scope.stompResponse= greetingText;
		}
    });
