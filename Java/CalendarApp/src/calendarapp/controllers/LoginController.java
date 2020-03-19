/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.controllers;

import calendarapp.CalendarApp;
import calendarapp.HelperClass;
import calendarapp.models.User;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author derekosborne
 */
public class LoginController implements Initializable {
    private String userName;
    private String password;
    private Locale userLocale;
    private String userLanguage;
    private final HelperClass helper = new HelperClass();
    
    String loginErrorString;
    
    
    // Observable logged in state
    private final ReadOnlyObjectWrapper<Boolean> loggedIn = new ReadOnlyObjectWrapper<>();

    /**
     * Return logged-in property of User
     * @return
     */
    public ReadOnlyObjectProperty<Boolean> loggedInProperty() {
        return loggedIn.getReadOnlyProperty();
    }

    /**
     * Get logged-in bool property 
     * @return
     */
    public boolean getLoggedIn() {
        return loggedIn.get();
    }
    
    // Observable user object, stores logged in user for access from outside the
    // controller 
    private final ReadOnlyObjectWrapper<User> loggedInUser = new ReadOnlyObjectWrapper();

    /**
     * exposes the logged in user object to external controllers
     * @return
     */
    public ReadOnlyObjectProperty<User> loggedInUserProp() {
        return loggedInUser.getReadOnlyProperty();
    }

    /**
     * Get the logged-in user
     * @return
     */
    public User getLoggedInUser() {
        return loggedInUser.get();
    }

    
    
// <editor-fold desc="FXML Controls">
    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
// </editor-fold>
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.userLocale = Locale.getDefault();
        this.userLanguage = this.userLocale.getLanguage();
        
        if(null == this.userLanguage){
            this.userLanguage = "en";
        }
        
        // check the users language and update the UI    
        // default is english
        switch (this.userLanguage) {
            case "es":
                this.loginLabel.setText(helper.getLoginString("es"));
                this.usernameTF.promptTextProperty().setValue(helper.getUserNameString("es"));
                this.passwordTF.promptTextProperty().setValue(helper.getPasswordString("es"));
                this.loginButton.setText(helper.getLoginString("es"));
                this.loginErrorString = helper.getLoginErrorString("es");
                break;
            case "fr":
           this.loginLabel.setText(helper.getLoginString("fr"));
                this.usernameTF.promptTextProperty().setValue(helper.getUserNameString("fr"));
                this.passwordTF.promptTextProperty().setValue(helper.getPasswordString("fr"));
                this.loginButton.setText(helper.getLoginString("fr"));
                this.loginErrorString = helper.getLoginErrorString("fr");
                break;
            default:
                this.loginLabel.setText(helper.getLoginString("en"));
                this.usernameTF.promptTextProperty().setValue(helper.getUserNameString("en"));
                this.passwordTF.promptTextProperty().setValue(helper.getPasswordString("en"));
                this.loginButton.setText(helper.getLoginString("en"));
                this.loginErrorString = helper.getLoginErrorString("en");
                break;
        }
        
        // listen for the enter key and try to login
        this.passwordTF.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                this.login();
            }
        });
 
    }
    
    
    /**
     * On-click action for for Login button
     * @param event 
     */
    @FXML
    private void loginButtonAction(ActionEvent event) {
        this.login();
    }
    
    private void login() {
            new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    this.userName = this.usernameTF.getText();
                    this.password = this.passwordTF.getText();

                    User user = new User();

                    // get the user from db create the 
                    if(user.findUserByUserName(this.userName, this.password)) {
                            this.loggedIn.set(true);
                            this.loggedInUser.set(user);
                            String loginString = CalendarApp.user.getUserName() +
                                    " logged in at " + LocalDateTime.now();

                            // write the login to the record
                            helper.logWritter(loginString);

                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            stage.close();

                    } else {
                        // login info is incorrect, inform user
                        helper.alertUser(this.loginErrorString);
                        this.loggedIn.set(false);
                    }

                    } catch (SQLException ex) {
                        System.out.println("An SQL exception occured when trying to locate user" 
                                + ex.getMessage()
                        );
                    }
                });
        }).start();
            
    }
}
