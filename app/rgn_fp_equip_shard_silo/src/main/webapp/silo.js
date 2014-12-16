//controllers
angular.module("siloApp")
    .constant("siloUrl", "http://localhost:8090/rest/siloes")
    .constant("mySiloUrl", "http://localhost:8090/rest/siloes/1")
    .constant("equipmentUrl", "http://localhost:8090/rest/equipments")
    .controller("mainCtrl"
            ,function($scope, $http, $location, siloUrl, mySiloUrl, equipmentUrl){

        $scope.data = {};

        $http.get(mySiloUrl)
            .success(function(data){
                $scope.data.mySilo = data;
            })
            .error(function(error){
                $scope.data.error = error;
            });

    })
    .controller("siloCtrl"
            ,function($scope, $http, $location, siloUrl, mySiloUrl, equipmentUrl){


    })
    .controller("equipmentCtrl"
            ,function($scope, $http, $location, siloUrl, mySiloUrl, equipmentUrl){

        $http.get(equipmentUrl + "?page=0&size=3")
            .success(function(data){
                $scope.data.equipments = data._embedded.equipments;
                $scope.totalItems = data.page.totalElements;
                $scope.currentPage = data.page.number;

            })
            .error(function(error){
                $scope.data.error = error;
            });


        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;

            console.log('Page set to: ' + $scope.currentPage);

        };

        $scope.pageChanged = function() {
            var pageNo = $scope.currentPage - 1;
            $http.get(equipmentUrl + "?page="+ pageNo + "&size=3")
                .success(function(data){
                    $scope.data.equipments = data._embedded.equipments;
                    $scope.totalItems = data.page.totalElements;
                    $scope.currentPage = data.page.number;

                })
                .error(function(error){
                    $scope.data.error = error;
                });

            console.log('Page changed to: ' + $scope.currentPage);
        };

    })
    ;
