//package dao;
//
//import domain.AppRoles;
//import domain.User;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import org.junit.After;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Maaha Ahmad
// */
//public class UserDAOTest {
//
//    private UserJdbcDAO userJDBC;
//    private User user1, user2;
//    String str = "1986-04-08 12:30";
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//
//    @BeforeEach
//    public void setUp() throws SQLException, ClassNotFoundException {
//
//        userJDBC = new UserJdbcDAO();
//
//        AppRoles role = new AppRoles("3", "General Member");
//
//        user1 = new User();
//        user1.setEmail("boris83@test.test");
//        user1.setPassword("qwerty");
//        user1.setRoles(role);
//
//        user2 = new User();
//        user2.setEmail("jane83@test.test");
//        user2.setPassword("qwerty");
//        user2.setRoles(role); 
//        
//        user1.setUserId(userJDBC.saveUser(user1).getUserId()); //Insert User and give class the proper ID
//        
//    }
//
//    @After
//    public void tearDown() {
//        userJDBC.deleteUser(user1);
//        userJDBC.deleteUser(user2);
//    }
//
//    
//    /** 
//     * For some reason AssertEquals doesn't like checking the entire user class when AppRoles !null. Hence the expansion
//     */
//    @Test
//    public void testSaveUser() {
//        user2.setUserId(userJDBC.saveUser(user2).getUserId()); //Insert User and give class the proper ID
//        
//        User userCheck = userJDBC.getUser(user2.getUserId());  //call user from db
//        
//        assertEquals(user2.getUserId(), userCheck.getUserId());
//        assertEquals(user2.getEmail(), userCheck.getEmail());
//        assertEquals(user2.getPassword(), userCheck.getPassword());
//        assertEquals(user2.getRoles().getName(), userCheck.getRoles().getName());
//        assertEquals(user2.getRoles().getAppRoleId(), userCheck.getRoles().getAppRoleId());
//    }
//    
//     @Test
//    public void testDeleteUser() {
//        //Check user is first in the db
//        User userCheck1 = userJDBC.getUser(user1.getUserId());  //call user from db
//        //assertEquals(user1, userCheck1);
//        assertEquals(user1.getUserId(), userCheck1.getUserId());
//        assertEquals(user1.getEmail(), userCheck1.getEmail());
//        assertEquals(user1.getPassword(), userCheck1.getPassword());
//        assertEquals(user1.getRoles().getName(), userCheck1.getRoles().getName());
//        assertEquals(user1.getRoles().getAppRoleId(), userCheck1.getRoles().getAppRoleId());
//        
//        //Remove and check
//        userJDBC.deleteUser(user1);
//        
//        User userCheck2 = userJDBC.getUser(user1.getUserId());  //call user from db
//        assertNull(userCheck2);
//    } 
//    
//    @Test
//    public void testGetUser() {
//        User userCheck = userJDBC.getUser(user1.getUserId());  //call user from db
//        //assertEquals(user1, userCheck);
//        assertEquals(user1.getUserId(), userCheck.getUserId());
//        assertEquals(user1.getEmail(), userCheck.getEmail());
//        assertEquals(user1.getPassword(), userCheck.getPassword());
//        assertEquals(user1.getRoles().getName(), userCheck.getRoles().getName());
//        assertEquals(user1.getRoles().getAppRoleId(), userCheck.getRoles().getAppRoleId());
//        
//    }
//}
