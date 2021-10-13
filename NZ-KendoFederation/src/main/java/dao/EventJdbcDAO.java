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

            String sql = "SELECT event.event_id,event.name AS e_name, club_role.member_id, event.club_id, event.venue, event.status, event.description AS e_desc, event.start_date_time, event.end_date_time, event.grading_id, club.mem_num, club.name AS c_name, club.location, club.email, club.phone, club.description AS c_desc, grading.name as g_name, grading_panel.grading_member_name as gm_name, martial_arts.martial_art_id as marts_id, martial_arts.name AS ma_name FROM public.event JOIN public.club ON event.club_id = club.club_id JOIN public.grading ON event.grading_id = grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id LEFT JOIN public.club_role ON club.club_id = club_role.club_id AND role_name = 'leader' LEFT JOIN public.grading_panel ON grading_panel.event_id = event.event_id AND grading_panel.grading_role = 'Head' WHERE event.event_id = ?";
            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
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
                    String venue = rs.getString("venue");
                    String eName = rs.getString("e_name");
                    String status = rs.getString("status");
                    String desc = rs.getString("e_desc");
                    String startDateTime = rs.getString("start_date_time");
                    String endDateTime = rs.getString("end_date_time");
                    String headGrader = rs.getString("gm_name");
                    List<String> otherGradingPanel = get2ndEventGrader(eventId);
                                 
                    Event event = new Event(eventId, eName, club, venue, grade, headGrader, otherGradingPanel, desc, startDateTime, endDateTime, status);

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
        String sql = "SELECT event.event_id,event.name AS e_name, club_role.member_id AS club_leader_id, event.club_id, event.venue, event.status, event.description AS e_desc, event.start_date_time, event.end_date_time, event.grading_id AS g_id, club.mem_num, club.name AS c_name, club.location, club.email, club.phone, club.description AS c_desc, grading.name as g_name, grading_panel.grading_member_name as gm_name, martial_arts.martial_art_id as marts_id, martial_arts.name AS ma_name FROM public.event JOIN public.club ON event.club_id = club.club_id JOIN public.grading ON event.grading_id = grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id LEFT JOIN public.club_role ON club.club_id = club_role.club_id AND role_name = 'leader' LEFT JOIN public.grading_panel ON grading_panel.event_id = event.event_id AND grading_panel.grading_role = 'Head'";
