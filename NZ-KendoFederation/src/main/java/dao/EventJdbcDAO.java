//package dao;
//
//import Database.DatabaseConnector;
//import domain.Event;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// *
// * @author Maaha Ahmad
// */
//public class EventJdbcDAO implements EventDAO {
//
//    Connection con = null;
//    DatabaseConnector obj = new DatabaseConnector();
//
//    public EventJdbcDAO() throws SQLException, ClassNotFoundException {
//        //this.con = obj.getConnection();
//    }
//
//    @Override
//    public Event getEvent(String eventID) {
//
//        String sql = "SELECT * FROM public.event WHERE event_id = ?";
//
//        try (PreparedStatement stmt = con.prepareStatement(sql);) {
//            stmt.setString(1, eventID);
//            ResultSet rs = stmt.executeQuery();
//            // String Event_id, String name, Club relatedClub, String venue, String highGrade
//
//            if (rs.next()) {
//                int event_id = rs.getInt("event_id");
//                String event_name = rs.getString("name");
//                int club_id = rs.getInt("club_id");
//                String venue = rs.getString("venue");
//                String grade = rs.getString("highest_grade_available");
//                int grading_id = rs.getInt("grading_id");
//
////             return new Event (event_id, event_name, club_id, venue, grade, grading_id);
//             
//            } else {
//                return null;
//            }
//
//        } catch (SQLException ex) {
//            throw new DAOException(ex.getMessage(), ex);
//        }
//
//        return null;
//
//    }
//
//    @Override
//    public void saveEvent(Event event) {
//
//    }
//}
