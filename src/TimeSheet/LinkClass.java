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
public class LinkClass {
    
    private String username;        //this is from the login window
    private String startTimeSheet;  //starting time
    private String stopTimeSheet;   //stoping time
    private String activity;        //activity performed by the user
    
    public LinkClass(){}
    
    public LinkClass(String username){
        this.username = username;
    }
       
    public LinkClass(String startTime, String stopTime, String activity){
        this.startTimeSheet = startTime;
        this.stopTimeSheet = stopTime;
        this.activity = activity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartTimeSheet() {
        return startTimeSheet;
    }

    public void setStartTimeSheet(String startTimeSheet) {
        this.startTimeSheet = startTimeSheet;
    }

    public String getStopTimeSheet() {
        return stopTimeSheet;
    }

    public void setStopTimeSheet(String stopTimeSheet) {
        this.stopTimeSheet = stopTimeSheet;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
    
    @Override
    public String toString(){
        return "Logged in user:  "+username;
    }
}
