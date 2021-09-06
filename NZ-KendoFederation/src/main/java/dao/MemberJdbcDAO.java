package dao;

import Database.DatabaseConnector;
import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.DatabaseConnector;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberJdbcDAO implements MemberDAO {

    public Connection con = null;
    public DatabaseConnector obj = new DatabaseConnector();

//    public MemberJdbcDAO(Connection con) throws SQLException, ClassNotFoundException {
//        this.con = con;
//        if (con != null) {
//            System.out.println("Connected to database");
//        }
//    }
//    public MemberJdbcDAO() throws SQLException, ClassNotFoundException {
//        DatabaseConnector db = new DatabaseConnector();
//        this.con =  db.connect();
//        if (con != null) {
//                System.out.println("Connected to database");
//            }
//    }
    public MemberJdbcDAO() {
    }

    @Override
    public Member getMember(String memberId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            //db.connect();
            con = db.connect();

            String sql = "SELECT * FROM public.member WHERE member_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(memberId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nzkfId = rs.getString("nzkf_membership_id");
                    String userID = Integer.toString(rs.getInt("user_id"));
                    Timestamp joinDate = rs.getTimestamp("join_date");
                    String fName = rs.getString("first_name");
                    String lName = rs.getString("last_name");
                    String mName = rs.getString("middle_name");
                    char sex = rs.getString("sex").charAt(0);
                    String ethnicity = rs.getString("ethnicity");

                    System.out.println("here now yay");
                    return new Member(memberId, nzkfId, getUser(userID), joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("error here 1");
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public String[] saveNewMember(Member member) {
        String[] ids = new String[2];

        try {
            DatabaseConnector db = new DatabaseConnector();
            //db.connect();
            con = db.connect();

            String sql1 = "INSERT INTO public.member (nzkf_membership_id, user_id, join_date, first_name, last_name, middle_name, sex, ethnicity) VALUES (?,?,?,?,?,?,?,?) RETURNING member_id";
            String sql2 = "INSERT INTO public.user (username, password, app_role_id) VALUES (?,?,?) RETURNING user_id";

            AppRoles roles = new AppRoles();  // Not returning anything to app role atm

            User user = member.getUser();

            // add a date to the member join date if one doesn't already exist
            if (member.getJoinDate() == null) {
                member.setJoinDate(LocalDateTime.now());
            }

            // convert join date into to java.sql.Timestamp
            LocalDateTime date = member.getJoinDate();
            Timestamp timestamp = Timestamp.valueOf(date);

            try (PreparedStatement insertMemberstmt = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS); PreparedStatement insertUserstmt = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);) {

                insertUserstmt.setString(1, user.getUsername());
                insertUserstmt.setString(2, user.getPassword());
                insertUserstmt.setInt(3, Integer.parseInt(user.getRoles().getAppRoleId()));

                int row = insertUserstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                //Get User_id from previous insert statement and add to member
                try (ResultSet generatedKeys = insertUserstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ids[0] = Integer.toString(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }

                    //insertMemberstmt.setInt(1, member.getMemberId());
                    insertMemberstmt.setString(1, member.getNzkfId());
                    insertMemberstmt.setInt(2, Integer.parseInt(ids[0]));
                    insertMemberstmt.setTimestamp(3, timestamp);
                    insertMemberstmt.setString(4, member.getfName());
                    insertMemberstmt.setString(5, member.getlName());
                    insertMemberstmt.setString(6, member.getmName());
                    insertMemberstmt.setString(7, String.valueOf(member.getSex()));
                    insertMemberstmt.setString(8, member.getEthnicity());

                    int row2 = insertMemberstmt.executeUpdate();

                    if (row2 == 0) {
                        throw new SQLException("Creating user failed, no rows affected.");
                    }

                    //Get member_id from previous insert statement and add to member
                    try (ResultSet generatedKeys2 = insertMemberstmt.getGeneratedKeys()) {
                        if (generatedKeys2.next()) {
                            ids[1] = Integer.toString(generatedKeys2.getInt(1));
                        } else {
                            throw new SQLException("Creating member failed, no ID obtained.");
                        }
                        con.close();
                        return ids;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUser(String userId) {
        String sql = "SELECT user_id,username,password, public.user.app_role_id, name FROM public.user, public.app_role WHERE user_id = ?  ORDER BY user_id";

        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            System.out.println("Finally here");
            stmt.setInt(1, Integer.parseInt(userId));
//            System.out.println("Finally here");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                //int member_id = rs.getInt("member_id");
                String roleId = Integer.toString(rs.getInt("app_role_id"));
                String roleName = rs.getString("name");

                System.out.println("user ID " + userId);
                AppRoles role = new AppRoles(roleId, roleName);
                return new User(userId, username, password, role);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("error here 3");
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public void saveUser(User user) {
        String sql2 = "INSERT INTO public.user (username, password, app_role_id) VALUES (?,?,?,?)";    // not inserting anything in approle atm

        try (PreparedStatement insertUserstmt = con.prepareStatement(sql2);) {
            //insertUserstmt.setInt(1, Integer.parseInt(user.getUserId()));
            insertUserstmt.setString(1, user.getUsername());
            insertUserstmt.setString(2, user.getPassword());
            //insertUserstmt.setInt(4, user.getMemberId());
            insertUserstmt.setInt(3, Integer.parseInt(user.getRoles().getAppRoleId()));

            insertUserstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error here 4");
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public AppRoles getAppRole(String roleId) {
        String sql = "SELECT * FROM public.app_role WHERE app_role_id = ? ORDER BY app_role_id";

        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            System.out.println("Finally here");
            stmt.setInt(1, Integer.parseInt(roleId));
//            System.out.println("Finally here");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString("name");
                String password = rs.getString("password");
                //int member_id = rs.getInt("member_id");
                String role = Integer.toString(rs.getInt("app_role_id"));

                return new AppRoles(role, roleName);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("error here 5");
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public void saveAppRole(AppRoles role) {
        //Implement later    
    }

    @Override
    public void deleteMember(Member member) {

    }

    @Override
    public void deleteUser(User user) {

    }

}
