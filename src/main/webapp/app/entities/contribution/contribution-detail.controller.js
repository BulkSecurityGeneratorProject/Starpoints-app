(function() {
    'use strict';

    angular
        .module('starPointsApp')
        .controller('ContributionDetailController', ContributionDetailController);

    ContributionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contribution', 'Activity', 'Community', 'Person'];

    function ContributionDetailController($scope, $rootScope, $stateParams, previousState, entity, Contribution, Activity, Community, Person) {
        var vm = this;

        vm.contribution = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('starPointsApp:contributionUpdate', function(event, result) {
            vm.contribution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
