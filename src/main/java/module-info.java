module io.auctionsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.slf4j;
    requires org.controlsfx.controls;
    requires MaterialFX;
    requires com.google.gson;
    requires com.azure.storage.blob;
    requires com.google.api.services.gmail;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.auth;

    opens io.auctionsystem.classes to com.google.gson;
    opens io.auctionsystem to javafx.fxml;
    opens io.auctionsystem.controllers to javafx.fxml;
    exports io.auctionsystem;
}