package dao;

import domain.Club;
import domain.Event;
import domain.Grade;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lachl
 */
public class EventDAOTest {

    //private MemberJdbcDAO memberJdbc;
    private GradeJdbcDAO gradeJdbc;
    private ClubJdbcDAO clubJdbc;
    private EventJdbcDAO eventJdbc;
    //private Member member1, member2;
    private Event event1;
    private Grade grade1, grade2, grade3;
    private Club club1, club2;
    String str = "08/09/2002";

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        //memberJdbc = new MemberJdbcDAO();
        //gradeJdbc = new GradeJdbcDAO();
        clubJdbc = new ClubJdbcDAO();
        eventJdbc = new EventJdbcDAO();

//        AppRoles role = new AppRoles("3", "General Member");
//
//        member1 = new Member();
//        member1.setRole(role);
//        member1.setNzkfId("6565");
//        member1.setEmail("email@test.test1");
//        member1.setPassword("qwerty");
//        member1.setDob(str);
//        member1.setfName("Boris");
//        member1.setmName("Horis");
//        member1.setlName("Doloris");
//        member1.setJoinDate(str);
//        member1.setSex('M');
//        member1.setEthnicity("Asian");
//
//        member2 = new Member();
//        member2.setRole(role);
//        member2.setNzkfId("6564");
//        member2.setNzkfRenewDate(str);
//        member2.setPassword("QWERTY");
//        member2.setEmail("email@test.test2");
//        member2.setDob(str);
//        member2.setfName("Jane");
//        member2.setmName(null);
//        member2.setlName("Doe");
//        member2.setJoinDate(str);
//        member2.setSex('F');
//        member2.setEthnicity("Asian");
//
//        member1 = memberJdbc.saveMember(member1);

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
        //grade1.setClub(club1);

//        grade2 = new Grade();
//        grade2.setArtId("1");
//        grade2.setGradeId("9");
//        grade2.setMartialArt("Kendo");
//        grade2.setGrade("2 Dan");
//        grade2.setDateReceived(str);
//        grade2.setClub(club2);
//
//        grade3 = new Grade();
//        grade3.setArtId("1");
//        grade3.setGradeId("9");
//        grade3.setMartialArt("Kendo");
//        grade3.setGrade("2 Dan");
//        grade3.setDateReceived(str);
//        grade3.setClub(club1);

        event1 = new Event();
        event1.setName("EventName");
        event1.setClub(club1);
        event1.setVenue("venue");
        event1.setDesc("desc");
        event1.setStatus("status");
        event1.setStartDateTime(str);
        event1.setEndDateTime(str);
        event1.setHighestGradeAvailable(grade1);
    }

    @AfterEach
    public void tearDown() {
        eventJdbc.deleteEvent(event1);
        clubJdbc.deleteClub(club1);      
    }

    @Test
    public void testSaveEvent() {
        event1 = eventJdbc.saveEvent(event1);
        Event eventCheck = eventJdbc.getEvent(event1.getEventId());
        grade1.setEventId(event1.getEventId());
        
        assertTrue(event1.toString().equals(eventCheck.toString()));
    }

    /**
     * Tests getAllForObj with a member object given/returned
     */
//    @Test
//    public void testGetAllForObjMem() {
//        member1 = gradeJdbc.saveGrade(grade1, member1);
//        member1 = gradeJdbc.saveGrade(grade2, member1);
//        member2 = memberJdbc.saveMember(member2);
//        member2 = gradeJdbc.saveGrade(grade3, member2);
//
//        Member gradeCheck = (Member) gradeJdbc.getAllForObj(member1);  //call grade(s) from db
//        
//        assertTrue(member1.getGrades().toString().equals(gradeCheck.getGrades().toString()));
//        assertTrue(gradeCheck.getGrades().size() == 2); //ensures no member2
//        
//        gradeJdbc.deleteGrade(grade1, member1.getMemberId());
//        gradeJdbc.deleteGrade(grade2, member1.getMemberId());
//        gradeJdbc.deleteGrade(grade3, member2.getMemberId());
//        memberJdbc.deleteMember(member2);
//    }
//    
//     /**
//     * Tests getAllForObj with a club object given/returned
//     */
//    @Test
//    public void testGetAllForObjClub() {
//        member1 = gradeJdbc.saveGrade(grade1, member1);
//        member1 = gradeJdbc.saveGrade(grade2, member1);
//        member2 = memberJdbc.saveMember(member2);
//        member2 = gradeJdbc.saveGrade(grade3, member2);
//
//        Club gradeCheck = (Club) gradeJdbc.getAllForObj(club1);  //call grade(s) from db
//        
//        assertTrue(gradeCheck.getGrades().size() == 2);
//        assertTrue(gradeCheck.getGrades().size() == 2); //ensures no club2
//        
//        gradeJdbc.deleteGrade(grade1, member1.getMemberId());
//        gradeJdbc.deleteGrade(grade2, member1.getMemberId());
//        gradeJdbc.deleteGrade(grade3, member2.getMemberId());
//        memberJdbc.deleteMember(member2);
//    }
}
