package dao;

import Database.DatabaseConnector;
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

    @Override
    public Grade getGrade(String gradeId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveGrade(Grade grade, Member member){
    try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "INSERT INTO public.grading_member (grading_id, date_received, member_id,club_id,event_id) VALUES (?,?,?,?,?)";

            try (PreparedStatement insertGradestmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertGradestmt.setString(1, grade.getGradeId());
                insertGradestmt.setTimestamp(2, Timestamp.valueOf(grade.getDateReceived()));
                insertGradestmt.setString(3, member.getMemberId());
                insertGradestmt.setString(4, grade.getClub().getClubId());
                insertGradestmt.setString(5, grade.getEventId());      
                        
                int row = insertGradestmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating grade failed, no rows affected.");
                }
                    member.getGrades().add(grade);
                    con.close();
                }
            } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
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