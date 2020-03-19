/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.HelperClass;
import calendarapp.models.AppointmentCard;
import calendarapp.models.User;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class ScheduleViewController implements Initializable {

    @FXML
    ComboBox userCB;
    
    @FXML
    TableView appointmentListView;
    @FXML TableColumn<AppointmentCard, Integer> aptIdCol;
    @FXML TableColumn<AppointmentCard, String> custNameCol;
    @FXML TableColumn<AppointmentCard, String> typeCol;
    @FXML TableColumn<AppointmentCard, ZonedDateTime>startCol;
    @FXML TableColumn<AppointmentCard, ZonedDateTime>endCol;
    
    
    HelperClass helper = new HelperClass();
    AppointmentCard apc = new AppointmentCard();
    User user = new User();
    
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<AppointmentCard> aptList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // get a list of all appointments
        this.aptList = apc.getAppointmentCardList();

        // get a list of all users
        this.users = user.getAllUsers();
        
        // populate user comboBox
        this.userCB.setItems(users);
        
        // listen for a selection in userCB
        this.userCB.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldItem, newItem) -> {
            displayInfo(newItem.toString());
        });
    }
    
    private void displayInfo(String selected) {
        new Thread(() -> {
            Platform.runLater(() -> {
                ObservableList<AppointmentCard> userAptsList = apc.getAppointmentCardList(selected);

                this.appointmentListView.getItems().clear();
                this.appointmentListView.setItems(userAptsList);

                this.aptIdCol.setCellValueFactory(
                        new PropertyValueFactory<>("appointmentId"));       
                this.custNameCol.setCellValueFactory(
                        new PropertyValueFactory<>("customerName"));
                this.typeCol.setCellValueFactory(
                        new PropertyValueFactory<>("type"));
                this.startCol.setCellValueFactory(
                        new PropertyValueFactory<>("start"));
                this.endCol.setCellValueFactory(
                        new PropertyValueFactory<>("end"));            

            });
        
        }).start();

    }
    
    
    
}
