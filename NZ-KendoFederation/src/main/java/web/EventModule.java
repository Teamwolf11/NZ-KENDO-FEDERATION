package web;

import dao.EventDAO;
import dao.EventJdbcDAO;
import domain.Event;
import org.jooby.Jooby;
import org.jooby.Status;

public class EventModule extends Jooby {

    EventDAO eventDao = new EventJdbcDAO();

    public EventModule(EventDAO eventDao) {
//       get("/api/events", () -> eventDao.getEvents();
        
        get("/api/events", () -> {
            return eventDao.getEvents();
        });

        get("/api/events/:id", (req) -> {
            String id = req.param("event_id").value();
            return eventDao.getEvent(id);
        });

        post("/api/adminCreateEvent", (req, rsp) -> {

            Event event = req.body().to(Event.class);
            eventDao.saveEvent(event);
            rsp.status(Status.CREATED);
        });
    }

}
