 iotapp.controller('dialController',function($scope,$http) {
	var url = 'https://device-status-webapp.run.covapp.io/devicelog';
	var appID = '68f54ef5-c9ba-4c5b-9f11-c3754bb1dd2b';
	var topicID = '7c7c81f4-aabb-46f7-a3bf-945df27724b0';

	var humidInterval = 3000;
	var humidIntervalId;
	var tempInterval = 1000;
	var tempIntervalId;

	// ------------------------------
	var humidityGauge = cuiGauge({
		size: 300,
		sizePercent: 100,
		clipWidth: 300,
		clipHeight: 300,

		selector: ".humidity-container",
                pointerColor: '#856F50',
		title: "Humidity",		
		titleFontSize: 20,
		titleY: -50,
		titleColor: '#444',
		tickYMajorStart: 10,
		tickYMinorStart: 10,

		labelFontSize: 15,
		labelY: -110,
		labelFilter: 4,
		labelColor: '#666',
		
		valueFontSize: 35,
		valueSuffix: '%',
		valueY: 70,
		valueTextColor: '#444',

		majorTicks: 45,
		tickY: -150,
		tickMajorMinorFilter: 2,
		tickMajorColor: '#856F50',
		tickMinorColor: '#856F50',

		ringWidth: 20,

		minAngle: -130,
		maxAngle: 130,
		maxValue: 100,

		transitionMs: 25,

		arcBorderColorFn: d3.interpolateHsl(d3.rgb('#856F50'), d3.rgb('#CCC')),
		arcColorFn: d3.interpolateHsl(d3.rgb('#C9C8C1'), d3.rgb('#C9C8C1')),
		minAngleWarn: 78,
		maxAngleWarn: 103,
		arcWarnColorFn: d3.interpolateHsl(d3.rgb('#FFF5B1'), d3.rgb('#FFE433')),
		minAngleError: 105,
		maxAngleError: 130,
		arcErrorColorFn: d3.interpolateHsl(d3.rgb('#FF3A33'), d3.rgb('#FF3A33')),
	});

	function randomHumidData() {
    humidityGauge.update( humidityGauge.randomData() );
	};

	function gotHumidData(url, response) {
		cui.log('gotHumidData', response);

		//var last = response[response.length-1];
		var last = response[0];
		if (last) {
			var contentStr = last.content;
			var contentObj =angular.fromJson(contentStr);;

			var decodedMessage = atob(contentObj.message);
			var dataObj = angular.fromJson(decodedMessage);

			cui.log('humid mess',contentObj.message,decodedMessage,dataObj);

			var val = dataObj.humidity;
			humidityGauge.update(val);							
		}
	}
	function humidData() {
        $http.get(url+'?topic=' + topicID  + '&appId=' + appID).success(function(data){
            gotHumidData(url,data);
        });
	}		

	//humidIntervalId = setInterval(randomHumidData, humidInterval);
	humidIntervalId = setInterval(humidData, humidInterval);
	cui.log('start humid', humidIntervalId);


	// =====================================================
	var temperature = cuiSparklineChart({	
		width: 620,
		height: 300,
		maxX: 110,
		maxY: 80,
		minY: 0,
		tickDuration: tempInterval,
		titleText: "Temperature",
		titleX: 50,
		titleY:70,
		subtitleText: " ",
		subtitleX: 30,
		subtitleY: 60,
		yAxisText: "Fahrenheit",
		yAxisTextX: -170,
		yAxisTextY: 20,
		yAxisTextFontSize: 15,
                yAxisTextColor: "#856F50",
                yAxisTextStyle:"normal",
                buttonTextColor:"#FFFFFF",
                buttonColor:"#856F50",
                buttonWidth:60,
                buttonHeight:30,
                buttonX:40,
		border: " ",
		pathStrokeColor:"#03042B",
                pathStrokeWidth:3,
		focusTextHoverYPos: 14,
                focusLineWidth:1,
                focusTextFontSize:15,
                focusTextFontColor:"Red",
                focusTextFontStyle:"normal"

	});
	
	d3.select(".temperature-container")
  	.data( [0] )
  	.call(temperature);

	function randomTemp() {
    temperature.tick( temperature.randomData() );
	};
	
	function gotTempData(url, response) {
		cui.log('gotTempData', response);
		//var last = response[response.length-1];
		var last = response[0];
		if (last) {
			var contentStr = last.content;
			var contentObj = angular.fromJson(contentStr);

			var decodedMessage = atob(contentObj.message);
			var dataObj = angular.fromJson(decodedMessage);
			
			cui.log('temp mess',contentObj.message,decodedMessage,dataObj);
			
			var val = dataObj.temp;
			temperature.tick(val);
		}
	}
	function tempData() {
        $http.get(url+'?topic=' + topicID  + '&appId=' + appID).success(function(data){
            gotTempData(url,data);
        });
		
	}

	//tempIntervalId = setInterval(randomTemp, tempInterval);
	tempIntervalId = setInterval(tempData, tempInterval);
	cui.log('start', tempIntervalId);

});
