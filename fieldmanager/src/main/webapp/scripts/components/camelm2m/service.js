'use strict';

angular.module('fieldmanagerApp')
    .factory('Camelm2mShell', function ($http, DETECT_RPI_DEVICES_URL, DEPLOY) {
        return {
            rpiDevices: function() {
                return $http.get(DETECT_RPI_DEVICES_URL).then(function(response) {
                    var devices = [];
                    angular.forEach(response.data, function(value) {
                        var rpi = {};
                        rpi.ip = value;
                        rpi.active = true;

                        this.push(rpi);
                    }, devices);
                    return devices;
                });
            },

            deploy: function(_uri, _ip) {
                var data = {}
                _uri = _uri + '?ssh-host=' + _ip + '&ssh-user=pi&ssh-password=raspberry';
                data.uri = _uri
                return $http.post(DEPLOY, data);
            }
        };
    });