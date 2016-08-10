(function() {
    'use strict';

    angular
        .module('starPointsApp')
        .controller('ContributionDetailController', ContributionDetailController);

    ContributionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Contribution', 'Activity', 'Community', 'User'];

    function ContributionDetailController($scope, $rootScope, $stateParams, entity, Contribution, Activity, Community, User) {
        var vm = this;

        vm.contribution = entity;

        var unsubscribe = $rootScope.$on('starPointsApp:contributionUpdate', function(event, result) {
            vm.contribution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
