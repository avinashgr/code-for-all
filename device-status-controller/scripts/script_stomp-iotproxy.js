// script.js

    // create the module and name it stompTester
    var stompApp = angular.module('stompTester',['ngStomp']);

    // create the controller and inject Angular's $scope
    stompApp.controller('stompController',function($stomp,$scope) {
        // create a message to display in our view
		var greetingText;

		$stomp.connect('http://iot-proxy.demo.covisintrnd.com:8080/device-status-webapp/stomp/device', {}).then(function(){
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
			$stomp.send("http://iot-proxy.demo.covisintrnd.com:8080/device-status-webapp/app/stomp/device/subscribe/"+$scope.topicToPost, data);
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
			$stomp.send("http://iot-proxy.demo.covisintrnd.com:8080/device-status-webapp/app/stomp/device/publish", data);
		};

		$scope.updateGreeting =  function(){
			$scope.stompResponse= greetingText;
		}



    });
