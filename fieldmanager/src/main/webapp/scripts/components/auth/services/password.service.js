'use strict';

angular.module('fieldmanagerApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {});
    });
