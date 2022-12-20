package io.auctionsystem.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class SortingMenuController implements Initializable {

    @FXML
    private ToggleGroup sort;
    @FXML
    private MFXRadioButton radio1;
    @FXML
    private MFXRadioButton radio2;
    @FXML
    private MFXRadioButton radio3;
    @FXML
    private MFXRadioButton radio4;
    @FXML
    private MFXRadioButton radio5;
    @FXML
    private MFXRadioButton radio6;
    @FXML
    private MFXButton okButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okButton.setOnMouseClicked(mouseEvent -> {
            System.out.println(HomeController.currentSort);
            okButton.getScene().getWindow().hide();
        });
        radio1.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio1.getText();
        });
        radio2.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio2.getText();
        });
        radio3.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio3.getText();
        });
        radio4.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio4.getText();
        });
        radio5.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio5.getText();
        });
        radio6.setOnMouseClicked(mouseEvent -> {
            HomeController.currentSort = radio6.getText();
        });
    }
}
