package dao;

import domain.Club;

/**
 *
 * @author Maaha Ahmad
 */
public interface ClubDAO {

    Club getClub(String clubID);
    void deleteClub(Club club);
    void updateClub(Club club);
    Club saveClub(Club club);
    Club saveNewClub(Club club); 
    
}
