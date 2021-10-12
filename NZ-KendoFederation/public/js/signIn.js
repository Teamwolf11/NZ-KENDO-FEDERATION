/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

"use strict";

// create a new module, and load the other pluggable modules
var module = angular.module('MemberApp', ['ngResource', 'ngStorage']);

module.factory('registerAPI', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInAPI', function ($resource) {
    return $resource('/api/members/:email');
});

module.config(function ($sessionStorageProvider, $httpProvider) {
    // get the auth token from the session storage
    let authToken = $sessionStorageProvider.get('authToken');

    // does the auth token actually exist?
    if (authToken) {
        // add the token to all HTTP requests
        $httpProvider.defaults.headers.common.Authorization = 'Basic ' + authToken;
    }
});

module.controller('MemberController', function (registerAPI, $window, signInAPI, $sessionStorage, $http) {

    this.signInMessage = "";
    this.registerMessage = "";

    if ($sessionStorage.member) {
        this.welcome = "Welcome " + $sessionStorage.member.fName;
    }

    this.registerMember = function (member) {
        registerAPI.save(null, member,
                // success callback
                        function () {
                            $window.location = 'signIn.html';
                            ctrl.registerMessage = 'Account created. Please sign in.';
                        },
                        // error callback
                                function (error) {
                                    console.log(error);
                                    ctrl.registerMessage = 'This email is already taken.';
                                }
                        );
                    };

            // alias 'this' so that we can access it inside callback functions
            let ctrl = this;
            this.signIn = function (email, password) {
                console.log("In sign in function")
                
                // generate authentication token
                let authToken = $window.btoa(email + ":" + password);
                // store token
                $sessionStorage.authToken = authToken;
                // add token to the HTTP request headers
                $http.defaults.headers.common.Authorization = 'Basic ' + authToken;

                // get member from web service
                signInAPI.get({'email': email},
                        // success callback
                                function (member) {
                                console.log("In success callback")
                                // also store the retrieved member
                                $sessionStorage.member = member;
                                // redirect to home
                                // $window.location = 'index.html';
                                console.log("Role Id: " + $sessionStorage.member.role.appRoleId)
//                                if ($sessionStorage.member.role.appRoleId == 3) {
//                                    $window.location = 'index.html';
//                                } else {
//                                    $window.location = 'locations.html';
//                                }
                                $window.location = 'index.html';


                            },
                            // fail callback
                                    function () {
                                        ctrl.signInMessage = 'Sign in failed. Please try again.';
                                        }
                                );
                            };

                    this.signOut = function () {
                        $sessionStorage.$reset();
                        // Redirect to home
                        $window.location = 'index.html';
                    };

                    this.checkSignIn = function () {
                        // has the member been added to the session?
                        if ($sessionStorage.member) {
                            this.signedIn = true;
                            this.welcome = "Welcome " + $sessionStorage.member.fName;
                        } else {
                            this.signedIn = false;
                        }
                    };

                    this.isFedLead = function () {
                        if ($sessionStorage.member.role.appRoleId == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    this.isClubLead = function () {
                        if ($sessionStorage.member.role.appRoleId == 2) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    this.isMemb = function () {
                        if ($sessionStorage.member.role.appRoleId == 3) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
