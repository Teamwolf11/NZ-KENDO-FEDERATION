package domain;

import java.util.List;

public class User {
    
    public int userId;
    public String username;
    public String password;
    public int memberId;
    // public List<AppRoles> roles;
    public int roles;
    
    public User(){
        
    }

    public User(int userId, String username, String password, int memberId, int roles) {    // removed approles just for testing
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.memberId = memberId;
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", memberId=" + memberId + ", roles=" + roles + '}';
    }
    
    
}
