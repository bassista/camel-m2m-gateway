'use strict';

angular.module('fieldmanagerApp')
    .controller('MainController', function ($scope, $interval, Camelm2mShell, UPDATE_INTERVAL) {

        $scope.searching = true;
        $scope.notFound = false;

        function update() {
            Camelm2mShell.rpiDevices().then(function(devices) {
                $scope.devices = devices;
                $scope.searching = false;

                if (devices.length === 0) {
                    $scope.notFound = true;
                }
            });
        }

        $interval(update, UPDATE_INTERVAL);
        update();
    });
