/*
 * Inventory Controller 
 * Created by: Derek H. Osborne
 * Inital date: 20190621
 */
package inventoryapp.controller;

import inventoryapp.model.Inventory;
import inventoryapp.model.Part;
import inventoryapp.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
        
/**
 *
 * @author derekosborne
 */
public class InventoryController implements Initializable {
    
    // part section controls
    @FXML
    private Button partAddButton;
    
    @FXML
    private Button partModifyButton;
    
    @FXML
    private Button partDeleteButton;
    
    @FXML
    private Button partSearchButton;
    
    @FXML
    private TextField partSearchTextField;
    
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    
    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, Double> partCPUCol;
    

    /**
     * Part Add Button Action
     * Opens a dialog to add a new part to Iventory.partsList
     * @param event
     * @throws IOException
     */    
    @FXML
    private void partAddButtonAction(ActionEvent event) throws IOException {
        
        // Initialize the modal dialog to add a Part
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/inventoryapp/view/Part.fxml"));
        Stage modal = new Stage();

        // set modal parameters
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("New Part");
        modal.setScene(new Scene((AnchorPane) loader.load()));
        
        // Attahc PartController to the modal 
        PartController controller = (PartController) loader.getController();
        controller.partProperty().addListener((obs, oldPart, newPart) ->{
            Inventory.addPart(newPart); // add the part to Inventory.partsList
        });
        
        // display the modal
        modal.showAndWait();        
 
    }

    /**
     * Part Modify Button Action
     * Opens a dialog to modify a part, chosen from the parts table view,
     * and update it in Inventory.partsList
     * @param event
     * @throws IOException
     */
    @FXML
    private void partModifyButtonAction(ActionEvent event) throws IOException {

        // check if a part has been selected from the Parts Table
        // then display the modify part dialog modal
        if(this.partsTable.getSelectionModel().getSelectedItem() != null){

            // Initialize the modal to modify the selected part
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/inventoryapp/view/Part.fxml"));
            Stage modal = new Stage();

            // set modal parameters
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle("Edit Part");
            modal.setScene(new Scene((AnchorPane) loader.load()));

            // attach the PartController to the modal dialog
            PartController controller = (PartController) loader.getController();
            controller.setPart(partsTable.getSelectionModel().getSelectedItem());
            
            // handle adding the updated part to Inventory.partsList
            controller.partProperty().addListener((obs, oldPart, newPart) -> {
                int index = Inventory.getPartIndex((oldPart));
                Inventory.updatePart(index, newPart);
            });
            
            // show the modal
            modal.showAndWait();

        } else {
            Alert alert = new Alert(AlertType.WARNING, 
                    "No item selected from parts table", ButtonType.CLOSE);
            alert.show();
            
        }

    }

    /**
     * Part delete button action
     * Checks if a part has been selected from the parts list and call
     * Inventory.deletePart()
     * @param event 
     */
    @FXML
    private void partDeleteButtonAction(ActionEvent event) {
        // check if a part has been selected from the parts table view
        // then handle deleting the part
        if(this.partsTable.getSelectionModel().getSelectedItem() != null){
            
            // verify delete
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            // Delete the part from Inventory.partsList
            if(alert.getResult().getText().equals("Yes")){
                Inventory.deletePart(this.partsTable.getSelectionModel()
                                    .getSelectedItem());                
            }
            
        } else {
            // alert user that no part has been selected to be modified
            Alert alert = new Alert(AlertType.WARNING, 
                    "No item selected from parts table", ButtonType.CLOSE);
            alert.show();
        }
        
    }

    /**
     * Part search button action
     * Get the search term from the search box and call Inventory.lookUpPart
     * @param event
     */
    @FXML
    private void partSearchButtonAction(ActionEvent event) {

        // create a list of parts that match search terms
        ObservableList<Part>list = FXCollections.observableArrayList();
        Part found;
        
        // if the search bar is not empty
        if(!"".equals(this.partSearchTextField.getText())){
            try{
                // check if the search term is an integer then call
                // Invetory.lookUpPart(int)
                int term = Integer.parseInt(this.partSearchTextField.getText());
                found = Inventory.lookUpPart(term);
                list.add(found);
                this.partsTable.setItems(list);
                
            } catch(NumberFormatException e){
                // the search term failed the integer.partsInt test so treat it as a string
                // and call Inventory.lookUpPart(String)
                String term = this.partSearchTextField.getText();
                list = Inventory.lookUpPart(term);
                this.partsTable.setItems(list);
            }
        } else {

            // alert the user that the search box is empty
            Alert alert = new Alert(AlertType.INFORMATION,
            "Please enter a search term",
            ButtonType.CLOSE);
            
            alert.showAndWait();
        }
    }




    // product section controls
    @FXML
    private Button productAddButton;
    
    @FXML
    private Button productModifyButton;
    
    @FXML
    private Button productDeleteButton;
    
    @FXML
    private Button productSearchButton;
    
    @FXML
    private TextField productSearchTextField;
    
    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;
    
    @FXML
    private TableColumn<Product, Integer> prodInvLvlCol;

    @FXML
    private TableColumn<Product, Double> prodCPUCol;
    
    
    
