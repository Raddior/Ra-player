module com.example.efg {
    requires java.desktop;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires javafx.base;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jaudiotagger;


    opens com.example.efg; //to javafx.fxml;
    exports com.example.efg;
}