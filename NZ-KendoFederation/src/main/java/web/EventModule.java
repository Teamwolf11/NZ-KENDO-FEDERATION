package web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.EventDAO;
import domain.Club;
import dao.EventJdbcDAO;
import domain.Event;
import domain.Grade;
import domain.Member;
import dao.MemberDAO;
import dao.MemberJdbcDAO;
import java.lang.reflect.Type;
import org.jooby.Jooby;
import org.jooby.Status;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Mike Cui
 */
public class EventModule extends Jooby {

    public EventModule(EventDAO eventDao, MemberDAO memberDao) {
        get("/api/events", () -> eventDao.getEvents());

        get("/api/events/:id", (req) -> {
            String id = req.param("event_id").value();
            return eventDao.getEvent(id);
        });

        post("/api/bookEvents", (req, rsp) -> {
            String event = req.body().to(String.class);
            //System.out.println(event);
            
            event = event.replaceAll("[\\[\\](){}\"]", "");
            System.out.println(event);
            String[] eventArr = event.split("\\s*,\\s*");
//            for(int i = 0; i < eventArr.length; i++) {
//                System.out.print(eventArr[i] + " ");
//            }
//            
            List<String> listBody = Arrays.asList(event);
            ArrayList<String> listOfString = new ArrayList<>(listBody);
            ArrayList<String> parameters = new ArrayList<>();
            System.out.println(listOfString);
            
            for (String valuePair : listOfString) {
                String[] elements = valuePair.split(":");
                parameters.add(elements[elements.length-1]);
                System.out.println(parameters);
            }
//            for (int i = 1; i < elements.length; i++){
//                if (elements[i].contains("eventId")){
//               System.out.println(elements[i]);
 //           }
      
//            
            
            //String member = req.body().to(String.class);
            //System.out.println(member);
            //Event event = req.body();
//            String member = req.param("memberId").value();
//            String event = req.param("eventId").value();
            //System.out.println(event.toString());
            //System.out.println(member.toString());
            //MemberDAO mem = new MemberJdbcDAO();
//            eventDao.registerForEvent(eventDao.getEvent(event), mem.getMember(member));
            //eventDao.registerForEvent(event, member);
            rsp.status(Status.CREATED);
        });

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

            Event event = new Event(parameters.get(0), club,
                     parameters.get(2), grade, parameters.get(3),
                    otherMembers, parameters.get(5), parameters.get(7), parameters.get(8), "Available");
            System.out.println(parameters.get(0) + club
                    + parameters.get(2) + grade + parameters.get(3)
                    + otherMembers + parameters.get(5) + parameters.get(6) + parameters.get(7));
            eventDao.saveEvent(event);
            rsp.status(Status.CREATED);
        });
    }

}
