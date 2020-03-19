/*
 * Main Class
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp;

import inventoryapp.model.InHouse;
import inventoryapp.model.Inventory;
import inventoryapp.model.Outsourced;
import inventoryapp.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
   
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/inventoryapp/view/Inventory.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Inventory.addPart(new InHouse(1, "name1", 1.00, 1, 1, 1, 1));
        Inventory.addPart(new Outsourced(2, "name2", 2.00, 2,2,2, "outsourced"));
        Inventory.addProduct(new Product(1, "name1", 100.00, 1, 1, 1));
        Inventory.addProduct(new Product(2, "name2", 200.00, 2, 2, 2));
        launch(args);
        
    }   
}