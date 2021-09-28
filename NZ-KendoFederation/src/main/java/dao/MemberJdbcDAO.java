package dao;

import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.DatabaseConnector;
import java.util.ArrayList;
import java.util.List;

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
                    Timestamp joinDate = rs.getTimestamp("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    Timestamp dob = rs.getTimestamp("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);
                    //User user = new User(userID, username, password, role);

                    return new Member(memberId, role, nzkfId, email, password, dob.toLocalDateTime(), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity, phoneNum);
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

            // add a date to the member join date if one doesn't already exist
            if (member.getJoinDate() == null) {
                member.setJoinDate(LocalDateTime.now());
            }

            // convert join date into to java.sql.Timestamp
            LocalDateTime date = member.getJoinDate();
            Timestamp timestamp = Timestamp.valueOf(date);

            String sql = "INSERT INTO public.member (nzkf_membership_id, app_role_id, email, password, date_of_birth, join_date, first_name, last_name, middle_name, sex, ethnicity, phone_num) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING member_id";
            try (PreparedStatement insertMemberstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertMemberstmt.setString(1, member.getNzkfId());
                insertMemberstmt.setInt(2, Integer.parseInt(member.getRole().getAppRoleId()));
                insertMemberstmt.setString(3, member.getEmail());
                insertMemberstmt.setString(4, member.getPassword());
                insertMemberstmt.setTimestamp(5, Timestamp.valueOf(member.getDob()));
                insertMemberstmt.setTimestamp(6, timestamp);
                insertMemberstmt.setString(7, member.getfName());
                insertMemberstmt.setString(8, member.getlName());
                insertMemberstmt.setString(9, member.getmName());
                insertMemberstmt.setString(10, String.valueOf(member.getSex()));
                insertMemberstmt.setString(11, member.getEthnicity());
                insertMemberstmt.setString(12, member.getPhoneNum());
                //if (member.getMemberId() != null) insertMemberstmt.setInt(13, Integer.parseInt(member.getMemberId()));

                int row = insertMemberstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                //Get User_id from previous insert statement and add to member
                try (ResultSet generatedKeys = insertMemberstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        member.setMemberId(Integer.toString(generatedKeys.getInt(1)));
                        con.close();
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
                    Timestamp joinDate = rs.getTimestamp("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    String email = rs.getString("email");
                    Timestamp dob = rs.getTimestamp("date_of_birth");
                    String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);

                    mem.add(new Member(memberId, role, nzkfId, email, password, dob.toLocalDateTime(), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity, phoneNum));
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
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    //Member fields
                    String memberId = Integer.toString(rs.getInt("member_id"));
                    String nzkfId = rs.getString("nzkf_membership_id");
                    Timestamp joinDate = rs.getTimestamp("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");
                    //String email = rs.getString("email");
                    Timestamp dob = rs.getTimestamp("date_of_birth");
                    //String password = rs.getString("password");
                    String phoneNum = rs.getString("phone_num");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();

                    AppRoles role = new AppRoles(roleId, roleName);
                    //User user = new User(userID, username, password, role);

                    return new Member(memberId, role, nzkfId, email, password, dob.toLocalDateTime(), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity, phoneNum);
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
     * A method for creating an entirely new member. DO NOT USE THIS METHOD IF
     * TRYING TO CONNECT USER WITH PREVIOUS MEMBER!!
     *
     * Creates a new user through a select statement. This statement returns the
     * generated user id. This Id is taken by the member insert to connect the
     * two.
     *
     * @author Lachlan (Plagirised from Maaha)
     * @param member
     * @return returns member with a reference to the user class
     */
//    @Override
//    public Member saveNewMember(Member member, User user) {
//        UserJdbcDAO userJdbc = new UserJdbcDAO();
//
//        user = userJdbc.saveUser(user);
//        member.setUser(user);
//        member = saveMember(member);
//        
//        return member;
//        
//    }
    /**
     * Gets a member from the db. Used when user is not needed. User and
     * AppRoles is NULL in the java class
     *
     * @author Lachlan
     * @param memberId
     * @return returns member with user as null
     */
//    @Override
//    public Member getSimpleMember(String memberId) { //Used when only the member class is needed - User is NULL
//        //Creates a connection to the db
//        try {
//            DatabaseConnector db = new DatabaseConnector();
//            con = db.connect();
//
//            String sql = "SELECT * FROM public.member WHERE member_id = ?";
//
//            try (PreparedStatement stmt = con.prepareStatement(sql);) {
//                stmt.setInt(1, Integer.parseInt(memberId));
//                ResultSet rs = stmt.executeQuery();
//
//                if (rs.next()) {
//                    String nzkfId = rs.getString("nzkf_membership_id");
//                    Timestamp joinDate = rs.getTimestamp("join_date");
//                    String fName = rs.getString("first_name");
//                    String lName = rs.getString("last_name");
//                    String mName = rs.getString("middle_name");
//                    char sex = rs.getString("sex").charAt(0);
//                    String ethnicity = rs.getString("ethnicity");
//                    String email = rs.getString("email");
//                    Timestamp dob = rs.getTimestamp("date_of_birth");
//
//                    con.close();
//                    System.out.println();
//                    return new Member(memberId, nzkfId, null, email, dob.toLocalDateTime(), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity);                   
//                } else {
//                    con.close();
//                    return null;
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    /**
     * For pulling up all the member data and other relevant account info for
     * this person
     *
     * @author Lachlan
     * @return return all members, their users and app roles
     */
    /**
     * For pulling up all the member data without user and app role
     *
     * @author Lachlan
     * @return return all members without their users and app roles
     */
//    public List<Member> getAllSimple() {
//        List<Member> mem = new ArrayList<Member>();
//        try {
//            DatabaseConnector db = new DatabaseConnector();
//            con = db.connect();
//
//            String sql = "SELECT * FROM public.member WHERE member_id = ?";
//            
//            try (PreparedStatement stmt = con.prepareStatement(sql);) {
//                ResultSet rs = stmt.executeQuery();
//
//                while (rs.next()) {
//                    String memberId = Integer.toString(rs.getInt("member_id"));
//                    String nzkfId = rs.getString("nzkf_membership_id");
//                    Timestamp joinDate = rs.getTimestamp("join_date");
//                    String fName = rs.getString("first_name");
//                    String lName = rs.getString("last_name");
//                    String mName = rs.getString("middle_name");
//                    char sex = rs.getString("sex").charAt(0);
//                    String ethnicity = rs.getString("ethnicity");
//                    String email = rs.getString("email");
//                    Timestamp dob = rs.getTimestamp("date_of_birth");
//                    
//                    
//                     mem.add(new Member(memberId, nzkfId, null, email, dob.toLocalDateTime(), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity));
//                }
//                
//                con.close();
//                return mem;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
