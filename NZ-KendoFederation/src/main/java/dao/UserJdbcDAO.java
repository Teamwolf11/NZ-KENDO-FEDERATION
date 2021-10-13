/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Database.DatabaseConnector;
import domain.AppRoles;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachl
 */
public class UserJdbcDAO implements UserDAO {

    public UserJdbcDAO() {
    }

    /**
     * Gets user based on their Id
     * 
     * @author Lachlan
     * @param userId
     * @return returns User class
     */
    @Override
    public User getUser(String userId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT user_id,email,password, public.user.app_role_id, name FROM public.user INNER JOIN public.app_role ON public.user.app_role_id = app_role.app_role_id WHERE user_id = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                stmt.setInt(1, Integer.parseInt(userId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                     //User fields
                    String username = rs.getString("email");
                    String password = rs.getString("password");

                    //Role fields
                    String roleId = Integer.toString(rs.getInt("app_role_id"));
                    String roleName = rs.getString("name");

                    con.close();
                    
                    AppRoles role = new AppRoles(roleId, roleName);
                    return new User(userId, username, password, role);
                } else {
                    con.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Save user to the db
     * 
     * @author Lachlan
     * @param user
     * @return returns the User class, now with its db assigned userId
     */
    @Override
    public User saveUser(User user) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql2 = "INSERT INTO public.user (email, password, app_role_id) VALUES (?,?,?) RETURNING user_id";

            try (PreparedStatement insertUserstmt = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);) {
                insertUserstmt.setString(1, user.getEmail());
                insertUserstmt.setString(2, user.getPassword());
                insertUserstmt.setInt(3, Integer.parseInt(user.getRoles().getAppRoleId()));

                int row = insertUserstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                //Get User_id from previous insert statement and add to member
                try (ResultSet generatedKeys = insertUserstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(Integer.toString(generatedKeys.getInt(1)));
                        con.close();
                        return user;
                    } else {
                        con.close();
                        throw new SQLException("Updating user failed, no ID obtained.");
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    /**
     * Delete user for the db based on their Id
     * 
     * @author Lachlan
     * @param user 
     */
    
    @Override
    public void deleteUser(User user) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "DELETE FROM public.user WHERE user_id = ?";

            try (PreparedStatement deleteUserrstmt = con.prepareStatement(sql);) {
                deleteUserrstmt.setInt(1, Integer.parseInt(user.getUserId()));

                deleteUserrstmt.executeUpdate();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
