package io.auctionsystem.controllers;

import io.auctionsystem.App;
import io.auctionsystem.classes.Address;
import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.User;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.MFXStepper.MFXStepperEvent;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Validated;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    private final DataSingleton data = DataSingleton.getInstance();
    private MFXTextField usernameField;
    private MFXPasswordField passwordField;
    private MFXPasswordField confirmPassword;
    private MFXTextField nameField;
    private MFXTextField phoneField;
    private MFXTextField emailField;
    private MFXTextField verificationCodeField;
    private MFXTextField addressField;
    private MFXTextField cityField;
    private MFXTextField stateField;
    private MFXTextField zipField;
    private MFXTextField countryField;
    private MFXButton sendCodeButton;

    @FXML
    private MFXButton loginButton;

    @FXML
    private MFXStepper stepper;

    public SignupController() {
        usernameField = new MFXTextField();
        passwordField = new MFXPasswordField();
        nameField = new MFXTextField();
        phoneField = new MFXTextField();
        emailField = new MFXTextField();
        verificationCodeField = new MFXTextField();
        addressField = new MFXTextField();
        cityField = new MFXTextField();
        stateField = new MFXTextField();
        zipField = new MFXTextField();
        countryField = new MFXTextField();
        confirmPassword = new MFXPasswordField();
        sendCodeButton = new MFXButton("Send Code");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validateFields();

        stepper.setSpacing(10);
        stepper.setProgressColor(Color.web("#000000"));
        stepper.setBaseColor(Color.web("#000000"));

        List<MFXStepperToggle> stepperToggles = createSteps();
        stepper.getStepperToggles().addAll(stepperToggles);

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Switch to log in screen
            Stage stage = (Stage) loginButton.getScene().getWindow();
            try {
                App.switchScene(stage, "login.fxml", "Login | SoTheBuys");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendCodeButton.setAlignment(Pos.BASELINE_RIGHT);
        sendCodeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Send verification code
            sendCodeButton.setText("Sent!");
        });

        stepper.setOnLastNext(event -> {
            // Add user
            User user = new User(
                    usernameField.getText(),
                    nameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    new Address(
                            addressField.getText(),
                            cityField.getText(),
                            stateField.getText(),
                            zipField.getText(),
                            countryField.getText()
                    ),
                    phoneField.getText()
            );

            data.getAuctionSystem().addUser(user);
            System.out.println("Added user: " + user);


            // Switch to log in screen
            Stage stage = (Stage) stepper.getScene().getWindow();
            try {
                App.switchScene(stage, "login.fxml", "SoTheBuys");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<MFXStepperToggle> createSteps() {
        MFXStepperToggle step1 = new MFXStepperToggle("", new MFXFontIcon("mfx-user", 16, Color.web("#000000")));
        VBox step1Box = new VBox(20, wrapNodeForValidation(nameField), wrapNodeForValidation(phoneField));
        step1Box.setAlignment(Pos.CENTER);
        step1.setContent(step1Box);
        step1.getValidator().dependsOn(nameField.getValidator()).dependsOn(phoneField.getValidator());

        MFXStepperToggle step2 = new MFXStepperToggle("", new MFXFontIcon("mfx-message", 16, Color.web("#000000")));
        VBox step2Box = new VBox(20, wrapNodeForValidation(emailField), sendCodeButton, wrapNodeForValidation(verificationCodeField));
        step2Box.setAlignment(Pos.CENTER);
        step2.setContent(step2Box);

        MFXStepperToggle step3 = new MFXStepperToggle("", new MFXFontIcon("mfx-lock", 16, Color.web("#000000")));
        VBox step3Box = new VBox(20, wrapNodeForValidation(usernameField), wrapNodeForValidation(passwordField), wrapNodeForValidation(confirmPassword));
        step3Box.setAlignment(Pos.CENTER);
        step3.setContent(step3Box);
        step3.getValidator().dependsOn(usernameField.getValidator()).dependsOn(passwordField.getValidator()).dependsOn(confirmPassword.getValidator());

        MFXStepperToggle step4 = new MFXStepperToggle("", new MFXFontIcon("mfx-map", 16, Color.web("#000000")));
        VBox step4Box = new VBox(20, wrapNodeForValidation(addressField), wrapNodeForValidation(cityField), wrapNodeForValidation(stateField), wrapNodeForValidation(zipField), wrapNodeForValidation(countryField));
        step4Box.setAlignment(Pos.CENTER);
        step4.setContent(step4Box);
        step4.getValidator().dependsOn(addressField.getValidator()).dependsOn(cityField.getValidator()).dependsOn(stateField.getValidator()).dependsOn(zipField.getValidator()).dependsOn(countryField.getValidator());

        return List.of(step1, step2, step3, step4);
    }

    private <T extends Node & Validated> Node wrapNodeForValidation(T node) {
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setManaged(false);
        stepper.addEventHandler(MFXStepperEvent.VALIDATION_FAILED_EVENT, event -> {
            MFXValidator validator = node.getValidator();
            List<Constraint> validate = validator.validate();
            if (!validate.isEmpty()) {
                errorLabel.setText(validate.get(0).getMessage());
            }
        });
        stepper.addEventHandler(MFXStepperEvent.NEXT_EVENT, event -> errorLabel.setText(""));
        VBox wrap = new VBox(3, node, errorLabel) {
            @Override
            protected void layoutChildren() {
                super.layoutChildren();

                double x = node.getBoundsInParent().getMinX();
                double y = node.getBoundsInParent().getMaxY() + getSpacing();
                double width = getWidth();
                double height = errorLabel.prefHeight(-1);
                errorLabel.resizeRelocate(x, y, width, height);
            }

            @Override
            protected double computePrefHeight(double width) {
                return super.computePrefHeight(width) + errorLabel.getHeight() + getSpacing();
            }
        };
        wrap.setAlignment(Pos.CENTER);
        return wrap;
    }

    public void validateFields() {
        usernameField.getValidator().constraint("Username must be at least 6 characters long", usernameField.textProperty().length().greaterThanOrEqualTo(6));
        confirmPassword.getValidator().constraint("The passwords must match", confirmPassword.textProperty().isEqualTo(passwordField.textProperty()));
        passwordField.getValidator().constraint("The password must be at least 8 characters long", passwordField.textProperty().length().greaterThanOrEqualTo(8));
        nameField.getValidator().constraint("The first name must be at least 2 characters long", nameField.textProperty().length().greaterThanOrEqualTo(2));
        phoneField.getValidator().constraint("The phone number must be at least 10 characters long", phoneField.textProperty().length().greaterThanOrEqualTo(10));
        emailField.getValidator().constraint("The email must be at least 6 characters long", emailField.textProperty().length().greaterThanOrEqualTo(6));
        verificationCodeField.getValidator().constraint("The verification code must be at least 6 characters long", verificationCodeField.textProperty().length().greaterThanOrEqualTo(6));
        addressField.getValidator().constraint("The address must be at least 6 characters long", addressField.textProperty().length().greaterThanOrEqualTo(6));
        cityField.getValidator().constraint("The city must be at least 2 characters long", cityField.textProperty().length().greaterThanOrEqualTo(2));
        stateField.getValidator().constraint("The state must be at least 2 characters long", stateField.textProperty().length().greaterThanOrEqualTo(2));
        zipField.getValidator().constraint("The zip code must be at least 5 characters long", zipField.textProperty().length().greaterThanOrEqualTo(5));
        countryField.getValidator().constraint("The country must be at least 2 characters long", countryField.textProperty().length().greaterThanOrEqualTo(2));

        // usernameField.setLeadingIcon(new MFXIconWrapper("mfx-user", 16, Color.web("#4D4D4D"), 24));

        // Setting float mode to FLOATING
        usernameField.setFloatMode(FloatMode.BORDER);
        passwordField.setFloatMode(FloatMode.BORDER);
        confirmPassword.setFloatMode(FloatMode.BORDER);
        nameField.setFloatMode(FloatMode.BORDER);
        phoneField.setFloatMode(FloatMode.BORDER);
        emailField.setFloatMode(FloatMode.BORDER);
        verificationCodeField.setFloatMode(FloatMode.BORDER);
        addressField.setFloatMode(FloatMode.BORDER);
        cityField.setFloatMode(FloatMode.BORDER);
        stateField.setFloatMode(FloatMode.BORDER);
        zipField.setFloatMode(FloatMode.BORDER);
        countryField.setFloatMode(FloatMode.BORDER);

        //Setting floating text
        usernameField.setFloatingText("  Username  ");
        passwordField.setFloatingText("  Password  ");
        confirmPassword.setFloatingText("  Confirm Password  ");
        emailField.setFloatingText("  Email  ");
        nameField.setFloatingText("  Name  ");
        phoneField.setFloatingText("  Phone  ");
        verificationCodeField.setFloatingText("  Verification Code  ");
        addressField.setFloatingText("  Address  ");
        cityField.setFloatingText("  City  ");
        stateField.setFloatingText("  State  ");
        zipField.setFloatingText("  Zip  ");
        countryField.setFloatingText("  Country  ");

        //Setting CSS class
        usernameField.getStyleClass().add("fields");
        passwordField.getStyleClass().add("fields");
        confirmPassword.getStyleClass().add("fields");
        emailField.getStyleClass().add("fields");
        nameField.getStyleClass().add("fields");
        phoneField.getStyleClass().add("fields");
        verificationCodeField.getStyleClass().add("fields");
        addressField.getStyleClass().add("fields");
        cityField.getStyleClass().add("fields");
        stateField.getStyleClass().add("fields");
        zipField.getStyleClass().add("fields");
        countryField.getStyleClass().add("fields");
    }
}
