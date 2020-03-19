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
import calendarapp.models.Customer;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class NewAppointmentController implements Initializable {
    
    /**
     * Observable appointment card wrapper object
     */
    public final ReadOnlyObjectWrapper<AppointmentCard> appointmentCard 
            = new ReadOnlyObjectWrapper();

    /**
     * Return appointment card read-only
     * @return
     */
    public ReadOnlyObjectProperty<AppointmentCard> apptProperty(){
        return appointmentCard.getReadOnlyProperty();
    }
    
    int userId = CalendarApp.user.getUserId();
    String userName = CalendarApp.user.getUserName();
    LocalTime startOfBusiness = LocalTime.of(9, 00);
    LocalTime closeOfBusiness = LocalTime.of(17, 00);
    
    
    boolean newAppointment = true;
    int appointmentId;
    
    // Class objects for access to SQL/Generic memeber functions
    HelperClass helper = new HelperClass();
    AppointmentCard apc = new AppointmentCard();
    Customer customer = new Customer();

    // objects for creation 
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    List<AppointmentCard> appointmentCardList = new ArrayList<>();

    
    
// <editor-fold desc="Form Controls">    
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private Label editTypeLabel;
    @FXML private Label userNameLabel;
    @FXML private ComboBox customerCB;
    @FXML private TextField titleTF;
    @FXML private TextField descriptionTF;
    @FXML private TextField locationTF;
    @FXML private TextField contactTF;
    @FXML private TextField typeTF;
    @FXML private TextField urlTF;
    @FXML private DatePicker dateDP;
    @FXML private TextField startTF;
    @FXML private TextField endTF;
    

// </editor-fold>    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.customerCB.setConverter( new StringConverter<Customer>(){
            @Override
            public String toString(Customer cust) {
                return cust.getCustomerName();
            }
            
            @Override
            public Customer fromString(String name) {
                Customer result = new Customer();
                
                for(Customer c : customerList){
                    if(name.equals(c.getCustomerName())){
                        result = c;
                    }
                }
                
                return result;
            }
        });
        // get customer list for choice box
        
        if(userName != null){
            this.userNameLabel.setText(this.userName);
        }
        
        try{
            this.customerList = this.customer.getCustomerList();
            this.customerCB.setItems(customerList);
        } catch(SQLException ex) {
            System.out.println("An exception occured while getting customer list" 
                    + ex.getMessage());
        }
        
        // check if there is a customer being edited
        // add the customer to the list after saved
        this.apptProperty().addListener((obs, oldapt, newapt) -> {
            if(newapt.getAppointmentId() > 0){
                this.newAppointment = false;
                try {
                    this.customer = customer.recallCustomer(newapt.getCustomerName());
                } catch (SQLException ex) {
                    System.out.println("Could not recall customer in new appointment: " 
                            + ex.getMessage());
                }
                
                this.appointmentId = newapt.getAppointmentId();
                this.editTypeLabel.setText("Appointment " + newapt.getAppointmentId());
                setApptCardControls(newapt);
            }
        });
        
    }    
    
// <editor-fold desc="Getters & Setters">

    
    /**
     * Set the card from outside the controller
     * @param appt AppointmentCard
     */
    public void setAppointmentCard(AppointmentCard appt){
        this.appointmentCard.set(appt);
    }
// </editor-fold>
    
