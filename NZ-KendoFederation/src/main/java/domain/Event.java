/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * @author Mike
 * @author lachlan
 */
public class Event {

    private String eventId;
    private String name;                 //Name of the event
    private String startDate;
    private Club club;
    private String venue;                //Location of event
    private Grade highestGradeAvailable;            //Highest grade available to be given by this Event
    private String headOfGradingPanel;
    private String otherMembersOfGradingPanel;
    private String event_description;
    private String start_time, end_time;
    private String status;

    public Event() {
    }

    public Event(String eventId, String name, String startDate, Club club, String venue, Grade highestGradeAvailable, String headOfGradingPanel, String otherMembersOfGradingPanel, String event_description, String start_time, String end_time, String status) {
        this.eventId = eventId;
        this.name = name;
        this.startDate = startDate;
        this.club = club;
        this.venue = venue;
        this.highestGradeAvailable = highestGradeAvailable;
        this.headOfGradingPanel = headOfGradingPanel;
        this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
        this.event_description = event_description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Grade getHighestGradeAvailable() {
        return highestGradeAvailable;
    }

    public void setHighestGradeAvailable(Grade highestGradeAvailable) {
        this.highestGradeAvailable = highestGradeAvailable;
    }

    public String getHeadOfGradingPanel() {
        return headOfGradingPanel;
    }

    public void setHeadOfGradingPanel(String headOfGradingPanel) {
        this.headOfGradingPanel = headOfGradingPanel;
    }

    public String getOtherMembersOfGradingPanel() {
        return otherMembersOfGradingPanel;
    }

    public void setOtherMembersOfGradingPanel(String otherMembersOfGradingPanel) {
        this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
