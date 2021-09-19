package dao;

import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberCollectionsDAOTest {

    private MemberJdbcDAO memberJdbc;
    private UserJdbcDAO userJdbc;
    private Member member1, member2, member3;
    private User user1, user2;
    String str = "1986-04-08 12:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        memberJdbc = new MemberJdbcDAO();
        userJdbc = new UserJdbcDAO();

        AppRoles role = new AppRoles("3", "General Member");

        user1 = new User();
        user1.setEmail("boris83");
        user1.setPassword("qwerty");
        user1.setRoles(role);

        user2 = new User();
        user2.setEmail("jane83");
        user2.setPassword("qwerty");
        user2.setRoles(role);

        member1 = new Member();
        member1.setUser(user1);
        member1.setNzkfId("6565");
        member1.setEmail("email@test.test1");
        member1.setDob(dateTime);
        member1.setfName("Boris");
        member1.setmName("Horis");
        member1.setlName("Doloris");
        member1.setJoinDate(dateTime);
        member1.setSex('M');
        member1.setEthnicity("Asian");

        member2 = new Member();
        member2.setUser(user2);
        member2.setNzkfId("6564");
        member2.setEmail("email@test.test2");
        member2.setDob(dateTime);
        member2.setfName("Jane");
        member2.setmName(null);
        member2.setlName("Doe");
        member2.setJoinDate(dateTime);
        member2.setSex('F');
        member2.setEthnicity("Asian");

        member3 = new Member();
        member3.setUser(null);
        member3.setNzkfId("1234");
        member3.setEmail("email@test.test3");
        member3.setDob(dateTime);
        member3.setfName("John");
        member3.setmName(null);
        member3.setlName("Doe");
        member3.setJoinDate(null);
        member3.setSex('F');
        member3.setEthnicity("European");

        member1 = memberJdbc.saveNewMember(member1, user1);
    }

    @After
    public void tearDown() {
        memberJdbc.deleteMember(member1);
        memberJdbc.deleteMember(member2);
        memberJdbc.deleteMember(member3);

        userJdbc.deleteUser(member1.getUser());
        userJdbc.deleteUser(member2.getUser());
        //member3 has no user
    }

    @Test
    public void testSaveNewMember() {
        member2 = memberJdbc.saveNewMember(member2, user2);
        Member memberCheck = memberJdbc.getMember(member2.getMemberId());  //call member from db
        assertEquals(member2, memberCheck);  //Member check
    }

    @Test
    public void testSaveMember() {
        member3 = memberJdbc.saveMember(member3);
        System.out.println(member3);
        Member memberCheck = memberJdbc.getSimpleMember(member3.getMemberId());  //call member from db
        assertEquals(member3, memberCheck);  //Member check
    }

    @Test
    public void testGetMember() {
        Member memberCheck = memberJdbc.getSimpleMember(member1.getMemberId());  //call member from db
        assertEquals(member1, memberCheck);  //Member check
    }

    @Test
    public void testDeleteMember() {
        Member memberCheck1 = memberJdbc.getSimpleMember(member1.getMemberId());  //call member from db
        assertEquals(member1, memberCheck1);  //Member check

        //Remove and check
        memberJdbc.deleteMember(member1);

       Member memberCheck2 = memberJdbc.getMember(member1.getMemberId());
        assertNull(memberCheck2);

    }
}
