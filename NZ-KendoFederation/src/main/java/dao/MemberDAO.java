package dao;

import domain.Member;
import domain.User;
import java.util.List;

/**
 *
 * @author Maaha Ahmad
 */
public interface MemberDAO {

    Member getMember(String memberId);
    
    Member saveMember(Member member);
    
    void deleteMember(Member member);
    
    //Member saveNewMember(Member member, User user); //When user and member needs to be added.
    
    //Member getSimpleMember(String memberId);  //Used when only the member class is needed - User is NULL
    
    List<Member> getAll();
    
    Member signIn(String email, String password);
    //List<Member> getAllSimple();
}
