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
public class GradeJdbcDAO implements GradeDAO {

    private Connection con;

    /**
     * Gets individual grade
     *
     * @param gradeId
     * @param memberId
     * @return returns grade
     */
    @Override
    public Grade getMemberGrade(String gradeId, String memberId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT member_grading.*, grading.name AS grading_name, martial_arts.martial_art_id, martial_arts.name AS martial_arts_name FROM public.member_grading JOIN public.grading ON grading.grading_id = member_grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id WHERE member_grading.member_id = ? AND member_grading.grading_id = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(memberId));
                stmt.setInt(2, Integer.parseInt(gradeId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // grade fields
                    String artId = Integer.toString(rs.getInt("martial_art_id"));
                    String gradeName = rs.getString("grading_name");
                    String martialArt = rs.getString("martial_arts_name");
                    String dateReceived = rs.getString("date_received");
                    String nextDateAvailable = rs.getString("date_next_grade_available");
                    String eventId = Integer.toString(rs.getInt("event_id"));
                    if (eventId.equals("0"))  eventId = null;

                    //club
                    String clubId = Integer.toString(rs.getInt("club_id"));
                    ClubJdbcDAO clubJdbc = new ClubJdbcDAO();
                    Club club = clubJdbc.getClub(clubId);
                    return new Grade(gradeName, martialArt, nextDateAvailable, dateReceived, gradeId, artId, club, null, eventId);

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

    /**
     * Creates a new grade in the DB. Member object is updated to have this
     * grade object.
     *
     * @param grade
     * @param member
     * @return member with the grade added to it's grade list
     */
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
                if (grade.getEventId() != null) {
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
    public void deleteGrade(Grade grade, String memberId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "DELETE FROM public.member_grading WHERE grading_id = ? AND club_id = ? AND member_id = ?";

            try (PreparedStatement deleteClubstmt = con.prepareStatement(sql);) {
                deleteClubstmt.setInt(1, Integer.parseInt(grade.getGradeId()));
                deleteClubstmt.setInt(2, Integer.parseInt(grade.getClub().getClubId()));
                deleteClubstmt.setInt(3, Integer.parseInt(memberId));

                deleteClubstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    /**
     * Takes in an object and returns the object with all its corresponding
     * grades
     *
     * @param obj (Member,Club,Event)
     * @return object (Member,Club,Event)
     */
    @Override
    public Object getAllForObj(Object obj) {
        String sql;
        int value;
        List<Club> cList = new ArrayList<>();
        List<Grade> gList = new ArrayList<>();
        List<Event> eList = new ArrayList<>();
        List<Member> mList = new ArrayList<>();
        List returnList;

        if (obj instanceof Member) {
            sql = "SELECT member_grading.*, grading.name AS grading_name, martial_arts.martial_art_id, martial_arts.name AS martial_arts_name FROM public.member_grading JOIN public.grading ON grading.grading_id = member_grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id WHERE member_grading.member_id = ?";
            Member member = (Member) obj;
            value = Integer.parseInt(member.getMemberId());
            returnList = mList;
        } else if (obj instanceof Club) {
            sql = "SELECT member_grading.*, grading.name AS grading_name, martial_arts.martial_art_id, martial_arts.name AS martial_arts_name FROM public.member_grading JOIN public.grading ON grading.grading_id = member_grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id WHERE member_grading.club_id = ?";
            Club club = (Club) obj;
            value = Integer.parseInt(club.getClubId());
            returnList = cList;
        } else if (obj instanceof Event) {
            sql = "SELECT member_grading.*, grading.name AS grading_name, martial_arts.martial_art_id, martial_arts.name AS martial_arts_name FROM public.member_grading JOIN public.grading ON grading.grading_id = member_grading.grading_id JOIN public.martial_arts ON grading.martial_art_id = martial_arts.martial_art_id WHERE member_grading.event_id = CAST(? as varchar)";
            Event event = (Event) obj;
            value = Integer.parseInt(event.getEventId());
            returnList = eList;
        } else {
            return null;
        }

        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, value);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    // grade fields
                    String gradeId = Integer.toString(rs.getInt("grading_id"));
                    String artId = Integer.toString(rs.getInt("martial_art_id"));
                    String gradeName = rs.getString("grading_name");
                    String martialArt = rs.getString("martial_arts_name");
                    String dateReceived = rs.getString("date_received");
                    String nextDateAvailable = rs.getString("date_next_grade_available");
                    String clubId = Integer.toString(rs.getInt("club_id"));
                    String memberId = Integer.toString(rs.getInt("member_id"));
                    String eventId = Integer.toString(rs.getInt("event_id"));
                    if (eventId.equals("0")) {
                        eventId = null;
                    }

                    Grade grade = new Grade(gradeName, martialArt, nextDateAvailable, dateReceived, gradeId, artId, null, null, null);

                    cl:
                    for (int i = 0; i < cList.size() || cList.size() == 0; i++) {
                        if (cList.size() != 0 && cList.get(i).getClubId().equals(clubId)) {  //club already in list
                            grade.setClub(cList.get(i));
                            cList.get(i).addGrade(grade);
                            gList.add(grade);
                            break cl;
                        }
                        if (i + 1 == cList.size() || cList.size() == 0) {   //If club not in list
                            ClubJdbcDAO clubJdbc = new ClubJdbcDAO();
                            Club club = clubJdbc.getClub(clubId);
                            grade.setClub(club);
                            club.addGrade(grade);
                            cList.add(club);
                            gList.add(grade);
                            break cl;
                        }
                        System.out.println(cList.size() + "   " + i);
                    }
                    
                    el:
                    //for (int i = 0; eventId != null && (i < eList.size() || eList.size() == 0); i++) {
                    for (int i = 0; eventId != null && (i < eList.size() || eList.size() == 0); i++) {
                        if (eventId != null && eList.size() != 0 && eList.get(i).getEventId().equals(eventId)) {  //club already in list
                            grade.setEventId(eList.get(i).getEventId());
                            grade.setEventName(eList.get(i).getName());
                            eList.get(i).addGrade(grade);
                            break el;
                        }
                        if (i + 1 == eList.size() || eList.size() == 0) {   //If club not in list
                            EventJdbcDAO eventJdbc = new EventJdbcDAO();
                            Event event = eventJdbc.getEvent(eventId);
                            grade.setEventId(event.getEventId());
                            grade.setEventName(event.getName());
                            event.addGrade(grade);
                            eList.add(event);
                            gList.add(grade);
                            break el;
                        }
                    }
                    
                    ml:
                    for (int i = 0; i < mList.size() || mList.size() == 0; i++) {
                        if (mList.size() != 0 && mList.get(i).getMemberId().equals(memberId)) {
                            mList.get(i).addGrade(grade);;
                            break ml;
                        }
                        if (i + 1 == mList.size() || mList.size() == 0) {   //If club not in list
                            MemberJdbcDAO memberJdbc = new MemberJdbcDAO();
                            Member member = memberJdbc.getMember(memberId);
                            member.addGrade(grade);
                            mList.add(member);
                            break ml;
                        }
                    }  
                }
                return returnList.get(0);
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
