'use strict';

angular.module('fieldmanagerApp')
    .controller('DeviceDetailsController', function ($rootScope, $scope, Camelm2mShell) {

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

        $rootScope.$on('selectDevice', function(event, args) {
            $scope.device = args;
            if (angular.isUndefined($scope.list[$scope.device.ip])) {
                $scope.list[$scope.device.ip] = [];
            }
            $scope.items = $scope.list[$scope.device.ip];
        });

        $scope.deploy = function() {
            var uri = 'fatjar:mvn:' + $scope.deployable.groupid + '/' + $scope.deployable.artifactid + '/' + $scope.deployable.version + '/' + $scope.deployable.artifactType.value;
            Camelm2mShell.deploy(uri).then(function(success) {
                $scope.deploySuccess = 'Deployed ' + uri;
            }, function(err) {
                $scope.deployError = err;
            });


            $scope.list[$scope.device.ip].push($scope.deployable);
            init();
        }

        init();
    });