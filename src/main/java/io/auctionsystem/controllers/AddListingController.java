package io.auctionsystem.controllers;

import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.Product;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import io.auctionsystem.classes.Listing;

public class AddListingController implements Initializable {

    @FXML
    private MFXButton addButton;
    @FXML
    private MFXComboBox<String> category;
    @FXML
    private MFXButton chooseImageButton;
    @FXML
    private MFXTextField description;
    @FXML
    private MFXDatePicker enddate;
    @FXML
    private Label imageLabel;
    @FXML
    private MFXTextField name;
    @FXML
    private MFXTextField startingprice;

    private final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.getItems().addAll("Car", "Jewelry", "Hand Bag", "Watch", "Fine Art", "Other");
        addButton.setOnMouseClicked(mouseEvent -> {
            data.getAuctionSystem().addListing(new Listing(
                    new Product(
                            name.getText(),
                            description.getText(),
                            category.getValue()
                    ),
                    Double.parseDouble(startingprice.getText()),
                    data.getAuctionSystem().getAuthenticatedUser(),
                    LocalDateTime.now(),
                    LocalDateTime.of(enddate.getValue(), LocalTime.now())
            ));
            addButton.getScene().getWindow();
        });
    }
}
