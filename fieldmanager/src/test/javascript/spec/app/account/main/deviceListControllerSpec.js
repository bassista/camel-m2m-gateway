'use strict';

describe('Controllers Tests ', function () {

    beforeEach(module('fieldmanagerApp'));

    var camelm2mShell, q, $scope;
    var deviceList = [
        {0: "192.168.1.1"},
        {1: "192.168.1.2"}
    ];

    beforeEach(function () {
        camelm2mShell = {
            rpiDevices: function () {
                var deferred = q.defer();
                $scope.devices = deviceList;
                return deferred.promise;
            }
        };
    });

    describe('DeviceListController', function () {

        beforeEach(inject(function ($rootScope, $controller, $q) {
            q = $q;
            $scope = $rootScope.$new();
            $controller('DeviceListController', {$scope: $scope, Camelm2mShell: camelm2mShell});
        }));

        it('should set device list', function () {
            expect($scope.devices).toEqual(deviceList);
        });
    });
});
