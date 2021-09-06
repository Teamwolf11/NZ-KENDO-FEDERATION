package dao;


import domain.AppRoles;
import domain.Member;
import domain.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberCollectionsDAO implements MemberDAO {
    private static Map<String, Member> members = new HashMap<String, Member>();
    private static Map<String, User> users = new HashMap<String, User>();
    private static Map<String, AppRoles> roles = new HashMap<String, AppRoles>();
    
    public MemberCollectionsDAO(){
    }
    
    @Override
    public String[] saveNewMember(Member member){
        String memId = member.getMemberId();
        members.put(member.getMemberId(), member);
//        users.put(member.getUser().userId, member.getUser());
       String[] ids = {memId};
        return ids;
    }
    
    @Override
    public void saveUser(User user){
         users.put(user.getUserId(), user);
    }
    
    
    @Override
    public Member getMember(String memberId){
        return members.get(memberId);
    }
    
    @Override
    public User getUser(String userId){
        return users.get(userId);
    }
    
    @Override
    public AppRoles getAppRole(String appRoleId){
        return roles.get(appRoleId);
    }
    
    @Override
    public void saveAppRole(AppRoles role){
         roles.put(role.getAppRoleId(), role);
    }
    
    @Override
    public void deleteUser(User user){  
    }
    
    @Override
    public void deleteMember(Member member){  
    }
    
    
//    @Override
//	public Boolean validateCredentials(String username, String password) {
//		if (members.containsKey(username)) {
//			return members.get(username).getPassword().equals(password);
//		} else {
//			return false;
//		}
//	}
    
    // delete Member?  
   
}
