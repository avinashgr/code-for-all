	iotapp.controller("appController", ["$scope", "$http", function($scope, $http) {
		
		$scope.getUsersFlag == false;
		$scope.whiteId = "";
		$scope.success == false;
		$scope.msg = "";
		$scope.successET == false;
		$scope.msgET = "";
		$scope.etId = "";
		$scope.addAttrFlag == false;
		$scope.addMoreAttrFlag == false;
		$scope.moreAttrs = [{nm:'',dsc:'',wlst:''}];
		$scope.errors1 = [];
		$scope.errors2 = [];
		$scope.errors3 = [];
		$scope.errors4 = [];
		$scope.errors5 = [];
		$scope.errors6 = [];
		$scope.errors7 = [];
		$scope.errors8 = [];
		$scope.errors9 = [];
		$scope.errors10 = [];
		$scope.choices = [];
		$scope.conds = [];
		$scope.addCondsFlag == false;
		$scope.successETP == false;
		$scope.msgETP = "";
		$scope.etpId = "";
		$scope.args = [];
		$scope.addArgsFlag == false;
		$scope.successCT == false;
		$scope.msgCT = "";
		$scope.ctId = "";
		$scope.dtAttTypes = [];
		$scope.dtEvTemps = [];
		$scope.dtCmdTemps = [];
		$scope.dtAttrFlag == false;
		$scope.dtEtFlag == false;
		$scope.dtCTFlag == false;
		$scope.successDT == false;
		$scope.msgDT = "";
		$scope.dtId = "";
		$scope.successD == false;
		$scope.msgD = "";
		$scope.dId = "";
		$scope.successA == false;
		$scope.msgA = "";
		$scope.aId = "";
		$scope.successES == false;
		$scope.msgES = "";
		$scope.esId = "";
		$scope.esName = "";
		$scope.successAETES == false;
		$scope.msgAETES = "";
		$scope.successSTR == false;
		$scope.msgSTR = "";
		$scope.strId = "";
		$scope.successR == false;
		$scope.msgR = "";
		$scope.rId = "";
		$scope.attrNm = "";
		$scope.attrDesc = "";
		$scope.streamConfigData = "";
		$scope.registerStreamMsg = "";
		$scope.successRegSTR== false;
		$scope.failureSTR==false;
	
	$scope.addAttribute = function() {
	$scope.addMoreAttrFlag = true;
	var newItemNo = $scope.moreAttrs.length+1;
	$scope.moreAttrs.push({nm:'',dsc:'',wlst:''});
	}
	$scope.ca = function() {
			$scope.errors1 = [];
			var abc = $scope.moreAttrs.length-1;
			$scope.attrNm = $scope.moreAttrs[abc].nm;
			$scope.attrDesc=$scope.moreAttrs[abc].dsc;
			
			 if ($scope.attrNm === undefined || $scope.attrNm ==="") $scope.errors1.push('Attribute Name is Required')
			 if ($scope.attrDesc === undefined || $scope.attrDesc ==="") $scope.errors1.push('Attribute Description is Required')
			if ($scope.errors1.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/attribute',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name": $scope.attrNm,
					"description": [{
						"lang":"en_us",
						"text": $scope.attrDesc
					}],
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					 "type": "decimal",
					 "active": "true",
					 "frozen": "false",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					//("HlNH57h2X9GlUGWTyvztAsXZGFOAHQnF:LhedhdbgKYWcmZru")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.success = true;
					if(data.id!=undefined){
					$scope.msg = "Attribute is created";
					$scope.moreAttrs[abc].wlst = data.id;
					} else {
					$scope.msg = "Attribute Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors1.push("ERROR -" + error.data.message);
				});
			}
			
		}
		
		$scope.aet = function(abc) {
			$scope.errors2 = [];
			if ($scope.eventTempNm === undefined || $scope.eventTempNm ==="") $scope.errors2.push('Event Template Name is Required')
			 if ($scope.eventTempNmDesc === undefined || $scope.eventTempNmDesc ==="") $scope.errors2.push('Event Template Description is Required')
			 if (abc.length == 0) $scope.errors2.push('Event Field is Required')
			 var attrNotPresent = false;
			 var attrDescNotPresent = false;
			$scope.eventFields = [];
			for (var i in abc){
			 $scope.eventFields.push({name:abc[i].id,type:abc[i].desc});
			 if(abc[i].id=="") attrNotPresent=true;
			 if(abc[i].desc=="") attrDescNotPresent=true;
			}
			if (attrNotPresent){
				$scope.errors2.push ('Event Field Name is Required');
			}
			if (attrDescNotPresent){
			$scope.errors2.push ('Event Field Type is Required');
			}
			if ($scope.errors2.length == 0) {
			
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/eventTemplate',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name": $scope.eventTempNm,
					"description": [{
						"lang":"en_us",
						"text": $scope.eventTempNmDesc
					}],
					"eventFields": $scope.eventFields,
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					 "active": "true",
					 "tags": [ "weather", "conditional" ],
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successET = true;
					if(data.id!=undefined){
					$scope.msgET = "Event Template is created";
					$scope.etId=data.id;
					} else {
					$scope.msgET = "Event Template Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors2.push("ERROR -" + error.data.message);
				});
			}
		}
		
	$scope.addEventFields = function() {
	$scope.addAttrFlag = true;
	var newItemNo = $scope.choices.length+1;
	$scope.choices.push({id:'',desc:''});
	}
	
	$scope.atp = function(abc) {
			$scope.errors3 = [];
			if ($scope.tpNm === undefined || $scope.tpNm ==="") $scope.errors3.push('Threshold Policy Name is Required')
			 if ($scope.eventTempId === undefined || $scope.eventTempId ==="") $scope.errors3.push('Event Template ID is Required')
			 if ($scope.threshDesc === undefined || $scope.threshDesc ==="") $scope.errors3.push('Description is Required')
			 if (abc.length == 0) $scope.errors3.push('Condition is Required')
			 var condBlank = false;
			$scope.all = [];
			for (var i in abc){
			 $scope.all.push({expr:abc[i].expr});
			 if (abc[i].expr=="")
				condBlank = true;
			}
			if (condBlank){
				$scope.errors3.push ('Condition is Required');
			}
			if ($scope.errors3.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/eventThreshold',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name": $scope.tpNm,
						"id": $scope.eventTempId,
					"description": [{
						"lang":"en_us",
						"text": $scope.threshDesc
					}],
					"condition": {
							"all": $scope.all
						},
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successETP = true;
					if(data.id!=undefined){
					$scope.msgETP = "Event Threshold Policy is created";
					$scope.etpId=data.id;
					} else {
					$scope.msgET = "Event Template Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors3.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.addConditions = function() {
		$scope.addCondsFlag = true;
			var newItemNo = $scope.conds.length+1;
			$scope.conds.push({expr:''});
	}
	
	
	$scope.act = function(abc) {
			$scope.errors4 = [];
			$scope.tempArgs = [];
			var argNameMissing =  false;
			var argDescMissing = false;
			var argTypeMissing = false;
			if ($scope.ctNm === undefined || $scope.ctNm ==="") $scope.errors4.push('Command Template Name is Required')
			 if ($scope.ctDesc === undefined || $scope.ctDesc ==="") $scope.errors4.push('Description is Required')
			 if (abc.length == 0) $scope.errors4.push('Argument is Required')
			for (var i in abc){
			 $scope.tempArgs.push({name:abc[i].name,description:[{lang:'en_us',text:abc[i].description}],type:abc[i].type,index:i});
			 if (abc[i].name=="")
				argNameMissing=true;
			 if(abc[i].description=="")
				argDescMissing=true;
			 if(abc[i].type=="")
				argTypeMissing=true;
			}
			if (argNameMissing){$scope.errors4.push('Argument Name is Required')}
			if (argDescMissing){$scope.errors4.push('Argument Description is Required')}
			if (argTypeMissing){$scope.errors4.push('Argument Type is Required')}
			if ($scope.errors4.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/commandTemplate',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name": $scope.ctNm,
					"description": [{
						"lang":"en_us",
						"text": $scope.ctDesc
					}],
					"args": $scope.tempArgs,
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successCT = true;
					if(data.id!=undefined){
					$scope.msgCT = "Command Template is created";
					$scope.ctId=data.id;
					} else {
					$scope.msgCT = "Command Template Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors4.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.addArguments = function() {
		$scope.addArgsFlag = true;
			var newItemNo = $scope.args.length+1;
			$scope.args.push({name:'',description:'',type:'',index:''});
	}
	
	
	$scope.addDtAttributes = function() {
		$scope.dtAttrFlag = true;
			var newItemNo = $scope.dtAttTypes.length+1;
			$scope.dtAttTypes.push({id:''});
	}
	
	$scope.addDtEventTemplates = function() {
		$scope.dtEtFlag = true;
			var newItemNo = $scope.dtEvTemps.length+1;
			$scope.dtEvTemps.push({id:''});
	}
	
	
	$scope.addDtCmdTemplates = function() {
		$scope.dtCTFlag = true;
			var newItemNo = $scope.dtCmdTemps.length+1;
			$scope.dtCmdTemps.push({id:''});
	}

	$scope.adt = function(abc, def, ghi) {
			$scope.errors5 = [];
			var argMissing = false;
			var etMissing = false;
			var ctMissing = false;
			$scope.tempArgs = [];
			$scope.tempEvTemps = [];
			$scope.tempCmdTemps = [];
			if ($scope.dtNm === undefined || $scope.dtNm ==="") $scope.errors5.push('Device Template Name is Required')
			 if ($scope.dtDesc === undefined || $scope.dtDesc ==="") $scope.errors5.push('Description is Required')
			 if (abc.length == 0) $scope.errors5.push('Attribute is Required')
			 if (def.length == 0) $scope.errors5.push('Event Template is Required')
			 if (ghi.length == 0) $scope.errors5.push('Command Template is Required')
			for (var i in abc){
			if (abc[i].id=="") argMissing=true;
			 $scope.tempArgs.push(abc[i].id);
			}
			for (var i in def){
			if (def[i].id=="") etMissing=true;
				$scope.tempEvTemps.push(def[i].id);
			}
			for (var i in ghi){
			if (ghi[i].id=="") ctMissing=true;
				$scope.tempCmdTemps.push(ghi[i].id);
			}
			if (argMissing) $scope.errors5.push('Attribute ID is Required')
			if (etMissing) $scope.errors5.push('Event Template ID is Required')
			if (ctMissing) $scope.errors5.push('Command Template ID is Required')
			if ($scope.errors5.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/deviceTemplate',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name" : [{
						"lang":"en_us",
						"text": $scope.dtNm
					}],
					"description": [{
						"lang":"en_us",
						"text": $scope.dtDesc
					}],
					"attributeTypes": $scope.tempArgs,
					"eventTemplates": $scope.tempEvTemps,
					"commandTemplates": $scope.tempCmdTemps,
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successDT = true;
					if(data.id!=undefined){
					$scope.msgDT = "Device Template is created";
					$scope.dtId=data.id;
					} else {
					$scope.msgDT = "Device Template Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors5.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.ad = function() {
			$scope.errors6 = [];
			if ($scope.dNm === undefined || $scope.dNm ==="") $scope.errors6.push('Device Name is Required')
			 if ($scope.dDesc === undefined || $scope.dDesc ==="") $scope.errors6.push('Description is Required')
			 if ($scope.devTempID === undefined || $scope.devTempID ==="") $scope.errors6.push('Device Template Id is Required')
			if ($scope.errors6.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/device',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name" : [{
						"lang":"en_us",
						"text": $scope.dNm
					}],
					"description": [{
						"lang":"en_us",
						"text": $scope.dDesc
					}],
					"id": $scope.devTempID,
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successD = true;
					if(data.id!=undefined){
					$scope.msgD = "Device  is created";
					$scope.dId=data.id;
					} else {
					$scope.msgD = "Device Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors6.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.aa = function() {
			$scope.errors7 = [];
			if ($scope.aNm === undefined || $scope.aNm ==="") $scope.errors7.push('Application Name is Required')
			 if ($scope.aDesc === undefined || $scope.aDesc ==="") $scope.errors7.push('Description is Required')
			if ($scope.errors7.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/application',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name" : [{
						"lang":"en_us",
						"text": $scope.aNm
					}],
					"description": [{
						"lang":"en_us",
						"text": $scope.aDesc
					}],
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successA = true;
					if(data.id!=undefined){
					$scope.msgA = "Application  is created";
					$scope.aId=data.id;
					} else {
					$scope.msgA = "Application Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors7.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.aes = function() {
			$scope.errors8 = [];
			if ($scope.esNm === undefined || $scope.esNm ==="") $scope.errors8.push('Event Source Name is Required')
			if ($scope.errors8.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/eventSource',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name" : [{
						"lang":"en_us",
						"value": $scope.esNm
					}],
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successES = true;
					if(data.id!=undefined){
					$scope.msgES = "Event Source  is created";
					$scope.esId=data.id;
					$scope.esName=data.name[0].value;
					} else {
					$scope.msgES = "Event Source Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors8.push("ERROR -" + error.data.message);
				});
			}
		}
		
		$scope.aETEs = function() {
			$scope.errors8 = [];
			if ($scope.esDTID === undefined || $scope.esDTID ==="") $scope.errors8.push('Device Template Id is Required')
			if ($scope.esETID === undefined || $scope.esETID ==="") $scope.errors8.push('Event Template Id is Required')
			if ($scope.errors8.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/eventSource',
					dataType: 'json',
					method: 'PUT',
					data: {
					"domainName": window.location.host,
					"name" : [{
						"lang":"en_us",
						"value": $scope.esName
					}],
						"id" : $scope.esId,
					"sourceDeviceTemplates" : [{
						"deviceTemplateId":$scope.esDTID,
						"eventTemplateId": $scope.esETID
					}],	
					"version" : "Ver1",
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successAETES = true;
					$scope.msgAETES = "Assignment to Event Source Successful.";
					
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors8.push("ERROR -" + error.data.message);
				});
			}
		}
		
		
		$scope.addStr = function() {
			$scope.errors9 = [];
			if ($scope.strNm === undefined || $scope.strNm ==="") $scope.errors9.push('Stream Name is Required')
			if ($scope.strType === undefined || $scope.strType ==="") $scope.errors9.push('Stream Type is Required')
			if ($scope.strOid === undefined || $scope.strOid ==="") $scope.errors9.push('Owner Id is Required')
			if ($scope.strPType === undefined || $scope.strPType ==="") $scope.errors9.push('Protocol Type is Required')
			if ($scope.strPSType === undefined || $scope.strPSType ==="") $scope.errors9.push('Protocol Security Type is Required')
			if ($scope.strPLSType === undefined || $scope.strPLSType ==="") $scope.errors9.push('Payload Security Type is Required')
			if ($scope.strPT === undefined || $scope.strPT ==="") $scope.errors9.push('Pulling Threads is Required')
			if ($scope.strST === undefined || $scope.strST ==="") $scope.errors9.push('Sleep Time is Required')
			if ($scope.strQ === undefined || $scope.strQ ==="") $scope.errors9.push('Quota is Required')
			if ($scope.errors9.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/stream',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
						"name" : [{
						"lang":"en_us",
						"text": $scope.strNm
					}],
					"ownerId" : $scope.strOid,
					"streamType": $scope.strType,
					  "protocolType": $scope.strPType,
					  "protocolSecurityType": $scope.strPSType,
					  "ownerSecurityType": "NONE",
					  "payloadSecurityType": $scope.strPLSType,

					  "streamConfiguration": {
						  "pullingThreads": parseInt($scope.strPT),
						  "sleepTime": parseInt($scope.strST),
						  "quota": parseInt($scope.strQ)
					 },
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					
					if(data.id!=undefined){
					$scope.successSTR = true;
					$scope.failureSTR =false;
					$scope.msgSTR = "Stream  is created";
					$scope.strId=data.id;
					$scope.ct=data.consumerTopic;
					$scope.pt=data.producerTopic;
					$scope.act=data.alertConsumerTopic;
					$scope.clId=data.clientId;
					$scope.clPwd=data.password;
					$scope.streamConfigData=data;
					} else {
					$scope.failureSTR =true;
					$scope.successSTR = false;
					$scope.msgSTR = "Stream Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors9.push("ERROR -" + error.data.message);
				}); 
			}
		}
		$scope.regStream = function() {		
		$http({
					url: 'https://device-status-webapp.run.covapp.io/config',
					dataType: 'json',
					method: 'POST',
					data:  $scope.streamConfigData,
					headers: {

					}

				}).success(function(data, message, xhr) {
				$scope.successRegSTR=true;
				 $scope.registerStreamMsg="Stream has been registered successfully.";
					
					
					
					}).error(function(error) {
					$scope.registerStreamMsg="Stream registration failed.";
					alert("ERROR"+error);
					$scope.errors9.push("ERROR -" + error.data.message);
				});
		}
		
		
		
		$scope.addR = function() {
			$scope.errors10 = [];
			if ($scope.rType === undefined || $scope.rType ==="") $scope.errors10.push('Route Type is Required')
			if ($scope.rstrId === undefined || $scope.rstrId ==="") $scope.errors10.push('Stream Id is Required')
			if ($scope.rsid === undefined || $scope.rsid ==="") $scope.errors10.push('Route Source Id is Required')
			if ($scope.rTpic === undefined || $scope.rTpic ==="") $scope.errors10.push('Routing Topic is Required')
			if ($scope.arTpic === undefined || $scope.arTpic ==="") $scope.errors10.push('Alert Routing Topic is Required')
			if ($scope.errors10.length == 0) {
				$http({
					url: 'https://iot-services-webapp.run.covapp.io/route',
					dataType: 'json',
					method: 'POST',
					data: {
					"domainName": window.location.host,
					"routeType" : $scope.rType,
					"streamId": $scope.rstrId,
					  "routeSourceId": $scope.rsid,
					  "routingTopic": $scope.rTpic,
					  "alertRoutingTopic": $scope.arTpic,
					"realm": "COVSMKT-DEMO",
					"creator":"WEBAPP",
					"creatorAppId":"IOT-Service",
					"domainCredentials":encodeURIComponent("CIsA5wHCHYEMlvNmxAso1ZRpMAfgG3Z2:2V1UqmRaYmjKu6Cp")
					},
					headers: {

					}

				}).success(function(data, message, xhr) {
					$scope.successR = true;
					if(data.id!=undefined){
					$scope.msgR = "Route  is created";
					$scope.rId=data.id;
					//$scope.esName=data.name[0].value;
					} else {
					$scope.msgR = "Route Creation Failed";
					}
					}).error(function(error) {
					alert("ERROR"+error);
					$scope.errors10.push("ERROR -" + error.data.message);
				});
			}
		}
		
	}]);