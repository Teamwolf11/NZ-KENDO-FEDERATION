package domain;

import java.util.List;

class User {
    
    public String userId;
    public String username;
    public String password;
    public String memberId;
    public List<AppRoles> roles;

    public User(String userId, String username, String password, String memberId, List<AppRoles> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.memberId = memberId;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<AppRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRoles> roles) {
        this.roles = roles;
    }
    
    
}
