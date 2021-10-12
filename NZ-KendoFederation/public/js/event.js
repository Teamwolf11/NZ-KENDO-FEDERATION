/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict";
 
// create a new module, and load the other pluggable modules
var module = angular.module('GradingApp', ['ngResource', 'ngStorage']);
 
module.factory('adminCreateEventAPI', function ($resource) {
    return $resource('/api/adminCreateEvent');
});
 
module.factory('eventAPI', function($resource) {
   return $resource('/api/events/:id'); 
});
 
module.factory('getEventsAPI', function($resource) {
    return $resource('/api/events');
});
 
module.controller('EventController', function (adminCreateEventAPI, eventAPI, getEventsAPI, $window) {
    this.events = eventAPI.query();
 
    this.registerEvent = function (event) {
        adminCreateEventAPI.save(null, event,
            // success callback
            function () {
                $window.location = 'grading.html';
            },
            // error callback
            function (error) {
                console.log(error);
            }
        );
    };
    
    this.selectAll = function () {
        this.events = getEventsAPI.query();
    };
});

