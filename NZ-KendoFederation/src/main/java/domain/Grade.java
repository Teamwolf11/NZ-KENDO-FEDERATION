/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDateTime;

/**
 *
 * @author lachl
 */
public class Grade{
    
    private String grade;
    private String martialArt;
    private LocalDateTime nextGradeDate;
    private LocalDateTime dateReceived;
    private String gradeId;
    private String artId;
    private Club club;
    private String eventId;  //event where grade was received

    public Grade(){}

    public Grade(String grade, String martialArt, LocalDateTime nextGradeDate, LocalDateTime dateReceived, String gradeId, String artId, Club club, String eventId) {
        this.grade = grade;
        this.martialArt = martialArt;
        this.nextGradeDate = nextGradeDate;
        this.dateReceived = dateReceived;
        this.gradeId = gradeId;
        this.artId = artId;
        this.club = club;
        this.eventId = eventId;
    }

    
    
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDateTime dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
    



    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    } 
    
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMartialArt() {
        return martialArt;
    }

    public void setMartialArt(String martialArt) {
        this.martialArt = martialArt;
    }

    public LocalDateTime getNextGradeDate() {
        return nextGradeDate;
    }

    public void setNextGradeDate(LocalDateTime timeInGrade) {
        this.nextGradeDate = timeInGrade;
    }
    
    
    
}