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
    
    public MemberCollectionsDAO(){
    }
    
    @Override
    public Member saveMember(Member member){
      members.put(member.getMemberId(), member);
      return member;
    } 
    
    @Override
    public Member getMember(String memberId){
        return members.get(memberId);
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

//    @Override
//    public Member saveNewMember(Member member, User user) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public Member getSimpleMember(String memberId) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public List<Member> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public List<Member> getAllSimple() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public Member signIn(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
