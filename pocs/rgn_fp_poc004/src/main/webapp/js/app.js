var taskManagerModule = angular.module('taskManagerApp', ['ngAnimate']);

taskManagerModule.controller('taskManagerController', function($scope, $http){
    var urlBase="";
    $scope.toggle=true;
    $scope.selection=[];
    $scope.statuses = ['ACTIVE','COMPLETED'];
    $scope.priorities = ['HIGH', 'LOW', 'MEDIUM'];
    $http.defaults.headers.post["Content-Type"] = "application/json";

    function findAllTasks(){
        $http.get(urlBase + '/tasks/search/findByTaskArchived?archivedFalse=0')
            .success(function(data){
                if (data._embedded != undefined){
                    $scope.tasks = data._embedded.tasks;
                }else{
                    $scope.tasks = [];
                }
                for(var i = 0; i < $scope.tasks.length; i++){
                    if($scope.tasks[i].taskStatus == 'COMPLETED'){
                        $scope.selection.push($scope.tasks[i]._links.self.href);
                    }
                }
                $scope.taskName="";
                $scope.taskDescription="";
                $scope.taskPriority="";
                $scope.taskStatus="";
                $scope.toggle = '!toggle';
            });
    }
    findAllTasks();

    //add a new task
    $scope.addTask = function addTask(){
         if($scope.taskName=="" || $scope.taskDescription=="" || $scope.taskPriority == "" || $scope.taskStatus == ""){
           alert("Insufficient Data! Please provide values for task name, description, priortiy and status");
         }else{
            $http.post(urlBase + '/tasks', {
                taskName: $scope.taskName,
                taskDescription: $scope.taskDescription,
                taskPriority: $scope.taskPriority,
                taskStatus: $scope.taskStatus
            })
            .success(function(data, status, headers){
                alert("Task added");
                var newTaskUri = headers()["location"];
                console.log("Might be good to GET " + newTaskUri + " and append the task.");
                 // Refetching EVERYTHING every time can get expensive over time
                 // Better solution would be to $http.get(headers()["location"]) and add it to the list
                 findAllTasks();
            });
         }
    };

    //toggleSelection for a given tasks by task id
    $scope.toggleSelection= function toggleSelection(taskUri){
        var idx = $scope.selection.indexOf(taskUri);

        //is currently selectted
        //HTTP PATCH  to ACTIVE state
        if(idx > -1){

            $http({ method: 'PATCH', url: taskUri
                , data: {taskStatus:'ACTIVE'}
                , headers: {'Accept': 'application/json'}});

            findAllTasks();
            $scope.selection.splice(idx, 1);
        } else {
            $http({ method: 'PATCH', url: taskUri
                , data: {taskStatus:'COMPLETED'}
                , headers: {'Accept': 'application/json'}});
            findAllTasks();
            $scope.selection.push(taskUri);
        }

    };

    //Archive COmpleted Tasks
    $scope.archiveTasks = function archiveTasks(){
        $scope.selection.forEach(function(taskUri){
            if(taskUri != undefined){
                $http({ method: 'PATCH', url: taskUri
                    , data: {taskArchived: 1}
                    , headers: {'Accept': 'application/json'}});
            }
        });
        alert("Successfully Archived");
        console.log("It's risky to run this without confirming all the patches are done. when.js is great for that");
        findAllTasks();
    };
});

//Angularjs Directive for confirm dialog box
taskManagerModule.directive('ngConfirmClick', [
function(){
    return {
        link: function (scope, element, attr){
            var msg = attr.ngConfirmClick || "Are you sure?";
            var clickAction = attr.confirmedClick;
            element.bind('click', function (event){
                if (window.confirm(msg)){
                    scope.$eval(clickAction);
                }
            });
        }
    };


}]);