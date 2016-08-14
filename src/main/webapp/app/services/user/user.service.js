(function () {
    'use strict';

    angular
        .module('starPointsApp')
        .factory('User', User);

    User.$inject = ['$resource', 'DateUtils'];

    function User ($resource, DateUtils) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.entryDate = DateUtils.convertLocalDateFromServer(data.entryDate);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
})();
