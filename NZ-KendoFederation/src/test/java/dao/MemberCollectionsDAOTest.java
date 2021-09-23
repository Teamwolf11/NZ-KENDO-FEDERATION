package dao;

import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.api.AfterEach;
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

        member1 = new Member();
        member1.setRole(role);
        member1.setNzkfId("6565");
        member1.setEmail("email@test.test1");
        member1.setPassword("qwerty");
        member1.setDob(dateTime);
        member1.setfName("Boris");
        member1.setmName("Horis");
        member1.setlName("Doloris");
        member1.setJoinDate(dateTime);
        member1.setSex('M');
        member1.setEthnicity("Asian");

        member2 = new Member();
        member2.setRole(role);
        member2.setNzkfId("6564");
        member2.setPassword("QWERTY");
        member2.setEmail("email@test.test2");
        member2.setDob(dateTime);
        member2.setfName("Jane");
        member2.setmName(null);
        member2.setlName("Doe");
        member2.setJoinDate(dateTime);
        member2.setSex('F');
        member2.setEthnicity("Asian");

//        member3 = new Member();
//        member3.setRole(role);
//        member3.setPassword("hello");
//        member3.setNzkfId("1234");
//        member3.setEmail("email@test.test3");
//        member3.setDob(dateTime);
//        member3.setfName("John");
//        member3.setmName(null);
//        member3.setlName("Doe");
//        member3.setJoinDate(null);
//        member3.setSex('F');
//        member3.setEthnicity("European");

        member1 = memberJdbc.saveMember(member1);
    }

    @AfterEach
    public void tearDown() {
        memberJdbc.deleteMember(member1);


        //userJdbc.deleteUser(member1.getUser());
        //userJdbc.deleteUser(member2.getUser());
        //member3 has no user
    }

//    @Test
//    public void testSaveNewMember() {
//        member2 = memberJdbc.saveNewMember(member2, user2);
//        Member memberCheck = memberJdbc.getMember(member2.getMemberId());  //call member from db
//        assertEquals(member2, memberCheck);  //Member check
//    }

    @Test
    public void testSaveMember() {
        member3 = memberJdbc.saveMember(member2);
        System.out.println(member2);
        Member memberCheck = memberJdbc.getMember(member2.getMemberId());  //call member from db
        assertEquals(member2, memberCheck);  //Member check
        memberJdbc.deleteMember(member2);
    }

    @Test
    public void testGetMember() {
        Member memberCheck = memberJdbc.getMember(member1.getMemberId());  //call member from db
        assertEquals(member1, memberCheck);  //Member check
    }

    @Test
    public void testDeleteMember() {
        Member memberCheck1 = memberJdbc.getMember(member1.getMemberId());  //call member from db
        assertEquals(member1, memberCheck1);  //Member check

        //Remove and check
        memberJdbc.deleteMember(member1);

       Member memberCheck2 = memberJdbc.getMember(member1.getMemberId());
       assertNull(memberCheck2);
    }
    
    @Test
    public void testSignIn(){
        //Assert member is returned if valid details are given
        Member memberCheck1 = memberJdbc.signIn(member1.getEmail(), member1.getPassword());
        assertEquals(member1, memberCheck1);  //Member check
        
        //Assert that nothing returns if email is invalid
        Member memberCheck2 = memberJdbc.signIn("invalid@test.co.nz", member1.getPassword());
        assertNull(memberCheck2);  //Member check
        
        //Assert that nothing returns if password is invalid
        Member memberCheck3 = memberJdbc.signIn(member1.getEmail(), "invaldi");
        assertNull(memberCheck3);  //Member check
        
         //Assert that nothing returns if everythings invalid
        Member memberCheck4 = memberJdbc.signIn("invalid@test.co.nz", "invaldi");
        assertNull(memberCheck4);  //Member check
    }
}
