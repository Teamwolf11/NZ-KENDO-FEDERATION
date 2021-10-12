package dao;

import domain.Member;
import java.util.List;
import web.*;

/**
 *
 * @author Maaha Ahmad
 */
public interface MemberDAO extends CredentialsValidator {

    Member getMember(String memberId);
    
    Member saveMember(Member member);
    
    void deleteMember(Member member);
    
    List<Member> getAll();
    
    // Member signIn(String email, String password);

    Member signInSimple(String email);
}
