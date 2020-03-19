/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 *
 * @author derekosborne
 */
public class HelperClass {

// <editor-fold desc="Login localization">
    // <editor-fold desc="English Strings">
    final String enUserNameString = "Username";
    final String enPasswordString = "Password";
    final String enLoginString = "Login";
    final String enLoginErrorString = "Username or password incorrect, try again!";
    // </editor-fold>
    
    
    // <editor-fold desc="Spanish Strings">
    final String esUserNameString = "";
    final String esPasswordString = "";
    final String esLoginString = "Iniciar sesion";
    final String esLoginErrorString = "Nombre de usario o contrasena incorrectos, inente neavamente!";
    // </editor-fold>
    
    
    // <editor-fold desc="French Strings">
    final String frUserNameString = "Nom d'utilisateur";
    final String frPasswordString = "Mot de passe";
    final String frLoginString = "S'identifier";
    final String frLoginErrorString = "Nom d'utilisateur ou mot de passe incorrect, réessayez!";
    // </editor-fold>
    
    public String getLoginString(String locale) {
        String loginString;
        
        switch (locale) {
            case "es":
                loginString = this.esLoginString;
                break;
            case "fr":
                loginString = this.frLoginString;
                break;
            default:
                loginString = this.enLoginString;
                break;
        }
        
        return loginString;
    }
    
    public String getLoginErrorString(String locale) {
        String loginErrorString;
        
        switch (locale){
            case "es":
                loginErrorString = this.esLoginErrorString;
                break;
            case "fr":
                loginErrorString = this.frLoginErrorString;
                break;
            default:
                loginErrorString = this.enLoginErrorString;
                break;
        }
        
        return loginErrorString;
    }
    
    public String getUserNameString(String locale) {
        String userNameString;
        
        switch(locale) {
            case "es":
                userNameString = this.esUserNameString;
                break;
            case "fr":
                userNameString = this.frUserNameString;
                break;
            default:
                userNameString = this.enUserNameString;
                break;
        }
        
        return userNameString;
    }
    
    public String getPasswordString(String locale) {
        String passwordString;
        
        switch(locale) {
            case "es":
                passwordString = this.esPasswordString;
                break;
            case "fr":
                passwordString = this.frPasswordString;
                break;
            default:
                passwordString = this.enPasswordString;
                break;
        }
        
        return passwordString;
    }
// </editor-fold>
    
