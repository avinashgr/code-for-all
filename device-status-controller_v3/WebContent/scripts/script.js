// script.js

  // create the module and name it samlApp
  var iotapp = angular.module('iotapp', ['ngStomp','ngMaterial']);

  iotapp.controller('MainController',['$scope','$mdSidenav', '$mdBottomSheet', '$log', '$q', MainController]);

  function MainController($scope, $mdSidenav, $mdBottomSheet, $log, $q){
        var self=this;
        self.toggleList   = toggleMenu;
        self.itemId = 1;

        self.setItem = function (itemId) {
            this.itemId = itemId;
        };

        self.isSet = function (itemId) {            
            return this.itemId === itemId;
        };
        /**
         * First hide the bottomsheet IF visible, then
         * hide or Show the 'left' sideNav area
         */
        function toggleMenu() {
          var pending = $mdBottomSheet.hide() || $q.when(true);

          pending.then(function(){
            $mdSidenav('left').toggle();
          });
        }
    }
