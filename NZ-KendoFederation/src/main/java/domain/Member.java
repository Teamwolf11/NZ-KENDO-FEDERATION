package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author lachl
 */
public class Member {
    
    private String member_id;
    private String nzkfId;
    private AppRoles role;
    private String email;
    private String password;
    private String dob;
    private String joinDate; 
    private String fName;    //FirstName
    private String lName;    //LastName
    private String mName;    //middleName
    private char sex;        //Will have to talk to Taasha about how she wants gender to work
    private String ethnicity;
    private String phoneNum;

    public Member() {
    }
      
    public Member(String memberId, AppRoles role, String nzkfId, String email, String password, String dob, String joinDate, String fName, String lName, String mName, char sex, String ethnicity, String phoneNum) {
        this.member_id = memberId;
        this.nzkfId = nzkfId;
        this.role = role;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.joinDate = joinDate;
        this.fName = fName;
        this.lName = lName;
        this.mName = mName;
        this.sex = sex;
        this.ethnicity = ethnicity;
        this.phoneNum = phoneNum;
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

    public AppRoles getRole() {
        return role;
    }

    public void setRole(AppRoles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
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
        return "Member{" + "member_id=" + member_id + ", nzkfId=" + nzkfId + ", role=" + role + ", email=" + email + ", password=" + password + ", dob=" + dob + ", joinDate=" + joinDate + ", fName=" + fName + ", lName=" + lName + ", mName=" + mName + ", sex=" + sex + ", ethnicity=" + ethnicity + ", phoneNum=" + phoneNum + '}';
    }

        

   
    
}
