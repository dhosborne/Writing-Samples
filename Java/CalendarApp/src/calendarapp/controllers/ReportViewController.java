/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.ApptType;
import calendarapp.CalendarApp;
import calendarapp.HelperClass;
import calendarapp.models.AppointmentCard;
import calendarapp.models.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Caffeine
 */


// <editor-fold desc="list variables">
public class ReportViewController implements Initializable {
    User user = CalendarApp.user;
    AppointmentCard apc  = new AppointmentCard();
    HelperClass helper = new HelperClass();
    ObservableList<AppointmentCard> apptList = FXCollections.observableArrayList();

    ObservableList<String> monthList =  FXCollections.observableArrayList(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December");
// </editor-fold>
        
// <editor-fold desc="fxml controls">
        @FXML
        Label userNameLabel;
        @FXML
        ComboBox monthCB;
        @FXML
        TableView aptlistTV;
        @FXML
        TableColumn<ApptType, String> appointmentCol;
        @FXML
        TableColumn<ApptType, Integer> countCol;
// </editor-fold>

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
           this.apptList = apc.getAppointmentCardList(user.getUserId()); 
        } catch (SQLException ex) {
            System.out.println("Could not get users appointemnts" 
                    + ex.getMessage());
        }
        
        // populate the month combobox
        monthCB.setItems(monthList);
        
        // listen for changes to the month combobox selected item
        this.monthCB.getSelectionModel().selectedItemProperty().addListener(
                (opt, oldItem, newItem) -> {
                    displayInfo(newItem.toString());
        });
        this.userNameLabel.setText(user.getUserName());
        
        this.appointmentCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.countCol.setCellValueFactory(new PropertyValueFactory<>("count"));  
    }    
    
    private void displayInfo(String selected) {
        ObservableList<ApptType> typeList = FXCollections.observableArrayList();

        // remove previous data from display area
        this.aptlistTV.getItems().clear();
        
        if(apptList.size() > 0){
            // hash map of appointment types that match the selected month
            Map<String, Integer> types = new HashMap<>();
            
            // filter the apptlist and find appointments from the selected month
            // then get the type and add it to the list, or increment the count
            // if they type is already on the list               
            this.apptList.forEach(apt -> {
                LocalDate date = helper.getDateFromLocalDateTime(apt.getStart());
                if(date.getMonth() == Month.valueOf(selected.toUpperCase())){
                int count = types.containsKey(
                        apt.getType()) ? types.get(apt.getType()) : 0;
                types.put(apt.getType(), count + 1);
                }
            });

            // iterate over the map and create objects of ApptType and add them
            // to the typelist
             types.entrySet().stream().map((Map.Entry<String, Integer> ent) -> {
                ApptType entry = new ApptType(ent.getKey(), ent.getValue());
                return entry;
            }).forEachOrdered((entry) -> {
                typeList.add(entry); // add entry to typelist
            });            
        }
        if(typeList.size() > 0){
            this.aptlistTV.setItems(typeList);
              
        } else {
            System.out.println("typelist was empty");
        }
    } 
}
