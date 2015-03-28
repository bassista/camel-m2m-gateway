'use strict';

angular.module('fieldmanagerApp')
    .factory('Camelm2mShell', function ($http, DETECT_RPI_DEVICES_URL, DEPLOY) {
        return {
            rpiDevices: function() {
                return $http.get(DETECT_RPI_DEVICES_URL).then(function(response) {
                    var devices = [];
                    angular.forEach(response.data, function(value) {
                        var rpi = {};
                        rpi.image = 'http://www.vclouds.nl/wp-content/uploads/2012/07/Raspberry-Pi-Logo_thumb.jpg';
                        rpi.ip = value;
                        rpi.active = true;

                        this.push(rpi);
                    }, devices);
                    return devices;
                });
            },

            deploy: function(uri) {
                return $http.post(uri, DEPLOY);
            }
        };
    });