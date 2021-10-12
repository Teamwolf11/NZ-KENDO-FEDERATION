package dao;

import domain.Club;
import domain.Event;
import domain.Grade;
import domain.Member;
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
public class EmailDAOTest {

    private ClubJdbcDAO clubJdbc;
    private EventJdbcDAO eventJdbc;
    private EmailJdbcDAO emailJdbc;
    private MemberJdbcDAO memberJdbc;
    private GradeJdbcDAO gradeJdbc;
    private Event event1;
    private Grade grade1, grade2;
    private Club club1;
    private Member member1;
    String str = "08/09/2002";
    private String headGrad;
    private List<String> secondGrad;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        clubJdbc = new ClubJdbcDAO();
        eventJdbc = new EventJdbcDAO();
        emailJdbc = new EmailJdbcDAO();
        memberJdbc = new MemberJdbcDAO();
        gradeJdbc = new GradeJdbcDAO();
        
        headGrad = "Head";
        secondGrad = new ArrayList<>();
        secondGrad.add("Person 1");
        secondGrad.add("Person 2");

        member1 = new Member();
        member1.setNzkfId("6565");
        member1.setEmail("email@test.test1");
        member1.setPassword("qwerty");
        member1.setDob(str);
        member1.setfName("Boris");
        member1.setmName("Horis");
        member1.setlName("Doloris");
        member1.setJoinDate(str);
        member1.setSex('M');
        member1.setEthnicity("Asian");
        
        member1 = memberJdbc.saveMember(member1);
        
        club1 = new Club();
        club1.setClubName("TestClub1");
        club1.setLocation("Location");
        club1.setDescription("Desc");
        club1.setEmail("email");
        club1.setPhone("12345");

        club1 = clubJdbc.saveClub(club1);
        
        grade1 = new Grade();
        grade1.setClub(club1);
        grade1.setArtId("1");
        grade1.setGradeId("1");
        grade1.setMartialArt("Kendo");
        grade1.setGrade("7 Kyu");
        grade1.setDateReceived(str);
        
        member1 = gradeJdbc.saveGrade(grade1, member1);
        
        grade2 = new Grade();
        //grade2.setClub(club1);
        grade2.setArtId("1");
        grade2.setGradeId("3");
        grade2.setMartialArt("Kendo");
        grade2.setGrade("7 Kyu");
       
        event1 = new Event();
        event1.setName("EventName");
        event1.setClub(club1);
        event1.setVenue("venue");
        event1.setDesc("desc");
        event1.setStatus("status");
        event1.setStartDateTime(str);
        event1.setEndDateTime(str);
        event1.setHighestGradeAvailable(grade2);
        event1.setHeadOfGradingPanel(headGrad);
        event1.setOtherMembersOfGradingPanel(secondGrad);
        
        event1 = eventJdbc.saveEvent(event1);
    }

    @AfterEach
    public void tearDown() {
        eventJdbc.deleteGraderEvent(event1, headGrad);
        for (int i = 0; i < event1.getOtherMembersOfGradingPanel().size(); i++) {
            eventJdbc.deleteGraderEvent(event1, event1.getOtherMembersOfGradingPanel().get(i));
        }
        eventJdbc.deleteEvent(event1);
        clubJdbc.deleteClub(club1);
        gradeJdbc.deleteGrade(grade1, member1.getMemberId());
        memberJdbc.deleteMember(member1);
    }

    @Test
    public void testSaveEvent() {
        List<Member> mList = emailJdbc.getPotentialMembers(event1);
        System.out.println(mList);
    }
}
