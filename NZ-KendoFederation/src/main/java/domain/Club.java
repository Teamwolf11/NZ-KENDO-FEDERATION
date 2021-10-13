/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lachl
 */
public class Club {
    public String clubId;
    public String clubLeaderId;
    public String clubName;
    public String phone;
    public String email;
    public int noOfMembers;
    public String location;
    public String description;
    private List<Grade> grades = new ArrayList<>();
    
    
    public Club(){
    }

    public Club(String clubId, String clubLeaderId, String clubName, String phone, String email, int noOfMembers, String location, String description) {
        this.clubId = clubId;
        this.clubLeaderId = clubLeaderId;
        this.clubName = clubName;
        this.phone = phone;
        this.email = email;
        this.noOfMembers = noOfMembers;
        this.location = location;
        this.description = description;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
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

    @Override
    public String toString() {
        return "Club{" + "clubId=" + clubId + ", clubLeaderId=" + clubLeaderId + ", clubName=" + clubName + ", phone=" + phone + ", email=" + email + ", noOfMembers=" + noOfMembers + ", location=" + location + ", description=" + description + '}';
    }
}