     /**
     * Create user alert box with OK Button
     * @param alertText 
     */
    public void alertUser(String alertText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertText, 
                ButtonType.OK);
        alert.show();
    }
    
    /**
     * Checks if supplied field is empty or not
     * @param field
     * @param message
     * @return boolean true if empty
     */
    public boolean checkIfEmptyField(TextField field, String message) {
        boolean isEmpty = false;
        if("".equals(field.getText())){
            alertUser(message);
            isEmpty = true;
        }
        return isEmpty;
    }
    
    /**
     * Shows alert window with supplied message and YES or NO Buttons
     * @param action
     * @return boolean Result of the alert dialog with user
     */
    public boolean userConfirmation(String action){
        boolean confirmation = false;
        Alert alert = new Alert(
                AlertType.CONFIRMATION, 
                "Are you sure you want to " + action +"?", 
                ButtonType.YES, 
                ButtonType.NO 
        );
        alert.showAndWait();
        
        if(alert.getResult().getText().equals("Yes")){
            confirmation = true;
        }
        
        return confirmation;
    }
    
    
    /**
     * Extract LocalDate from LocalDateTime formats to "dd/MM/yyyy"
     * @param ldt
     * @return LocalDate formatted for as "dd/MM/yyyy"
     */
    public LocalDate getDateFromLocalDateTime(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter.format(ldt);
        LocalDate date = ldt.toLocalDate();
        return date;
    }
    
    /**
     * Extract time from LocalDateTime
     * @param ldt
     * @return String "HH:mm"
     */
    public String getTimeFromLocalDateTime(LocalDateTime ldt) {
        String time = String.valueOf(ldt);
        String[] split = time.split("T");
        System.out.println("time in gtfldt = " + split[1]);
        return split[1];
    }
    
    /**
     * Processes a local date time of system default zone, converts it to UTC
     * then returns it a local date time for submission to the db.
     * @param date
     * @param time
     * @return LocalDateTime converted to UTC
     */
    public LocalDateTime joinDateTime(LocalDate date, LocalTime time){
        LocalDateTime localDT = LocalDateTime.of(date, time);
        return localDT;
    }
    
    /**
     * Returns string formatted to "HH:mma"
     * @param  time String
     * @return String
     */
    public String formatTime(String time) {
        String fTime = time;
                
        // check if time contains a : between hh & mm
        // if not add it
        if(!fTime.contains(":")){
            if(fTime.length() == 3){
                fTime = fTime.substring(0, 1) + ":" + fTime.substring(1, 2);
            } else {
                fTime = fTime.substring(0, 2) + ":" + fTime.substring(2, 4);
            }
        }
        
        // if time is less than 5 chars including :
        // add a leading zero or LocalTime.parse() will fail
        if (fTime.length() < 5) {
            fTime = "0" + fTime;
        }
        
        // determine if the hour is am or pm
        
        // if hours less than 5, assume that the appointment is in the
        // afternoon
        int hrs = Integer.parseInt(fTime.substring(0, 2));
        if(hrs <= 5){
            hrs += 12;
        }
        if(hrs < 12) {
            fTime += "AM";
        } else if (hrs == 12) {
            fTime += "PM";
        } else {
            fTime = fTime.substring(1, fTime.length());
            fTime += "PM";
        }
        

        return fTime; // format of HH:mma or H:mm
    }
    
    /**
     * Returns a 24-hour formatted LocalTime object
     * @param newTime String "hh:mma"
     * @return LocalTime formatted as "hh:mm:
     */
    public LocalTime time24HrFormat(String newTime){
        DateTimeFormatter time24HourFormat = DateTimeFormatter.ofPattern("h:mma");
        
        // strip the leading 0 after conversion
        if(newTime.startsWith("0")){
            newTime = newTime.substring(1, newTime.length());
        }
        
        LocalTime time24 = LocalTime.parse(newTime, time24HourFormat);
        return time24;
    }
    
    /**
     * Returns a 12-hour formatted LocalTime object
     * @param newTime String format of "HH:mm"
     * @return <LocalTime> format of "hh:mma"
     */
    public String time12HrFormat(String newTime){
        
        // remove leading zero if applicable
        if(newTime.startsWith("0")){
            // a leading zero indicates time is am
            newTime = newTime.substring(1, newTime.length());
        } else {
            // hours is noon or later parse hours for testing
            int hrs = Integer.parseInt(newTime.substring(0, 2));
        
            System.out.println("hrs = " + hrs);
            // if hours greater than noon, adjust back to 12-hour format
            if(hrs > 12) {
                hrs = hrs - 12;
                newTime = String.valueOf(hrs) + newTime.substring(2, newTime.length());
            }
            
            // create a string with the adjusted time and add "PM"
            
        }
        
        // return the parsed value
        return newTime;
        
    }
    
    /**
     * Converts LocalDateTime to UTC ZonedDateTime
     * @param ldt
     * @return a UTC converted LocalDateTime object
     */
    public LocalDateTime ldtToUTCConverter(LocalDateTime ldt)
    {   // input ex. 2020-01-17T01:00
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcDT = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        
        // returns 2020-01-17T08:00Z
        return utcDT.toLocalDateTime();
    }
    
    /**
     * Convert Zoned time to users local time 
     * with offset for daylight savings time
     * @param zdt
     * @return LocalDateTime corrected to user time zone
     */
    public LocalDateTime utcToLDTConverter(Timestamp zdt) {
        ZonedDateTime utcTime = ZonedDateTime
                .of(zdt.toLocalDateTime(), ZoneId.of("UTC"));
        ZonedDateTime userTime = utcTime.withZoneSameInstant(ZoneId.systemDefault());
        return userTime.toLocalDateTime();
    }
    
    /**
     * Supplied time is within 15 minutes of now
     * @param  apptStart LocalDateTime
     * @return true if within 15 minutes of LocalDateTime.now()
     */
    public boolean ldtWithin15Minutes(LocalDateTime apptStart){
        long mins = 16L;
        boolean in15Minutes = false;
        
        LocalDateTime nowPlus15 = LocalDateTime.now().plusMinutes(mins);
        
        // test if time is now or within 15 minutes
        if(apptStart.isEqual(LocalDateTime.now()) || 
                (apptStart.isAfter(LocalDateTime.now()) 
                    && apptStart.isBefore(nowPlus15))){
            in15Minutes = true;
        }
        return in15Minutes;
    }
    
    /**
     * Appends a line to the user login log text tile "login.txt"
     * @param message 
     */
    public void logWritter(String message) {
        try(FileWriter fw = new FileWriter("login.txt", true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            try (PrintWriter out = new PrintWriter(bw)) {
                out.println(message);
            }
        } catch(IOException ex) {
            System.out.println("Could not write to the log file" +
                    ex.getMessage());
        }
    }
}
