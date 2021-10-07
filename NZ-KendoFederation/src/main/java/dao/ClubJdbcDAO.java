/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Database.DatabaseConnector;
import domain.Club;
import domain.MartialArt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maaha Ahmad
 */
public class ClubJdbcDAO implements ClubDAO {

    Connection con = null;
    DatabaseConnector obj = new DatabaseConnector();

    @Override
    public Club getClub(String clubID) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT club.*, martial_arts.*, club_role.member_id FROM public.club LEFT JOIN public.martial_arts ON club.martial_art_id = martial_arts.martial_art_id INNER JOIN public.club_role ON club.club_id = club_role.club_id WHERE club_role.app_role_id = 2";
                  
            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, clubID);
                ResultSet rs = stmt.executeQuery();
               
                if (rs.next()) {
                    // club fields
                    String club_id = rs.getString("club_id");
                    String clubLeaderId = rs.getString("member_id");             // fix
                    String clubName = rs.getString("name");
                    String location = rs.getString("location");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    int noMembers = rs.getInt("mem_num");
                    String description = rs.getString("description");                 // fix
                    
                    // martial art fields
                    String martialArtId = rs.getString("martial_art_id");
                    String martialArtName = rs.getString("name");
                    
                    MartialArt martialArt = new MartialArt(martialArtId, martialArtName);
                    
                    return new Club(club_id, clubLeaderId, clubName, martialArt, phone, email, noMembers, location, description);
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
            String sql = "DELETE FROM public.club WHERE club_id = '?'";
            
            try (PreparedStatement deleteClubstmt = con.prepareStatement(sql);) {
                deleteClubstmt.setInt(1, Integer.parseInt(club.getClubId()));

                deleteClubstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public void updateClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();
            
           
            String sql = "UPDATE public.club SET mem_num = ?, name = ?, location = ?, email = ?, phone = ?, dscription = ? WHERE club_id = ?";

            try (PreparedStatement updateClubstmt = con.prepareStatement(sql);) {
                updateClubstmt.setInt(1, club.getNoOfMembers());
                updateClubstmt.setString(2, club.getClubName());
                updateClubstmt.setString(3, club.getLocation());
                updateClubstmt.setString(4, club.getEmail());
                updateClubstmt.setString(5, club.getPhone());
                updateClubstmt.setString(6, club.getDescription());      
                updateClubstmt.setString(7, club.getClubId());
                
                updateClubstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public Club saveClub(Club club) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "INSERT INTO public.club (club_id, mem_num, name, location, email, phone, description, club_leader_id, martial_art_id,) VALUES (?,?,?,?,?,?,?) RETURNING club_id";
            
            try (PreparedStatement insertClubstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertClubstmt.setString(1, club.getClubId());
                insertClubstmt.setInt(2, club.getNoOfMembers());
                insertClubstmt.setString(3, club.getClubName());
                insertClubstmt.setString(4, club.getLocation());
                insertClubstmt.setString(5, club.getEmail());
                insertClubstmt.setString(6, club.getPhone());
                insertClubstmt.setString(7, club.getDescription());
                
                insertClubstmt.setString(8, club.getClubLeaderId());
                insertClubstmt.setString(9, club.getMartialArts().getMartialArtId());
                  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   

}
