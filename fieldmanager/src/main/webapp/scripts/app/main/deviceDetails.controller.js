'use strict';

angular.module('fieldmanagerApp')
    .controller('DeviceDetailsController', function ($rootScope, $scope, $timeout, Camelm2mShell) {

        $scope.artifactTypes = [
            { label: 'jar', value: 'jar' },
            { label: 'war', value: 'war' }
        ];

        function init() {
            $scope.deployable = {
                groupid: null,
                artifactid: null,
                version: null,
                artifactType: null
            }
        }

        function clearMessages() {
            $scope.deploySuccess = null;
            $scope.deployError = null;
        }

        $rootScope.$on('selectDevice', function(event, args) {
            $scope.device = args;
            if (angular.isUndefined($scope.list[$scope.device.ip])) {
                $scope.list[$scope.device.ip] = [];
            }
            $scope.items = $scope.list[$scope.device.ip];
        });

        $scope.deploy = function() {
            var uri = 'fatjar:mvn:' + $scope.deployable.groupid + '/' + $scope.deployable.artifactid + '/' + $scope.deployable.version + '/' + $scope.deployable.artifactType.value;
            Camelm2mShell.deploy(uri, $scope.device.ip).then(function(success) {
                $scope.deploySuccess = 'Deployed ' + uri;
                $scope.list[$scope.device.ip].push($scope.deployable);
                init();
                $timeout(clearMessages, 4000);
            }, function(err) {
                $scope.deployError = err.statusText;
                init();
                timeout(clearMessages, 4000);
            });
        }

        init();
    });