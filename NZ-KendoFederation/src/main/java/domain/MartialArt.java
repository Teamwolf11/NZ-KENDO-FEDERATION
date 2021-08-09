package domain;

import java.util.Date;

class MartialArt {
    public String martialArtId;
    public String name;             //Name of the martial art
    public String grade;             //Grade of this instance
    public Date dateReceived;       //When rank was given

    public MartialArt(String martialArtId, String name, String grade, Date dateReceived) {
        this.martialArtId = martialArtId;
        this.name = name;
        this.grade = grade;
        this.dateReceived = dateReceived;
    }

    public String getMartialArtId() {
        return martialArtId;
    }

    public void setMartialArtId(String martialArtId) {
        this.martialArtId = martialArtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
    
    
    
}
