package dao;

import domain.Event;

/**
 *
 * @author Maaha Ahmad
 */
public interface EventDAO {

    Event getEvent(String eventID);
    void deleteEvent(Event event);
    void updateEvent(Event event);
    Event saveEvent(Event event);
    
}
