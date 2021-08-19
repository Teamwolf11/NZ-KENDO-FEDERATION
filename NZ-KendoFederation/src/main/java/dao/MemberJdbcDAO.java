package dao;

import Database.DatabaseConnector;
import domain.Member;
import domain.User;
import java.sql.*;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberJdbcDAO implements MemberDAO {

    Connection con = null;
    DatabaseConnector obj = new DatabaseConnector();

    public MemberJdbcDAO() throws SQLException, ClassNotFoundException {
        this.con = obj.getConnection();
    }

    @Override
    public Member getMember(String memberId) {
        String sql = "SELECT * FROM public.member WHERE member_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
//            User user = new User();
            if (rs.next()) {
                String id = rs.getString("member_id");
                String nzkfId = rs.getString("nzkf_membership_id");
                String userID = rs.getString("user_id");
                Timestamp joinDate = rs.getTimestamp("join_date");
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String mName = rs.getString("middle_name");
                char sex = rs.getString("sex").charAt(0);
                String ethnicity = rs.getString("ethnicity");

//                return new Member (id, nzkfId, user.getUserId(userID), joinDate, fName, lName, mName, sex, ethnicity);

            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        
        return null;
    }

    @Override
    public void saveMember(Member member) {
        // String id, String nzkfId, User user, Date joinDate, String fName, String lName, String mName, char sex, String ethnicity
        String sql = "INSERT INTO public.member (member_id, nzkf_membership_id, user_id, join_date, first_name, last_name, middle_name, sex, ethnicity) VALUES (?,?,?,?,?,?,?,?,?)";
 
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            
            stmt.setString(1, member.getMemberId());
            stmt.setString(2, member.getNzkfId());
            stmt.setString(3, member.getUser().getUserId());
            stmt.setTimestamp(4, member.getJoinDate());
            stmt.setString(5, member.getfName());
            stmt.setString(6, member.getlName());
            stmt.setString(7, member.getmName());
            stmt.setString(8, String.valueOf(member.getSex()));
            stmt.setString(9, member.getEthnicity());

            stmt.executeQuery();
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

}
