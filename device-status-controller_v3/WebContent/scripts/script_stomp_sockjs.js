    // create the controller and inject Angular's $scope
    iotapp.controller('stompSockJSController',function($scope) {
        // create a message to display in our view
    	var socket;	
    	var stompClient;
    	$scope.greetingText;		
		var appUrl='https://device-status-webapp.run.covapp.io/';
//    	var appUrl='https://device-status-webappn.run.covapp.io/';
		$scope.toggleButton={name:'Disconnect'};
		$scope.isDisconnected=false;
		$scope.errorCallback = function(error) {
		      // display the error's message header:
		      alert(error.headers.message);
		};
		$scope.connect=function(){
			console.log('trying to connect');
			socket = new SockJS(appUrl+"stomp/device");
			stompClient = Stomp.over(socket);	
			stompClient.connect();
			stompClient.debug = function(str) {
			    console.log("connected:"+str);
			     if(str.indexOf("Lost connection to")>-1){
			    	 $scope.greetingText = "Unable to connect! Please click on connect and try again.";
			    	 $scope.updateGreeting(true);
			    	 $scope.isDisconnected=true;
			    	 $scope.toggleButton={name:'Connect'};
			     }else if(str.indexOf("connected to server")>-1){
			    	 $scope.isDisconnected=false;
			    	 $scope.toggleButton={name:'Disconnect'};
			    	 $scope.greetingText="Connected to server!";
			    	 $scope.updateGreeting(false);
			    	 $scope.$apply();
			     }
			};
		};
    	$scope.connect();			

        $scope.getMessages=function(){
            var data = {
	                message: $scope.stompText,
	                appId:$scope.appId,
	                publishToTopic: $scope.topicToPost
	            };
                console.log("The data:"+data);
	            stompClient.send("/app/stomp/device/subscribe/"+$scope.topicToPost,{},JSON.stringify(data));
	            stompClient.subscribe('/topic/'+$scope.topicToPost,function(greeting){
					console.log('processing stomp message greeting' + JSON.parse(greeting.body).content);
					$scope.greetingText = JSON.parse(greeting.body).content;
					$scope.$apply(function(){
						   $scope.initiator = true;
						   $scope.updateGreeting(false);
					   });
				});
        };

		$scope.processMessage= function(){
			if(true==$scope.isDisconnected){
				$scope.connect();
			}
            var data = {
						message: $scope.stompText,
						appId:$scope.appId,
						publishToTopic: $scope.topicToPost
            };
            stompClient.send("/app/stomp/device/publish",{},JSON.stringify(data));
			console.log('processing stomp message' + '\n');	
	    	$scope.greetingText="Posted the message to the server";
	    	$scope.updateGreeting(false);
		};
		$scope.toggle= function(){	
			if(!$scope.isDisconnected){
				stompClient.disconnect();
				$scope.isDisconnected=true;
				console.log('endpoint disconnected' +'\n');
				$scope.greetingText=null;
				$scope.updateGreeting(false);
				$scope.toggleButton={name:'Connect'};
			}else{
				$scope.connect();
			}
		}
		$scope.updateGreeting =  function(error){
			$scope.stompResponse= $scope.greetingText;
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
