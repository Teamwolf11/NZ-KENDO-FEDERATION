package dao;

import java.sql.Connection;
import java.sql.*;
import Database.DatabaseConnector;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebAuthenticationJdbcDAO {

    public Connection con = null;
    public WebAuthenticationJdbcDAO() {
    }
    
    public Set<String> checkPath(String method, String path){
        try {
            Set<String> roles = new HashSet<String>();
            DatabaseConnector db = new DatabaseConnector();
            con = db.connect();

            String sql = "SELECT app_role_id FROM public.role_path WHERE method = ? AND path = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setString(1, method);
                stmt.setString(2, path);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    roles.add(Integer.toString(rs.getInt("app_role_id")));
                            }
                return roles;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }
}
