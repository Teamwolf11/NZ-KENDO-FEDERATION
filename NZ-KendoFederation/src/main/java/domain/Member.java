package domain;

import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime dob;
    private LocalDateTime joinDate; 
    private String fName;    //FirstName
    private String lName;    //LastName
    private String mName;    //middleName
    private char sex;        //Will have to talk to Taasha about how she wants gender to work
    private String ethnicity;
    private String phoneNum;
    private List<Grade> grades;

    public Member(){}
    
    public Member(String member_id, String nzkfId, AppRoles role, String email, String password, LocalDateTime dob, LocalDateTime joinDate, String fName, String lName, String mName, char sex, String ethnicity, String phoneNum, List<Grade> grades) {
        this.member_id = member_id;
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
        this.grades = grades;
    }
      
    public Member(String memberId, AppRoles role, String nzkfId, String email, String password, LocalDateTime dob, LocalDateTime joinDate, String fName, String lName, String mName, char sex, String ethnicity, String PhoneNum) {
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

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

        

   
    
}
