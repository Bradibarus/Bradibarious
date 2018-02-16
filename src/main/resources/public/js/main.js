var app = angular.module("bradibatious", ['ngCookies']);
app.controller("LoginContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    if($cookies.get("access_token")){
        window.location.href = "app";
        console.log("redirect to app")
    }
    $scope.wrongCredentials = false;

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
        }).catch(function (reason) {
            $scope.wrongCredentials = true;
        });
        $scope.userId = '';
        $scope.password = '';
    }
})



app.controller("AppContr", function ($scope, $http, $cookies) {
        if ($cookies.get("access_token")) {
            $http.defaults.headers.common.Authorization =
                'Bearer ' + $cookies.get("access_token");
        } else {
            window.location.href = "login";
        }

        getUsername = function () {
            $http.get('api/account')
                .then(function (responseData) {
                    $scope.username = responseData.data.username;
                })
        }
        getUsername();

        $scope.terms = [];
        getTerms = function () {
            $http.get('api/terms')
                .then(function (responseData) {
                    console.log(responseData);
                    if (angular.equals(responseData.data, {})) {
                        $scope.hideTable = true;
                        document.getElementById("no_terms").innerHTML = "<p>You don't have any words yet<br>Feel free to add them using form to the right 👉</p>";
                    } else {
                        $scope.hideTable = false;
                        document.getElementById("no_terms").innerHTML = "<p>Yout words: </p>";
                        $scope.terms = responseData.data._embedded.termResourceList.reverse();
                    }
                })
                .catch(function (error) {
                    console.log(error);
                })

        }
        getTerms();
        $scope.submit = function () {
            term = {'word1': $scope.word1, 'word2': ''};
            $http.post('api/terms/', term).then(function (responseData) {
                console.log(responseData);
                //$scope.terms.push(responseData.data);
                getTerms();
                console.log($scope.terms);
            });
            $scope.word1 = '';
        }

        $scope.logout = function () {
            $cookies.remove("access_token");
            window.location.href = "login";
        }


        $scope.del = function (url) {
            $http.delete(url)
                .then(function (value) {
                    getTerms();
                })
        }


        /* CARUSRL */


    }
)

app.controller("TrainContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    if ($cookies.get("access_token")) {
        $http.defaults.headers.common.Authorization =
            'Bearer ' + $cookies.get("access_token");
    } else {
        window.location.href = "login";
    }

    getUsername = function () {
        $http.get('api/account')
            .then(function (responseData) {
                $scope.username = responseData.data.username;
            })
    }
    getUsername();

    $scope.terms = [];
    getTerms = function () {
        $http.get('api/terms')
            .then(function (responseData) {
                console.log(responseData);
                if (angular.equals(responseData.data, {})) {
                    $scope.hideCarousel = true;
                    document.getElementById("no_terms").innerHTML = "<p>You don't have any words to remember<br>Head over to <a href='/app'>Dictionary</a> to get some </p>" +
                        "<img src= '128.png'/>";
                } else {
                    $scope.hideCarousel = false;
                    document.getElementById("no_terms").outerHTML = "";
                    $scope.terms = responseData.data._embedded.termResourceList;
                    $scope.words = [];
                    console.log($scope.terms[0].term.word1);
                    $scope.terms.forEach(function (value) {
                        $scope.words.push(value.term.word1);
                        $scope.words.push(value.term.word2);
                    })
                    barWidth = -1/$scope.words.length*100;
                }
            })
            .catch(function (error) {
                console.log(error);
            })

    }
    getTerms();

    $scope.logout = function () {
        $cookies.remove("access_token");
        window.location.href = "login";
    }


    $scope.nextTerm = function() {
        $('.carousel').carousel({
            wrap: false
        })
        $('.carousel').carousel('next');
        $('.carousel').carousel('pause');
    }
    $('.carousel').on('slide.bs.carousel', function () {
        barWidth += 1/$scope.words.length*100;
        $scope.barStyle = "width: "+barWidth+"%;"
    })


})

app.controller("SignupContr", function ($scope, $http, $httpParamSerializer, $cookies) {
    if($cookies.get("access_token")){
        window.location.href = "app";
        console.log("redirect to app")
    }

    $scope.submit = function () {
        console.log('entering submit');
        if($scope.password1 != $scope.password2) {
            console.log("passwords are different");
            $scope.wrongPasswords = true;
            return;
        }else{
            $scope.wrongPasswords = false;
        }
        $http({
            method: 'POST',
            url: '/api/account/'+$scope.userId,
            data: $scope.password1
        }).then(function(responseData){
            tokenRequestBody = {
                'client_id': 'client',
                'username' : responseData.data.username,
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
