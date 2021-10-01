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

            String sql = "SELECT club.*, martial_art_id, name, grade, date FROM public.club LEFT JOIN public.martial_arts ON club.club_id = martial_arts.club_id";
            
//    Club schema                                           // add FK to MartialArts
//    club_id SERIAL NOT NULL,                              // add clubLeaderId field
//    name character varying NOT NULL,                      // add no_of_members
//    location character varying,                           // add when was it fomrmed? 
//    contact_details character varying NOT NULL,

//    Club domain 
//    public String clubId;
//    public String clubLeaderId;
//    public String clubName;
//    public MartialArt martialArts;
//    public String contact;
//    public int noOfMembers;

//    Martial Arts domain                                   // do we need MartialArtsDate here? 
//    private String martialArtId;                          // description? 
//    private String name;                                  // should have club as FK?
//    private String grade;                                 // do we need to have a grade class? 
//    private Date dateReceived; 

//    Martial Arts schema
//    martial_art_id SERIAL NOT NULL,                       // add grade
//    name character varying NOT NULL,                      // add club_id
//    "desc" character varying,                             // add date?

            try ( PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, clubID);
                ResultSet rs = stmt.executeQuery();
               
                if (rs.next()) {
                    // club fields
                    String club_id = rs.getString("club_id");
                    String clubLeaderId = rs.getString("club_leader_id");
                    String clubName = rs.getString("name");
                    String location = rs.getString("location");
                    String contact = rs.getString("contact_details");
                    int noMembers = rs.getInt("no_of_members");
                    String description = rs.getString("description");
                    
                    // martial art fields
                    String martialArtId = rs.getString("martial_art_id");
                    String martialArtName = rs.getString("name");
                    String martialArtsGrade = rs.getString("grade");
                    Timestamp martialArtsDate = rs.getTimestamp("date");
                    
                    MartialArt martialArt = new MartialArt(martialArtId, martialArtName, martialArtsGrade, martialArtsDate);
                    
                    return new Club(club_id, clubLeaderId, clubName, martialArt, contact, noMembers, location, description);
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
            
            String sql = "DELETE FROM public.club WHERE club_id = ?";
            
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
            
            // club_id, name, club_leader_id, martial_art_id, location, contact_details, no_members, description
            String sql = "UPDATE FROM public.event SET club_id = ?, name = ?, club_leader_id = ?, martial_art_id = ?, location = ?, contact_details = ?, no_members = ?, description = ?";
            
            try (PreparedStatement updateClubstmt = con.prepareStatement(sql);) {
                updateClubstmt.setString(1, club.getClubId());
                updateClubstmt.setString(2, club.getClubName());
                updateClubstmt.setString(3, club.getClubLeaderId());
                updateClubstmt.setString(4, club.getMartialArts().getMartialArtId());
                updateClubstmt.setString(5, club.getLocation());
                updateClubstmt.setString(6, club.getContact());
                updateClubstmt.setInt(7, club.getNoOfMembers());
                updateClubstmt.setString(8, club.getDescription());
                
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

            // club_id, clubLeaderId, clubName, martialArt, contact, noMembers, location, description
            String sql = "INSERT INTO public.club (club_id, name, club_leader_id, martial_art_id, location, contact_details, no_members, description) VALUES (?,?,?,?,?,?,?,?) RETURNING club_id";
            
            try (PreparedStatement insertClubstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertClubstmt.setString(1, club.getClubId());
                insertClubstmt.setString(2, club.getClubName());
                insertClubstmt.setString(3, club.getClubLeaderId());
                insertClubstmt.setString(4, club.getMartialArts().getMartialArtId());
                insertClubstmt.setString(5, club.getLocation());
                insertClubstmt.setString(6, club.getContact());
                insertClubstmt.setInt(7, club.getNoOfMembers());
                insertClubstmt.setString(8, club.getDescription());  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   

}
