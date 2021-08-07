/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lachl
 */
public class DatabaseTest {
    
    public static void dbTest(Connection conn) throws SQLException{
        PreparedStatement st;  // For creating queries
        ResultSet rs;          // For running queries with executeQuery()
        int row;               // Inserting queries require reference to an int      
        
        // Basic INSERT Query
        st = conn.prepareStatement("INSERT INTO public.member(nzkf_membership_id, first_name, last_name) VALUES (?,?,?)");
        st.setString(1,"234532_A");
        st.setString(2, "Mike");
        st.setString(3, "Wazowski");

        row = st.executeUpdate();
        System.out.println("Inserted " + row + " row");
        
        
        // basic SELECT query
        st  = conn.prepareStatement("SELECT * FROM public.member");
        rs = st.executeQuery();
        
        while (rs.next())
            {
                System.out.print("Member");
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
            }     
        
        // Basic UPDATE Query
        st = conn.prepareStatement("UPDATE public.member SET nzkf_membership_id = ? WHERE first_name = ?");
        st.setString(1,"BBBBBB");
        st.setString(2, "Mike");

        row = st.executeUpdate();
        System.out.println("Updated " + row + " row(s)");
        
        // Basic DELETE Query
        st = conn.prepareStatement("DELETE FROM public.member WHERE first_name = ?");
        st.setString(1, "Mike");

        row = st.executeUpdate();
        System.out.println("Deleted " + row + " row(s)");
        
        
        
        System.out.println("Looks like everything works!");
    }
    
}
