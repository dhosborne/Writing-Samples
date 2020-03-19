/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.CountryType;
import calendarapp.HelperClass;
import calendarapp.models.ContactCard;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class CountriesReportController implements Initializable {
    ContactCard contact = new ContactCard();
    HelperClass helper = new HelperClass();
    
    ObservableList<ContactCard> customerList = FXCollections.observableArrayList();
    
    @FXML TableView<CountryType> countriesTV;
    @FXML TableColumn<CountryType, String> countryNameCol;
    @FXML TableColumn<CountryType, Integer> counterCol;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            this.customerList = contact.getContactList();
        } catch(SQLException ex) {
            System.out.println("Could not get Customerlist in Country report" +
                    ex.getMessage());
        }
        

        
        drawTable();
        
    }    
    
    private void drawTable(){
        ObservableList<CountryType> ctl = FXCollections.observableArrayList();
        
        if(this.customerList.size() > 0){
            // a map of various customer countries and their occurance
            Map<String, Integer> cntry = new HashMap<>();
            
            // iterate the list and get countries from contact cards
            // if the country does not yet exist in the map add it and set count,
            // else increment the count
            this.customerList.forEach(cust -> {
                int count = cntry.containsKey(
                        cust.getCountry()) ? cntry.get(cust.getCountry()) : 0;
                
                cntry.put(cust.getCountry(), count + 1);
            });
            
            // 
            cntry.entrySet().stream().map((Map.Entry<String, Integer> ent) -> {
               CountryType entry = new CountryType(ent.getKey(), ent.getValue());
               return entry;
            }).forEachOrdered((entry) -> {
                ctl.add(entry);
            });
        }
        
        if(ctl.size() > 0) {            
            this.countriesTV.setItems(ctl);
            this.countryNameCol.setCellValueFactory(
                new PropertyValueFactory<>("countryName")
            );
            this.counterCol.setCellValueFactory(
                new PropertyValueFactory<>("count")
            );
        } else {
            System.out.println("CountryType List was empty");
        }
    }
}
