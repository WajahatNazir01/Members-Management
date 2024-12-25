module com.example.labpractisefinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labpractisefinal to javafx.fxml;
    exports com.example.labpractisefinal;
}