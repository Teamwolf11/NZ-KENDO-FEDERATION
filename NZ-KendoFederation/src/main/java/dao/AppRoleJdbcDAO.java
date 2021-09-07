/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Database.DatabaseConnector;
import domain.AppRoles;
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
public class AppRoleJdbcDAO implements AppRoleDAO {

    @Override
    public AppRoles getAppRole(String roleId) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "SELECT * FROM public.app_role WHERE app_role_id = ? ORDER BY app_role_id";

            try (PreparedStatement stmt = con.prepareStatement(sql);) {
                System.out.println("Finally here");
                stmt.setInt(1, Integer.parseInt(roleId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String roleName = rs.getString("name");
                    String role = Integer.toString(rs.getInt("app_role_id"));

                    con.close();
                    return new AppRoles(role, roleName);
                } else {
                    con.close();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public AppRoles saveAppRole(AppRoles role) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "INSERT INTO public.user (name) VALUES (?) RETURNING app_role_id";

            try (PreparedStatement insertUserstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                insertUserstmt.setString(1, role.getName());

                int row = insertUserstmt.executeUpdate();

                if (row == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                //Get User_id from previous insert statement and add to member
                try (ResultSet generatedKeys = insertUserstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        role.setAppRoleId(Integer.toString(generatedKeys.getInt(1)));
                        con.close();
                        return role;
                    } else {
                        con.close();
                        throw new SQLException("Updating role class failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteAppRole(AppRoles role) {
        try {
            DatabaseConnector db = new DatabaseConnector();
            Connection con = db.connect();

            String sql = "DELETE FROM public.app_role WHERE app_role_id = ?";

            try (PreparedStatement deleteRolestmt = con.prepareStatement(sql);) {
                deleteRolestmt.setInt(1, Integer.parseInt(role.getAppRoleId()));

                deleteRolestmt.executeUpdate();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
