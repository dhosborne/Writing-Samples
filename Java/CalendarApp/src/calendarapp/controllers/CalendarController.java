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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

enum daysOfTheWeek { Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday };

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class CalendarController implements Initializable {
    CalendarApp app;
    Calendar cal;

    // Class objects for access to SQL member fucntions
    Appointment appt = new Appointment();
    AppointmentCard appointmentCard = new AppointmentCard();
    HelperClass helper = new HelperClass();

    // list of appointments as appointmentcards
    ObservableList<AppointmentCard> appointments = FXCollections.observableArrayList();
    
    
    // regional variables
    private final String userName = CalendarApp.user.getUserName(); // get username of logged in user
    private int intOfMonth;
    private String stringOfMonth;
    private int year;
    private int currentDay;
    
    // maximum size of the calendar grid
    final int MAX_COLS = 7;
    final int MAX_ROWS = 4;
    
    // string array of month names
    private final String[] monthNames = { 
        "January", "Februrary", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};

// <editor-fold desc="Calendar FXML Controls">
    @FXML
    Label helloLabel;
    @FXML
    Label monthLabel;
    @FXML
    Label yearLabel;
    @FXML
    GridPane calendarGrid;
    @FXML
    Pane middlePane;
// </editor-fold>
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.app = new CalendarApp();
        this.cal = Calendar.getInstance();
        this.intOfMonth = cal.get(Calendar.MONTH); // 10
        this.stringOfMonth = this.monthNames[intOfMonth]; // October
        this.currentDay = cal.get(Calendar.DATE); // 30
        this.year = cal.get(Calendar.YEAR); // 2019
        // set user name greeting
        this.helloLabel.setText("Welcome, " + this.userName);

        try{
            initializeCalendar();
        } catch(SQLException ex) {
            helper.alertUser("Could not initalize calendar " + ex.getMessage());
        } 

        
    }    

