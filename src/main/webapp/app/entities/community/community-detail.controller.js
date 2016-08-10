(function() {
    'use strict';

    angular
        .module('starPointsApp')
        .controller('CommunityDetailController', CommunityDetailController);

    CommunityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Community', 'Person'];

    function CommunityDetailController($scope, $rootScope, $stateParams, previousState, entity, Community, Person) {
        var vm = this;

        vm.community = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('starPointsApp:communityUpdate', function(event, result) {
            vm.community = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
