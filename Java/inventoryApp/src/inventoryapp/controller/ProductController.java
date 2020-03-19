/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryapp.controller;

import inventoryapp.model.Inventory;
import inventoryapp.model.Part;
import inventoryapp.model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class ProductController implements Initializable {

    // create observable wrapper to expose product to InventoryController
    private ReadOnlyObjectWrapper<Product> product = new ReadOnlyObjectWrapper<>();
    // return the product attribute information to external caller
    public ReadOnlyObjectProperty<Product> productProperty(){
        return product.getReadOnlyProperty();
    }
    public Product getProduct(){
        return product.get();
    }
    
    // a list of associated parts in the product
    ObservableList<Part> prodPartsList = FXCollections.observableArrayList();
    
    
    
    
    @FXML
    private Label windowNameLabel;
    
    @FXML
    private TextField idTF;
    
    @FXML
    private TextField nameTF;
    
    @FXML
    private TextField invTF;
    
    @FXML
    private TextField priceTF;
    
    @FXML
    private TextField minTF;
    
    @FXML
    private TextField maxTF;
    
    @FXML
    private Button searchBTN;
    
    @FXML
    private TextField searchTF;
    
    @FXML
    private TableView partsTVTop;
    
    @FXML
    private TableColumn<Product, Integer> idTopCol;
    @FXML
    private TableColumn<Product, String> nameTopCol;
    @FXML
    private TableColumn<Product, Integer> invTopCol;
    @FXML
    private TableColumn<Product, Double> costTopCol;
    
    @FXML
    private TableView partsTVBot;
    @FXML
    private TableColumn<Product, Integer> idBotCol;
    @FXML
    private TableColumn<Product, String> nameBotCol;
    @FXML
    private TableColumn<Product, Integer> invBotCol;
    @FXML
    private TableColumn<Product, Double> costBotCol;    
    @FXML
    private Button addBtn;
    
    @FXML
    private Button deleteBtn;
    
    @FXML
    private Button cancelBtn;
    
    @FXML
    private Button saveBtn;
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the interface for 
        if("".equals(this.windowNameLabel.getText())){
            this.windowNameLabel.setText("New Product");
            this.idTF.setText(Integer.toString(this.getNextProdId()));
            this.invTF.setText("0");
        }
        
        this.buildTables();
        
    }

     private void alertUser(String information){
        Alert alert = new Alert(
                AlertType.ERROR,
                information,
                ButtonType.CLOSE
        );
        
        alert.showAndWait();
    }   
    
    /**
     * Validate user input
     * check if user input in all fields are valid
     * @return boolean
     */
    private boolean validateUserInput(){
        // check if string field is populated
        if("".equals(this.nameTF.getText())){
            this.alertUser("Name field required");
            return false;
        }
        
        // check if inventory is populated
        if( "".equals( this.invTF.getText() ) ){
            this.alertUser("Inventory field required");
            return false;
        } else {
            // check if inventory is a number
            try{
                Integer.parseInt(this.invTF.getText());
            }catch (NumberFormatException e){
                this.alertUser("Inventory field should be a number");
                return false;
            }
        }
        
        // check if price field is populated
        if("".equals(this.priceTF.getText())){
            this.alertUser("Price field required");
            return false;
        }
        
        // check if min field is populated
        if("".equals(this.minTF.getText())){
            this.alertUser("Min field required");
            return false;
        }else{
            // check if min field is an integer
            try{
                Integer.parseInt(this.minTF.getText());
            }catch (NumberFormatException e){
                this.alertUser("Min field should be a number");
                return false;
            }
        }
        
        // check if max field is populated
        if("".equals(this.maxTF.getText())){
            this.alertUser("Max field required");
            return false;
        }else{
            // check if max field is an integer
            try{
                Integer.parseInt(this.maxTF.getText());
            }catch (NumberFormatException e){
                this.alertUser("Max field should be a number");
                return false;
            }            
        }
        
        // check if max field is gte min field
        if(!("".equals(this.minTF.getText()) && !("".equals(this.maxTF.getText())))){
            int min = Integer.valueOf(this.minTF.getText());
            int max = Integer.valueOf(this.maxTF.getText());
            
            if(min > max){
                this.alertUser("Min field cannot be greater than Max field");
                return false;
            }
        }
        
        
        // verify cost of product is gte priceTF
        Double partsCostTotal = 0.00;
        
        for(Part p : this.prodPartsList){
            partsCostTotal += p.getPrice();
        }
        
        if(Double.valueOf(this.priceTF.getText()) < partsCostTotal){
            this.alertUser("Price of product must be greater than or equal to sum of parts prices");
            return false;
        }
        
        return true;
    }
    
    /**
     * Build all tables 
     */
    private void buildTables(){
        this.partsTVTop.setItems(Inventory.getAllParts());
        this.idTopCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameTopCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.invTopCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.costTopCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        this.partsTVBot.setItems(this.prodPartsList);
        this.idBotCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameBotCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.invBotCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.costBotCol.setCellValueFactory(new PropertyValueFactory<>("price"));        
    }
    
    /**
     * Set Product
     * Allows caller to set the product for modification
     * @param product
     */
    public void setProduct(Product product){
        this.windowNameLabel.setText("Edit Product");
        this.product.set(product);
        this.prodPartsList = product.getAllAssociatedParts();
        this.setFields(product);
    }
    
    /**
     * Set Fields
     * Sets all text fields with product attributes
     * @param product
     */
    private void setFields(Product product){
        this.idTF.setText(Integer.toString(product.getId()));
        this.nameTF.setText(product.getName());
        this.invTF.setText(Integer.toString(product.getStock()));
        this.priceTF.setText(Double.toString(product.getPrice()));
        this.minTF.setText(Integer.toString(product.getMin()));
        this.maxTF.setText(Integer.toString(product.getMax()));
        this.buildTables();

    }
    

    /**
     * Get next product id
     * Gets the next product id for creating new products
     * @return
     */
    private int getNextProdId() {
        ObservableList<Product> products = Inventory.getAllProducts();
        int size = products.size();
        int id = 0;
        if(size > 0){
            id = products.get(size - 1).getId();            
        }
        return ++id;
    }
    

    /**
     * Cancel button action
     * Verify that the user indeed wants to close the form
     * @param e
     */
    @FXML
    private void cancelButtonAction(ActionEvent e) {
        // verify cancel command
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult().getText().equals("Yes")){
            Stage stage = (Stage) this.cancelBtn.getScene().getWindow();
            stage.close();            
        }
    }
    

    /**
     * Save button action
     * Add the product from the dialog to the Inventory.addProduct()
     * @param e
     */
    @FXML
    private void saveButtonAction(ActionEvent e) {
        // if user input is valid
        if(validateUserInput()){
            Product newProd = new Product(
                            Integer.valueOf(this.idTF.getText()),
                            this.nameTF.getText(),
                            Double.valueOf(this.priceTF.getText()),
                            Integer.valueOf(this.invTF.getText()),
                            Integer.valueOf(this.minTF.getText()),
                            Integer.valueOf(this.maxTF.getText())
                    );
            for(Part p : prodPartsList){
                newProd.addAssociatedPart(p);
            }
            this.product.set(newProd);

            // close the dialog
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();            
        }

    }
    

    /**
     * Add button action
     * Adds a part selected from the top view list to the bottom view list for inclusion
     * in the new product
     * @param e
     */
    @FXML
    private void addButtonAction(ActionEvent e){
        if(this.partsTVTop.getSelectionModel().getSelectedItem() != null){
            this.prodPartsList.add((Part) this.partsTVTop.getSelectionModel().getSelectedItem());
        } else {
            Alert alert = new Alert(AlertType.WARNING,
            "No item selected from top table", ButtonType.CLOSE);
            alert.showAndWait();
        }
        
    }
    
    /**
     * Delete button action
     * Remove a part from the product inclusion list
     * @param e
     */
    @FXML
    private void deleteButtonAction(ActionEvent e){
        if(this.partsTVBot.getSelectionModel().getSelectedItem() != null){
            if(this.prodPartsList.contains((Part) this.partsTVBot.getSelectionModel().getSelectedItem())){
                this.prodPartsList.remove((Part)this.partsTVBot.getSelectionModel().getSelectedItem());
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING,
            "No item selected from bottom table", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    

    /**
     * Part search button action
     * Search for a part in the all parts list by the entered term
     * @param e
     */
    @FXML
    private void partSearchButtonAction(ActionEvent e){
       ObservableList<Part>list = FXCollections.observableArrayList();
       Part found;
       
       if(!"".equals(this.searchTF.getText())){
           try{
               int term = Integer.parseInt(this.searchTF.getText());
               found = Inventory.lookUpPart(term);
               list.add(found);
               this.partsTVTop.setItems(list);
               
           } catch (NumberFormatException ex) {
               String term = this.searchTF.getText();
               list = Inventory.lookUpPart(term);
               this.partsTVTop.setItems(list);
           }
       } else {
            Alert alert = new Alert(AlertType.INFORMATION, 
                    "Please enter a search term",
            ButtonType.CLOSE);           
       }
    }
    
    /**
     * Key pressed 
     * Key press event listener for the parts search bar, updates the parts top table
     * @param e
     */
    @FXML
    private void keyPressed(KeyEvent e){
        // if the back space key empties the search bar, repopulate the top table
        // with all parts
        if(e.getCode().equals(KeyCode.BACK_SPACE)){
            ObservableList<Part>list = FXCollections.observableArrayList();
            if("".equals(this.searchTF.getText())){
                list = Inventory.getAllParts();
                this.partsTVTop.setItems(list);
            }
        }
        
        // if the enter key is pressed, fire the parts search event
        if(e.getCode().equals(KeyCode.ENTER)){
            this.searchBTN.fire();
        }
    }
}
