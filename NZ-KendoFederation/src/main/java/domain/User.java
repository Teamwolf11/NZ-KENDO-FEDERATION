package domain;

import java.util.List;

public class User {
    
    private String userId;
    private String username;
    private String password;
    private String memberId;
    private List<AppRoles> roles;

    // Instance where a user is linked to a member Id
    public User(String userId, String username, String password, String memberId, List<AppRoles> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.memberId = memberId;
        this.roles = roles;
    }
    
    // Instance where a user is not linked to a memberId
    public User(String userId, String username, String password, List<AppRoles> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    // Instance where user doesn't have a memberId or database has given userId
    public User(String username, String password, List<AppRoles> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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
