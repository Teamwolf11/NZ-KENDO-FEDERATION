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

module.controller('MemberController', function (registerAPI, $window, signInAPI, $sessionStorage) {

    this.signInMessage = "";
    this.registerMessage = "";

    if ($sessionStorage.member) {
        this.welcome = "Welcome " + $sessionStorage.member.firstName;
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
                // get member from web service
                signInAPI.get({'email': email,'password': password},
                        // success callback
                                function (member) {
                                    // also store the retrieved member
                                    $sessionStorage.member = member;
                                    // redirect to home
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
                            this.welcome = "Welcome " + $sessionStorage.member.firstName;
                        } else {
                            this.signedIn = false;
                        }
                    };

                });
