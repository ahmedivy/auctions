package io.auctionsystem.controllers;

import io.auctionsystem.classes.Comment;
import io.github.gleidson28.GNAvatarView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommentController {

    @FXML
    private GNAvatarView image;
    @FXML
    private Label message;
    @FXML
    private Label name;
    @FXML
    private Label time;

    public void setData(Comment comment) {
        name.setText(comment.getUser().getName());
        message.setText(comment.getMessage());
        time.setText(comment.getTime().toString());
    }

}
