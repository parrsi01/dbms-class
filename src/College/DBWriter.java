/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package College;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class DBWriter {
    
    public ArrayList<String> readDepartments(String file) {
        ArrayList<String> departments = new ArrayList<>();
        try {
            Scanner fs = new Scanner(new File(file));
            while (fs.hasNextLine()) {
                // TODO: Parse each line into an object of type Address and add it to the ArrayList
                String line = fs.nextLine();
                //need to make the address class call an actual thing
                departments.add(line);
                //fs.nextLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(DBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return departments;
    }
    
    public void createTables(String user, String pass, String url) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try{
           //STEP 2: Register JDBC driver
           //Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           System.out.println("Connecting to a selected database...");
           conn = DriverManager.getConnection(url, user, pass);
           System.out.println("Connected database successfully...");

           //STEP 4: Execute a query
           System.out.println("Creating table in given database...");
           statement = conn.createStatement();
           
           statement.executeUpdate("DROP TABLE IF EXISTS enrollment;");
           statement.executeUpdate("DROP TABLE IF EXISTS student;");
           statement.executeUpdate("DROP TABLE IF EXISTS section;");
           statement.executeUpdate("DROP TABLE IF EXISTS major;");
           statement.executeUpdate("DROP TABLE IF EXISTS course;");
           statement.executeUpdate("DROP TABLE IF EXISTS faculty;");
           statement.executeUpdate("DROP TABLE IF EXISTS semester;");
           statement.executeUpdate("DROP TABLE IF EXISTS location;");
           statement.executeUpdate("DROP TABLE IF EXISTS department;");
           
           String sql = "CREATE TABLE semester " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " year INTEGER, " + 
                        " season TEXT, " + 
                        " PRIMARY KEY ( id ))"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE location " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " building TEXT, " + 
                        " room INTEGER, " + 
                        " purpose TEXT, " +
                        " PRIMARY KEY ( id ))"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE department " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " name TEXT, " + 
                        " building TEXT, " + 
                        " PRIMARY KEY ( id ))"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE major " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " department INTEGER, " + 
                        " name TEXT, " + 
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( department ) REFERENCES department(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE course " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " department INTEGER, " + 
                        " abbreviation TEXT, " + 
                        " number INTEGER, " +
                        " title TEXT, " +
                        " credits INTEGER, " +
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( department ) REFERENCES department(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE faculty " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " name TEXT, " + 
                        " department INTEGER, " + 
                        " startDate INTEGER, " +
                        " endDate INTEGER, " +
                        " office INTEGER, " +
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( department ) REFERENCES department(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( startDate ) REFERENCES semester(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( endDate ) REFERENCES semester(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( office ) REFERENCES location(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE section " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " course INTEGER, " + 
                        " instructor INTEGER, " + 
                        " offered INTEGER, " +
                        " location INTEGER, " +
                        " startHour TIME, " +
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( course ) REFERENCES course(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( instructor ) REFERENCES faculty(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( offered ) REFERENCES semester(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( location ) REFERENCES location(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE student " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " name TEXT, " + 
                        " graduationDate INTEGER, " + 
                        " major INTEGER, " +
                        " advisor INTEGER, " +
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( graduationDate ) REFERENCES semester(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( major ) REFERENCES major(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( advisor ) REFERENCES faculty(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           sql = "CREATE TABLE enrollment " +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " student INTEGER, " + 
                        " section INTEGER, " + 
                        " grade TEXT, " +
                        " PRIMARY KEY ( id ), " +
                        " FOREIGN KEY ( student ) REFERENCES student(id) ON DELETE CASCADE," +
                        " FOREIGN KEY ( section ) REFERENCES section(id) ON DELETE CASCADE)"; 

           statement.executeUpdate(sql);
           
           System.out.println("Created tables in given database...");
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(statement!=null)
                 conn.close();
           }catch(SQLException se){
           }// do nothing
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }//end finally try
        }//end try
        System.out.println("Goodbye!");
     }
    public void addData(String user, String pass, String url) throws SQLException {
        ArrayList<String> buildings = new ArrayList<>();
        buildings.add("Olin");
        buildings.add("Valders");
        buildings.add("Campus House");
        buildings.add("Main");
        buildings.add("Sampson Hoffland");
        buildings.add("Koren");
        buildings.add("Regents Center");
        buildings.add("Preus Library");
        buildings.add("Center for the Arts");
        buildings.add("Union");
        buildings.add("Ockham House");
        buildings.add("Jenson-Noble");
        
        ArrayList<Integer> years = new ArrayList<>();
        years.add(2018);
        years.add(2019);
        years.add(2020);
        years.add(2021);
        years.add(2022);

        
                
           //STEP 2: Register JDBC driver
           //Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
        System.out.println("Connecting to a selected database...");
        final Connection conn = DriverManager.getConnection(url, user, pass);
           
        String query = " INSERT INTO semester (year, season) VALUES (?, ?)";

        for (Integer i: years) {

      // create the mysql insert preparedstatement
            PreparedStatement preparedstatement = conn.prepareStatement(query);
            preparedstatement.setInt (1, i);
            preparedstatement.setString (2, "Spring");
      // execute the preparedstatement
            preparedstatement.execute();
            
            preparedstatement = conn.prepareStatement(query);
            preparedstatement.setInt (1, i);
            preparedstatement.setString (2, "Summer");
            preparedstatement.execute();
            
            preparedstatement = conn.prepareStatement(query);
            preparedstatement.setInt (1, i);
            preparedstatement.setString (2, "Fall");
            preparedstatement.execute();
            
            preparedstatement = conn.prepareStatement(query);
            preparedstatement.setInt (1, i);
            preparedstatement.setString (2, "Winter");
            preparedstatement.execute();
        }
        query = " INSERT INTO location (building, room, purpose) VALUES (?, ?, ?)";
        final PreparedStatement ps = conn.prepareStatement(query);
        for (String b: buildings) {
            
            IntStream.range(0, 60).forEach(
                n -> {
                    try{
                        ps.setString(1, b);
                        if (n < 21) {
                            ps.setInt(2, 100 + n);
                            ps.setString(3, "classroom");
                        } else if (n < 41) {
                            n = n-20;
                            ps.setInt(2, 200 + n);
                            ps.setString(3, "classroom");
                        } else {
                            n = n-40;
                            ps.setInt(2, 300 + n);
                            ps.setString(3, "office");
                        }
                        ps.execute();

                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                    System.out.println(n);
                }
            );
        }
        conn.close();
    }
    public void addDept(ArrayList<String> depts, String user, String pass, String url) throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, pass);
        String query = " INSERT INTO department (name, building) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        
        for (String al: depts) {
            String[] s = al.split("\\|");
            //System.out.println(s[0]);
            //System.out.println(s[3]);
            ps.setString(1, s[0]);
            ps.setString(2, s[3]);
            ps.execute();
        }
        conn.close();
    }
    public void addMajor(String file, String user, String pass, String url) throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, pass);
        String query = " INSERT INTO major (department, name) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        try {
            Scanner fs = new Scanner(new File(file));
            while (fs.hasNextLine()) {
                String[] line = fs.nextLine().split("\\|");
                if (line.length == 2) {
                    Statement statement = conn.createStatement();
                    System.out.println(line[0]);
                    ResultSet results = statement.executeQuery("SELECT id FROM department WHERE name = '" + line[1] + "';");
                    if (results.first()) {
                        int dept_id = results.getInt("id");
                        results.close();
                        ps.setInt(1, dept_id);
                        ps.setString(2, line[0]);
                        ps.execute();
                    }
                }
            }
            conn.close();
                 } catch (IOException ex) {
            Logger.getLogger(DBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        }


     