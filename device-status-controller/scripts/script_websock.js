// script.js

    // create the module and name it stompTester
    var stompApp = angular.module('stompTester',['ngStomp']);


    // create the controller and inject Angular's $scope
    stompApp.controller('stompController',function($stomp,$scope) {
        // create a message to display in our view
		var greetingText;
        var config ={
			headers:{
				'CommandType':'CreateTask'
			}
		};
            var TestWebSocket = {
                socket: null,
                connect: (function() {
                    var host = 'ws://' + window.location.host + '/device-status-webapp/getstatus';
                    if ('WebSocket' in window) {
                        TestWebSocket.socket = new WebSocket(host);
                    } else if ('MozWebSocket' in window) {
                        TestWebSocket.socket = new MozWebSocket(host);
                    } else {
                        alert('Error: WebSocket is not supported by this browser.');
                        return;
                    }
                    // process message from server
                    TestWebSocket.socket.onmessage = function (message) {
                        document.getElementById('content').innerHTML = message.data;
                    };
                    /* optional
                    // do something while onopen/onclose if needed
                    TestWebSocket.socket.onopen = function () {
                        alert('WebSocket opened.');
                    };
                    TestWebSocket.socket.onclose = function () {
                        alert('WebSocket closed.');
                    };
                    */
                }),
                // send message to server
                trigger: (function() {
                    TestWebSocket.socket.send('trigger');
                }),
            };

        TestWebSocket.connect();

		$scope.processMessage= function(){
			var data = { name: $scope.stompText};
			console.log('processing stomp message' + '\n');

		};

		$scope.updateGreeting =  function(){
			$scope.stompResponse= greetingText;
		}



    });
