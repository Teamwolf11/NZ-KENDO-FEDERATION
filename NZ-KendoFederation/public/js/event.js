/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict";

// create a new module, and load the other pluggable modules
var module = angular.module('GradingApp', ['ngResource', 'ngStorage']);

module.factory('adminCreateEvent', function ($resource) {
    return $resource('/api/adminCreateEvent');
});

module.factory('viewEvent', function($resource) {
   return $resource('/api/viewEvent'); 
});

module.controller('EventController', function (adminCreateEvent, viewEvent, $window, $sessionStorage) {
// Ben Scobie you can add a thing for client join event above just like what I have done with adminCreateEvenet
    this.eventMessage = "Create a grading event below for members to sign up.";

    if ($sessionStorage.member) {
        this.welcome = "Welcome " + $sessionStorage.member.firstName;
    }

    this.registerEvent = function (event) {
        adminCreateEvent.save(null, event,
            // success callback
            function () {
                $window.location = 'adminEvent.html';
            },
            // error callback
            function (error) {
                console.log(error);
            }
        );
    };
    
    this.events = viewEvent.query();

});

