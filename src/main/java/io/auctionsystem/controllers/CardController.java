package io.auctionsystem.controllers;

import io.auctionsystem.classes.AzureAccess;
import io.auctionsystem.classes.Listing;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void setData(Listing listing) {
        title.setText(listing.getProduct().getName());
        price.setText((int) listing.getCurrentPrice() + " USD");
        totalBids.setText(listing.getBids().size() + " bids");
        categoryLabel.setText(listing.getProduct().getCategory());
        executorService.submit(() -> {
            image.setImage(new Image(AzureAccess.getBlobUrl(listing.getImageSrc())));
        });
        timeLeft.setText(listing.isActive()
                ? "Ending In :  " + listing.getTimeLeft() : "Sold to " + listing.getWinner().getUsername());
        popularNowLabel.setVisible(listing.isPopular());
    }

}
