package dao;

import domain.Member;
import domain.User;

/**
 *
 * @author Maaha Ahmad
 */
public interface MemberDAO {

    Member getMember(int memberId);
    // delete Member?
    // validate member

    void saveMember(Member member);
    
    void saveUser(User user);
    
    User getUser(int userId);
}
