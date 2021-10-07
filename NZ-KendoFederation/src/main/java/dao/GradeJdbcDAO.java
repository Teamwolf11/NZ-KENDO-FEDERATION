package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.Grade;
import domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachl
 */
public class GradeJdbcDAO implements GradeDAO {

    private Connection con;

    @Override
    public Grade getMemberGrade(String gradeId, String memberId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT member_grading.*, grading.name AS grading_name, martial_arts.martial_art_id, martial_arts.name AS martial_arts_name FROM public.member_grading JOIN public.grading ON grading.grading_id = member_grading.member_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id WHERE member_grading.member_id = ? AND member_grading.grading_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(memberId));
                stmt.setInt(2, Integer.parseInt(memberId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // grade fields
                    String artId = Integer.toString(rs.getInt("martial_art_id"));
                    String gradeName = rs.getString("grading_name");
                    String martialArt = rs.getString("martial_arts.name");
                    String dateReceived = rs.getString("date_received");
                    String nextDateAvailable = rs.getString("date_next_grade_available");
                    String eventId = Integer.toString(rs.getInt("event_id"));

                    String clubId = Integer.toString(rs.getInt("club_id"));
                    ClubJdbcDAO clubJdbc = new ClubJdbcDAO();
                    Club club = clubJdbc.getClub(clubId);
                    return new Grade(gradeName, martialArt, nextDateAvailable, dateReceived, gradeId, artId, club, eventId);

                } else {
                    return null;
                }
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

    @Override
    public Member saveGrade(Grade grade, Member member) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.member_grading (grading_id, date_received, member_id,club_id,event_id) VALUES (?,?,?,?,?) RETURNING date_next_grade_available";

            try (PreparedStatement insertGradestmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertGradestmt.setInt(1, Integer.parseInt(grade.getGradeId()));
                insertGradestmt.setString(2, grade.getDateReceived());
                insertGradestmt.setInt(3, Integer.parseInt(member.getMemberId()));
                insertGradestmt.setInt(4, Integer.parseInt(grade.getClub().getClubId()));
                if(grade.getEventId() != null){
                    insertGradestmt.setInt(5, Integer.parseInt(grade.getEventId()));
                } else {
                   insertGradestmt.setNull(5, java.sql.Types.INTEGER);
                }
                
                int row = insertGradestmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating grade failed, no rows affected.");
                }
                //Get NextGradeDate from previous insert statement and add to member
                try (ResultSet generatedKeys = insertGradestmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        grade.setNextGradeDate(generatedKeys.getString(1));
                        member.addGrade(grade);
                        return member;
                    } else {
                        System.out.println("6");
                        throw new SQLException("Updating nextGradeDate failed.");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    @Override
    public void deleteGrade(Grade grade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Grade> getAllForMember(Member member) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
