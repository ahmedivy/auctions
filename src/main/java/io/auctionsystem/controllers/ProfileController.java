package io.auctionsystem.controllers;

import io.auctionsystem.classes.DataSingleton;
import io.auctionsystem.classes.Listing;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private MFXButton editButton;

    @FXML
    private MFXTextField emailField;

    @FXML
    private Label name;

    @FXML
    private MFXTextField nameField;

    @FXML
    private MFXTextField phoneField;

    @FXML
    private MFXPaginatedTableView<Listing> table;

    @FXML
    private MFXTextField usernameField;

    private final DataSingleton data = DataSingleton.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeTable(data.getAuctionSystem().getUsersListings());
        name.setText(data.getAuctionSystem().getAuthenticatedUser().getName());
        usernameField.setText(data.getAuctionSystem().getAuthenticatedUser().getUsername());
        emailField.setText(data.getAuctionSystem().getAuthenticatedUser().getEmail());
        phoneField.setText(data.getAuctionSystem().getAuthenticatedUser().getPhone());
        nameField.setText(data.getAuctionSystem().getAuthenticatedUser().getName());
        editButton.setOnMouseClicked(mouseEvent -> {
            if (editButton.getText().equals("Edit Profile")) {
                nameField.setAllowEdit(true);
                emailField.setAllowEdit(true);
                phoneField.setAllowEdit(true);
                editButton.setText("Save");
            } else {
                data.getAuctionSystem().getAuthenticatedUser().setName(nameField.getText());
                data.getAuctionSystem().getAuthenticatedUser().setEmail(emailField.getText());
                data.getAuctionSystem().getAuthenticatedUser().setPhone(phoneField.getText());
                name.setText(data.getAuctionSystem().getAuthenticatedUser().getName());
                nameField.setAllowEdit(false);
                emailField.setAllowEdit(false);
                phoneField.setAllowEdit(false);
                editButton.setText("Edit Profile");
            }
        });
    }

    public void makeTable(ArrayList<Listing> ls) {
        MFXTableColumn<Listing> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Listing::getProductName));
        MFXTableColumn<Listing> categoryColumn = new MFXTableColumn<>("Category", false, Comparator.comparing(Listing::getProductCategory));
        MFXTableColumn<Listing> startingPriceColumn = new MFXTableColumn<>("Starting Price", false, Comparator.comparing(Listing::getStartingPrice));
        MFXTableColumn<Listing> highestBidColumn = new MFXTableColumn<>("Highest Bid", false, Comparator.comparing(Listing::getCurrentPrice));
        MFXTableColumn<Listing> timeLeftColumn = new MFXTableColumn<>("Time Left", false, Comparator.comparing(Listing::getTimeLeft));
        MFXTableColumn<Listing> totalBidsColumn = new MFXTableColumn<>("Bids", false, Comparator.comparing(Listing::getTotalBids));

        nameColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getProductName));
        categoryColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getProductCategory));
        timeLeftColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getTimeLeft));
        startingPriceColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getStartingPrice) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        highestBidColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getCurrentPrice) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});
        totalBidsColumn.setRowCellFactory(listing -> new MFXTableRowCell<>(Listing::getTotalBids) {{
            setAlignment(Pos.CENTER_RIGHT);
        }});

        table.getTableColumns().addAll(nameColumn, categoryColumn, startingPriceColumn, highestBidColumn, timeLeftColumn, totalBidsColumn);
        table.getFilters().addAll(
                new StringFilter<>("Name", Listing::getProductName),
                new StringFilter<>("Category",   Listing::getProductCategory),
                new DoubleFilter<>("Starting Price", Listing::getStartingPrice),
                new DoubleFilter<>("Highest Bid", Listing::getCurrentPrice)
        );
        table.setItems(FXCollections.observableArrayList(ls));
    }
}
