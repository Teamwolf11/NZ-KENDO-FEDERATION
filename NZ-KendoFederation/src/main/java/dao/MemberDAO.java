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
    
    List<Member> getAll();
    
    Member signIn(String email, String password);

    Member signInSimple(String email);
}
