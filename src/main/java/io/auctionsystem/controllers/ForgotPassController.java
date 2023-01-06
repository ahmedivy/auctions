package io.auctionsystem.controllers;

import io.auctionsystem.App;
import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.MailSender;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPassController implements Initializable {
    @FXML
    private MFXTextField codeField;
    @FXML
    private MFXPasswordField confirmPasswordField;
    @FXML
    private MFXTextField emailField;
    @FXML
    private Label label;
    @FXML
    private MFXButton okButton;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private Label pwValidLabel;
    @FXML
    private MFXButton signupButton;
    @FXML
    private MFXButton sendCodeButton;
    @FXML
    private Label unValidLabel;

    DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int code = 666666;
        label.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordField.setVisible(false);
        codeField.setVisible(false);
        okButton.setVisible(false);

        final String[] otp = new String[1];

        sendCodeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (data.getAuctionSystem().validateEmail(emailField.getText())) {
                // Send Code
                otp[0] = new MailSender(emailField.getText()).sendOtp();
                unValidLabel.setVisible(false);
                sendCodeButton.setText("Code Sent");
                codeField.setVisible(true);
            } else {
                label.setVisible(true);
                label.setText("Invalid Email");
                codeField.setVisible(false);
                sendCodeButton.setVisible(true);
            }
        });

        codeField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (codeField.getText().equals(otp[0])) {
                confirmPasswordField.setVisible(true);
                passwordField.setVisible(true);
                okButton.setVisible(true);
            }
        });

        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                // Change Password
                data.getAuctionSystem().getUsers().forEach(System.out::println);
                data.getAuctionSystem().changePassword(emailField.getText(),passwordField.getText());
                System.out.println("Password Changed");
                data.getAuctionSystem().getUsers().forEach(System.out::println);
                label.setVisible(false);
                try {
                    App.switchScene((Stage) okButton.getScene().getWindow(), "login.fxml", "Login | SoTheBuys");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                label.setVisible(true);
                label.setText("Passwords do not match");
            }
        });

        signupButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                App.switchScene((Stage) signupButton.getScene().getWindow(), "signup.fxml", "Sign Up | Auction System");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
