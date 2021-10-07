package dao;

import domain.Club;

/**
 *
 * @author Maaha Ahmad
 */
public interface ClubDAO {

    Club getClub(String clubID);
    
    void deleteClub(Club club);
    
    Club updateClub(Club club);
    
    Club saveClub(Club club);
   
}
