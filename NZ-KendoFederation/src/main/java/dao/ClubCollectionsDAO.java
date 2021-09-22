package dao;

import domain.Club;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maaha Ahmad
 */
public class ClubCollectionsDAO implements ClubDAO {
    private static Map<String, Club> clubs = new HashMap<String, Club>();

    @Override
    public Club getClub(String clubID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteClub(Club club) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateClub(Club club) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Club saveClub(Club club) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
