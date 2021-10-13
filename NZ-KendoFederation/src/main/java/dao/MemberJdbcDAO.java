package dao;

import domain.AppRoles;
import domain.Member;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.DatabaseConnector;
import java.util.ArrayList;
import java.util.List;
//import dao.*;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberJdbcDAO implements MemberDAO {

    public Connection con = null;
    public MemberJdbcDAO() {
    }

    /**
     * Pulls member from the db through SELECT statement. Also contains User and
     * AppRoles. Use this method when all this information is needed.
     *
     * @author Lachlan (Plagirised from Maaha)
     * @param memberId
     * @return member object pulled from db
     */
    @Override
    public Member getMember(String memberId) {

        //Creates a connection to the db
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "SELECT member.*, app_role.app_role_id, name FROM public.member INNER JOIN public.app_role ON member.app_role_id = app_role.app_role_id WHERE member_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(memberId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    //Member fields
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String joinDate = rs.getString("join_date");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);
                    //User user = new User(userID, username, password, role);

                    return new Member(memberId, nzkfId, nzkfIdRenewDate, role, email, password, dob, joinDate, fName, lName, mName, sex, ethnicity, phoneNum);
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Saves a member to the db. Can be used when user is null or not. Keep in
     * mind this does not create a user row in the db. Use saveNewMember() if
     * wanting to insert both
     *
     * @author lachlan
     * @param member
     * @return returns member with new member Id
     */
    @Override
    public Member saveMember(Member member) {
        
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.member (email, password, date_of_birth, join_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING member_id";
            try (PreparedStatement insertMemberstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertMemberstmt.setString(1, member.getEmail().toLowerCase());
                insertMemberstmt.setString(2, member.getPassword());
                insertMemberstmt.setString(3, member.getDob());
                insertMemberstmt.setString(4, member.getJoinDate());
                insertMemberstmt.setString(5, member.getfName());
                insertMemberstmt.setString(6, member.getlName());
                insertMemberstmt.setString(7, member.getmName());
                insertMemberstmt.setString(8, String.valueOf(member.getSex()));
                insertMemberstmt.setString(9, member.getEthnicity());
                insertMemberstmt.setString(10, member.getPhoneNum());
                
                
                int row = insertMemberstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                //Get User_id from previous insert statement and add to member
                try (ResultSet generatedKeys = insertMemberstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        member.setMemberId(Integer.toString(generatedKeys.getInt(1)));
                        con.close();
                        
                        EmailJdbcDAO emailDao = new EmailJdbcDAO();
                        emailDao.sendConfirmationEmail(member);
                        
                        return member;
                       
                    } else {
                        con.close();
                        throw new SQLException("Updating id failed, no ID obtained.");
                    }
                    
                    
            
                    
                }
                
                
            }
            
          

        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Deletes a member from the db
     *
     * @author Lachlan
     * @param member
     */
    @Override
    public void deleteMember(Member member) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "DELETE FROM public.member WHERE member_id = ?";

            try (PreparedStatement deleteMemberstmt = con.prepareStatement(sql);) {
                deleteMemberstmt.setInt(1, Integer.parseInt(member.getMemberId()));

                deleteMemberstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * NEEDS TO BE UPDATED Get all members from the db
     *
     * @author Lachlan
     * @param member
     */
    @Override
    public List<Member> getAll() {
        List<Member> mem = new ArrayList<Member>();
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "SELECT member.*, public.user.email as user_email, password, app_role.app_role_id, name FROM public.member LEFT JOIN public.user ON member.user_id = public.user.user_id INNER JOIN public.app_role ON public.user.app_role_id = app_role.app_role_id";
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    //Member fields
                    String memberId = Integer.toString(rs.getInt("member_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String joinDate = rs.getString("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);

                    mem.add(new Member(memberId, nzkfId, nzkfIdRenewDate, role, email, password, dob, joinDate, fName, lName, mName, sex, ethnicity, phoneNum));
                }

                con.close();
                return mem;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Used for member sign in. Takes sign in details and returns a member if the sign is accurate.
     * 
     * @author Lachlan
     * @param email
     * @param password
     * @return 
     */
    @Override
    public Member signIn(String email, String password) {
        //Creates a connection to the db
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "SELECT member.*, app_role.app_role_id, name FROM public.member INNER JOIN public.app_role ON member.app_role_id = app_role.app_role_id WHERE email = ? AND password = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, email.toLowerCase());
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    //Member fields
                    String memberId = Integer.toString(rs.getInt("member_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String joinDate = rs.getString("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    //String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    //String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);
                    //User user = new User(userID, username, password, role);

                    return new Member(memberId, nzkfId, nzkfIdRenewDate, role, email, password, dob, joinDate, fName, lName, mName, sex, ethnicity, phoneNum);
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Member signInSimple(String email) {
        //Creates a connection to the db
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "SELECT member.*, app_role.app_role_id, name FROM public.member INNER JOIN public.app_role ON member.app_role_id = app_role.app_role_id WHERE email = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, email.toLowerCase());
                //stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    //Member fields
                    String memberId = Integer.toString(rs.getInt("member_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String nzkfIdRenewDate = rs.getString("nzkf_membership_renew_date");
                    String joinDate = rs.getString("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    //String email = rs.getString("email");
                    String dob = rs.getString("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);

                    return new Member(memberId, nzkfId, nzkfIdRenewDate, role, email, password, dob, joinDate, fName, lName, mName, sex, ethnicity, phoneNum);
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

//    /**
//     *
//     * @param member
//     * @return
//     */
//     
//    public void email(Member member) {
//          //  CompletableFuture.runAsync(() -> {
//                String newEmail = member.getEmail();
//                Email email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(587);
//                email.setAuthenticator(new DefaultAuthenticator("benjaminm.12184", "Y3y3dqax"));
//                email.setSSLOnConnect(true);
//
//                try {
//                    email.setFrom("benjaminm.12184@gmail.com");
//                    email.setSubject("New Member #" + member.getMemberId());
//                    email.setMsg("New Member sign up details for " + member.getfName() + 
//                            " " + member.getlName() + "Your username and password are" + 
//                            member.getPassword() + member.getEmail());
//
//                    email.addTo("benjaminm.12184@gmail.com");
//                    email.send();
//                } catch (EmailException ex) {
//                    Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//        //    });
//	}
//   
//    
//    
//    public void sendGradingEmail(Email email){
//        Runnable gradingEmail = () -> {
//            System.out.println("ben"); 
//        };
//        ScheduledFuture<?> emailHandle = scheduler.scheduleAtFixedRate(gradingEmail, 20, 20, SECONDS);
//        Runnable canceller = () -> emailHandle.cancel(false);
//        scheduler.schedule(canceller, 1, HOURS);
//    }
//    }
//
