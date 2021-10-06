/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.Timestamp;

/**
 * @author Mike
 * @author lachlan
 */
public class Event {

  private String event_id;
  private String name;                 //Name of the event
  private String start_date;
  private Club club;
  private String venue;                //Location of event
  private Grade highest_grade_available;            //Highest grade available to be given by this Event
  private String headOfGradingPanel;
  private String otherMembersOfGradingPanel;
  private String event_description;
  private String start_time, end_time;
  private String status;


  public Event() {
  }

  public Event(String event_id, String name, String start_date, Club club, String venue, Grade highest_grade_available, String headOfGradingPanel, String otherMembersOfGradingPanel, String event_description, String start_time, String end_time, String status) {
    this.event_id = event_id;
    this.name = name;
    this.start_date = start_date;
    this.club = club;
    this.venue = venue;
    this.highest_grade_available = highest_grade_available;
    this.headOfGradingPanel = headOfGradingPanel;
    this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
    this.event_description = event_description;
    this.start_time = start_time;
    this.end_time = end_time;
    this.status = status;
  }

  public String getEvent_id() {
    return event_id;
  }

  public Event setEvent_id(String event_id) {
    this.event_id = event_id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Event setName(String name) {
    this.name = name;
    return this;
  }

  public String getStart_date() {
    return start_date;
  }

  public Event setStart_date(String start_date) {
    this.start_date = start_date;
    return this;
  }

  public Club getClub() {
    return club;
  }

  public Event setClub(Club club) {
    this.club = club;
    return this;
  }

  public String getVenue() {
    return venue;
  }

  public Event setVenue(String venue) {
    this.venue = venue;
    return this;
  }

  public Grade getHighest_grade_available() {
    return highest_grade_available;
  }

  public Event setHighest_grade_available(Grade highest_grade_available) {
    this.highest_grade_available = highest_grade_available;
    return this;
  }

  public String getHeadOfGradingPanel() {
    return headOfGradingPanel;
  }

  public Event setHeadOfGradingPanel(String headOfGradingPanel) {
    this.headOfGradingPanel = headOfGradingPanel;
    return this;
  }

  public String getOtherMembersOfGradingPanel() {
    return otherMembersOfGradingPanel;
  }

  public Event setOtherMembersOfGradingPanel(String otherMembersOfGradingPanel) {
    this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
    return this;
  }

  public String getEvent_description() {
    return event_description;
  }

  public Event setEvent_description(String event_description) {
    this.event_description = event_description;
    return this;
  }

  public String getStart_time() {
    return start_time;
  }

  public Event setStart_time(String start_time) {
    this.start_time = start_time;
    return this;
  }

  public String getEnd_time() {
    return end_time;
  }

  public Event setEnd_time(String end_time) {
    this.end_time = end_time;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public Event setStatus(String status) {
    this.status = status;
    return this;
  }
}
