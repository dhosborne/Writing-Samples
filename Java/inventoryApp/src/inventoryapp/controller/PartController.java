/*
 * Part Controller
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.controller;

import inventoryapp.Main;
import inventoryapp.model.InHouse;
import inventoryapp.model.Inventory;
import inventoryapp.model.Outsourced;
import inventoryapp.model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class PartController extends Main implements Initializable {

    // Create an observable wrapper to expose part to InventoryController
    private ReadOnlyObjectWrapper<Part> part = new ReadOnlyObjectWrapper<>();
    
    // Return part to external caller
    public ReadOnlyObjectProperty<Part> partProperty(){
        return part.getReadOnlyProperty();
    }
    
    public Part getPart(){
        return part.get();
    }
    
    @FXML
    private Label windowNameLabel;
    
    @FXML
    private Label partTypeLabel;
    
    @FXML
    private RadioButton inHouseRB;
    
    @FXML
    private RadioButton outSourceRB;

    @FXML
    private TextField idTextField;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField invTextField;
    
    @FXML
    private TextField priceTextField;
    
    @FXML
    private TextField maxTextField;
    
    @FXML
    private TextField minTextField;
    
    @FXML
    private TextField partTypeTextField;
   
    @FXML
    private Button saveButton;
    
    @FXML
    private Button cancelButton;
    
    
    /**
     * Alert user
     * Generic alert function
     * @param information
     */
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
     * Validates all user input from all text boxes
     * @return
     */
    private boolean validateUserInput(){
        // ensuer the name field is not empty
        if("".equals(this.nameTextField.getText())){
            this.alertUser("Name field required");
            return false;
        }
        
        // ensure inventory field is populated and is an integer
        if("".equals(this.invTextField.getText())){
            this.alertUser("Inventory level required");
            return false;
        }else{
            try{
               Integer.parseInt(this.invTextField.getText()); 
            }catch(NumberFormatException e){
                this.alertUser("Inventory field should be a number");
                return false;
            }  
        }
        
        // validates the parts field is not empty
        if("".equals(this.priceTextField.getText())){
            this.alertUser("Price required");
            return false;
        }
        

        // validate that the min text field is not empty and is an integer
        if("".equals(this.minTextField.getText())){
            this.alertUser("Min field required");
            return false;
        }else{
            try{
                Integer.parseInt(this.minTextField.getText());
            }catch (NumberFormatException e){
                this.alertUser("Min field should be a number");
                return false;
            }
        }
        
        // validates that the max text field is not empty and is an integer
        if("".equals(this.maxTextField.getText())){
            this.alertUser("Max field required");
            return false;
        }else{
            try{
                Integer.parseInt(this.maxTextField.getText());
            }catch (NumberFormatException e){
                this.alertUser("Max field should be a number");
                return false;
            }            
        }
        

        // verify that the min field value is not larget than the max field value
        if(!("".equals(this.minTextField.getText())) && !("".equals(this.maxTextField.getText()))){
            int min = Integer.valueOf(this.minTextField.getText());
            int max = Integer.valueOf(this.maxTextField.getText());
            
            if (min > max){
                this.alertUser("Min field cannot be greater than Max field");
                return false;
            }
        }
        

        // insure that the part type field is not empty
        if("".equals(this.partTypeTextField.getText())){
            if(this.partTypeLabel.getText() == "Machine ID"){
                this.alertUser("Machine ID field required");
            }else{
                this.alertUser("Company Name field required");
            }
            
            return false;
        }
        
        // checks if the part type field is not empty and that it is an integer
        // validates machine id
        if(this.partTypeLabel.getText() == "Machine ID"){
            String cName = this.partTypeTextField.getText();
            try{
                Integer.parseInt(cName);
            }catch(NumberFormatException e){
                this.alertUser("Machine ID must be an number");
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Save button action
     * Creates a new part and adds it to the Inventory.partsList
     * @param e
     */
    @FXML
    private void saveButtonAction(ActionEvent e) {
        
        // if user input is valid then save parts
        if(validateUserInput()){
            // Determine the type of part required then create the
            // appropriate part and assign it to this.part
            if(this.inHouseRB.isSelected()) {
                this.part.set(new InHouse(
                        Integer.valueOf(this.idTextField.getText()),
                        this.nameTextField.getText(),
                        Double.valueOf(this.priceTextField.getText()),
                        Integer.valueOf(this.invTextField.getText()),
                        Integer.valueOf(this.minTextField.getText()),
                        Integer.valueOf(this.maxTextField.getText()),
                        Integer.valueOf(this.partTypeTextField.getText())
                    ));
            }else{
                this.part.set(new Outsourced(
                        Integer.valueOf(this.idTextField.getText()),
                        this.nameTextField.getText(),
                        Double.valueOf(this.priceTextField.getText()),
                        Integer.valueOf(this.invTextField.getText()),
                        Integer.valueOf(this.minTextField.getText()),
                        Integer.valueOf(this.maxTextField.getText()),
                        this.partTypeTextField.getText()
                ));
            }
            // close the part editor dialog
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        


    }
    
    /**
     * Cancel button action
     * ensure that they user meant to close the form
     * @param e
     * @throws IOException
     */
    @FXML
    private void cancelButtonAction(ActionEvent e) throws IOException {
        
        // verify cancel command
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        // if yes is selected then close the dialog modal
        if(alert.getResult().getText().equals("Yes")){
            Stage stage = (Stage) this.cancelButton.getScene().getWindow();
            stage.close();            
        }

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // if part hasnt already been assigned by caller then
        // set up for a new part
        if("".equals(this.windowNameLabel.getText())){
            this.windowNameLabel.setText("New Part");
            this.idTextField.setText(String.valueOf(this.getNextPartId())); // get the next unique id
            this.partTypeLabel.setText("Machine ID");
            this.invTextField.setText("0");
        }
    }

    /**
     * Set part
     * Set up the interface for part modification
     * @param part
     */
    public void setPart(Part part) {
        
        // determine the type of part being edited and set up the interface accordingly
        if(part instanceof InHouse){
            this.part.set(new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHouse) part).getMachineId()));
            this.partTypeTextField.setText(Integer.toString( ( (InHouse) part).getMachineId()));
            this.inHouseRB.setSelected(true);
            
        } else {
            this.part.set(new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((Outsourced) part).getCompanyName()));
            this.outSourceRB.setSelected(true);
            this.partTypeLabel.setText("Company");
            this.partTypeTextField.setText( ( (Outsourced) part).getCompanyName());
        }
        
        this.windowNameLabel.setText("Edit Part");
        this.setFields(part);
    }
    
    /**
     * Set fields
     * Sets the user interface fields with the part attributes
     * @param part
     */
    private void setFields(Part part){
        this.idTextField.setText(Integer.toString(part.getId()));
        this.nameTextField.setText(part.getName());
        this.priceTextField.setText(Double.toString(part.getPrice()));
        this.invTextField.setText(Integer.toString(part.getStock()));
        this.minTextField.setText(Integer.toString(part.getMin()));
        this.maxTextField.setText(Integer.toString(part.getMax()));  
    }
    
    /**
     * Get next part id
     * Determine the next unique id for Inventory.partsList
     * @return id
     */
    private int getNextPartId(){
        int id = 0;
        ObservableList<Part> parts = Inventory.getAllParts();
        int size = parts.size();
        if(size > 0){
            id = parts.get(size - 1).getId();
        }

        return ++id;
    }
    
    
    /**
     * Radio button action
     * Update the part type label when the appropriate radio button is selected
     * @param e
     */
    @FXML
    private void RadioButtonAction(ActionEvent e){
        
        // part type changed, flush current value in type field
        this.partTypeTextField.setText("");
        
        // update the part type label to the appropriate attribute name
        if( "inHouseRB".equals(( (Control)e.getSource() ).getId())){
            this.partTypeLabel.setText("Machine ID");
        } else {
            this.partTypeLabel.setText("Company Name");
        }
    }
}
