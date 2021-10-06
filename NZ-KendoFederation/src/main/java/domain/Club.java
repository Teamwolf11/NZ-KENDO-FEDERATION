/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author lachl
 */
public class Club {
    public String clubId;
    public String clubLeaderId;
    public String clubName;
    public MartialArt martialArts;
    public String phone;
    public String email;
    public int noOfMembers;
    public String location;
    public String description;
    
    // schema has the following
//    club_id SERIAL NOT NULL,
//    mem_num integer,
//    name character varying NOT NULL,
//    location character varying,
//    email character varying,
//    phone character varying,
    
    public Club(){
    }
    
    public Club(String clubId, String clubLeaderId, String clubName, MartialArt martialArts, String phone, String email, int noOfMembers, String location, String description) {
        this.clubId = clubId;
        this.clubLeaderId = clubLeaderId;
        this.clubName = clubName;
        this.martialArts = martialArts;
        this.phone = phone;
        this.email = email;
        this.noOfMembers = noOfMembers;
        this.location = location;
        this.description = description; 
        
    }

    public String getClubId() {
        return clubId;
    }

    public String getClubLeaderId() {
        return clubLeaderId;
    }

    public String getClubName() {
        return clubName;
    }

    public MartialArt getMartialArts() {
        return martialArts;
    }


    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public void setClubLeaderId(String clubLeaderId) {
        this.clubLeaderId = clubLeaderId;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setMartialArts(MartialArt martialArts) {
        this.martialArts = martialArts;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    
    
}
