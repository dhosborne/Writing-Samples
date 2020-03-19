 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.CalendarApp;
import calendarapp.HelperClass;
import calendarapp.models.Appointment;
import calendarapp.models.AppointmentCard;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class WeekViewController implements Initializable {
    private Calendar cal;
    private final int ROW = 0;
    private final int MAX_DAYS = 7;
    private int currentMonth;
    private int currentYear;
    private String monthName;
    private int currentDay;
    
    // Class objects for acces to SQL/Generic member functions
    HelperClass helper = new HelperClass();
    AppointmentCard appointmentCard = new AppointmentCard();

    // list for this weeks appointments
    List<AppointmentCard> weekApptList = new ArrayList<>();
    
    
// <editor-fold desc="FXML Controls">   
    @FXML
    private GridPane weekGridPane;
    @FXML
    private Label monthYearLabel;
    @FXML
    private MenuItem closeMenuItem;
// </editor-fold>


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Appointment appt = new Appointment();
        cal = Calendar.getInstance();
        
        // try to populate the weeks appointments list
        try {
            this.weekApptList = appointmentCard.getAppointmentCardList(CalendarApp.user.getUserId());
        } catch (SQLException ex){
            System.out.println("plop " + ex.getMessage());
        }
        
        this.monthYearLabel.setText(this.monthName + ", " + this.currentYear);
        this.populateWeek();
        
    }
    
    
// <editor-fold desc="Getters & Setters">
    public void setMonthString(String month){
        this.monthName = month;
    }
    
    public void setWeekApptList(List<AppointmentCard> apts) {
        this.weekApptList = apts;
    }
    
    public void setCurrentDay(int day) {
        this.currentDay = day;
    }
    
    public void setCurrentMonth(int month){
        this.currentMonth = month;
    }
    
    public void setCurrentYear(int year) {
        this.currentYear = year;
    }
// </editor-fold>
    
// <editor-fold desc="Week creation functions">
    private void populateWeek() {
        
        int date = cal.get(Calendar.DATE); // 11
        int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK); // 1, Sunday 
        int start = 1 - dayOfTheWeek; // calculate difference between sunday and today
        
        for(int i = 0; i < MAX_DAYS; i++) {
            if (date + start > 1 && !(date + start > cal.getActualMaximum(Calendar.DAY_OF_MONTH))){
                addDayPane(date + start, i, ROW);
                start++;  
            }

        }
        
    }
    
    /**
     * Adds the weeks day panes with any appointment information
     * @param day
     * @param col
     * @param row 
     */
    private void addDayPane(int day, int col, int row) {

        Pane pane = new Pane();
        VBox dayVBox = new VBox();
        
        // if count is todays date, highlight it on the calendar
        Label dayLabel = new Label(Integer.toString(day));
        if (currentDay == day) {
            dayLabel.setId("todays_date");
        }
        
        // create the pane and add the day label
        dayVBox.getChildren().add(dayLabel);
        
        // find any appontments that match the current day
        List<AppointmentCard> daysAppointments = new ArrayList<>(); 
        daysAppointments = getDaysAppointments(day);
        
        // appointments were found, create vbox containing apppointments 
        if(daysAppointments.size() > 0){
            VBox aptVBox = new VBox();
            
            // iterate daysAppointments and add them to the vbox
            daysAppointments.forEach((apt) -> {
                String start = helper.time12HrFormat(String.valueOf(helper.getTimeFromLocalDateTime(apt.getStart())));
                String end = helper.time12HrFormat(String.valueOf(helper.getTimeFromLocalDateTime(apt.getEnd())));                
                Label custLabel = new Label(apt.getCustomerName());
                Label timeLabel = new Label(start + " - " + end);
               
                aptVBox.getChildren().add(custLabel);
                aptVBox.getChildren().add(timeLabel);
            });
            
            // add aptVbox to dayVBox
            dayVBox.getChildren().add(aptVBox);
        }
        
        // add dayVBox to pane
        pane.getChildren().add(dayVBox);
        
        // add pane to the calendar grid
        weekGridPane.add(pane, col, row);
        
    }
    
    /**
     * Get a list of appointments for the given day
     * @param day
     * @return 
     */
    private List<AppointmentCard> getDaysAppointments(int day) {
        LocalDate thisDate = LocalDate
                .of(currentYear, currentMonth, day);
        return weekApptList.stream()
                .filter(apt ->
                        helper.getDateFromLocalDateTime(apt.getStart()).equals(thisDate))
                .collect(Collectors.toList());
    }   
// </editor-fold>
    
// <editor-fold desc="Week FXML controll functions">
    @FXML
    public void closeMenuItemClick() {
        Stage stage = (Stage) monthYearLabel.getScene().getWindow();
        stage.close();
    }
    
// </editor-fold>
}
