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
  private Timestamp start_date;
  private Integer club_id;
  private String venue;                //Location of event
  private String highest_grade_available;            //Highest grade available to be given by this Event
  private Integer grading_id;
  private String headOfGradingPanel;
  private String otherMembersOfGradingPanel;

  public Event() {
  }

  public Event(String event_id, String name, Timestamp start_date, Integer club_id, String venue, String highest_grade_available, Integer grading_id, String headOfGradingPanel, String otherMembersOfGradingPanel) {
    this.event_id = event_id;
    this.name = name;
    this.start_date = start_date;
    this.club_id = club_id;
    this.venue = venue;
    this.highest_grade_available = highest_grade_available;
    this.grading_id = grading_id;
    this.headOfGradingPanel = headOfGradingPanel;
    this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
  }

  @Override
  public String toString() {
    return "Event{" +
        "event_id='" + event_id + '\'' +
        ", name='" + name + '\'' +
        ", start_date=" + start_date +
        ", club_id=" + club_id +
        ", venue='" + venue + '\'' +
        ", highest_grade_available='" + highest_grade_available + '\'' +
        ", grading_id=" + grading_id +
        ", headOfGradingPanel='" + headOfGradingPanel + '\'' +
        ", otherMembersOfGradingPanel='" + otherMembersOfGradingPanel + '\'' +
        '}';
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

  public Timestamp getStart_date() {
    return start_date;
  }

  public Event setStart_date(Timestamp start_date) {
    this.start_date = start_date;
    return this;
  }

  public Integer getClub_id() {
    return club_id;
  }

  public Event setClub_id(Integer club_id) {
    this.club_id = club_id;
    return this;
  }

  public String getVenue() {
    return venue;
  }

  public Event setVenue(String venue) {
    this.venue = venue;
    return this;
  }

  public String getHighest_grade_available() {
    return highest_grade_available;
  }

  public Event setHighest_grade_available(String highest_grade_available) {
    this.highest_grade_available = highest_grade_available;
    return this;
  }

  public Integer getGrading_id() {
    return grading_id;
  }

  public Event setGrading_id(Integer grading_id) {
    this.grading_id = grading_id;
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
}