// <editor-fold desc="Member functions">
    
    /**
     * Set the controls with an appointment card to be edited
     * @param newAppt <AppointmentCard>
     */
    private void setApptCardControls(AppointmentCard newAppt) {
        if(newAppt != null){
            
            String startTime = helper.time12HrFormat(
                        helper.getTimeFromLocalDateTime(newAppt.getStart()
                    )
            );
            
            String endTime = helper.time12HrFormat(
                        helper.getTimeFromLocalDateTime(newAppt.getEnd()
                    )
            );
            
            this.customerCB.getSelectionModel().select(this.customer);
            this.titleTF.setText(newAppt.getTitle());
            this.descriptionTF.setText(newAppt.getDescription());
            this.locationTF.setText(newAppt.getLocation());
            this.contactTF.setText(newAppt.getContact());
            this.typeTF.setText(newAppt.getType());
            this.urlTF.setText(newAppt.getUrl());
            // set date from start date
            this.dateDP.setValue(helper.getDateFromLocalDateTime(newAppt.getStart()));
            // set hour from start date hour
            this.startTF.setText(startTime);
            // set hour from end date hour
            this.endTF.setText(endTime);
        } else {
            System.out.println("AppointmentCard was null");
        }
    }
    

      
    /**
     * Validate the users input.
     * @return boolean true if all cases pass
     */
    private boolean validateUserInput() {
        boolean valid = true;
        
        // check if the customer is valid or exists
        // customers are selected as objects and cannot be created
        // in appointments

        // check if the start and end times have atleast three digits
        // but no more than 5 (with 
        if(this.startTF.getText().length() < 3 || this.startTF.getText().length() > 5  ){
            helper.alertUser("Start time invalid format. Must be \"HH:mm\"");
        }
        if(this.endTF.getText().length() < 3 || this.endTF.getText().length() > 5  ){
            helper.alertUser("End time invalid format. Must be \"HH:mm\"");
        }
        
        
        //  ensure that the user supplied times meat the required regex for
        // parsing; then convert the time to 24 hour format
        LocalTime start = helper.time24HrFormat(
                helper.formatTime(this.startTF.getText()));
        LocalTime end = helper.time24HrFormat(
                helper.formatTime(this.endTF.getText()));

        
        // check if appointment time is outside business hours       
        if( 
                (start.isAfter(this.closeOfBusiness) || start.isBefore(this.startOfBusiness)) ||
                (end.isAfter(this.closeOfBusiness) || end.isBefore(this.startOfBusiness))
        ){
            helper.alertUser("This appointment is outside of normal business hours: " 
                    + this.startOfBusiness + "AM " + this.closeOfBusiness + "PM" );
            valid = false;
        }
        
        
        // check if the appointment overlaps with another appointment
        // that belongs to the user
        try {
            this.appointmentCardList = apc.getAppointmentCardList(userId);

            
            if (this.appointmentCardList.size() > 0){

                // search the appointment list for any appointments that overlap
                // on the same day as the appointment.
                if(this.appointmentCardList.stream().anyMatch(apt -> {
                    
                        // ignore current 
                        if( this.appointmentId == apt.getAppointmentId()){
                            return false;
                        }
                        LocalDate aptDate = helper.getDateFromLocalDateTime(apt.getStart());

                        // if appointments are on the same day, then test if their
                        // appointment times conflict
                        if(aptDate.equals(this.dateDP.getValue())){

                            // get the start and end times
                            LocalTime aptStart = LocalTime.parse(
                                    helper.getTimeFromLocalDateTime(apt.getStart())
                            );
                            LocalTime aptEnd = LocalTime.parse(
                                    helper.getTimeFromLocalDateTime(apt.getEnd())
                            );


                            // test the edge cases of the appointments
                            if(start.equals(aptStart) || end.equals(aptEnd)){
                                helper.alertUser("This appointment overlaps with appointment id " 
                                        + apt.getAppointmentId());
                                return true;
                            }

                            // test if the start or end times fall within an existing
                            // appointment time.
                            if (start.isAfter(aptStart) &&  start.isBefore(aptEnd) ||
                                    end.isAfter(aptStart) && end.isBefore(aptEnd)) {
                                helper.alertUser("This appointment overlaps with apt id " +
                                        apt.getAppointmentId());
                                return true;
                            }
                            
                            return false;
                        }
                        
                        return false;  
                    })){
                    valid = false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL ex occured while recalling appointments: " +
                    ex.getMessage());
        }
        
        return valid;
    }
// </editor-fold>
// <editor-fold desc="FXML control functions">
    /**
     * On-click action of Save button
     * @param event 
     */
    @FXML
    private void saveButtonAction(ActionEvent event){

        
        if(validateUserInput() && CalendarApp.user != null){
            
            // get customer selected in the choice box
            customer = (Customer) this.customerCB.getSelectionModel().getSelectedItem();
            
            // format start and end times to 24 hrs
            LocalTime startTime = helper.time24HrFormat(
                    (helper.formatTime(this.startTF.getText())
            ));
            LocalTime endTime = helper.time24HrFormat(
                    (helper.formatTime(this.endTF.getText())
            ));


            // LocalDateTime objects from date 
            // and start and end 24 hour formatted times.
            LocalDate apptDate = this.dateDP.getValue();
            LocalDateTime startDT = helper.joinDateTime(apptDate, 
                    startTime);
            LocalDateTime endDT = helper.joinDateTime(apptDate, 
                    endTime);
            
            // create a new appointment object for saving
            Appointment newApt = new Appointment(
                    this.customer.getCustomerId(),
                    CalendarApp.user.getUserId(),
                    this.titleTF.getText(),
                    this.descriptionTF.getText(),
                    this.locationTF.getText(),
                    this.contactTF.getText(),
                    this.typeTF.getText(),
                    this.urlTF.getText(),
                    startDT,
                    endDT,
                    CalendarApp.user.getUserName()
            );
            
            try{
                int res = 0; // result of sql ops
                if(newAppointment){
                    // appointment is new so create it
                    res = newApt.createAppointment(newApt);   
                } else {
                    
                    // we're editing an appointment so call update instead
                    newApt.setAppointmentId(appointmentId);
                    res = newApt.updateAppointemnt(newApt);
                }
                
                // if the save was successful, notify the user and update
                // the observable appointment card
                if(res > 0 && customer != null){
                    
                    if(newAppointment) {
                        appointmentId = res;
                    }
                    // notify user of save success
                    helper.alertUser("Appointment created");
                    
                    // get the new appointment card as it exists in the
                    // database and add it to the observable appointment list
                    AppointmentCard newCard = new AppointmentCard();
                    newCard = newCard.getAppointmentCard(appointmentId);
                    
                    // update the observable appointment card
                    this.appointmentCard.set(newCard);
                    
                    // all done, close the form
                    closeWindow();
                    
                } else {
                    // save or update failed because res = 0
                    helper.alertUser("Appointment could not be saved");
                }
                
            } catch(SQLException ex){
                // an sql exception occured when saving the appointemnt
                System.out.println("An error occured saving the appointment:" + 
                        ex.getMessage());
            }
        }
    }
    
    /**
     * On-click action of Cancel button
     * @param event 
     */
    @FXML
    private void cancelButtonAction(ActionEvent event){
       // make sure the user really wants to abandon the form
       if(helper.userConfirmation("cancel")){
           closeWindow();
       }
    }


    /**
     * Closes the stage
     */
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();    
    }
// </editor-fold>
}
