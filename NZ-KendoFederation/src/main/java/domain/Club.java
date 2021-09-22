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
    public String contact;
    public int noOfMembers;
    public String location;

    public Club(){
    }
    
    public Club(String clubId, String clubLeaderId, String clubName, MartialArt martialArts, String contact, int noOfMembers, String location) {
        this.clubId = clubId;
        this.clubLeaderId = clubLeaderId;
        this.clubName = clubName;
        this.martialArts = martialArts;
        this.contact = contact;
        this.noOfMembers = noOfMembers;
        this.location = location;
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

    public String getContact() {
        return contact;
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

    public void setContact(String contact) {
        this.contact = contact;
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
    
    
}
