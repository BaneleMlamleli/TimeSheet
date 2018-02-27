/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimeSheet;

/**
 *
 * @author Banele Mlamleli
 */
import java.awt.HeadlessException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class DatabaseConnection {

    static Connection connect = null;
    static ResultSet result = null;
    static Statement stmnt = null;
    static PreparedStatement prepStmnt = null;
    
    public DatabaseConnection(){
                try {
            /*
             * The below code will load the ucanaccess drivers, jar files and connect to the database.
             * It will also check the database in the specified directory
             **/
            connect = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Shaun\\Documents\\Programming\\Java\\Projects\\TimeSheet\\src\\Database\\Timesheet.accdb");
            System.out.println("Driver successfully loaded");
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Unable to load database driver\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean getLogin(String username, String password) {
        boolean login = true;
        try{
            stmnt = connect.createStatement();
            //read all usernames and passwords from the database
            ResultSet checkCredentials = stmnt.executeQuery("SELECT Username, Password FROM Users");

            int checkUsers = 0;
            while(checkCredentials.next()){
                checkUsers += 1;
            }
                        
            if(checkUsers != 0){
                while(checkCredentials.next()){
                    login = checkCredentials.getString("Username").equals(username) && checkCredentials.getString("Password").equals(password);
                }
            }else{
                login = false;
            }

        }catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return login;
    }
    
    public String registerUser(String password, String title, String name, String surname){
        String loginUsername = "";
        try{            
            int countRows = 1;
            stmnt = connect.createStatement();
            ResultSet columns = stmnt.executeQuery("SELECT * FROM Users;");
            while(columns.next()){
                countRows += 1;
            }
            
            loginUsername = ""+name.charAt(0)+surname.charAt(0)+countRows;
            
            String registerCredentials = "INSERT INTO Users(Username, Password, Title, Name, Surname) "+
                                         "VALUES(?, ?, ?, ?, ?);";
            prepStmnt = connect.prepareStatement(registerCredentials);
            prepStmnt.setString(1, loginUsername);
            prepStmnt.setString(2, password);
            prepStmnt.setString(3, title);
            prepStmnt.setString(4, name);
            prepStmnt.setString(5, surname);
            prepStmnt.executeUpdate();
            
        }catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, name+" was registered successfully!","result",JOptionPane.INFORMATION_MESSAGE);
        return loginUsername;
    }
    
    public void timeSheetEntry(String startTime, String stopTime, String loggedInUser, String timeSheetDate){
        try{
            String insertData = "INSERT INTO Timesheet"+
                                "(Start_time, Stop_time, LoggedIn_User, Timesheet_date) "+
                                "VALUES (?, ?, ?, ?);";
            prepStmnt = connect.prepareStatement(insertData);
            prepStmnt.setString(1, startTime);
            prepStmnt.setString(2, stopTime);
            prepStmnt.setString(3, loggedInUser);
            prepStmnt.setString(4, timeSheetDate);
            prepStmnt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Timesheet entry was successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException error){
            System.out.println("Unable to register Timesheet entry\n" + error.getMessage());
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
        public void activityEntry(String activity, String loggedInUser, String activityDate){
        try{
            String insertActivity = "INSERT INTO Activities(Activity, LoggedIn_User, Activity_date) VALUES(?, ?, ?)";
            prepStmnt = connect.prepareStatement(insertActivity);
            prepStmnt.setString(1, activity);
            prepStmnt.setString(2, loggedInUser);
            prepStmnt.setString(3, activityDate);
            prepStmnt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Activity entry was successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException | SQLException error){
            System.out.println("Unable to register your activity\n" + error.getMessage());
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
