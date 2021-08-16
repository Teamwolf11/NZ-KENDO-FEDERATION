package dao;

import domain.Member;

/**
 *
 * @author Maaha Ahmad
 */
public interface MemberDAO {

    Member getMember(String memberId);
    // delete Member?
    // validate member

    void saveMember(Member member);
    
}
