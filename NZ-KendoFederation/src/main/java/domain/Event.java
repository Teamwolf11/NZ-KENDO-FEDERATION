/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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

public Event(){
    }
    
    public Event(String Event_id, String name, Club relatedClub, String venue, String highGrade) {
        this.Event_id = Event_id;
        this.name = name;
        this.relatedClub = relatedClub;
        this.venue = venue;
        this.highGrade = highGrade;
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
    
    
}
