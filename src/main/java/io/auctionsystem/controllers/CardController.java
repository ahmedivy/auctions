package io.auctionsystem.controllers;

import io.auctionsystem.classes.GsonHandling;
import io.auctionsystem.classes.Listing;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardController {
    @FXML
    private Label categoryLabel;
    @FXML
    private ImageView image;
    @FXML
    private Label popularNowLabel;
    @FXML
    private Label price;
    @FXML
    private Label timeLeft;
    @FXML
    private Label title;
    @FXML
    private Label totalBids;

    public void setData(Listing listing) {
        title.setText(listing.getProduct().getName());
        price.setText((int) listing.getCurrentPrice() + " USD");
        totalBids.setText(String.valueOf(listing.getBids().size()) + " bids");
        categoryLabel.setText(listing.getProduct().getCategory());
        image.setImage(new Image(GsonHandling.imagesFolder + listing.getImageSrc()));
        timeLeft.setText(listing.getTimeLeft());
    }

}
