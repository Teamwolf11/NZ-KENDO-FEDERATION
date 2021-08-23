package dao;


import domain.Member;
import domain.User;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberCollectionsDAO implements MemberDAO {
    private static Map<Integer, Member> members = new HashMap<Integer, Member>();
    private static Map<Integer, User> users = new HashMap<Integer, User>();
    
    public MemberCollectionsDAO(){
    }
    
    @Override
    public void saveMember(Member member){
        members.put(member.getMemberId(), member);
//        users.put(member.getUser().userId, member.getUser());
    }
    
    @Override
    public void saveUser(User user){
         users.put(user.getUserId(), user);
    }
    
    
    @Override
    public Member getMember(int memberId){
        return members.get(memberId);
    }
    
    @Override
    public User getUser(int userId){
        return users.get(userId);
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
