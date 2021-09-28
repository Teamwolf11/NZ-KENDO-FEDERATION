/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
import dao.EventDAO;
import org.jooby.Jooby;
import org.jooby.Status;

public class EventModule extends Jooby {
    
    public EventModule(EventDAO eventDao){
        get("/api/viewEvents", () -> eventDao.getEvents());
        
        get("/api/events/:id", (req) -> {
            String id = req.param("event_id").value();
            return eventDao.getEvent(id);
        });
    }
    
}
