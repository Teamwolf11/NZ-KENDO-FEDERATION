package dao;


import domain.AppRoles;
import domain.Member;
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

    @Override
    public Member signInSimple(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
