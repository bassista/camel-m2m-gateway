'use strict';

describe('Services Tests ', function () {

    beforeEach(module('fieldmanagerApp'));

    describe('Camelm2mShell', function () {
        var mockBackend, camelm2mShell, deviceList;

        beforeEach(inject(function ($injector, Camelm2mShell) {
            mockBackend = $injector.get('$httpBackend');

            camelm2mShell = Camelm2mShell;
            deviceList = {};
            deviceList.data = [
                {0: "192.168.1.1"},
                {1: "192.168.1.2"}
            ];

            mockBackend.expectGET('/detect-rpi-devices').respond(deviceList);
            mockBackend.expectGET('api/account').respond(deviceList);
            mockBackend.expectGET('i18n/en/global.json').respond(200, '');
            mockBackend.expectGET('i18n/en/language.json').respond(200, '');
            mockBackend.expectGET('scripts/components/navbar/navbar.html').respond({});
            mockBackend.expectGET('i18n/en/global.json').respond(200, '');
            mockBackend.expectGET('i18n/en/language.json').respond(200, '');
            mockBackend.expectGET('i18n/en/main.json').respond(200, '');
            mockBackend.expectGET('scripts/app/main/main.html').respond({});
        }));

        //make sure no expectations were missed in your tests.
        //(e.g. expectGET or expectPOST)
        afterEach(function () {
            mockBackend.verifyNoOutstandingExpectation();
            mockBackend.verifyNoOutstandingRequest();
        });

        it('should call backend to get device list', function () {
            //WHEN
            var devices = {};

            camelm2mShell.rpiDevices().then(function(response) {
                devices = response;
            });

            mockBackend.flush();

            //THEN
            expect(devices[0].ip).toEqual(deviceList.data);
        });

    });
});
