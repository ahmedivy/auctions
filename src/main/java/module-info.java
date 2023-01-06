module io.auctionsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.slf4j;
    requires org.controlsfx.controls;
    requires MaterialFX;
    requires com.google.gson;
    requires com.azure.storage.blob;
    requires java.mail;

    opens io.auctionsystem.classes to com.google.gson;
    opens io.auctionsystem to javafx.fxml;
    opens io.auctionsystem.controllers to javafx.fxml;
    exports io.auctionsystem;
}