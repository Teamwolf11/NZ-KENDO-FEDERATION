/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author lachl
 */
public class DatabaseConnector {
    
    public static void main(String [] args) throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user","javaapp");
        props.setProperty("password","D4h/XW57%sw31");
        //props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);
 
        
        if (conn != null) {
                System.out.println("Connected to database");
            }
        
        //call test file
        DatabaseTest dbt = new DatabaseTest();
        dbt.dbTest(conn);
        
        
        conn.close();
    }   
    
}





