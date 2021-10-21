module com.example.efg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jaudiotagger;
    requires java.desktop;
    requires javafx.media;

    opens com.example.efg to javafx.fxml;
    exports com.example.efg;
}