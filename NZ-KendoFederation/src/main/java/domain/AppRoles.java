package domain;

public class AppRoles {
    
    private String name; //Name of the role
    private String appRoleId;
    
    public AppRoles(){
        
    }
    
    //Still havent implemented this properly. Working out what this actually entails.

    public AppRoles(String appRoleId, String name) {
        this.name = name;
        this.appRoleId = appRoleId;
    }

    public String getName() {
        return name;
    }

    public String getAppRoleId() {
        return appRoleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppRoleId(String appRoleId) {
        this.appRoleId = appRoleId;
    }

    @Override
    public String toString() {
        return "AppRoles{" + "name=" + name + ", appRoleId=" + appRoleId + '}';
    }
    
    

    
}
