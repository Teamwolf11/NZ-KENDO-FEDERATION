package dao;


import domain.Member;
import java.sql.Date;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maaha Ahmad
 */
public class MemberCollectionsDAO implements MemberDAO {
    private static Map<String, Member> members = new HashMap<String, Member>();
    
    public MemberCollectionsDAO(){

        Member boris = new Member();
        boris.setMemberId("5454");
//        boris.setUser("b345");
        boris.setNzkfId("6565");
        boris.setfName("Boris");
        boris.setlName("Doloris");
        boris.setJoinDate(Date.valueOf("2011-02-18"));
      
        Member doris = new Member();
        doris.setMemberId("6454");
//        doris.setUser("d345");
        doris.setNzkfId("6666");
        doris.setfName("Doris");
        doris.setlName("Doloris");
        boris.setJoinDate(Date.valueOf("2011-03-18"));
 
        saveMember(boris);
        saveMember(doris);
    }
    
    @Override
    public void saveMember(Member member){
        members.put(member.getMemberId(), member);
    }
    
    @Override
    public Member getMember(String memberId){
        return members.get(memberId);
    }
    
    // delete Member?  
    // validate member
}
