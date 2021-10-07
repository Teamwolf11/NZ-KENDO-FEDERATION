/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.AppRoles;
import domain.Club;
import domain.Grade;
import domain.Member;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lachl
 */
public class GradeDAOTest {

    private MemberJdbcDAO memberJdbc;
    private GradeJdbcDAO gradeJdbc;
    private ClubJdbcDAO clubJdbc;
    private Member member1;
    private Grade grade1;
    private Club club1;
    String str = "08/09/2002";

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        memberJdbc = new MemberJdbcDAO();
        gradeJdbc = new GradeJdbcDAO();
        clubJdbc = new ClubJdbcDAO();
        
        

        AppRoles role = new AppRoles("3", "General Member");
            

        member1 = new Member();
        member1.setRole(role);
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
        grade1.setArtId("1");
        grade1.setGradeId("1");
        grade1.setMartialArt("Kendo");
        grade1.setGrade("7 Kyu");
        grade1.setDateReceived(str);
        grade1.setClub(club1);        
    }

    @AfterEach
    public void tearDown() {
        memberJdbc.deleteMember(member1);
        clubJdbc.deleteClub(club1);
    }


    @Test
    public void testSaveGrade() {
        member1 = gradeJdbc.saveGrade(grade1, member1);
        Grade gradeCheck = gradeJdbc.getMemberGrade(grade1.getGradeId(), member1.getMemberId());  //call grade from db
        assertTrue(member1.getGrades().stream().anyMatch(o -> o.toString().equals(gradeCheck.toString())));
        gradeJdbc.deleteGrade(grade1, member1.getMemberId());
//    }
    }
}