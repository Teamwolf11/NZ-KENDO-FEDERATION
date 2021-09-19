package domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author lachl
 */
public class Member {
    
    public String member_id;
    public String nzkfId;
    public User user;
    private String email;
    private LocalDateTime dob;
    public LocalDateTime joinDate; 
    public String fName;    //FirstName
    public String lName;    //LastName
    public String mName;    //middleName
    public char sex;        //Will have to talk to Taasha about how she wants gender to work
    public String ethnicity;

    public Member() {
    }
    
    //All info known
    public Member(String id, String nzkfId, User user, String email, LocalDateTime dob, LocalDateTime joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
        this.member_id = id;
        this.nzkfId = nzkfId;
        this.user = user;
        this.email = email;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
    }
    
    //MemberId from database not known
        public Member(String nzkfId, User user, String email, LocalDateTime dob, LocalDateTime joinDate, String fName, String lName, String mName, char sex, String ethnicity) {
        this.nzkfId = nzkfId;
        this.user = user;
        this.email = email;
        this.dob = dob;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
    }
        
    //MemberId and User class not known    
    public Member(String nzkfId, LocalDateTime dob, LocalDateTime joinDate, String email, String fName, String lName, String mName, char sex, String ethnicity) {
        this.nzkfId = nzkfId;
        this.user = user;
        this.email = email;
        this.dob = dob;
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

       public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
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
