# TimeSheet
This is a small test project I was assigned at a company that I applied at.

This is a simulation application of typical company time sheet.

N.B. I have used UCanAccess 4.0.2 for the Java JDBC client program to read/write Microsoft Access database.
I have also uploaded uploaded it in this repo.

UCanAccess Instruction
======================

1. Donwload UCanAccess from http://ucanaccess.sourceforge.net/site.html or use the file I uploaded.
2. Extract the zip file
3. Copy all the JAR files into the following directoy
   C:\Program Files\Java\jdk1.8.0_144\jre\lib\ext

Program flow
============

1. Login screen
- This is a basic login screen that will allow a user to login or be registered if not registered on this system yet.<br>
<img src="/images/login.PNG">	

2. Timesheet screen
- Once the user successfully login the Timesheet window will appear with the running time.Once the user logs in successfully that will be
  their initial or starting time which will be displayed in this windows as well.
- If <b><i>Stop timesheet</i></b> button is clicked a pop-up box appear asking to specify the reason for stopping timesheet (e.g., It could be
  you're taking a break, going to a meeting or any other activity).
- The TextArea will display all your activities until the you close you timesheet for the day.
- If there's any changes that must be done your timesheet you can click <b><i>Amend timesheet</i></b>. The changes can only be done by an
  authorised user (e.g., Manager, team leader e.t.c)<br>
  <img src="/images/timesheet.PNG">
