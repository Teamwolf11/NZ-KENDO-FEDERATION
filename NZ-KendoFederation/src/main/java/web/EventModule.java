package web;

import dao.EventDAO;
import domain.Club;
import dao.EventJdbcDAO;
import domain.Event;
import domain.Grade;
import org.jooby.Jooby;
import org.jooby.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mike Cui
 */
public class EventModule extends Jooby {

  public EventModule(EventDAO eventDao) {
//        get("/api/viewEvents", () -> eventDao.getEvents());
//
//        get("/api/events/:id", (req) -> {
//            String id = req.param("event_id").value();
//            return eventDao.getEvent(id);
//        });
    post("/api/adminCreateEvent", (req, rsp) -> {
      String body = req.body().to(String.class);
      System.out.println(body);
      body = body.replaceAll("[\\[\\](){}\"]", "");
      System.out.println(body);
      String[] bodyArray = body.split("\\s*,\\s*");

      List<String> listBody = Arrays.asList(bodyArray);
      ArrayList<String> listOfString = new ArrayList<>(listBody);
      ArrayList<String> parameters = new ArrayList<>();
      for (String valuePair : listOfString) {
        String[] elements = valuePair.split(":");
        parameters.add(elements[1]);
      }
      Club club = new Club();
      club.setClubId(parameters.get(1));
      Grade grade = new Grade();
      grade.setGradeId(parameters.get(8));
      String[] otherMembersArray = parameters.get(4).split("\\s*-\\s*");
      List<String> otherMembers = Arrays.asList(otherMembersArray);

      Event event = new Event(parameters.get(0), club
          , parameters.get(2), grade, parameters.get(3),
          otherMembers, parameters.get(5), parameters.get(7), parameters.get(8),"Available");
      System.out.println(parameters.get(0) + club
          + parameters.get(2)+ grade+ parameters.get(3)+
          otherMembers+ parameters.get(5)+ parameters.get(6)+ parameters.get(7));
        eventDao.saveEvent(event);
      rsp.status(Status.CREATED);
    });
  }

}
