package mlb;
/**
 * @author Roman Yasinovskyy
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseReader {
    private Connection db_connection;
    private final String SQLITEDBPATH = "jdbc:sqlite:data/mlb/mlb.sqlite";
    
    public DatabaseReader() { }
    /**
     * Connect to a database (file)
     */
    public void connect() {
        try {
            this.db_connection = DriverManager.getConnection(SQLITEDBPATH);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseReaderGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Disconnect from a database (file)
     */
    public void disconnect() {
        try {
            this.db_connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseReaderGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Populate the list of divisions
     * @param divisions
     */
    public void getDivisions(ArrayList<String> divisions) {
        Statement stat;
        ResultSet results;
        
        this.connect();
        try {
            stat = this.db_connection.createStatement();
            // TODO: Write an SQL statement to retrieve a league (conference) and a division
            String sql = "SELECT DISTINCT division,conference FROM team";
            // TODO: Add all 6 combinations to the ArrayList divisions
            results = stat.executeQuery(sql);

            ResultSetMetaData rsmd = results.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (results.next()) {
                String divisionstring = " | ";
                for (int i = 1; i <= columnsNumber; i++) {
                    divisionstring += results.getString(i);
                }
                divisions.add(divisionstring);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.disconnect();
        }
    }
    /**
     * Read all teams from the database
     * @param confDiv
     * @param teams
     */
    public void getTeams(String confDiv, ArrayList<String> teams) {
        Statement stat;
        ResultSet results;
        String conference = confDiv.split(" | ")[0];
        String division = confDiv.split(" | ")[2];
        
        this.connect();
        try {
            stat = this.db_connection.createStatement();
            // TODO: Write an SQL statement to retrieve a teams from a specific division
            String[] con_div = confDiv.split("[|]");
            con_div[0] = con_div[0].replaceAll("\\s+", "");
            con_div[1] = con_div[1].replaceAll("\\s+", "");
            String sql = "SELECT * FROM team WHERE conference==\"" + con_div[0] + "\"and division==\"" + con_div[1] + "\"";

            results = stat.executeQuery(sql);
            // TODO: Add all 5 teams to the ArrayList teams
            ResultSetMetaData rsmd = results.getMetaData();
            //An object that can be used to get information about the types and properties of the columns in a ResultSet object.
            int columnsNumber = rsmd.getColumnCount();
            while (results.next()) {
                String teamString = "";
                for (int i = 1; i <= columnsNumber; i++) {
                    teamString += results.getString(i);
                }
                teams.add(teamString);
            }
            results.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.disconnect();
        }
    }
    /**
     * @param teamName
     * @return Team info
     */
    public Team getTeamInfo(String teamName) throws SQLException {
        Team team = null;
                this.connect();
        try {
            Statement stat = this.db_connection.createStatement();
            // TODO: Retrieve team info (roster, address, and logo) from the database

            String sql = "﻿SELECT * FROM team";
            ResultSet results = stat.executeQuery(sql);
            Team newTeam;
        results = stat.executeQuery(sql);
        
        results.close();
        return team;
    } catch (SQLException ex) {
        Logger.getLogger(DatabaseReader.class.getName()).log(Level.SEVERE, null, ex);
        return team;
    }
}}