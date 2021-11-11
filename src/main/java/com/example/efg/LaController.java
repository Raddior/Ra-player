package com.example.efg;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class LaController implements Initializable {

    @FXML
    private Label labeldata;

    @FXML
    private ColorPicker color;

    @FXML
    private DatePicker datap;

    @FXML
    private Pane pane;

    @FXML
    private Button back;

    @FXML
    private Button next;


    Stage prevStage;

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        datap.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = datap.getValue();
                LocalDate now = LocalDate.now();

                int year = now.getYear() - date.getYear();
                int day = now.getDayOfYear() - date.getDayOfYear();

                if (year >=0 && day >= 0) {
                    labeldata.setText("Цей день був " + year + " років " + day + " днів назад");
                } else{
                    labeldata.setText("До цього дня " + Math.abs(year) + " років " + Math.abs(day) + " днів");
                }

            }
        });



        color.setOnAction(new EventHandler() {
            public void handle(Event t) {

                pane.setBackground(new Background(new BackgroundFill(color.getValue(), null, null)));
            }
        });



        back.setOnAction(event -> {


            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Labs-vier.fxml"));

            BorderPane myPane = null;
            try {
                myPane = (BorderPane)myLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            LabsController controller = (LabsController) myLoader.getController();

            Stage tstage = (Stage) color.getScene().getWindow();
            controller.setPrevStage(tstage);

            Scene myScene = new Scene(myPane);
            tstage.setScene(myScene);
            tstage.show();


        });




        next.setOnAction(event -> {


            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("La2.fxml"));

            BorderPane myPane = null;
            try {
                myPane = (BorderPane)myLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            La2Controller controller = (La2Controller) myLoader.getController();

            Stage tstage = (Stage) color.getScene().getWindow();
            controller.setPrevStage(tstage);

            Scene myScene = new Scene(myPane);
            tstage.setScene(myScene);
            tstage.show();


        });




    }







}