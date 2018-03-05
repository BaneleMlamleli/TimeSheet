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
    
    /*
     * The below method will register/insert the new user's details into the database
     */
    public String registerUser(String password, String title, String name, String surname){
        String loginUsername = "";
        try{            
            //value that will be incremented depending on the amount of rows there is in the table
            int countRows = 1;
            stmnt = connect.createStatement();
            ResultSet columns = stmnt.executeQuery("SELECT * FROM Users;");
            
            //count how many rows the table has
            while(columns.next()){
                countRows += 1;
            }
            
            /*
             * creating a unique username.
             * username is the first letter of the surname and first letter of the name
             * and the row counter variable.
             * e.g.,    name:       James
             *          surname:    Cutter
             *          countRows:  5
             *
             * Username JC5
             */
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
    
    /* 
     * This method will insert the user's details into the database
     */
    public void timeSheetEntry(String startTime, String stopTime, String loggedInUser, String timeSheetDate, String activity){
        try{
            String insertData = "INSERT INTO Timesheet"+
                                "(Start_time, Stop_time, LoggedIn_User, Timesheet_date, Activity) "+
                                "VALUES (?, ?, ?, ?, ?);";
            prepStmnt = connect.prepareStatement(insertData);
            prepStmnt.setString(1, startTime);
            prepStmnt.setString(2, stopTime);
            prepStmnt.setString(3, loggedInUser);
            prepStmnt.setString(4, timeSheetDate);
            prepStmnt.setString(5, activity);
            prepStmnt.executeUpdate();
            
        }catch(HeadlessException | SQLException error){
            System.out.println("Unable to register Timesheet entry\n" + error.getMessage());
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
     * This method will check the username and password entered if it matches
     * with the database credentials
     */
    public boolean getLogin(String username, String password) {
        boolean login = true;
        try{
            stmnt = connect.createStatement();
            //read all usernames and passwords from the database
            ResultSet checkCredentials = stmnt.executeQuery("SELECT Username, Password FROM Users");
            ArrayList<String> usernames = new ArrayList<>();    //ArrayList to store usernames
            ArrayList<String> passwords = new ArrayList<>();    //ArrayList to store passwordss
               
            //populating ArrayList with usernames and passwords from the database
            while(checkCredentials.next()){
                usernames.add(checkCredentials.getString("Username"));
                passwords.add(checkCredentials.getString("Password"));
            }
            
            for(int a = 0; a < usernames.size(); a++){
                //The below code will check if the username and password entered match with login details in the database
                if ((usernames.get(a).equals(username)) && (passwords.get(a).equals(password))) {
                    login = true;
                    break;
                } else {
                    login = false;
                }
            }

        }catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return login;
    }
    
     /*
      * This method will check the username, password and title entered if they match
      * with the database credentials in order to authenticate the requested change. 
      */
    public boolean getAutherisation(String username, String password) {
        boolean autherisation = false;
        try{
            stmnt = connect.createStatement();
            //read all usernames and passwords from the database
            ResultSet checkCredentials = stmnt.executeQuery("SELECT Title FROM Users WHERE Username = '"+username+"'"+
                                                            "AND Password = '"+password+"'");
               
            //populating ArrayList with usernames and passwords from the database
            while(checkCredentials.next()){
                if(checkCredentials.getString("Title").equals("Manager")){
                    autherisation = true;
                    break;
                }else{
                    if(checkCredentials.getString("Title").equals("Team Leader")){
                        autherisation = true;
                        break;
                    }
                }
            }
        }catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Unable to read the Title of the entered user\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return autherisation;
    }
    
    /*
     * The below method will update the selected timesheet entry
     */
    public void updateTimeSheetEntry(String date, String startTime,
            String endTime, String  activity, String user, String authUser, int ID){
        try{
            stmnt = connect.createStatement();
            String updateCredentials = "UPDATE Timesheet "
                    + "SET Start_time = ?, Stop_time = ?, LoggedIn_User = ?, "
                    + "Timesheet_date = ?, Activity = ?, Amending_user = ? "
                    + "WHERE Timesheet_ID = " + ID;
            prepStmnt = connect.prepareStatement(updateCredentials);
            prepStmnt.setString(1, startTime);
            prepStmnt.setString(2, endTime);
            prepStmnt.setString(3, user);
            prepStmnt.setString(4, date);
            prepStmnt.setString(5, activity);
            prepStmnt.setString(6, authUser);
            prepStmnt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Successfully update TimeSheet", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException | HeadlessException error){
            System.out.println("Unable to update provided details\n" + error.getMessage());
            JOptionPane.showMessageDialog(null, "Unable to update provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    /*
     * The below method will deleted the selected timesheet entry
     */
    public void deleteTimeSheetEntry(int ID){
        try{
            stmnt = connect.createStatement();
            String deleteCredentials = "DELETE FROM Timesheet "
                    + "WHERE Timesheet_ID = " + ID;
            prepStmnt = connect.prepareStatement(deleteCredentials);
            prepStmnt.execute();
            
            JOptionPane.showMessageDialog(null, "Successfully deleted TimeSheet", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException | HeadlessException error){
            System.out.println("Unable to update provided details\n" + error.getMessage());
            JOptionPane.showMessageDialog(null, "Unable to delete the selected row\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }
}
