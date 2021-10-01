/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.Time;
import java.time.LocalDateTime;


/**
 *
 * @author lachl
 */
public class Event {

    public String Event_id;
    public String name;                 //Name of the event
    public Club relatedClub;
    public String venue;                //Location of event
    public String highGrade;            //Highest grade available to be given by this Event
    public LocalDateTime eventDate, createdAt, lastModified;
    public String nameOfGradingPanel;
    public String headOfGradingPanel;
    public String eventDescription;
    public Time startTime, endTime;
    
    public Event() {
    }

    public Event(String Event_id, String name, Club relatedClub, String venue, String highGrade, LocalDateTime eventDate, String nameOfGradingPanel, String headOfGradingPanel, String eventDescription, Time startTime, Time endTime, LocalDateTime createdAt, LocalDateTime lastModified) {
        this.Event_id = Event_id;
        this.name = name;
        this.relatedClub = relatedClub;
        this.venue = venue;
        this.highGrade = highGrade;
        this.eventDate = eventDate;
        this.nameOfGradingPanel = nameOfGradingPanel;
        this.headOfGradingPanel = headOfGradingPanel;
        this.startTime = startTime;
        this.endTime = endTime; 
        this.createdAt = createdAt;
        this.lastModified = lastModified; 
        this.eventDescription = eventDescription;
    }

    public String getEvent_id() {
        return Event_id;
    }

    public void setEvent_id(String Event_id) {
        this.Event_id = Event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getRelatedClub() {
        return relatedClub;
    }

    public void setRelatedClub(Club relatedClub) {
        this.relatedClub = relatedClub;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(String highGrade) {
        this.highGrade = highGrade;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getNameOfGradingPanel() {
        return nameOfGradingPanel;
    }

    public String getHeadOfGradingPanel() {
        return headOfGradingPanel;
    }

    public void setNameOfGradingPanel(String nameOfGradingPanel) {
        this.nameOfGradingPanel = nameOfGradingPanel;
    }

    public void setHeadOfGradingPanel(String headOfGradingPanel) {
        this.headOfGradingPanel = headOfGradingPanel;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    
   

}
