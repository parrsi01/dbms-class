/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package College;

/**
 *
 * @author simon
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class createTables{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/mysqljdbc";

        
        String username = "parrsi01";
        String password = "admin";

        String sql = "CREATE DATABASE IF NOT EXISTS Luther";
        
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.execute();
            conn.close();
            url = url + "/Luther";
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DBWriter dbw = new DBWriter();
        ArrayList<String> depts = dbw.readDepartments("data/departments.csv");

        dbw.createTables(username, password, url);
        dbw.addData(username, password, url);
        dbw.addDept(depts, username, password, url);
        dbw.addMajor("data/majors.csv", username, password, url);
    }
    
}