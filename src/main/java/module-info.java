module com.example.dentist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.dentist to javafx.fxml;
    exports com.example.dentist;
}