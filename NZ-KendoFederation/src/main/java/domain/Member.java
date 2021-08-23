/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author lachl
 */
public class Member {
    
    public int member_id;
    public String nzkfId;
    public User user;
    public LocalDateTime joinDate; 
    public String fName;    //FirstName
    public String lName;    //LastName
    public String mName;    //middleName
    public char sex;        //Will have to talk to Taasha about how she wants gender to work
    public String ethnicity;

    public Member() {
    }
    
    public Member(int id, String nzkfId, User user, LocalDateTime joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
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

    public int getMemberId() {
        return member_id;
    }

    public void setMemberId(int id) {
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

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
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

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Member other = (Member) obj;
		if (!Objects.equals(this.member_id, other.member_id)) {
			return false;
		}
		return true;
	}
    @Override
    public String toString() {
        return "Member{" + "member_id=" + member_id + ", nzkfId=" + nzkfId + ", user=" + user + ", joinDate=" + joinDate + ", fName=" + fName + ", lName=" + lName + ", mName=" + mName + ", sex=" + sex + ", ethnicity=" + ethnicity + '}';
    }

   
    
}
