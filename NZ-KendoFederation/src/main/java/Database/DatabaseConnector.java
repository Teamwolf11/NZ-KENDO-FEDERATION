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
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

/**
 *
 * @author lachl
 */
public class DatabaseConnector {
    
    //public static Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
    
    public DatabaseConnector(){
        
    }
    
//    public static void main(String [] args) throws SQLException{
//        String url = "jdbc:postgresql://localhost:5432/postgres";
//        Properties props = new Properties();
//        props.setProperty("user","javaapp");
//        props.setProperty("password","D4h/XW57%sw31");
//        //props.setProperty("ssl","true");
//        Connection conn = DriverManager.getConnection(url, props);
// 
//        
//        if (conn != null) {
//                System.out.println("Connected to database");
//            }
//
//        //call test file
//        DatabaseTest dbt = new DatabaseTest();
//        dbt.dbTest(conn);
//        
//        
//        conn.close();
//    }  
    
    public Connection connect() throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user","javaapp");
        props.setProperty("password","D4h/XW57%sw31");
        //props.setProperty("ssl","true");
        return DriverManager.getConnection(url, props);
    }


//    public static void getConnection() {
//
//        source.setDataSourceName("A Data Source");
//        source.setPortNumber(5432);
//        source.setServerName("localhost");
//        source.setDatabaseName("postgres");
//        source.setUser("javaapp");
//        source.setPassword("D4h/XW57%sw31");
//        source.setMaxConnections(10);
//    
//    }
}


