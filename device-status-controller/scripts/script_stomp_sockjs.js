    // create the controller and inject Angular's $scope
    iotapp.controller('stompSockJSController',function($scope) {
        // create a message to display in our view
    	var socket;	
    	var stompClient;
		var appUrl='http://localhost:8080/device-status-webapp/';
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
			    console.log("connected"+str);
			}
			$scope.isDisconnected=false;
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
					greetingText = JSON.parse(greeting.body).content;
					 $scope.$apply(function(){
						   $scope.initiator = true;
						   $scope.updateGreeting(false);
					   });
				});
        };

		$scope.processMessage= function(){
        	$scope.connect();
            var data = {
						message: $scope.stompText,
						appId:$scope.appId,
						publishToTopic: $scope.topicToPost
            };
			console.log('processing stomp message' + '\n');			
		};
		$scope.toggle= function(){	
			if(!$scope.isDisconnected){
				stompClient.disconnect();
				$scope.isDisconnected=true;
				console.log('endpoint disconnected' +'\n');
				$scope.toggleButton={name:'Connect'};
			}else{
				$scope.connect();
				$scope.toggleButton={name:'Disconnect'};
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