package dao;

import Database.DatabaseConnector;
import domain.Club;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maaha Ahmad
 */
public class ClubJdbcDAO implements ClubDAO {

    public Connection con = null;

    public ClubJdbcDAO() {
    }

    /**
     * Just gets the club, none of its connections
     * 
     * @param clubID
     * @return return's a club
     */
    @Override
    public Club getClub(String clubID) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT club.*, club_role.member_id FROM public.club LEFT JOIN public.club_role ON club.club_id = club_role.club_id AND role_name = 'leader' WHERE club.club_id = ?";
                  
            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(clubID));
                ResultSet rs = stmt.executeQuery();
               
                if (rs.next()) {
                    // club fields
                    String clubLeaderId = rs.getString("member_id");             // fix
                    String clubName = rs.getString("name");
                    String location = rs.getString("location");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    int noMembers = rs.getInt("mem_num");
                    String description = rs.getString("description");                 // fix
                        
                    return new Club(clubID, clubLeaderId, clubName, phone, email, noMembers, location, description);
                }
                else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
         finally{
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }
    }

    @Override
    public void deleteClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
            String sql = "DELETE FROM public.club WHERE club_id = ?";
            
            try (PreparedStatement deleteClubstmt = con.prepareStatement(sql);) {
                deleteClubstmt.setInt(1, Integer.parseInt(club.getClubId()));

                deleteClubstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  
         finally{
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }
    }

    @Override
    public Club updateClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
           
            String sql = "UPDATE public.club SET name = ?, location = ?, email = ?, phone = ?, description = ? WHERE club_id = ?";

            try (PreparedStatement updateClubstmt = con.prepareStatement(sql);) {
                updateClubstmt.setString(1, club.getClubName());
                updateClubstmt.setString(2, club.getLocation());
                updateClubstmt.setString(3, club.getEmail());
                updateClubstmt.setString(4, club.getPhone());
                updateClubstmt.setString(5, club.getDescription());      
                updateClubstmt.setInt(6, Integer.parseInt(club.getClubId()));
                
                updateClubstmt.executeUpdate();
                return getClub(club.getClubId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
        finally {
            try { con.close(); } catch (Exception e) { /* Ignored */ } 
        }
    }

    @Override
    public Club saveClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.club (name, location, email, phone, description) VALUES (?,?,?,?,?) RETURNING club_id";     
            try (PreparedStatement insertClubstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertClubstmt.setString(1, club.getClubName());
                insertClubstmt.setString(2, club.getLocation());
                insertClubstmt.setString(3, club.getEmail());
                insertClubstmt.setString(4, club.getPhone());
                insertClubstmt.setString(5, club.getDescription());
                
                int row = insertClubstmt.executeUpdate();
                
                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = insertClubstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        club.setClubId(Integer.toString(generatedKeys.getInt(1)));
                        return club;
                    } else {
                        throw new SQLException("Updating id failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally{
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }
    }
}
