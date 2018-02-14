var app = angular.module("bradibatious", ['ngCookies']);
app.controller("LoginContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    var isLoginPage = window.location.href.indexOf("login") != -1;
    if(isLoginPage){
        if($cookies.get("access_token")){
            window.location.href = "app";
        }
    } else{
        if($cookies.get("access_token")){
            $http.defaults.headers.common.Authorization =
                'Bearer ' + $cookies.get("access_token");
        } else{
            window.location.href = "login";
        }
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
    console.log(token);
    $scope.token = $cookies.get("access_token");

    }
)