//                + "SELECT event.event_id, event.name AS e_name, club_role.member_id AS club_leader_id, event.club_id, event.venue, event.status, event.description AS e_desc, event.start_date_time, event.end_date_time, event.grading_id, club.mem_num, club.name AS c_name, club.location, club.email, club.phone, club.description AS c_desc, grading.name AS g_name, martial_arts.martial_art_id AS marts_id, martial_arts.name AS ma_name FROM public.event JOIN public.club ON event.club_id = club.club_id JOIN public.grading ON event.grading_id = grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id LEFT JOIN public.club_role ON club.club_id = club_role.club_id AND role_name = 'leader'";

        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            List<Event> events = new ArrayList<Event>();

            while (rs.next()) {
                // grade fields
                String gradeId = Integer.toString(rs.getInt("g_id"));
                String artId = Integer.toString(rs.getInt("marts_id"));
                String gradeName = rs.getString("g_name");
                String martialArt = rs.getString("ma_name");
                Grade grade = new Grade(gradeName, martialArt, null, null, gradeId, artId, null, null, null);

                //club
                String clubId = rs.getString("club_id");
                String clubLeaderId = rs.getString("club_leader_id");             // fix
                String clubName = rs.getString("c_name");
                String location = rs.getString("location");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int noMembers = rs.getInt("mem_num");
                String description = rs.getString("c_desc");
                Club club = new Club(clubId, clubLeaderId, clubName, phone, email, noMembers, location, description);

                //event
                String eventid = Integer.toString(rs.getInt("event_id"));
                String venue = rs.getString("venue");
                String eName = rs.getString("e_name");
                String status = rs.getString("status");
                String desc = rs.getString("e_desc");
                String startDateTime = rs.getString("start_date_time");
                String endDateTime = rs.getString("end_date_time");
                String headGrader = rs.getString("gm_name");
                    List<String> otherGradingPanel = get2ndEventGrader(eventid);
                                 
                    Event event = new Event(eventid, eName, club, venue, grade, headGrader, otherGradingPanel, desc, startDateTime, endDateTime, status);

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

            try ( PreparedStatement insertEventstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
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
                try ( ResultSet generatedKeys = insertEventstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(Integer.toString(generatedKeys.getInt(1)));
                        
                        setGraderEvent(event.getHeadOfGradingPanel(), "Head", event);
                        for(int i = 0; i < event.getOtherMembersOfGradingPanel().size(); i++){
                          setGraderEvent(event.getOtherMembersOfGradingPanel().get(i), "Secondary Grader", event);  
                        }
                        
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

            try ( PreparedStatement deleteEventstmt = con.prepareStatement(sql);) {
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
    
    public void setGraderEvent(String name, String role, Event event){
       try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.grading_panel (event_id, grading_member_name,grading_role) VALUES (?,?,?)";
            try (PreparedStatement insertEventstmt = con.prepareStatement(sql);) {
                insertEventstmt.setInt(1, Integer.parseInt(event.getEventId()));
                insertEventstmt.setString(2, name);
                insertEventstmt.setString(3, role);
                
                int row = insertEventstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating grade failed, no rows affected.");
                    }
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
    
    public void deleteGraderEvent(Event event, String name) {
       try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

           String sql = "DELETE FROM public.grading_panel WHERE event_id = ? AND grading_member_name = ?";

            try (PreparedStatement deleteEventstmt = con.prepareStatement(sql);) {
                deleteEventstmt.setInt(1, Integer.parseInt(event.getEventId()));
                deleteEventstmt.setString(2, name);

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
    
    public List<String> get2ndEventGrader(String eventId) {
        List<String> graderList = new ArrayList<>();
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT grading_member_name FROM public.grading_panel WHERE event_id = ? AND grading_role != 'Head'";           
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(eventId));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    graderList.add(rs.getString("grading_member_name"));
                }
                   
            return graderList;

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
    public Event updateEvent(Event event) {
       try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "UPDATE FROM public.event SET event_id = ?, name = ?, club_id = ?, status = ?, , description = ? ,start_date_time = ?, end_date_time = ?, grading_id = ?";

            try (PreparedStatement updateEventstmt = con.prepareStatement(sql);) {
                updateEventstmt.setString(1, event.getName());
                updateEventstmt.setInt(2, Integer.parseInt(event.getClub().getClubId()));
                updateEventstmt.setString(3, event.getVenue());
                updateEventstmt.setString(4, event.getDesc());
                updateEventstmt.setString(5, event.getStartDateTime());
                updateEventstmt.setString(6, event.getEndDateTime());
                updateEventstmt.setInt(7, Integer.parseInt(event.getHighestGradeAvailable().getGradeId()));
                updateEventstmt.setString(8, event.getStatus());

                updateEventstmt.executeUpdate();
                con.close();

                int row = updateEventstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Updating event failed, no rows affected.");
                }
                //Get EventId from previous insert statement and add to member
                try ( ResultSet generatedKeys = updateEventstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(Integer.toString(generatedKeys.getInt(1)));
                        return event;
                    } else {
                        throw new SQLException("Updating next Event failed.");
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
    public void registerForEvent(Event event, Member member) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.event_line (event_id, member_id) VALUES (?,?)";

            try ( PreparedStatement registerForEventstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                registerForEventstmt.setInt(1, Integer.parseInt(event.getEventId()));
                registerForEventstmt.setInt(2, Integer.parseInt(member.getMemberId()));

                int row = registerForEventstmt.executeUpdate();
                
                if (row == 0) {
                    throw new SQLException("Creating eventline failed, no rows affected.");
                }
                //Get EventId from previous insert statement and add to member
                try ( ResultSet generatedKeys = registerForEventstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setEventId(Integer.toString(generatedKeys.getInt(1)));
                        System.out.println("value returned");
                    } else {
                        throw new SQLException("Updating nextEventLine failed.");
                    }
                }
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
    public void withdrawFromEvent(Event event, Member member) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "DELETE FROM public.event_line WHERE event_id = ? AND member_id = ?";

            try ( PreparedStatement registerForEventstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                registerForEventstmt.setString(1, event.getEventId());
                registerForEventstmt.setString(2, member.getMemberId());

                int row = registerForEventstmt.executeUpdate();

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

//     @Override
//    public void assignGradesToStudent (Event event, Member member) {
//
//    }

}
