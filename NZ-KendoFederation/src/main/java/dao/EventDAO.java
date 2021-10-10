package dao;

import domain.Event;
import java.util.Collection;

/**
 *
 * @author Maaha Ahmad
 */
public interface EventDAO {

    Event getEvent(String eventID);
    Collection<Event> getEvents();
    void deleteEvent(Event event);
    void updateEvent(Event event);
    Event saveEvent(Event event);
    
}
