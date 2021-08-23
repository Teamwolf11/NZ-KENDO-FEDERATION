package dao;

import domain.Member;
import domain.User;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
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
    String str = "1986-04-08 12:30";
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user","javaapp");
        props.setProperty("password","D4h/XW57%sw31");
       
        member = new MemberJdbcDAO(DriverManager.getConnection(url, props));
 
        User user1 = new User(1, "boris83", "qwerty", 1, 50);
        member1 = new Member();
        member1.setMemberId(1);
        member1.setUser(user1);
        member1.setNzkfId("6565");
        member1.setfName("Boris");
        member1.setmName("");
        member1.setlName("Doloris");
        member1.setJoinDate(dateTime);
        member1.setSex('M');
        member1.setEthnicity("Asian");
        
        User user2 = new User(2, "jane83", "qwerty", 2, 50);
        member2 = new Member();
        member2.setMemberId(2);
        member2.setUser(user2);
        member2.setNzkfId("6564");
        member2.setfName("Jane");
        member2.setmName("");
        member2.setlName("Doe");
        member2.setJoinDate(dateTime);
        member2.setSex('F');
        member2.setEthnicity("Asian");

        member.saveMember(member1);
        member.saveMember(member2);
        
    }
    
    
     @Test
	public void testSaveMember() {
            Member m = member.getMember(1);
            System.out.println(member1.toString());
            System.out.println(m.toString());
            assertEquals(m,member1);
            Member m1 = member.getMember(2);
            assertEquals(member2, m1);
            
	}

//	@Test
//	public void testGetProducts() {
//
//		assertThat(product.getProducts(), hasItem(product4));
//		assertThat(product.getProducts(), hasItem(product5));
//		assertThat(product.getProducts(), hasSize(2));
//		

    }
