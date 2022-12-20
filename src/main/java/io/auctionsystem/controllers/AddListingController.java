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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import io.auctionsystem.classes.Listing;
import javafx.stage.FileChooser;

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
    private File imageFile;
    private Listing listing;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.getItems().addAll("Car", "Jewelry", "Hand Bag", "Watch", "Fine Art", "Other");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image of Product");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        chooseImageButton.setOnAction(event -> {
            imageFile = fileChooser.showOpenDialog(null);
            if (imageFile != null) {
                imageLabel.setText(imageFile.getName());
            }
        });
        addButton.setOnMouseClicked(mouseEvent -> {
            listing = new Listing(
                    new Product(
                            name.getText(),
                            description.getText(),
                            category.getValue()
                    ),
                    Double.parseDouble(startingprice.getText()),
                    data.getAuctionSystem().getAuthenticatedUser(),
                    LocalDateTime.now(),
                    LocalDateTime.of(enddate.getValue(), LocalTime.now())
            );
            data.getAuctionSystem().addListing(listing);
            addButton.getScene().getWindow().hide();
            String imageP = listing.getId() + "." + imageFile.getName().split("\\.")[1];
            listing.setImageSrc(imageP);
            try {
                Files.copy(imageFile.toPath(),
                        new File("src/main/resources/io/auctionsystem/data/images/" + imageP).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
}

