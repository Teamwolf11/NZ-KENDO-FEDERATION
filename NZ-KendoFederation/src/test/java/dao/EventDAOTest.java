package dao;

import domain.Club;
import domain.Event;
import domain.Grade;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lachl
 */
public class EventDAOTest {

    private ClubJdbcDAO clubJdbc;
    private EventJdbcDAO eventJdbc;
    private Event event1;
    private Grade grade1;
    private Club club1;
    private String headGrad;
    private List<String> secondGrad;
    String str = "08/09/2002";

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        clubJdbc = new ClubJdbcDAO();
        eventJdbc = new EventJdbcDAO();

        headGrad = "Head";
        secondGrad = new ArrayList<>();
        secondGrad.add("Person 1");
        secondGrad.add("Person 2");

        club1 = new Club();
        club1.setClubName("TestClub1");
        club1.setLocation("Location");
        club1.setDescription("Desc");
        club1.setEmail("email");
        club1.setPhone("12345");

        club1 = clubJdbc.saveClub(club1);

        grade1 = new Grade();
        grade1.setArtId("1");
        grade1.setGradeId("1");
        grade1.setMartialArt("Kendo");
        grade1.setGrade("7 Kyu");

        event1 = new Event();
        event1.setName("EventName");
        event1.setClub(club1);
        event1.setVenue("venue");
        event1.setDesc("desc");
        event1.setStatus("status");
        event1.setStartDateTime(str);
        event1.setEndDateTime(str);
        event1.setHighestGradeAvailable(grade1);
        event1.setHeadOfGradingPanel(headGrad);
        event1.setOtherMembersOfGradingPanel(secondGrad);
    }

    @AfterEach
    public void tearDown() {
        eventJdbc.deleteGraderEvent(event1, headGrad);
        for (int i = 0; i < event1.getOtherMembersOfGradingPanel().size(); i++) {
            eventJdbc.deleteGraderEvent(event1, event1.getOtherMembersOfGradingPanel().get(i));
        }
        eventJdbc.deleteEvent(event1);
        clubJdbc.deleteClub(club1);
        
    }

    @Test
    public void testSaveEvent() {
        event1 = eventJdbc.saveEvent(event1);
        Event eventCheck = eventJdbc.getEvent(event1.getEventId());
        grade1.setEventName(event1.getName());
        grade1.setEventId(event1.getEventId());

        System.out.println(event1.toString());
        System.out.println(eventCheck.toString());
        
        assertTrue(event1.toString().equals(eventCheck.toString()));
    }

//     @Test
//    public void testSetGraderEvent(){
//        event1 = eventJdbc.saveEvent(event1);
//        //eventJdbc.setGraderEvent(headGrad, "Head", event1);
//        eventJdbc.setGraderEvent(secondGrad, "general Grader", event1);
//        
//        Event eventCheck = eventJdbc.getEvent(event1.getEventId());
//
//        assertTrue(headGrad.equals(eventCheck.getHeadOfGradingPanel()));
//        
//        eventJdbc.deleteGraderEvent(event1, secondGrad);
//    }
}
