package com.example.efg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;


public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 450);

        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/css/MainStyle.css").getFile());


        stage.setTitle("Ra` player");
        stage.getIcons().add( new Image(getClass().getResourceAsStream("/img/icon.png")));

        stage.setMinHeight(450);
        stage.setMinWidth(750);
        stage.setScene(scene);

        stage.show();




        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {

                Platform.exit();
                System.exit(0);
            }
        });


    }


    public static void main(String[] args) {
        launch();
    }

}