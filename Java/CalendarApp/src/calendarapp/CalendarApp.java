/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp;

import calendarapp.controllers.LoginController;
import calendarapp.models.User;
import java.time.LocalTime;
import java.util.Locale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author derekosborne
 */
public class CalendarApp extends Application {
    
    // global start and end of business times
    LocalTime startOfBusiness;
    LocalTime closeOfBusiness;
    
    // a global object of the logged in user
    // and the status of the users validation
    public static User user;
    Boolean loggedIn = false;
    
    
    HelperClass helper = new HelperClass();
    
    @Override
    public void start(Stage stage) throws Exception {
        startOfBusiness = LocalTime.of(9, 00);
        closeOfBusiness = LocalTime.of(17, 00);

        // set up the login dialog
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/calendarapp/views/Login.fxml"));
        Stage modal = new Stage();
        
        // set modal parameters
        modal.initModality(Modality.APPLICATION_MODAL);
        
        // set localized title string for window
        modal.setTitle(helper.getLoginString(Locale.getDefault().getLanguage()));
        modal.setScene(new Scene((AnchorPane) loader.load()));
        
        //Attach controller to modal
        LoginController controller = (LoginController) loader.getController();
        
        // listen for logged in user
        controller.loggedInProperty().addListener((obs) -> {
            // set logged in to true
            this.loggedIn = controller.getLoggedIn();
        });
        controller.loggedInUserProp().addListener((obs) -> {
            // set logged in user from query
            if(controller.getLoggedInUser() instanceof User) {
               CalendarApp.user = controller.getLoggedInUser(); 
            } else {
                CalendarApp.user = null;
            }
            
        });
        
        // show the login card
        modal.showAndWait();
       
        // check if user logged in successfully
        if (loggedIn) {
            Parent root = FXMLLoader.load(getClass().getResource("/calendarapp/views/Calendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // user closed the login dialog without successfully loggin in
            Platform.exit();
        }
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {         
        launch(args);
    }
    
}
