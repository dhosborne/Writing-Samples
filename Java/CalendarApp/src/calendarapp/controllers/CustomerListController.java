/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.HelperClass;
import calendarapp.models.ContactCard;
import calendarapp.models.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
public class CustomerListController implements Initializable {
    ObservableList<ContactCard> contactList = FXCollections.observableArrayList();
    
    // Class objects for access to SQL/Member Functions
    ContactCard contact = new ContactCard();
    Customer customer = new Customer();
    HelperClass helper = new HelperClass();

// <editor-fold desc="FXML Controls">
    @FXML
    AnchorPane anchorPane;
    @FXML
    TableView<ContactCard> customerListView;
    @FXML
    Button addUserButton;
    @FXML
    Button updateUserButton;
    @FXML
    Button deleteUserButton;
    
    
    // Table items
    @FXML 
    TableColumn<ContactCard, Integer> idCol;
    @FXML 
    TableColumn<ContactCard, String> nameCol;
    @FXML 
    TableColumn<ContactCard, String> addCol;
    @FXML 
    TableColumn<ContactCard, String> add2Col;
    @FXML 
    TableColumn<ContactCard, String> cityCol;
    @FXML 
    TableColumn<ContactCard, String> postalCol;
    @FXML 
    TableColumn<ContactCard, String> countryCol;
    @FXML 
    TableColumn<ContactCard, String> phoneCol;
    @FXML 
    TableColumn<ContactCard, Integer> activeCol;

// </editor-fold>
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            this.contactList = contact.getContactList();
            buildTable();
        } catch (SQLException ex){
            System.out.println("An exception occured when getting contacts list: " 
                    + ex.getMessage());
        }
        

 
    }
    
    /**
     * Cell Factory function for building the table
     */
    @FXML
    public void buildTable(){
        this.customerListView.setItems(this.contactList);
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.addCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.add2Col.setCellValueFactory(new PropertyValueFactory<>("address2"));
        this.cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        this.countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        this.phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));   
    }
    
    /**
     * On-click action for Add button
     * @throws IOException
     */
    @FXML
    public void addCustomerButtonClicked() throws IOException {
        
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass()
                  .getResource("/calendarapp/views/NewCustomer.fxml"));
            Parent root = loader.load();
            
            // get the cotnroller and pass username
            NewCustomerController ncc = loader.getController();
            
            // get the new contact and add it to the contact list
            ncc.contactProperty().addListener((obs, oldContact, newContact) -> {
                this.contactList.add(newContact);
            });
            
            Scene scene = new Scene(root);
            stage.setTitle("Create Customer");
            stage.setScene(scene);
        } catch(IOException ex){
            System.out.println("IOException caught: " + ex.getMessage());
        }
        stage.showAndWait();

    }
    
    /**
     * On-click action for Update button
     * @throws IOException
     */
    @FXML
    public void updateCustomerButtonClicked() throws IOException {
        if(this.customerListView.getSelectionModel().getSelectedItem() != null){
            Stage stage = new Stage();
            
            try{
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/calendarapp/views/NewCustomer.fxml"));
                Parent root = loader.load();
                
                // get the controller and pass username and the selected 
                // contact object
                NewCustomerController ncc = loader.getController();

                // get the selected customer from the list
                ContactCard selected = this.customerListView.getSelectionModel()
                        .getSelectedItem();
                
                // pass the selected customer for edit
                ncc.setContact(selected);
                
                // get the new contact and add it to the contact list
                // lambda expression used to handle the call back function
                // after updated contact is returned by the NewContactController
                ncc.contactProperty().addListener((obs, oldContact, newContact) -> {
                    int index = this.contactList.indexOf(oldContact);
                    this.contactList.set(index, newContact);
                });

                
                Scene scene = new Scene(root);
                stage.setTitle("Update Contact");
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println("Exception occured opening customer edit: " 
                        + ex.getMessage());
            }
            
            stage.showAndWait();
        } else {
            helper.alertUser("Please select a record from the list to update");
        }
    }
    
    /**
     * On-click action for Delete button
     * @throws SQLException
     */
    @FXML
    public void deleteCustomerButtonClicked() throws SQLException {
        if (this.customerListView.getSelectionModel().getSelectedItem() != null){
            ContactCard selected = this.customerListView.getSelectionModel()
                    .getSelectedItem();
            
            if(this.helper.userConfirmation("delete Customer")){
                customer.deleteCustomer(selected.getCustomerId());
                this.contactList.remove(selected);
                buildTable();                
            }           
        } else {
            helper.alertUser("Please select a record from the list to delete");
        }
    }
    
    /**
     * On-click action for Update button
     */
    @FXML
    public void closeMenuItemSelected() {
        System.out.println("Implement me or delete me");
    }
}
