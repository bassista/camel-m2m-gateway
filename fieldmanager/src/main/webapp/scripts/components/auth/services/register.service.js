'use strict';

angular.module('fieldmanagerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {});
    });


