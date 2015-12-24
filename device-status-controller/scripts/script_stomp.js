    // create the controller and inject Angular's $scope
    iotapp.controller('stompController',function($stomp,$scope) {
        // create a message to display in our view
		var greetingText;		
		var appUrl='http://localhost:8080/device-status-webapp/';
		$scope.toggleButton={name:'Disconnect'};
		$scope.isDisconnected=false;

		$scope.connect=function(){
			console.log('trying to connect');

			$stomp.connect(appUrl+'stomp/device', {},$scope.callBackForErrors).then(function(){
				console.log('endpoint connected' +'\n');
				$stomp.setDebug(function (args) {
				     console.log('initiating stomp:' +args+ '\n');
				     if(args.indexOf("Lost connection to")>-1){
				    	 greetingText = "Unable to connect! Please click on connect and try again.";
				    	 $scope.updateGreeting(true);
				    	 $scope.isDisconnected=true;
				    	 $scope.toggleButton={name:'Connect'};
				     }else if(args.indexOf("connected to server")>-1){
				    	 $scope.isDisconnected=false;
				    	 $scope.toggleButton={name:'Disconnect'};
				    	 greetingText="Success! Connected to server!";
				    	 $scope.updateGreeting(false);
				     }
	            });
				
			});
		}
		$scope.connect();	
		
		$scope.callBackForErrors=function(){
				console.log('message from connection:' + message+'\n');
		};
        $scope.getMessages=function(){
        	console.log('Connection status:'+$scope.isDisconnected);
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
					   $scope.updateGreeting(false);
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
		$scope.toggle= function(){	
			if(!$scope.isDisconnected){
				$stomp.disconnect();
				$scope.isDisconnected=true;
				console.log('endpoint disconnected' +'\n');
				$scope.toggleButton={name:'Connect'};
			}else{
				$scope.connect();
			}
		}
		$scope.updateGreeting =  function(error){
			$scope.stompResponse= greetingText;
			$scope.stompError=error;
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
