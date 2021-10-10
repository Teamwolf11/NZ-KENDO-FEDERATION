package dao;

import domain.Event;
import domain.Member;
import java.util.List;

/**
 *
 * @author Maaha Ahmad
 */
public interface EmailDAO {

    List<Member> getPotentialMembers(Event event);
    void sendConfirmationEmail(Member member);
    void sendGradingEmail(Event event, List<Member> mList);
    void sendExpiryEmail(Member member);
    
}
public  interface EmailDAO {
 
    public List getPotentialMembers(Event event);
    public void email(Member member);
    public void gradingEmail(Event event, Member member, List mList);
    public void expiryEmail(Member member);
    public void sendConfirmationEmail(Member member);
    public void sendExpiryEmail(Member member);
    public void sendGradingEmail(Member member);
    }
    
