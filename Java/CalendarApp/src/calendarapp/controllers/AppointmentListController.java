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
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Caffeine
 */
public class AppointmentListController implements Initializable {
   
    String userName = CalendarApp.user.getUserName();
    
    // List of appointments for display
    ObservableList<AppointmentCard> appointmentList = FXCollections.observableArrayList();
    
    // class objects for access to SQL member functions
    HelperClass helper = new HelperClass();
    AppointmentCard appointmentCard = new AppointmentCard();
    Appointment appointment = new Appointment();

// <editor-fold desc="FXML Controls">    
    // controls
    @FXML AnchorPane anchorPane;
    @FXML TableView<AppointmentCard> appointmentListView;
    @FXML Button addApptButton;
    @FXML Button updateApptButton;
    @FXML Button deleteApptButton;
    
    
    // table items
    @FXML TableColumn<AppointmentCard, Integer> aptIdCol;
    @FXML TableColumn<AppointmentCard, String> custNameCol;
    @FXML TableColumn<AppointmentCard, String> userNameCol;
    @FXML TableColumn<AppointmentCard, Integer> descriptionCol;
    @FXML TableColumn<AppointmentCard, String> locationCol;
    @FXML TableColumn<AppointmentCard, String> contactCol;
    @FXML TableColumn<AppointmentCard, String> typeCol;
    @FXML TableColumn<AppointmentCard, String> urlCol;
    @FXML TableColumn<AppointmentCard, ZonedDateTime> startCol;
    @FXML TableColumn<AppointmentCard, ZonedDateTime> endCol;
// </editor-fold>    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getAppointments();
            buildTable();
        } catch(SQLException ex) {
            System.out.println("Could not get appointment list: " + ex.getMessage());
        }
        
        System.out.println("userName is " + this.userName + " in ALC");
        
        
    }

// <editor-fold desc="Member Functions">    
    /**
     * Gets list of appointment objects as appointment cards
     * @throws SQLException 
     */
    private void getAppointments() throws SQLException {
        this.appointmentList = appointmentCard.getAppointmentCardList(CalendarApp.user.getUserId());
    }
    
    
    /**
     * Cell Factory function for building the appointment list table
     */
    private void buildTable(){
        this.appointmentListView.setItems(this.appointmentList);
        this.aptIdCol.setCellValueFactory(
                new PropertyValueFactory<>("appointmentId"));
        this.custNameCol.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));
        this.userNameCol.setCellValueFactory(
                new PropertyValueFactory<>("userName"));
        this.descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        this.locationCol.setCellValueFactory(
                new PropertyValueFactory<>("location"));
        this.contactCol.setCellValueFactory(
                new PropertyValueFactory<>("contact"));
        this.typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        this.urlCol.setCellValueFactory(
                new PropertyValueFactory<>("url"));
        this.startCol.setCellValueFactory(
                new PropertyValueFactory<>("start"));
        this.endCol.setCellValueFactory(
                new PropertyValueFactory<>("end"));
    }
// </editor-fold>

// <editor-fold desc="On-click functions">    
    /**
     * Action function for Add button on-click
     * @throws IOException
     */
    @FXML
    public void addApptButtonClicked() throws IOException {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/calendarapp/views/NewAppointment.fxml"));
            Parent root = loader.load();
            
            //get the controller
            NewAppointmentController nac = loader.getController();
            
            // get the new appointment and add it to the list
            nac.apptProperty().addListener((obs, oldAppt, newAppt) -> {
                this.appointmentList.add(newAppt);
            });
            
            Scene scene = new Scene(root);
            stage.setTitle("Create Appointment");
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println("IOException caught: " + ex.getMessage());
        }
        
        stage.showAndWait();
    }
    
    /**
     * Action function for Update button on-click
     * @throws IOException
     */
    @FXML
    public void updateApptButtonClicked() throws IOException {
        if(this.appointmentListView.getSelectionModel().getSelectedItem() != null){
            Stage stage = new Stage();
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/calendarapp/views/NewAppointment.fxml"));
                Parent root = loader.load();
                
                // get the controller and pass username and the selected
                // appointment object
                NewAppointmentController nac = loader.getController();
                AppointmentCard selected = this.appointmentListView
                        .getSelectionModel().getSelectedItem();
                nac.setAppointmentCard(selected);
                
                // get the updated contact and replace it in the contact list
                // lambda expression used to handle the callback function
                // after updated apointment is returned from the nac
                nac.apptProperty().addListener((obs, old, newAppt) ->{
                    int index = this.appointmentList.indexOf(old);
                    this.appointmentList.set(index, newAppt);
                });
                
                Scene scene = new Scene(root);
                stage.setTitle("Update Appointment");
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println("IOException occured opening Appointment edit: " 
                        + ex.getMessage());
            }
            
            stage.showAndWait();
        }
    }
    
    /**
     * Action function for Delete button on-click
     * @throws SQLException
     */
    @FXML
    public void deleteApptButtonClicked() throws SQLException {
        if(this.appointmentListView.getSelectionModel().getSelectedItem() != null){
            AppointmentCard selected = this.appointmentListView
                    .getSelectionModel().getSelectedItem();
            Appointment appt = new Appointment();
            
            // verify the user really wants to delete the appointment
            
            if(helper.userConfirmation("delete this Appointment")){
                appointment.deleteAppointment(selected.getAppointmentId());
                this.appointmentList.remove(selected);
                buildTable();
            }
        }
    }
// </editor-fold>    
}
