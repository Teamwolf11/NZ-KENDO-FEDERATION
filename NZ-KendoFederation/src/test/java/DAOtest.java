/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.*;
import java.text.SimpleDateFormat;  
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author benmcmahon
 */
public class DAOtest {
    private User userOne;
    private User userTwo;
    private User userThree;
    
    private Member memberOne;
    private Member memberTwo;
    private Member memberThree;
    
    private AppRoles def = new AppRoles();

    public DAOtest() {
    }
    
    @Before
    public void setUp() {
        Date date = new Date();
                
        this.userOne = new User("username1", "pass1", def);
	this.userTwo = new User("username2", "pass2", def);
	this.userThree = new User("username3", "pass3", def);
        
        this.memberOne = new Member("nzkf1", userOne, date, "Fname1", "Lname1", "Mname1", 'M', "NZ European");
        this.memberTwo = new Member("nzkf2",userTwo, date, "Fname2", "Lname2", "Mname2", 'F', "Maori");
        this.memberThree = new Member("nzkf3",userThree, date, "Fname3", "Lname3",null, 'O', null);
        
        
        dao.saveUser(userOne);
	dao.saveUser(userTwo);
        dao.saveUser(userTwo);
        
        dao.saveMember(memberOne);
	dao.saveMember(memberTwo);
        dao.saveMember(memberTwo);
    }
    
    @After
    public void tearDown() {
        dao.deleteMember(memberOne);
	dao.deleteMember(memberTwo);
	dao.deleteMember(memberThree);
        
        dao.deleteUser(userOne);
	dao.deleteUser(userTwo);
	dao.deleteUser(userThree);
    }
    
    @Test
	public void testUpdateMemberDetails() {
            memberOne.setfName("ben");
            dao.updateMember(memberOne);
            Member retrieved = dao.getMember(memberOne.getMemberId());
            
            assertEquals("Retrieved user should be the same",
                            memberOne, retrieved);
            assertEquals("Retrieved user name should have name ben",
                             memberOne.getfName(), "ben");
	}
        
        @Test
	public void testGetUserDetails() {
		assertEquals(userOne.getUserId(), u.getUserId());
                assertEquals(userOne.getUsername(), u.getName());
		assertEquals(userOne.getContact(), u.getContact());
                assertEquals(userOne.getSex(), u.getSex());
		assertEquals(userOne.getEthnicity(), u.getEthnicity());
		assertEquals(userOne.getMartialArt(), u.getMartialArt());
	}
        
        @Test
	public void testDaoGetAllUsers() {
		Collection<User> users = dao.getUsers();
		assertTrue("userOne should exist", users.contains(userOne));
		assertTrue("userTwo should exist", users.contains(userTwo));
                assertTrue("userThree should exist", users.contains(userThree));
                assertEquals("Only 3 users in result", 3, users.size());
        }
        
        @Test
	public void testDaoGetAllMembers() {
		Collection<Member> members = dao.getMembers();
		assertTrue("userOne should exist", members.contains(memberOne));
		assertTrue("userTwo should exist", members.contains(memberTwo));
                assertTrue("userThree should exist", members.contains(memberThree));
                assertEquals("Only 3 users in result", 3, members.size());  //Potenitally will get more if db populated?
        }
        
}