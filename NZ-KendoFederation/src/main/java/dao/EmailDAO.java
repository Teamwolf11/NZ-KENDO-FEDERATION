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

