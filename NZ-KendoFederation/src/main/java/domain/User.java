package domain;

public class User {
    
    public String userId;
    public String username;
    public String password;
    public AppRoles roles;
    
    public User(){   
    }

    public User(String userId, String username, String password, AppRoles roles) {    
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public User(String username, String password, AppRoles roles) {
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

    public AppRoles getRoles() {
        return roles;
    }

    public void setRoles(AppRoles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", roles=" + roles + '}';
    }
    
    
}