    /**
     * Product add button action
     * Opens a dialog to get add a new product to Inventory.partsList()
     * @param event
     * @throws IOException
     */
    @FXML
    private void productAddButtonAction(ActionEvent event) throws IOException {

        // Initialize modal dialog for a new product
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/inventoryapp/view/Product.fxml"));
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setScene(new Scene((AnchorPane) loader.load()));
        
        // set controller and handle returned data
        ProductController controller = (ProductController) loader.getController();
        controller.productProperty().addListener((obs, oldProduct, newProduct) ->{
            Inventory.addProduct(newProduct); // add a new product the Inventory.partsList
        });

        // display modal dialog
        modal.showAndWait();

    }

    /**
     * Product modify button action
     * Opens a dialog to modify a product, chosen from the product table view,
     * and update it in Inventory.productsList()
     * @param event
     * @throws IOException
     */
    @FXML
    private void productModifyButtonAction(ActionEvent event) throws IOException {

        // check if an item has been selected from the list
        // then modify it
        if(this.productsTable.getSelectionModel().getSelectedItem() != null){
            
            // Initalize modal dialog for Product modification
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/inventoryapp/view/Product.fxml"));
            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setScene(new Scene ((AnchorPane) loader.load()));

            // Initalize modal controller and handle modified part
            ProductController controller = (ProductController) loader.getController();
            controller.setProduct(this.productsTable.getSelectionModel()
                    .getSelectedItem());
            controller.productProperty().addListener((obs,oldProduct, newProduct) -> {

                // get idex of current product in Inventory.productList
                int index = Inventory.getProductIndex(oldProduct);
                
                // update Inventory.productsList with modified part
                Inventory.updateProduct(index, newProduct);
            });

            modal.showAndWait();            
        } else {
            Alert alert = new Alert(AlertType.WARNING, 
                    "No item selected from products table", ButtonType.CLOSE);
            alert.show();
        }

    }
    
    
    /**
     * Product delete button action
     * Checks if a product has been selected from the product list and call
     * Inventory.deleteProducts()
     * @param event
     */
    @FXML
    private void productDeleteButtonAction(ActionEvent event) {

        // check if a product has been selected from the products list
        // then handle deleting it from Inventory.productsList
        if(this.productsTable.getSelectionModel().getSelectedItem() != null){
            
            // Verify delete with user
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            
            // if yes selected delete the product
            if(alert.getResult().getText().equals("Yes")){
                // delete the product
                Inventory.deleteProduct(this.productsTable.getSelectionModel()
                        .getSelectedItem());                
            }
            
        } else {
            Alert alert = new Alert(AlertType.WARNING, 
                    "No item selected from products table", ButtonType.CLOSE);
            alert.show();
        }
    }
    
    /**
     * Product search button action
     * Get the search term from the search box and call Inventory.lookUpProduct()
     * @param event
     */
    @FXML
    private void productSearchButtonAction(ActionEvent event) {

        // list to hold the products matching the search term
        ObservableList<Product>list = FXCollections.observableArrayList();
        Product found;
        
        // if the search box is not empty determine the data type of the term and search
        if(!"".equals(this.productSearchTextField.getText())){
            try{
                // test if the search term passes parseInt and call Invetory.lookUpProduct(int)
                int term = Integer.parseInt(this.productSearchTextField.getText());
                found = Inventory.lookUpProduct(term);
                list.add(found);
                this.productsTable.setItems(list);
            } catch(NumberFormatException e){
                // term failed parseInt test, treat it like a string and call Inventory.looUpProduct(String)
                String term = this.productSearchTextField.getText();
                list = Inventory.lookUpProduct(term);
                this.productsTable.setItems(list);
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION,
            "No product matchin search terms found",
            ButtonType.CLOSE);
            
            alert.showAndWait();
        }
 
    }
    
    @FXML
    private void exitButtonPressed(ActionEvent action) {
        Platform.exit();
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * Build Parts table view
         */
        this.partsTable.setItems(Inventory.getAllParts());
        this.partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.partCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        /**
         * Build Products table view
         */
        this.productsTable.setItems(Inventory.getAllProducts());
        this.prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.prodCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    

    /**
     * Key press event listener for part search bar
     * Listens for delete and enter key press and handle event
     * @param e
     */
    @FXML
    private void keyPressed(KeyEvent e){
        // listen for back space key, if it empties the part search box,
        // repopulate all the list with all parts
        if(e.getCode().equals(KeyCode.BACK_SPACE)){
            ObservableList<Part>list = FXCollections.observableArrayList();
            if("".equals(this.partSearchTextField.getText())){
                list = Inventory.getAllParts();
                this.partsTable.setItems(list);
            }            
        }
        
        // listen for the enter key and fire the part search button event
        if(e.getCode().equals(KeyCode.ENTER)){
            this.partSearchButton.fire();
        }
    }
    
    /**
     * Key pressed event listener for prouct search bar
     * Listens for delete and enter key press and handle event
     * @param e
     */
    @FXML
    private void keyPressedProduct(KeyEvent e){
        // TODO: get keypress source
        if(e.getCode().equals(KeyCode.BACK_SPACE)){
            ObservableList<Product>list = FXCollections.observableArrayList();
            if("".equals(this.productSearchTextField.getText())){
                list = Inventory.getAllProducts();
                this.productsTable.setItems(list);
            }            
        }
        
        // listen for the enter key and fire the product search buttton event
        if(e.getCode().equals(KeyCode.ENTER)){
            this.productSearchButton.fire();
        }
    }
}

