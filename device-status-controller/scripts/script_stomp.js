    // create the controller and inject Angular's $scope
    iotapp.controller('stompController',function($stomp,$scope) {
        // create a message to display in our view
		var greetingText;

		
		
		var isDisconnected=false;

		$scope.connect=function(){
			var appUrl='http://localhost:8080/device-status-webapp/';
			$stomp.connect(appUrl+'stomp/device', {},$scope.callBackForErrors).then(function(){
				console.log('endpoint connected' +'\n');
				isDisconnected = false;
				$stomp.setDebug(function (args) {
				     console.log('initiating stomp' +args+ '\n');
	            });

			});
		}
		$scope.connect();
        $scope.getMessages=function(){
        	if(true==isDisconnected){
        		$scope.connect();
        	}
			$scope.sleep(10000);
			$scope.callBackForErrors=function(){
				console.log('message from connection' + message+'\n');
			};

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
        	if(true==isDisconnected){
        		$scope.connect();
        	}
            var data = {
						message: $scope.stompText,
						appId:$scope.appId,
						publishToTopic: $scope.topicToPost
            };
			console.log('processing stomp message' + '\n');
			$stomp.send("/app/stomp/device/publish", data);
		};
		$scope.stopMessages= function(){
			$stomp.disconnect();
			isDisconnected=true;
			console.log('endpoint disconnected' +'\n');
		}
		$scope.updateGreeting =  function(){
			$scope.stompResponse= greetingText;
		}
		$scope.sleep=function sleep(milliseconds) {
			  var start = new Date().getTime();
			  for (var i = 0; i < 1e7; i++) {
			    if ((new Date().getTime() - start) > milliseconds){
			      break;
			    }
			  }
		}
    });
