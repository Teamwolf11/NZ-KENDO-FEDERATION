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
}

