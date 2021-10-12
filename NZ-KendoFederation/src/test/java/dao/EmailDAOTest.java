package dao;

import domain.Club;
import domain.Event;
import domain.Grade;
import domain.Member;
import static java.lang.ProcessBuilder.Redirect.from;
import static java.lang.ProcessBuilder.Redirect.to;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
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
    private Member member1, member2;
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
        
        member2 = new Member();
        member2.setNzkfId("6564");
        member2.setPassword("QWERTY");
        member2.setEmail("email@test.test2");
        member2.setDob(str);
        member2.setfName("Jane");
        member2.setmName("hmm");
        member2.setlName("Doe");
        member2.setJoinDate(str);
        member2.setSex('F');
        member2.setEthnicity("Asian");

        member2 = memberJdbc.saveMember(member2);
        
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
        grade2.setClub(club1);
        grade2.setArtId("1");
        grade2.setGradeId("3");
        grade2.setMartialArt("Kendo");
        grade2.setGrade("5 Kyu");
        grade2.setDateReceived(str);
        
        member2 = gradeJdbc.saveGrade(grade1, member2);
        //member2 = gradeJdbc.saveGrade(grade2, member2);
       
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
    
//    @BeforeEach
//    public void sendMessageSetup() throws SQLException, ClassNotFoundException {
//        String newEmail = member.getEmail();
//        Email email = new SimpleEmail();
//        email.setHostName("smtp.gmail.com");
//        email.setSmtpPort(587);
//        email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
//        email.setSSLOnConnect(true);
//
//            MimeMessage msg = createMessage(session, from, to, subject, body);
//            Transport.send(msg);
//    }
//    

    @AfterEach
    public void tearDown() {
        eventJdbc.deleteGraderEvent(event1, headGrad);
        for (int i = 0; i < event1.getOtherMembersOfGradingPanel().size(); i++) {
            eventJdbc.deleteGraderEvent(event1, event1.getOtherMembersOfGradingPanel().get(i));
        }
        eventJdbc.deleteEvent(event1);
        clubJdbc.deleteClub(club1);
        gradeJdbc.deleteGrade(grade1, member1.getMemberId());
        gradeJdbc.deleteGrade(grade1, member2.getMemberId());
        gradeJdbc.deleteGrade(grade2, member2.getMemberId());
        memberJdbc.deleteMember(member1);
        memberJdbc.deleteMember(member2);
    }

    @Test
    public void testSaveEvent() {
        List<Member> mList = emailJdbc.getPotentialMembers(event1);
        assertTrue(mList.size() == 2);
    }
    
    
    
    @Test
    public void testConfirmationEmail(){
        
    }
    
    
//    @Test
// public void testSendInRegualarJavaMail() throws MessagingException, IOException, EmailException {
//
//  String subject = "Test1";
//  String body = "Test Message1";
//  mailSender.sendMail("test.dest@nutpan.com", "test.src@nutpan.com", subject, body);
//  
//  Session session = Session.getDefaultInstance(new Properties());
//  Store store = session.getStore("pop3");
//  store.connect("nutpan.com", "test.dest", "password");
//
//  Folder folder = store.getFolder("inbox");
//
//  folder.open(Folder.READ_ONLY);
//  Message[] msg = folder.getMessages();
//
//  assertTrue(msg.length == 1);
//  assertEquals(subject, msg[0].getSubject());
//  assertEquals(body, msg[0].getContent());
//  folder.close(true);
//  store.close();
// }
//
// @Test
// public void testSendInMockWay() throws MessagingException, IOException, EmailException {
//
//  String subject = "Test2";
//  String body = "Test Message2";
//  
//  mailSender.sendMail("test.dest@nutpan.com", "test.src@nutpan.com", subject, body);
//  
//  List<Message> inbox = Mailbox.get("test.dest@nutpan.com");
//  
//  assertTrue(inbox.size() == 1);  
//  assertEquals(subject, inbox.get(0).getSubject());
//  assertEquals(body, inbox.get(0).getContent());
//
// }
//}
//    
    
}
