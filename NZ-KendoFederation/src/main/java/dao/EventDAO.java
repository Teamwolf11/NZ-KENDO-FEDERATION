package dao;

import domain.Event;

/**
 *
 * @author Maaha Ahmad
 */
public interface EventDAO {

    Event getEvent(String eventID);
    // delete event
    // update event

    void saveEvent(Event event);
    
}
