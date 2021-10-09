package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.Event;
import domain.Grade;
import domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachl
 */
public class EmailJdbcDAO {

    private Connection con;

    /**
     * Gets individual grade
     *
     * @param gradeId
     * @param memberId
     * @return returns grade
     */
    public List<Member> getPotentialMembers(Event event) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT DISTINCT gv.*, m.*, m.member_id AS m_id, ma.name AS ma_name, ma.martial_art_id, g.name FROM email.grading_vault gv JOIN public.member m ON gv.member_id = m.member_id  JOIN public.grading g ON gv.grading_id = g.grading_id JOIN public.martial_arts ma ON g.martial_art_id = ma.martial_art_id WHERE current_grade = 'Y' AND grade_level < (SELECT grade_level FROM public.grading WHERE grading_id = ?) AND ma.martial_art_id = ?";            
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                
                stmt.setInt(1, Integer.parseInt(event.getHighestGradeAvailable().getGradeId()));
                stmt.setInt(2, Integer.parseInt(event.getHighestGradeAvailable().getArtId()));
                ResultSet rs = stmt.executeQuery();
                
                System.out.println(stmt);

                List<Member> mList = new ArrayList<>();
                
                while (rs.next()) {
                    // grade fields
                    String gradeId = Integer.toString(rs.getInt("grading_id"));
                    String artId = Integer.toString(rs.getInt("martial_art_id"));
                    String gradeName = rs.getString("name");
                    String martialArt = rs.getString("ma_name");
                    String dateReceived = rs.getString("date_received");
                    String nextDateAvailable = rs.getString("date_next_grade_available");
                    String eventId = Integer.toString(rs.getInt("event_id"));
                    if (eventId.equals("0"))  eventId = null;

                    Grade grade = new Grade(gradeName, martialArt, nextDateAvailable, dateReceived, gradeId, artId, null, null, eventId);

                    
                    String memberId = Integer.toString(rs.getInt("m_id"));
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
                    
                    Member member =  new Member(memberId, nzkfId, nzkfIdRenewDate, null, email, password, dob, joinDate, fName, lName, mName, sex, ethnicity, phoneNum);
                    member.addGrade(grade);
                    mList.add(member);
                }
                return mList;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }
}
