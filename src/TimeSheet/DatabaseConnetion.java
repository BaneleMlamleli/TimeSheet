/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimeSheet;

/**
 *
 * @author Shaun
 */
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.*;

public class DatabaseConnetion {

    static Connection connect = null;
    static ResultSet result = null;
    static Statement stmnt = null;
    static PreparedStatement prepStmnt = null;
    
    public DatabaseConnetion(){
                try {
            /*
             * The below code will load the ucanaccess drivers, jar files and connect to the database.
             * It will also check the database in the specified directory
             **/
            connect = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Shaun\\Documents\\Programming\\Java\\Projects\\TimeSheet\\src\\Database\\Timesheet.accdb");
            System.out.println("Driver successfully loaded");
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseConnetion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Unable to load database driver\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean getLogin(String username, String password) {
        boolean login = true;
        try{
            stmnt = connect.createStatement();
            //read all usernames and passwords from the database
            ResultSet checkCredentials = stmnt.executeQuery("SELECT Username, Password FROM Users;");
            while(checkCredentials.next()){
                login = checkCredentials.getString("Username").equals(username) && checkCredentials.getString("Password").equals(password);
            }
        }catch (HeadlessException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Unable to register provided details\n" + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }        
        return login;
    }
    
    public String getRegisterUser(String password, String title, String name, String surname){
        String loginUsername = "";
        try{            
            int countColumns = 1;
            stmnt = connect.createStatement();
            ResultSet columns = stmnt.executeQuery("SELECT * FROM Users;");
            while(columns.next()){
                countColumns += 1;
            }
            
            loginUsername = ""+name.charAt(0)+surname.charAt(0)+countColumns;
            
            String registerCredentials = "INSERT INTO Users(Username, Password, Title, Name, Surname) VALUES(?, ?, ?, ?, ?);";
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
}
