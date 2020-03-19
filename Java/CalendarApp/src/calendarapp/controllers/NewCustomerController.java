/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.CalendarApp;
import calendarapp.HelperClass;
import calendarapp.models.Address;
import calendarapp.models.City;
import calendarapp.models.ContactCard;
import calendarapp.models.Country;
import calendarapp.models.Customer;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class NewCustomerController implements Initializable {
    
    private final ReadOnlyObjectWrapper<ContactCard> contact = new ReadOnlyObjectWrapper();

    /**
     * Read-only contact object
     * @return
     */
    public ReadOnlyObjectProperty<ContactCard> contactProperty(){
        return contact.getReadOnlyProperty();
    }
    
    String userName = CalendarApp.user.getUserName();
    Boolean newCustomer = true;
    int customerId;
    HelperClass helper = new HelperClass();

// <editor-fold desc="Form Controls">  
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField address2TextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField phoneTextField;
// </editor-fold>
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (contact != null){
            newCustomer = false;
            setContactControls(); 
        }
        
    }
    
    /**
     * Set contact to edit
     * @param contact 
     */
    public void setContact(ContactCard contact) {
            this.contact.set(contact);
    }
    
    /**
     * Subscribe to the passed in user object and set the controls
     */
    private void setContactControls(){
        this.contactProperty().addListener((obs, temp, newTemp) -> {
            if(newTemp != null) {
                this.customerId = newTemp.getCustomerId();
                this.customerNameTextField.setText(newTemp.getCustomerName());
                this.addressTextField.setText(newTemp.getAddress());
                this.address2TextField.setText(newTemp.getAddress2());
                this.cityTextField.setText(newTemp.getCity());
                this.postalCodeTextField.setText(newTemp.getPostalCode());
                this.countryTextField.setText(newTemp.getCountry());
                this.phoneTextField.setText(newTemp.getPhone());  
            } else {
                System.out.println("Contact was null");
            }            
        });


    }
    
    /**
     * Validates User Input
     * @return 
     */
    private boolean validateUserInput() {
        boolean valid = true;
        
        if(helper.checkIfEmptyField(customerNameTextField, 
                "Customer Name requried")){
            valid = false;
        }
        
        if(helper.checkIfEmptyField(addressTextField, 
                "Address Field requried")) {
            valid = false;
        }
        
        if(helper.checkIfEmptyField(cityTextField, 
                "City Field requried")){
            valid = false;
        }
        
        if(helper.checkIfEmptyField(countryTextField, 
                "Country field requried")){
            valid = false;
        }
    
        return valid;
    }
    
    /**
     * Cancel Button Event Handle
     * @param event 
     */
    @FXML
    private void cancelButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", 
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult().getText().equals("Yes")){
            closeWindow();            
        }
    }
    
    /**
     * Save Button Event Handler
     * @param event 
     */
    @FXML
    private void saveButtonAction(ActionEvent event){
        
        if (validateUserInput()){
            Country country = new Country();
            int countryId = 0;

            City city = new City();
            int cityId = 0;

            Address address = new Address();
            int addressId = 0;

            Customer customer = new Customer();

            // get user supplied inputs
            ContactCard properties = new ContactCard(
                this.customerNameTextField.getText(),
                this.addressTextField.getText(),
                this.address2TextField.getText(),
                this.cityTextField.getText(),
                this.postalCodeTextField.getText(),
                this.countryTextField.getText(),
                this.phoneTextField.getText());

            // save country and get id
            try{
                country = country.recallCountry(properties.getCountry());
                if(country == null){
                    country = new Country(
                            this.countryTextField.getText(),
                            this.userName);
                    countryId = country.createCountry(country);
                } else {
                    countryId = country.getCountryId();
                    country.updateCountry(country);
                }
            }catch (SQLException ex){
                System.out.println("SQL Ex caught1:" + ex.getMessage());
            }

            // save city and get city id
            try{
                city = city.recallCity(properties.getCity());
                if(city == null){
                    city = new City(
                            this.cityTextField.getText(),
                            countryId,
                            this.userName);
                    cityId = city.createCity(city);
                } else {
                    cityId = city.getCityId();
                    city.updateCity(city);
                }
            } catch (SQLException ex) {
                System.out.println("SQL Ex caught2: " + ex.getMessage());
            }

            // save address and get address id
            try{
                address = address.recallAddress(properties.getAddress());
                if (address == null) {
                    address = new Address(
                            this.addressTextField.getText(),
                            this.address2TextField.getText(),
                            cityId,
                            this.postalCodeTextField.getText(),
                            this.phoneTextField.getText(),
                            this.userName);

                    addressId = address.createAddress(address);
                } else {
                    addressId = address.getAddressId();
                    address.updateAddress(address);
                }
            } catch (SQLException ex) {
                System.out.println("SQL Ex caught3: " + ex.getMessage());
            }


            // save customer
            try{
                if(!newCustomer) {
                    customer = customer.recallCustomer(this.customerId);
                }

                if(customer == null) {
                    System.out.println("Customer was null, making new one");
                    customer = new Customer(
                            this.customerNameTextField.getText(),
                            addressId,
                            this.userName);

                    // set the customer id to the retured generated key
                    this.customerId = customer.createCustomer(customer);                
                } else {
                    // add in the values from the form object
                    customer.setCustomerName(properties.getCustomerName());
                    if(properties.getActive() == 0){
                        customer.setActive(false);
                    } else {
                        customer.setActive(true);
                    }
                    customer.setAddressId(addressId);

                    // no need to update the id with the call,
                    // got it from the tableview
                    customer.updateCustomer(customer);
                }

            } catch(SQLException ex) {
                System.out.println("SQL Ex caught4 : " + ex.getMessage());
            }

            if(this.customerId > 0) {
                helper.alertUser("Contact Created");
                // update observable contact object
                properties.setCustomerId(this.customerId);
                this.contact.set(properties);

                // close the window
                closeWindow();
            }

          }
          
    }
    
    
    /**
     * Close window function
     */
    private void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
