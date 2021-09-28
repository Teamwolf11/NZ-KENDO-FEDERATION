/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.AppRoles;
import domain.Grade;
import domain.Member;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lachl
 */
public class GradeDAOTest {

    private MemberJdbcDAO memberJdbc;
    private GradeJdbcDAO gradeJdbc;
    private Member member1;
    private Grade grade1;
    String str = "1986-04-08 12:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {

        memberJdbc = new MemberJdbcDAO();
        gradeJdbc = new GradeJdbcDAO();

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

        member1 = memberJdbc.saveMember(member1);
        
        grade1 = new Grade();
        grade1.setArtId("1");
        grade1.setGradeId("1");
        grade1.setMartialArt("Kendo");
        grade1.setGrade("7 Kyu");
        grade1.setNextGradeDate(dateTime);
        grade1.setDateReceived(dateTime);
        //grade1.setClub();        
    }

    @AfterEach
    public void tearDown() {
        memberJdbc.deleteMember(member1);
    }


    @Test
    public void testSaveGrade() {
        
    }
        member2 = memberJdbc.saveMember(member2);
        Member memberCheck = memberJdbc.getMember(member2.getMemberId());  //call member from db
        assertEquals(member2, memberCheck);  //Member check
        memberJdbc.deleteMember(member2);
    }