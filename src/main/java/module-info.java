module com.kelton.tooljavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;



    opens com.kelton.tooljavafx to javafx.fxml;
    exports com.kelton.tooljavafx;
}