/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;

/**
 *
 * @author lachl
 */
public class Member {
    
    private String member_id;
    private String nzkfId;
    private User user;
    private Date joinDate;
    private String fName;    //FirstName
    private String lName;    //LastName
    private String mName;    //middleName
    private char sex;        //Will have to talk to Taasha about how she wants gender to work
    private String ethnicity;

    public Member() {
    }
    
    //All info known
    public Member(String id, String nzkfId, User user, Date joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
        this.member_id = id;
        this.nzkfId = nzkfId;
        this.user = user;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
    }
    
    //MemberId from database not known
        public Member(String nzkfId, User user, Date joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
        this.nzkfId = nzkfId;
        this.user = user;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
    }
        
    //MemberId and User class not known    
    public Member(String nzkfId, Date joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
        this.nzkfId = nzkfId;
        this.user = user;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
    }    

    public String getMemberId() {
        return member_id;
    }

    public void setMemberId(String id) {
        this.member_id = id;
    }

    public String getNzkfId() {
        return nzkfId;
    }

    public void setNzkfId(String nzkfId) {
        this.nzkfId = nzkfId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
    
    
    
}
