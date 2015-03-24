'use strict';

angular.module('fieldmanagerApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
