/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike
 * @author lachlan
 */
public class Event {

    private String eventId;
    private String name;                 //Name of the event
    private Club club;
    private String venue;                //Location of event
    private Grade highestGradeAvailable;            //Highest grade available to be given by this Event
    private String headOfGradingPanel;
    private List<String> otherMembersOfGradingPanel;
    private String desc;
    private String startDateTime, endDateTime;
    private String status;
    private List<Grade> grades = new ArrayList<>();

    public Event() {
    }

    public Event(String eventId, String name, Club club, String venue, Grade highestGradeAvailable, String headOfGradingPanel, List<String> otherMembersOfGradingPanel, String desc, String startDateTime, String endDateTime, String status) {
        this.eventId = eventId;
        this.name = name;
        this.club = club;
        this.venue = venue;
        this.highestGradeAvailable = highestGradeAvailable;
        this.headOfGradingPanel = headOfGradingPanel;
        this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
        this.desc = desc;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public Event(String name, Club club, String venue, Grade highestGradeAvailable, String headOfGradingPanel, List<String> otherMembersOfGradingPanel, String desc, String startDateTime, String endDateTime, String status) {
        this.name = name;
        this.club = club;
        this.venue = venue;
        this.highestGradeAvailable = highestGradeAvailable;
        this.headOfGradingPanel = headOfGradingPanel;
        this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
        this.desc = desc;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public void addGrade(Grade grade){
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
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

    public List<String> getOtherMembersOfGradingPanel() {
        return otherMembersOfGradingPanel;
    }

    public void setOtherMembersOfGradingPanel(List<String> otherMembersOfGradingPanel) {
        this.otherMembersOfGradingPanel = otherMembersOfGradingPanel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" + "eventId=" + eventId + ", name=" + name + ", club=" + club + ", venue=" + venue + ", highestGradeAvailable=" + highestGradeAvailable + ", headOfGradingPanel=" + headOfGradingPanel + ", otherMembersOfGradingPanel=" + otherMembersOfGradingPanel + ", desc=" + desc + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", status=" + status + '}';
    }
}
