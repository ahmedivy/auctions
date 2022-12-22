package io.auctionsystem.controllers;

import io.auctionsystem.App;
import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.Listing;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private HBox activeListingButton;
    @FXML
    private HBox homeButton;
    @FXML
    private HBox profileButton;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private HBox trendingButton;
    @FXML
    private HBox watchListButton;
    @FXML
    private HBox yourListingButton;
    @FXML
    private MFXButton sellButton;
    @FXML
    private HBox secondaryBar;
    @FXML
    private MFXButton sortButton;
    private ArrayList<Listing> currentListing;
    public static String currentSort;
    @FXML
    private Label allCat;
    @FXML
    private Label bagsCat;
    @FXML
    private Label carsCat;
    @FXML
    private Label fineArtCat;
    @FXML
    private Label jewelryCat;
    @FXML
    private Label othersCat;
    @FXML
    private Label watchesCat;
    @FXML
    private Label usernameLabel;
    @FXML
    private MFXButton logoutButton;
    @FXML
    private AnchorPane mainSection;
    private final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(data.getAuctionSystem().getAuthenticatedUser().getName());
        refreshGridPane(data.getAuctionSystem().getListingsExcludingUser());
        addEventHandlers();
        homeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        allCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), true);
    }

    public void addListingPageHandler(VBox node, Listing listing) {
        node.setOnMouseClicked(mouseEvent -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("FXML/listing.fxml"));
                AnchorPane root = loader.load();
                ListingController listingController = loader.getController();
                listingController.setData(listing);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void backFromFile() {
        mainSection.getChildren().clear();
        mainSection.getChildren().add(secondaryBar);
        mainSection.getChildren().add(scrollPane);
    }

    public void addEventHandlers() {
        trendingButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(trendingButton);
            backFromFile();
            refreshGridPane(data.getAuctionSystem().getTrendingListings());
        });
        homeButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(homeButton);
            backFromFile();
            refreshGridPane(data.getAuctionSystem().getListingsExcludingUser());
        });
        activeListingButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(activeListingButton);
            backFromFile();
            refreshGridPane(data.getAuctionSystem().getActiveListings());
        });
        yourListingButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(yourListingButton);
            backFromFile();
            refreshGridPane(data.getAuctionSystem().getUsersListings());
        });
        watchListButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(watchListButton);
            backFromFile();
            refreshGridPane(data.getAuctionSystem().getWatchList());
        });
        allCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(allCat);
            refreshGridPane(data.getAuctionSystem().getListingsExcludingUser());
        });
        bagsCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(bagsCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Bags"));
        });
        carsCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(carsCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Cars"));
        });
        fineArtCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(fineArtCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Fine Art"));
        });
        jewelryCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(jewelryCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Jewelry"));
        });
        othersCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(othersCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Others"));
        });
        watchesCat.setOnMouseClicked(mouseEvent -> {
            changePseudoClassofCats(watchesCat);
            refreshGridPane(data.getAuctionSystem().getByCategory("Watch"));
        });
        sellButton.setOnMouseClicked(mouseEvent -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("FXML/addListing.fxml"));
                AnchorPane root = loader.load();
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        profileButton.setOnMouseClicked(mouseEvent -> {
            changePseudoClass(profileButton);
            mainSection.getChildren().clear();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("FXML/profile.fxml"));
                AnchorPane root = loader.load();
                mainSection.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sortButton.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("FXML/sortingmenu.fxml"));
            try {
                AnchorPane root = loader.load();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.addEventHandler(WindowEvent.WINDOW_HIDING, windowEvent -> {
                if (currentSort != null) {
                    switch (currentSort) {
                        case "Price (Low to High)" ->
                                currentListing.sort(Comparator.comparing(Listing::getCurrentPrice));
                        case "Price: (High to Low)" ->
                                currentListing.sort(Comparator.comparing(Listing::getCurrentPrice).reversed());
                        case "Newest" -> currentListing.sort(Comparator.comparing(Listing::getStartTime).reversed());
                        case "Oldest" -> currentListing.sort(Comparator.comparing(Listing::getStartTime));
                        case "Ending Soon" -> currentListing.sort(Comparator.comparing(Listing::getEndTime));
                        case "Ending Later" -> currentListing.sort(Comparator.comparing(Listing::getEndTime).reversed());
                    }
                    refreshGridPane(currentListing);
                }
            });
        });
        logoutButton.setOnMouseClicked(mouseEvent -> {
            try {
                data.getAuctionSystem().setAuthenticatedUser(null);
                App.switchScene((Stage) logoutButton.getScene().getWindow(), "login.fxml", "Login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void changePseudoClass(HBox button) {
        trendingButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        homeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        activeListingButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        yourListingButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        watchListButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        profileButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    public void changePseudoClassofCats (Label label) {
        allCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        bagsCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        carsCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        fineArtCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        jewelryCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        othersCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        watchesCat.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), false);
        label.pseudoClassStateChanged(PseudoClass.getPseudoClass("selectedCategory"), true);
    }
    public void refreshGridPane(ArrayList<Listing> ls) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        scrollPane.setContent(gridPane);
        for(int i = 0; i < ls.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("FXML/card.fxml"));
            try {
                VBox vBox = loader.load();
                CardController cardController = loader.getController();
                cardController.setData(ls.get(i));
                addListingPageHandler(vBox, ls.get(i));
                gridPane.add(vBox, i % 2, i / 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentListing = ls;
    }
}
