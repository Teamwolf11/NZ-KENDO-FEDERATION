package dao;

import Database.DatabaseConnector;
import domain.AppRoles;
import domain.Member;
import domain.User;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberJdbcDAO implements MemberDAO {

    Connection con = null;
    DatabaseConnector obj = new DatabaseConnector();

    public MemberJdbcDAO(Connection con) throws SQLException, ClassNotFoundException {
        this.con = con;
    }

    @Override
    public Member getMember(int memberId) {
//        String sql = "SELECT * FROM public.member WHERE member_id = ?";
        String sql = "SELECT * FROM public.member WHERE member_id = ? ORDER BY user_id";

        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
                       
            if (rs.next()) {
                String nzkfId = rs.getString("nzkf_membership_id");
                int userID = rs.getInt("user_id");
                Timestamp joinDate = rs.getTimestamp("join_date");
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String mName = rs.getString("middle_name");
                char sex = rs.getString("sex").charAt(0);
                String ethnicity = rs.getString("ethnicity");
                
                System.out.println("here now yay");
                return new Member (memberId, nzkfId, getUser(userID) , joinDate.toLocalDateTime(), fName, lName, mName, sex, ethnicity);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("error here 1");
            throw new DAOException(ex.getMessage(), ex);
        }
    }


    @Override
    public void saveMember(Member member) {
       // String userId, String username, String password, int memberId, List<AppRoles> roles
        String sql1 = "INSERT INTO public.member (member_id, nzkf_membership_id, user_id, join_date, first_name, last_name, middle_name, sex, ethnicity) VALUES (?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO public.user (user_id, username, password, member_id, app_role_id) VALUES (?,?,?,?,?)";    // not inserting anything in approle atm
        
        AppRoles roles = new AppRoles();
        
        
        User user = member.getUser();

        // add a date to the member join date if one doesn't already exist
        if (member.getJoinDate() == null) {
            member.setJoinDate(LocalDateTime.now());
        }

        // convert join date into to java.sql.Timestamp
        LocalDateTime date = member.getJoinDate();
        Timestamp timestamp = Timestamp.valueOf(date);

        try (PreparedStatement insertMemberstmt = con.prepareStatement(sql1);PreparedStatement insertUserstmt = con.prepareStatement(sql2);) {

            
            insertUserstmt.setInt(1, user.getUserId());
            insertUserstmt.setString(2, user.getUsername());
            insertUserstmt.setString(3, user.getPassword());
            insertUserstmt.setInt(4, user.getMemberId());
            insertUserstmt.setInt(5, user.getRoles());
            
            int row = insertUserstmt.executeUpdate();
           
            insertMemberstmt.setInt(1, member.getMemberId());
            insertMemberstmt.setString(2, member.getNzkfId());
            insertMemberstmt.setInt(3, user.getUserId());
            insertMemberstmt.setTimestamp(4, timestamp);
            insertMemberstmt.setString(5, member.getfName());
            insertMemberstmt.setString(6, member.getlName());
            insertMemberstmt.setString(7, member.getmName());
            insertMemberstmt.setString(8, String.valueOf(member.getSex()));
            insertMemberstmt.setString(9, member.getEthnicity());

            int row2 = insertMemberstmt.executeUpdate();
           

        } catch (SQLException ex) {
            System.out.println("error here 2");
            throw new DAOException(ex.getMessage(), ex);
            
        }

    }

    @Override
    public User getUser(int userId) {
        String sql = "SELECT * FROM public.user WHERE user_id = ? ORDER BY user_id";
              
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            System.out.println("Finally here");
            stmt.setInt(1, userId);
//            System.out.println("Finally here");
            ResultSet rs = stmt.executeQuery();
                  
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int member_id = rs.getInt("member_id");
                int roles = rs.getInt("app_role_id");
                
                System.out.println("user ID " + userId);
                return new User (userId, username, password, member_id, roles);
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
        String sql2 = "INSERT INTO pulic.user (user_id, username, password, member_id, app_role_id) VALUES (?,?,?,?,?)";    // not inserting anything in approle atm
        
        try (PreparedStatement insertUserstmt = con.prepareStatement(sql2);) {
            insertUserstmt.setInt(1, user.getUserId());
            insertUserstmt.setString(2, user.getUsername());
            insertUserstmt.setString(3, user.getPassword());
            insertUserstmt.setInt(4, user.getMemberId());
             insertUserstmt.setInt(5, user.getRoles());
            
            insertUserstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error here 4");
            throw new DAOException(ex.getMessage(), ex);
        }
        
    }

}
