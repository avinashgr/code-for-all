// script.js

    // create the module and name it samlApp
    var samlApp = angular.module('samlApp', []);

    samlApp.controller('TabController', function (){
        this.tab = 1;

        this.setTab = function (tabId) {
            this.tab = tabId;
        };

        this.isSet = function (tabId) {
            return this.tab === tabId;
        };
    });

    // create the controller and inject Angular's $scope
    samlApp.controller('mainController', function($scope,$http,$window) {
        // create a message to display in our view
        var config = {
			headers:{
				'CommandType':'CreateTask'
			}
		}
		$scope.encryptionAlgValues = [
		  { id: 'aes128-cbc', name: 'AES128-CBC'},
		  { id: 'aes256-cbc', name: 'AES256-CBC'}
		 ];

		$scope.signingAlgValues = [
		  { id: 'rsa-sha1', name: 'RSA-SHA1'},
		  { id: 'sha1', name: 'SHA1'}
		 ];


		var postSamlPopUp;

		$scope.createCert= function(){
			    var data = {'subjectDN':$scope.subjectDN,'certficateBeginDate':$scope.certificateBeginDate,'certificateEndDate':$scope.certificateEndDate};
			    alert($scope.subjectDN);
			    alert($scope.certificateBeginDate);
			    alert($scope.certificateEndDate);
				$http.post('/saml-webapp/cert/create',data,config).success(function(data){
				    $scope.message = 'The certificate creation status:'+data.status;
			});
		};

		$scope.createSaml= function(){
			    var data = {
					'entityId':$scope.samlProducer,
					'subjectId':$scope.samlConsumer,
					'samlConsumer':$scope.samlConsumer,
					'assertionSigningEnabled':$scope.assertionSigningEnabled,
					'responseSigningEnabled':$scope.responseSigningEnabled,
					'encryptionEnabled':$scope.encryptionEnabled,
					'samlxml':$scope.samlxml,
					'encryptionAlgorithm':$scope.encryptionAlg.id,
					'digestAlgorithm':$scope.signingAlg.id,
					'attributes':$scope.choices};
				$http.post('/saml-webapp/saml/produce',data,config).success(function(data){
				$scope.samlresult = 'The saml creation status:'+data.status;
				$scope.samlxml = data.samlResponse;


			});
		};

		$scope.postSamlToConsumer= function(){
			postSamlPopUp = $window.open("saml_post.html", "_blank", "width=800,height=600");
			setTimeout(function(){
				postSamlPopUp.document.getElementById("samlxml").value = $scope.samlxml;
				postSamlPopUp.document.getElementById("samlConsumerUrl").value = $scope.samlConsumerUrl;
				postSamlPopUp.document.getElementById("samlPostForm").action=$scope.samlConsumerUrl;
				postSamlPopUp.document.getElementById("samlPostForm").method="POST";
				postSamlPopUp.document.getElementById("samlResponse").value=btoa($scope.samlxml);
				postSamlPopUp.document.getElementById("samlRequest").value=btoa($scope.samlxml);
			}, 1000);

		};

		$scope.xmlInNewWindow=function(){
			$window.open("data:application/xml,"+ encodeURIComponent($scope.samlxml), "_blank", "width=800,height=600");
		};

		$scope.postSaml= function(){
						alert($scope.samlConsumerUrl);
						alert($scope.samlxml);
					    var data = {
							'samlResponse':$scope.samlxml};
						$http.post($scope.samlConsumerUrl,data,config).success(function(data){
						$scope.samlresult = 'The saml creation status:'+data.status;
						$scope.samlxml = data.samlResponse;
					});
		};

		  $scope.choices = [{id: 'choice1'}];

		  $scope.addNewChoice = function() {
			var newItemNo = $scope.choices.length+1;
			$scope.choices.push({'id':'choice'+newItemNo});
		  };

		  $scope.removeChoice = function() {
			var lastItem = $scope.choices.length-1;
			$scope.choices.splice(lastItem);
		  };


    });