// <editor-fold desc="calendar creation functions">
    
    /**
     * Set up the calendar view
     * @throws SQLException 
     */
    private void initializeCalendar() throws SQLException {
        // Get todays month, update month label
        this.monthLabel.setText(this.stringOfMonth);

        // Get todays year as string, update year label
        this.yearLabel.setText(Integer.toString(year));
        
        try {
           // clear the grid pane
           this.calendarGrid.getChildren().clear();
           
           // clear the appointments list
           this.appointments.clear();
           this.appointments = appointmentCard.getAppointmentCardList(CalendarApp.user.getUserId()); 
        } catch (SQLException ex) {
            System.out.println(
                    "An error occured when getting users appointments " 
                    + ex.getMessage());
        }
        this.populateCalendar();
    }
    
    /**
     * iterates each day of the month and calls addDay pane to draw
     * the calendar day boxes
     */
    private void populateCalendar() {

        // what day of the week does the month start on?
        int firstDayOfMonth = startDayOfMonth(year, this.intOfMonth); //EX. 4 = Wednesday        
        // how many days are in the current month?
        int lengthOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        

        // populate the calendar
        int dayCounter = 1;
        int col = firstDayOfMonth - 1; // zero-index days
        int row = 0;
        do {
            addDayPane(dayCounter, col, row);
            dayCounter++;
            col++;
            if( col % MAX_COLS == 0) {
                row ++;
                col = 0;
            }
        } while(dayCounter <= lengthOfMonth);
        
        
    }
    
    /**
     * Determines the position in the calendar grid to start drawing
     * the day cards
     * @param year
     * @param month
     * @return dayOfTheWeek
     */
    private int startDayOfMonth(int year, int month) {
        Calendar temp = Calendar.getInstance();
        temp.set(year, month, 1);
        return temp.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * Adds a pane to the calendarGrid containing the days date
     * and any appointments for the day
     * @param int day
     * @param int col
     * @param int row 
     */
    private void addDayPane(int day, int col, int row) {

        Pane pane = new Pane();
        VBox dayVBox = new VBox();
        
        // if count is todays date, highlight it on the calendar
        Label dayLabel = new Label(Integer.toString(day));
        if (this.currentDay == day) {
            dayLabel.setId("todays_date");
        }
        
        // create the pane and add the day label
        dayVBox.getChildren().add(dayLabel);
        
        // find any appontments that match the current day
        List<AppointmentCard> daysAppointments = getDaysAppointments(day);
        
        // appointments were found, create vbox containing apppointments 
        if(daysAppointments.size() > 0){
            VBox aptVBox = new VBox();

            // iterate daysAppointments and add them to the vbox
            daysAppointments.forEach((apt) -> {
                //String start = helper.time12HrFormat(String.valueOf(helper.getTimeFromLocalDateTime(apt.getStart())));
                //String end = helper.time12HrFormat(String.valueOf(helper.getTimeFromLocalDateTime(apt.getEnd())));
                Label custLabel = new Label(apt.getCustomerName());
                //Label timeLabel = new Label(start + " - " + end);
               
                // add the appointment information, meat and potatoes
                aptVBox.getChildren().add(custLabel);
                //aptVBox.getChildren().add(timeLabel);
            });
            
            // add aptVbox to dayVBox
            dayVBox.getChildren().add(aptVBox);
        }
        
        // add dayVBox to pane
        pane.getChildren().add(dayVBox);
        pane.getStyleClass().add("dayPane");
          
        // add pane to the calendar grid
        this.calendarGrid.add(pane, col, row);
    }
    
    /**
     * Finds any appointments in the appointment list that fall on the day
     * passed in arguments; Alerts user if any appointment is within 15 mins
     * of LocalDateTime
     * @param day
     * @return List<AppointmentCard>
     */
    private List<AppointmentCard> getDaysAppointments(int day) {
        List<AppointmentCard> daysAppointments = new ArrayList<>();
        
        
        if(this.intOfMonth + 1 > 0 && this.intOfMonth < 13) { // int of month + 1 to correct Zero-indexing
            LocalDate thisDate = LocalDate.of(this.year, this.intOfMonth + 1, day);
            
            // loop through appointments list and find any appointments
            // that match the date provided in args
            this.appointments.forEach((apc) -> {
                if(thisDate.equals(helper.getDateFromLocalDateTime(apc.getStart()))){
                    // check if appointment start time is with 15 minuts of now
                    if(helper.ldtWithin15Minutes(apc.getStart())){
                        helper.alertUser("You have an appointment with " 
                                + apc.getCustomerName() 
                                + " in 15 minutes or less");
                    }
                    
                    // appointment is today, add to the list
                    daysAppointments.add(apc);
                }
            });            
        } else {
            System.out.println("Month was less than 1 or greater than 12 :/");
        }

        
        return daysAppointments;
    }

// </editor-fold>
    
// <editor-fold desc="calendar FXML control functions">
    // all functions run on separate threads
    
    /**
     * On-Select action for Week View
     * @throws IOException 
     */
    @FXML
    private void weekViewClicked() throws IOException {
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    WeekViewController wvc = new WeekViewController();
                    wvc.setMonthString(this.stringOfMonth);
                    wvc.setCurrentMonth(this.intOfMonth + 1);
                    wvc.setCurrentDay(this.currentDay);
                    wvc.setCurrentYear(this.year);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/calendarapp/views/WeekView.fxml"));
                    loader.setController(wvc);

                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setScene(new Scene((Pane) loader.load()));
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("An exception occured opening the week view " 
                            + ex.getMessage());
                }            
            });
        
        }).start();

        
        
    }
    /**
     * On-Select action for Customer Menu.
     * @throws IOException 
     */
    @FXML 
    private void customerMenuItemSelected() throws IOException {
        
        new Thread(() -> {
            Platform.runLater(() -> {
                Stage stage = new Stage();
                try{
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/calendarapp/views/CustomerList.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Manage Customers");
                    stage.setScene(scene);
                } catch (IOException ex) {
                    System.out.println("An exception occured opening the customer list: "  + ex.getMessage());
                }

                stage.show();            
            
            });
        
        }).start();

        

        
    }
    
    /**
     * On-Select action for Appointment List View
     * @throws IOException 
     */
    @FXML
    private void appointmentMenuControlSelected() throws IOException {
        
        new Thread(() -> {
            Platform.runLater(() -> {
                Stage stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/calendarapp/views/AppointmentList.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Manage Appointments");
                    stage.setScene(scene);
                }catch (IOException ex) {
                    System.out.println("An exception occured opening appointment list" + ex.getMessage());
                }   
                
                stage.show();
                
                // listen for the appointment menu to close and refresh
                // the calendar view to display any appointment changes
                stage.setOnCloseRequest(event -> {
                    try {
                        initializeCalendar(); // the magic
                    } catch (SQLException ex){
                        helper.alertUser("Could not re-initalize calendar " + ex.getMessage());
                    }
                    
                });
            });
        }).start();

    }
    
    /**
     * On-Select action for Report View
     * @throws IOException 
     */
    @FXML
    private void typeMenuItemClicked() throws IOException {
    
        new Thread(() -> {
            Platform.runLater(() -> {
                Stage stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass()
                            .getResource("/calendarapp/views/ReportView.fxml"));
                    Parent root = (Parent) loader.load();
                    Scene scene = new Scene(root);
                    stage.setTitle("Appointment Type Report");
                    stage.setScene(scene);
                }catch (IOException ex) {
                    System.out.println("Could not open report view "
                            + ex.getMessage());
                } catch (Exception ex){
                    System.out.println(ex.getMessage());
                } finally {
                   stage.show();
                }
            });
        
        }).start();
    
    }
    
    @FXML
    private void scheduleMenuItemClicked() throws IOException {
        
        new Thread(() ->{
            Platform.runLater(() -> {
                Stage stage = new Stage();
                
                try{
                    FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/calendarapp/views/ScheduleView.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Consultant Schedule Report");
                stage.setScene(scene);
                
                } catch (IOException ex) {
                    System.out.println("An exception occured when opening "
                            + "schedule view " + ex.getMessage());
                } finally {
                    stage.show();
                }
            });
        
        }).start();
    }
    
    @FXML
    private void countriesMenuItemClicked() throws IOException {
        new Thread(() -> {
            Platform.runLater(()->{
                Stage stage = new Stage();
                
                try{
                    FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/calendarapp/views/CountriesReport.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Customers by Country Report");
                stage.setScene(scene);
                
                } catch (IOException ex) {
                    System.out.println("An exception occured when opening "
                            + "countries view " + ex.getMessage());
                } finally {
                    stage.show();
                }                
            });
        }).start();
    }
    
    /**
     * On-Select action for Quit.
     */
    @FXML
    public void exitMenuControlSelected() {
        Platform.exit();
    }
    
// </editor-fold>
}
