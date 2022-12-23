package io.auctionsystem.controllers;

import io.auctionsystem.App;
import io.auctionsystem.classes.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListingController implements Initializable {

    @FXML
    private Label activeLabel;
    @FXML
    private MFXTextField bidField;
    @FXML
    private Label bidValidationLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label winnerLabel;
    @FXML
    private MFXTextField commentField;
    @FXML
    private MFXButton commentPostButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label endLabel;
    @FXML
    private ImageView image;
    @FXML
    private MFXButton placeBidButton;
    @FXML
    private ImageView closeButton;
    @FXML
    private Label popularNowLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label totalBidsLabel;
    @FXML
    private Label userLabel;
    @FXML
    private MFXButton endnowButton;
    @FXML
    private MFXScrollPane commentsPane;
    @FXML
    private Label priceTagLabel;
    private final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Listing listing) {
        titleLabel.setText(listing.getProduct().getName());
        descriptionLabel.setText(listing.getProduct().getDescription());
        priceLabel.setText(String.valueOf(listing.getCurrentPrice()));
        totalBidsLabel.setText(String.valueOf(listing.getBids().size()));
        endnowButton.setVisible(false);
        categoryLabel.setText(listing.getProduct().getCategory());
        image.setImage(new Image(GsonHandling.imagesFolder + listing.getImageSrc()));
        startLabel.setText(listing.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        endLabel.setText(listing.getEndTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        userLabel.setText(listing.getSeller().getName());
        activeLabel.setText(listing.isActive() ? "Active" : "Sold");
        popularNowLabel.setVisible(listing.getBids().size() > 5);
        priceTagLabel.setText(listing.isActive() ? "Current Price" : "Sold Price");
        winnerLabel.setVisible(false);

        if (listing.getSeller().equals(data.getAuctionSystem().getAuthenticatedUser())) {
            placeBidButton.setVisible(false);
            bidField.setVisible(false);
            popularNowLabel.setVisible(false);
            endnowButton.setVisible(true);
        }
        if (!listing.isActive()) {
            priceLabel.setVisible(true);
            priceTagLabel.setText("Sold Price");
            priceLabel.setText(String.valueOf(listing.getCurrentPrice()));
            bidField.setVisible(false);
            placeBidButton.setVisible(false);
            popularNowLabel.setVisible(false);
            endnowButton.setVisible(false);
            winnerLabel.setVisible(true);
            winnerLabel.setText(
                    listing.getWinner() != null
                            ? String.format("Winner is %s", listing.getWinner().getName())
                            : "No winner"
            );
            bidValidationLabel.setVisible(false);
        }
        VBox commentsBox = new VBox();
        commentsBox.setAlignment(Pos.TOP_LEFT);
        commentsBox.setSpacing(0);
        commentsBox.setStyle("-fx-background-radius: 10");
        for (Comment comment:listing.getComments()) {
            FXMLLoader commentRoot = new FXMLLoader();
            commentRoot.setLocation(App.class.getResource("FXML/comment.fxml"));
            try {
                commentsBox.getChildren().add(commentRoot.load());
                CommentController commentController = commentRoot.getController();
                commentController.setData(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        commentsPane.setContent(commentsBox);

        // Adding Event Listeners
        placeBidButton.setOnAction(event -> {
            try {
                double bid = Double.parseDouble(bidField.getText());
                if (bid > listing.getCurrentPrice()) {
                    listing.setCurrentPrice(bid);
                    priceLabel.setText(String.valueOf(bid));
                    listing.getBids().add(
                            new Bid(
                                    bid,
                                    data.getAuctionSystem().getAuthenticatedUser(),
                                    LocalDateTime.now()
                            )
                    );
                    bidValidationLabel.setText("Bid placed successfully");
                    totalBidsLabel.setText(String.valueOf(listing.getBids().size()));

                } else {
                    bidValidationLabel.setText("Bid must be greater than current price");
                }
            } catch (Exception e) {
                bidValidationLabel.setText("Invalid bid");
            }
        });

        commentPostButton.setOnAction(event -> {
            if (commentField.getText().length() > 0) {
                listing.getComments().add(
                        new Comment(
                                data.getAuctionSystem().getAuthenticatedUser(),
                                commentField.getText(),
                                LocalDateTime.now()
                        )
                );
                commentField.setText("");
                setData(listing);
            }
        });

        endnowButton.setOnMouseClicked(event -> {
            if (listing.getBids().size() > 0) {
                listing.setWinner(listing.getBids().get(listing.getBids().size() - 1).getBidder());
                listing.setActive(false);

            } else {
                listing.setActive(false);
                listing.setWinner(null);
            }
            setData(listing);
        });
        closeButton.setOnMouseClicked(event -> {
            closeButton.getScene().getWindow().hide();
        });
    }
}
