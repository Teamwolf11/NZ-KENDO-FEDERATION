package dao;

import domain.AppRoles;
import domain.Club;
import domain.Member;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Lachlan
 */
public class ClubDAOTest {

    private MemberJdbcDAO memberJdbc;
    private ClubJdbcDAO clubJdbc;
    private Member member1, member2;
    private Club club1;
    String str = "08/09/2002";

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        memberJdbc = new MemberJdbcDAO();
        clubJdbc = new ClubJdbcDAO();

        AppRoles role = new AppRoles("3", "General Member");

        member1 = new Member();
        member1.setRole(role);
        member1.setNzkfId("6565");
        member1.setEmail("email@test.test1");
        member1.setNzkfRenewDate(str);
        member1.setPassword("qwerty");
        member1.setDob(str);
        member1.setfName("Boris");
        member1.setmName("Horis");
        member1.setlName("Doloris");
        member1.setJoinDate(str);
        member1.setSex('M');
        member1.setEthnicity("Asian");

        member2 = new Member();
        member2.setRole(role);
        member2.setNzkfId("6564");
        member2.setNzkfRenewDate(str);
        member2.setPassword("QWERTY");
        member2.setEmail("email@test.test2");
        member2.setDob(str);
        member2.setfName("Jane");
        member2.setmName(null);
        member2.setlName("Doe");
        member2.setJoinDate(str);
        member2.setSex('F');
        member2.setEthnicity("Asian");

        member1 = memberJdbc.saveMember(member1);
        member2 = memberJdbc.saveMember(member2);
        
        club1 = new Club();
        club1.setClubName("TestClub1");
        club1.setLocation("Location");
        club1.setDescription("Desc");
        club1.setEmail("email");
        club1.setPhone("12345");
    }

    @AfterEach
    public void tearDown() {
        memberJdbc.deleteMember(member1);
        memberJdbc.deleteMember(member2);
   }

    @Test
    public void testSaveClub() {
        club1 = clubJdbc.saveClub(club1);
        Club clubCheck = clubJdbc.getClub(club1.getClubId());  //call club from db
        assertEquals(club1.toString(), clubCheck.toString());  //club check
        clubJdbc.deleteClub(club1);
    }


    @Test
    public void testDeleteClub() {
        club1 = clubJdbc.saveClub(club1);
        Club clubCheck1 = clubJdbc.getClub(club1.getClubId());  //call club from db
        assertEquals(club1.toString(), clubCheck1.toString());  //club check
        clubJdbc.deleteClub(club1);

        Club clubCheck2 = clubJdbc.getClub(club1.getClubId());  //call club from db
        assertNull(clubCheck2);
    }

    @Test
    public void testUpdateClub(){
        club1 = clubJdbc.saveClub(club1);
        Club clubCheck1 = clubJdbc.getClub(club1.getClubId());  //call club from db
        assertEquals(club1.toString(), clubCheck1.toString());  //club check
        
        club1.setClubName("update");
        club1 = clubJdbc.updateClub(club1);
        Club clubCheck2 = clubJdbc.getClub(club1.getClubId());  //call club from db
        club1.setClubName("TestClub1");
        assertNotEquals(club1.toString(), clubCheck2.toString());  //club check
        
        clubJdbc.deleteClub(club1);
    }
    
    @Test
    public void testClubRole(){
        club1 = clubJdbc.saveClub(club1);
        
        clubJdbc.saveClubRole(club1.getClubId(), member1, "leader");
        List<Member> roleCheck1 = clubJdbc.getClubMembers(club1.getClubId());
        assertTrue(roleCheck1.size() == 1);
        
        clubJdbc.saveClubRole(club1.getClubId(), member2, "general member");
        List<Member> roleCheck2 = clubJdbc.getClubMembers(club1.getClubId());
        assertTrue(roleCheck2.size() == 2);
        
        
        clubJdbc.deleteClubRole(club1, member1);
        clubJdbc.deleteClubRole(club1, member2);
        clubJdbc.deleteClub(club1);
    }
}
