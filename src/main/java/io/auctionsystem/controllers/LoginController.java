package io.auctionsystem.controllers;

import io.auctionsystem.App;
import io.auctionsystem.classes.DataSingleton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private MFXButton loginButton;
    @FXML
    private MFXButton signupButton;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private MFXTextField usernameField;

    @FXML
    private Label unValidLabel;

    @FXML
    private Label pwValidLabel;

    @FXML
    private MFXButton forgotPassButton;

    private final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unValidLabel.setVisible(false);
        pwValidLabel.setVisible(false);

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int validationCode = data.getAuctionSystem().validateUser(usernameField.getText(), passwordField.getText());
            switch (validationCode) {
                case 0 -> {
                    System.out.println("Login Successful");
                    data.getAuctionSystem().setAuthenticatedUser(usernameField.getText(), passwordField.getText());
                    try {
                        App.switchScene((Stage) loginButton.getScene().getWindow(), "home.fxml", "SoTheBuys");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 1 -> {
                    System.out.println("Password Incorrect");
                    pwValidLabel.setVisible(true);
                    unValidLabel.setVisible(false);
                    passwordField.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"), true);
                    usernameField.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"), false);
                    pwValidLabel.setText("Password Incorrect");
                }
                case -1 -> {
                    System.out.println("Username Incorrect");
                    usernameField.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"), true);
                    passwordField.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"), false);
                    unValidLabel.setVisible(true);
                    unValidLabel.setText("Username not found");
                    pwValidLabel.setVisible(false);
                }
            }
        });
        signupButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                App.switchScene((Stage) signupButton.getScene().getWindow(), "signup.fxml", "Sign Up | Auction System");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        forgotPassButton.setOnMouseClicked( event -> {
            try {
                App.switchScene((Stage) forgotPassButton.getScene().getWindow(), "forgotpass.fxml", "Change Password | Auction System");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        usernameField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.setFocusTraversable(true);
            }
        });
        passwordField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                loginButton.fire();
            }
        });
    }
}
