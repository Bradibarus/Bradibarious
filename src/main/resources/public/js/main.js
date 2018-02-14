var app = angular.module("bradibatious", ['ngCookies']);
app.controller("LoginContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    if($cookies.get("access_token")){
        window.location.href = "app";
        console.log("redirect to app")
    }


    $scope.userId = 'jhoeller';
    $scope.password = '1337password';
    $scope.submit = function () {
        console.log('entering submit');
        tokenRequestBody = {
            'client_id': 'client',
            'username' : $scope.userId,
            'grant_type' : 'password',
            'password' : $scope.password
        }

        var authCode = window.btoa("client:1337password")
        authHeader = { 'Authorization':'Basic ' + authCode, 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'};
        console.log(authHeader);
        $http({
            method: 'POST',
            url: '/oauth/token',
            headers: authHeader,
            data: $httpParamSerializer(tokenRequestBody)
        }).then(function(responseData){
            console.log(responseData);
            $http.defaults.headers.common.Authorization =
                'Bearer ' + responseData.data.access_token;
            $cookies.put("access_token", responseData.data.access_token);
            console.log($cookies.get("access_token"));
            window.location.href="app";
        });
        $scope.userId = '';
        $scope.password = '';
    }
})



app.controller("AppContr", function ($scope, $http, $cookies) {
    if($cookies.get("access_token")){
        $http.defaults.headers.common.Authorization =
            'Bearer ' + $cookies.get("access_token");
    } else{
        window.location.href = "login";
    }

    getUsername = function () {
        $http.get('api/account')
            .then(function(responseData){
                $scope.username = responseData.data.username;
            })
    }
    getUsername();

    $scope.terms = [];
    getTerms = function() {
        $http.get('api/terms')
            .then(function (responseData) {
            $scope.terms = responseData.data._embedded.termResourceList
        })
            .catch(function(error){
                //logout();
            })

        }
    getTerms();
    $scope.submit = function () {
        term = {'word1': $scope.word1, 'word2': ''};
        $http.post('api/terms/', term).then(function (responseData) {
            console.log(responseData);
            $scope.terms.push(responseData.data);
            console.log($scope.terms);
        });
        $scope.word1 = '';
        }

    logout = function () {
        $cookies.remove("access_token");
        window.location.href = "login";
        }
    }
)

app.controller("SignupContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    if($cookies.get("access_token")){
        window.location.href = "app";
        console.log("redirect to app")
    }

    $scope.userId = 'bradibarus';
    $scope.password1 = '1337';
    $scope.password2 = '1337';
    $scope.submit = function () {
        console.log('entering submit');
        if($scope.password1 != $scope.password2) {
            console.log("passwords are different");
            return;
        }
        $http({
            method: 'POST',
            url: '/api/account/'+$scope.userId,
            data: $scope.password1
        }).then(function(responseData){
            tokenRequestBody = {
                'client_id': 'client',
                'username' : $scope.userId,
                'grant_type' : 'password',
                'password' : $scope.password1
            }

            var authCode = window.btoa("client:1337password")
            authHeader = { 'Authorization':'Basic ' + authCode, 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'};
            console.log(authHeader);
            $http({
                method: 'POST',
                url: '/oauth/token',
                headers: authHeader,
                data: $httpParamSerializer(tokenRequestBody)
            }).then(function(responseData){
                console.log(responseData);
                $http.defaults.headers.common.Authorization =
                    'Bearer ' + responseData.data.access_token;
                $cookies.put("access_token", responseData.data.access_token);
                console.log($cookies.get("access_token"));
                window.location.href="app";
            });
        });
        $scope.userId = '';
        $scope.password = '';
    }
})
