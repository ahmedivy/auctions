package io.auctionsystem.controllers;

import io.auctionsystem.classes.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class CommentController {

    @FXML
    private Label message;
    @FXML
    private Label name;
    @FXML
    private Label time;

    public void setData(Comment comment) {
        name.setText(comment.getUser().getName());
        message.setText(comment.getMessage());
        time.setText(comment.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

}
