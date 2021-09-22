package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.Event;
import domain.MartialArt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maaha Ahmad
 */
public class EventJdbcDAO implements EventDAO {

    Connection con = null;
    DatabaseConnector obj = new DatabaseConnector();

    public EventJdbcDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public Event getEvent(String eventID) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT event.*, club_id, name, location, contact_details FROM public.event LEFT JOIN public.club ON event.club_id = club.club_id INNER JOIN public.martial_art ON public.club.martial_art_id = martial_art.martial_art_id WHERE event_id = ?";

            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, eventID);
                ResultSet rs = stmt.executeQuery();

//    Event details
//    event_id SERIAL NOT NULL UNIQUE,                      // do we need grading_id? 
//    name character varying NOT NULL,                      // make event date field
//    club_id integer NOT NULL,                             // add name of graidng panel
//    venue character varying,                              // add head of grading panel
//    highest_grade_available character varying NOT NULL,
//    grading_id integer NOT NULL,

//    Club schema                                           // add FK to MartialArts
//    club_id SERIAL NOT NULL,                              // add clubLeaderId field
//    name character varying NOT NULL,                      // add no_of_members
//    location character varying,                           
//    contact_details character varying NOT NULL,

//    Club domain 
//    public String clubId;
//    public String clubLeaderId;
//    public String clubName;
//    public MartialArt martialArts;
//    public String contact;
//    public int noOfMembers;

//    Martial Arts domain                                   // do we need MartialArtsDate here? 
//    private String martialArtId;
//    private String name;             
//    private String grade;             
//    private Date dateReceived; 

//    Martial Arts schema
//    martial_art_id SERIAL NOT NULL,                       // add grade
//    name character varying NOT NULL,                      
//    "desc" character varying,

                if (rs.next()) {
                    // Event fields
                    String event_id = rs.getString("event_id");
                    String event_name = rs.getString("name");
                    String venue = rs.getString("venue");
                    String grade = rs.getString("highest_grade_available");
                    int grading_id = rs.getInt("grading_id");
                    Timestamp eventDate = rs.getTimestamp("date");
                    String nameOfGradingPanel = rs.getString("name_of_graidng_panel");
                    String headOfGradingPanel = rs.getString("head_of_grading_panel");
                    
                    Time startTime = rs.getTime("start_time");                          // new details for event - add to schema
                    Time endTime = rs.getTime("end_time");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp lastModified = rs.getTimestamp("last_modified");
                    String eventDescription = rs.getString("event_description");
                    
                    
                    // Related Club details
                    String club_id = rs.getString("club_id");
                    String clubLeaderId = rs.getString("club_leader_id");
                    String clubName = rs.getString("name");
                    String location = rs.getString("location");
                    String contact = rs.getString("contact_details");
                    int noMembers = rs.getInt("no_of_members");

                    // Martial Arts details
                    String martialArtId = rs.getString("martial_art_id");
                    String martialArtName = rs.getString("name");
                    String martialArtsGrade = rs.getString("grade");
                    Timestamp martialArtsDate = rs.getTimestamp("date");

                    MartialArt martialArt = new MartialArt(martialArtId, martialArtName, martialArtsGrade, martialArtsDate);
                    Club club = new Club(club_id, clubLeaderId, clubName, martialArt, contact, noMembers, location);

                    return new Event(event_id, event_name, club, venue, grade, eventDate.toLocalDateTime(), nameOfGradingPanel, headOfGradingPanel, eventDescription, startTime, endTime, createdAt.toLocalDateTime(), lastModified.toLocalDateTime());
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Event saveEvent(Event event) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            
            if (event.getCreatedAt()== null) {
                event.setCreatedAt(LocalDateTime.now());
            }
            
            LocalDateTime date1 = event.getEventDate();
            Timestamp timestamp = Timestamp.valueOf(date1);
            
            // convert join date into to java.sql.Timestamp
            LocalDateTime date2 = event.getCreatedAt();
            Timestamp createEvent = Timestamp.valueOf(date2);
            
            // convert join date into to java.sql.Timestamp
            LocalDateTime date3 = event.getLastModified();
            Timestamp lastmodified = Timestamp.valueOf(date3);

            String sql = "INSERT INTO public.event (event_id, name, club_id, venue, highest_grade_available, event_date, name_of_grading_panel, head_of_grading_panel, description, start_time, end_time, created_at, last_modified) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING event_id";
            
            try (PreparedStatement insertEventstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertEventstmt.setString(1, event.getEvent_id());
                insertEventstmt.setString(2, event.getName());
//                insertEventstmt.setString(3, event.getClub().getClubId());
                insertEventstmt.setString(4, event.getVenue());
                insertEventstmt.setString(5, event.getHighGrade());
                insertEventstmt.setTimestamp(6, timestamp);
                insertEventstmt.setString(7, event.getNameOfGradingPanel());
                insertEventstmt.setString(8, event.getHeadOfGradingPanel());
                insertEventstmt.setString(9, event.getEventDescription());
                insertEventstmt.setTime(10, event.getStartTime());
                insertEventstmt.setTime(11, event.getEndTime());
                insertEventstmt.setTimestamp(12, createEvent);
                insertEventstmt.setTimestamp(13, lastmodified);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    @Override
    public void deleteEvent(Event event) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
            String sql = "DELETE FROM public.event WHERE event_id = ?";
            
            try (PreparedStatement deleteEventstmt = con.prepareStatement(sql);) {
                deleteEventstmt.setInt(1, Integer.parseInt(event.getEvent_id()));

                deleteEventstmt.executeUpdate();
                con.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public void updateEvent(Event event) {
       try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
            String sql = "UPDATE FROM public.event SET event_id = ?, name = ?, club_id = ?, venue = ?, highest_grade_available = ?, event_date = ?, name_of_grading_panel = ?, head_of_grading_panel = ?, description = ?, start_time = ?, end_time = ?, created_at = ?, last_modified = ?";
            
            Timestamp eventDate = Timestamp.valueOf(event.getEventDate());
            Timestamp createdAt = Timestamp.valueOf(event.getCreatedAt());
            Timestamp lastModified = Timestamp.valueOf(event.getLastModified());
                    
            try (PreparedStatement updateEventstmt = con.prepareStatement(sql);) {
                updateEventstmt.setString(1, event.getEvent_id());
                updateEventstmt.setString(2, event.getName());
                updateEventstmt.setString(3, event.getEvent_id());
                updateEventstmt.setString(4, event.getVenue());
                updateEventstmt.setString(5, event.getHighGrade());
                updateEventstmt.setTimestamp(6, eventDate);
                updateEventstmt.setString(7, event.getNameOfGradingPanel());
                updateEventstmt.setString(8, event.getHeadOfGradingPanel());
                updateEventstmt.setString(9, event.getEventDescription());
                updateEventstmt.setTime(10, event.getStartTime());
                updateEventstmt.setTime(11, event.getEndTime());
                updateEventstmt.setTimestamp(12, createdAt);
                updateEventstmt.setTimestamp(13, lastModified);
                
                
                updateEventstmt.executeUpdate();
                con.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EventJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
