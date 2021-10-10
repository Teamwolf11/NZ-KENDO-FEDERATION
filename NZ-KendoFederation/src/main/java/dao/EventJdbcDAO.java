package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.Event;
import domain.Grade;
import domain.MartialArt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maaha Ahmad
 * @author Lachlan Campbell
 */
public class EventJdbcDAO implements EventDAO {

    Connection con = null;

    public EventJdbcDAO() {
    }

       @Override
    public Event getEvent(String eventId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT event.event_id,event.name AS e_name, club_role.member_id, event.club_id, event.venue, event.status, event.description AS e_desc, event.start_date_time, event.end_date_time, event.grading_id, club.mem_num, club.name AS c_name, club.location, club.email, club.phone, club.description AS c_desc, grading.name as g_name, martial_arts.martial_art_id as marts_id, martial_arts.name AS ma_name FROM public.event JOIN public.club ON event.club_id = club.club_id JOIN public.grading ON event.grading_id = grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id LEFT JOIN public.club_role ON club.club_id = club_role.club_id AND role_name = 'leader' WHERE event_id = ?";   
             try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(eventId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    
                    // grade fields
                    String gradeId = Integer.toString(rs.getInt("grading_id"));
                    String artId = Integer.toString(rs.getInt("marts_id"));
                    String gradeName = rs.getString("g_name");
                    String martialArt = rs.getString("ma_name");
                    Grade grade = new Grade(gradeName, martialArt, null, null, gradeId, artId, null, null, null);

                    //club
                    String clubId = rs.getString("club_id");
                    String clubLeaderId = rs.getString("member_id");             // fix
                    String clubName = rs.getString("c_name");
                    String location = rs.getString("location");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    int noMembers = rs.getInt("mem_num");
                    String description = rs.getString("c_desc");
                    Club club = new Club(clubId, clubLeaderId, clubName, phone, email, noMembers, location, description);
                    
                    //event
                    String venue =  rs.getString("venue");
                    String eName =  rs.getString("e_name");
                    String status =  rs.getString("status");
                    String desc =  rs.getString("e_desc");
                    String startDateTime =  rs.getString("start_date_time");
                    String endDateTime =  rs.getString("end_date_time");
                    
                    Event event = new Event(eventId, eName, club, venue, grade, null, null, desc, startDateTime, endDateTime, status);
                    
                    grade.setEventId(event.getEventId());
                    grade.setEventName(event.getName());
                    return event;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }
    
       @Override
    public Collection<Event> getEvents() {
        String sql = "SELECT grading.grading_id AS g_id, grading.martial_art_id AS gm_id, grading.name AS g_name, grading.grade_level AS g_level, martial_arts.name AS ma_name LEFT JOIN ON grading.ma";
//                + "*, *, martial_art_id from event, grading martial_arts order by event_id";
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
                
            List<Event> events = new ArrayList<Event>();
                
            while (rs.next()) {
                    // grade fields
                    String gradeId = Integer.toString(rs.getInt("g_id"));
                    String artId = Integer.toString(rs.getInt("gm_id"));
                    String gradeName = rs.getString("g_name");
                    String martialArt = rs.getString("ma_name");
                    Grade grade = new Grade(gradeName, martialArt, null, null, gradeId, artId, null, null, null);

                    //club
                    String clubId = rs.getString("club_id");
                    String clubLeaderId = rs.getString("member_id");             // fix
                    String clubName = rs.getString("c_name");
                    String location = rs.getString("location");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    int noMembers = rs.getInt("mem_num");
                    String description = rs.getString("c_desc");
                    Club club = new Club(clubId, clubLeaderId, clubName, phone, email, noMembers, location, description);
                    
                    //event
                    String eventid = Integer.toString(rs.getInt("event_id"));
                    String venue =  rs.getString("venue");
                    String eName =  rs.getString("e_name");
                    String status =  rs.getString("status");
                    String desc =  rs.getString("e_desc");
                    String startDateTime =  rs.getString("start_date_time");
                    String endDateTime =  rs.getString("end_date_time");
                    
                    Event event = new Event(eventid, eName, club, venue, grade, null, null, desc, startDateTime, endDateTime, status);
                    
                    grade.setEventId(event.getEventId());
                    grade.setEventName(event.getName());
                    
                    events.add(event);
                } 
                return events;
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    @Override
    public Event saveEvent(Event event) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.event (name, club_id, venue, description, start_date_time, end_date_time, grading_id, status) VALUES (?,?,?,?,?,?,?,?) RETURNING event_id";

            try (PreparedStatement insertEventstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertEventstmt.setString(1, event.getName());
                insertEventstmt.setInt(2, Integer.parseInt(event.getClub().getClubId()));
                insertEventstmt.setString(3, event.getVenue());
                insertEventstmt.setString(4, event.getDesc());
                insertEventstmt.setString(5, event.getStartDateTime());
                insertEventstmt.setString(6, event.getEndDateTime());
                insertEventstmt.setInt(7, Integer.parseInt(event.getHighestGradeAvailable().getGradeId()));
                insertEventstmt.setString(8, event.getStatus());

                int row = insertEventstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating grade failed, no rows affected.");
                }
                //Get EventId from previous insert statement and add to member
                try (ResultSet generatedKeys = insertEventstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(Integer.toString(generatedKeys.getInt(1)));
                        return event;
                    } else {
                        throw new SQLException("Updating nextGradeDate failed.");
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    
    @Override
    public void deleteEvent(Event event) {
       try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

           String sql = "DELETE FROM public.event WHERE event_id = ?";

            try (PreparedStatement deleteEventstmt = con.prepareStatement(sql);) {
                deleteEventstmt.setInt(1, Integer.parseInt(event.getEventId()));

                deleteEventstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    @Override
    public void updateEvent(Event event) {
//       try {
//            DatabaseConnector db = new DatabaseConnector();
//            con = db.connect();
//            
//            String sql = "UPDATE FROM public.event SET event_id = ?, name = ?, club_id = ?, venue = ?, highest_grade_available = ?, event_date = ?, name_of_grading_panel = ?, head_of_grading_panel = ?, description = ?, start_time = ?, end_time = ?, created_at = ?, last_modified = ?";
//            
//            Timestamp eventDate = Timestamp.valueOf(event.getEventDate());
//            Timestamp createdAt = Timestamp.valueOf(event.getCreatedAt());
//            Timestamp lastModified = Timestamp.valueOf(event.getLastModified());
//                    
//            try (PreparedStatement updateEventstmt = con.prepareStatement(sql);) {
//                updateEventstmt.setString(1, event.getEvent_id());
//                updateEventstmt.setString(2, event.getName());
//                updateEventstmt.setString(3, event.getEvent_id());
//                updateEventstmt.setString(4, event.getVenue());
//                updateEventstmt.setString(5, event.getHighGrade());
//                updateEventstmt.setTimestamp(6, eventDate);
//                updateEventstmt.setString(7, event.getNameOfGradingPanel());
//                updateEventstmt.setString(8, event.getHeadOfGradingPanel());
//                updateEventstmt.setString(9, event.getEventDescription());
//                updateEventstmt.setTime(10, event.getStartTime());
//                updateEventstmt.setTime(11, event.getEndTime());
//                updateEventstmt.setTimestamp(12, createdAt);
//                updateEventstmt.setTimestamp(13, lastModified);
//                
//                
//                updateEventstmt.executeUpdate();
//                con.close();
//            }
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } 
    }

}
