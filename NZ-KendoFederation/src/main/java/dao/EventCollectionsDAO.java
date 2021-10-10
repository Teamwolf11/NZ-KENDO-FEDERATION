package dao;

import domain.Club;
import domain.Event;
import domain.MartialArt;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Maaha Ahmad
 */
public class EventCollectionsDAO implements EventDAO {
    private static final Collection<Event> eventsList = new HashSet<Event>();
    private static Map<String, Event> events = new HashMap<String, Event>();

    public EventCollectionsDAO() {
        
//        
        //Club club1 = new Club("5454", "3444p", "Kendo club", new MartialArt("4545", "Kendo", "8 Dan", Date.valueOf("2011-02-18")),"Tasha", 8);
//        Club club2 = new Club("5545", "3445p", "Iaido club", new MartialArt("4325", "Iaido", "8 Dan", Date.valueOf("2011-02-18")),"Tasha", 8); 
//
//        Event event1 = new Event();
//        event1.setEventId("K4645");
        //event1.setHighestGradeAvailable("8 Dan");
//        event1.setName("Kendo");
        //event1.setRelatedClub(club1);
//        event1.setVenue("some place");
//
//        Event event2 = new Event();
//        event2.setEvent_id("I4545");
//        event2.setHighGrade("8 Dan");
//        event2.setName("Iaido");
//        event2.setRelatedClub(club1);
//        event2.setVenue("some place");
//
//       saveEvent(event1);
//        saveEvent(event2);
    }

    @Override
    public Event saveEvent(Event event) {
        eventsList.add(event);
        return event;
    }

    @Override
    public Event getEvent(String eventID){
        return events.get(eventID);
    }
    
    @Override
    public Collection<Event> getEvents(){
        return eventsList;
    }
    // delete event
    // update event

    @Override
    public void deleteEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public Event saveEvent(Event event) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
