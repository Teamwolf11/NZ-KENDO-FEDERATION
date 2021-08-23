package domain;

public class AppRoles {
    
    public String name; //Name of the role
    public int appRoleId;
    
    public AppRoles(){
        
    }
    
    //Still havent implemented this properly. Working out what this actually entails.

    public AppRoles(int appRoleId, String name) {
        this.name = name;
        this.appRoleId = appRoleId;
    }

    public String getName() {
        return name;
    }

    public int getAppRoleId() {
        return appRoleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppRoleId(int appRoleId) {
        this.appRoleId = appRoleId;
    }
    
}
