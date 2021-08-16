package dao;

import domain.Member;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberJdbcDAO implements MemberDAO{

//    Class.forName("org.postgresql.Driver");
    public MemberJdbcDAO(){
    }
    
    public MemberJdbcDAO(String uri){
        
    }
    public Member getMember(String memberId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveMember(Member member) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
