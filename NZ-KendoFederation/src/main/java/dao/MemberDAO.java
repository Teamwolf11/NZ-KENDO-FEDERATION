package dao;

import domain.AppRoles;
import domain.Member;
import domain.User;
import java.util.List;

/**
 *
 * @author Maaha Ahmad
 */
public interface MemberDAO {

    Member getMember(String memberId);
    // delete Member?
    // validate member

    String[] saveNewMember(Member member);
    
    void saveUser(User user);
    
    User getUser(String userId);
    
    AppRoles getAppRole(String roleId);
    
    void saveAppRole(AppRoles Approle);
    
    void deleteMember(Member member);
    
    void deleteUser(User user);
}
