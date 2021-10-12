package dao;

import domain.Event;
import domain.Member;
import java.util.Collection;

/**
 *
 * @author Maaha Ahmad
 */
public interface EventDAO {

    Event getEvent(String eventID);
    void deleteEvent(Event event);
    Event updateEvent(Event event);
    Event saveEvent(Event event);
    Collection<Event> getEvents();
    void registerForEvent(Event event, Member member);
    void withdrawFromEvent(Event event, Member member);
}
