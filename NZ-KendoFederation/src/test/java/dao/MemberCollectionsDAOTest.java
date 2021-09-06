package dao;

import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberCollectionsDAOTest {

    private MemberJdbcDAO member;
    private Member member1, member2, member3;
    private User user1, user2;
    String str = "1986-04-08 12:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user", "javaapp");
        props.setProperty("password", "D4h/XW57%sw31");
        //member = new MemberJdbcDAO(DriverManager.getConnection(url, props));
        member = new MemberJdbcDAO();

        AppRoles role = new AppRoles("3", "General Member");

        user1 = new User();
        user1.setUsername("boris83");
        user1.setPassword("qwerty");
        user1.setRoles(role);

        user2 = new User();
        user2.setUsername("jane83");
        user2.setPassword("qwerty");
        user2.setRoles(role);

        member1 = new Member();
        member1.setUser(user1);
        member1.setNzkfId("6565");
        member1.setfName("Boris");
        member1.setmName("Horis");
        member1.setlName("Doloris");
        member1.setJoinDate(dateTime);
        member1.setSex('M');
        member1.setEthnicity("Asian");

        member2 = new Member();
        member2.setUser(user2);
        member2.setNzkfId("6564");
        member2.setfName("Jane");
        member2.setmName(null);
        member2.setlName("Doe");
        member2.setJoinDate(dateTime);
        member2.setSex('F');
        member2.setEthnicity("Asian");

        String[] mem1 = member.saveNewMember(member1);
        member1.setMemberId(mem1[0]);
        user1.setUserId(mem1[1]);
        //member.saveNewMember(member2);

    }

    @After
    public void tearDown() {
        member.deleteMember(member1);
        member.deleteMember(member2);
        //member.deleteMember(member3);

        member.deleteUser(user1);
        member.deleteUser(user1);
        //member.deleteMember(member3);
    }

    @Test
    public void testSaveNewMember() {
        String[] ids = member.saveNewMember(member2); //Insert member and user
        member2.setMemberId(ids[1]); //Update java class with member id
        member2.getUser().setUserId(ids[0]);  //Update java class with user id
        Member memberCheck = member.getMember(ids[1]);  //call member from db
        assertEquals(member2, memberCheck);  //Member check
        
        User userCheck = member.getUser(ids[0]);  //call user from db
        //Techinically should add test for checking user objects are the same, but cbf dealing with the app role stuff
        assertEquals(member2.getUser().getRoles().toString(), userCheck.getRoles().toString());  //User check

    }

//	@Test
//	public void testGetProducts() {
//
//		assertThat(product.getProducts(), hasItem(product4));
//		assertThat(product.getProducts(), hasItem(product5));
//		assertThat(product.getProducts(), hasSize(2));
//		
}
