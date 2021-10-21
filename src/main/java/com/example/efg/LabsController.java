package com.example.efg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class LabsController implements Initializable {


    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check3;

    @FXML
    private CheckBox check4;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private RadioButton radio1;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private RadioButton radio2;

    @FXML
    private ChoiceBox<String> choise;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // ComboBox
        combo.getItems().addAll("Оля", "Таня","Настя", "Олександра");


        //check
        check1.setText("2+2=4");
        check2.setText("2×2=4");
        check3.setText("2+2×2=8");
        check4.setText("2+2+2=2");


        // radio
        radio1.setText("40");
        radio2.setText("19");
        radio3.setText("22");
        radio4.setText("5");

        ToggleGroup Rgroup = new ToggleGroup();

        radio1.setToggleGroup(Rgroup);
        radio2.setToggleGroup(Rgroup);
        radio3.setToggleGroup(Rgroup);
        radio4.setToggleGroup(Rgroup);


        // ChoiceBox
        choise.getItems().addAll("10", "20","50", "100");



    }


    @FXML
    void getres() {
        int Selected = 0;

        if(check1.isSelected()) Selected += 1;
        if(check2.isSelected()) Selected += 3;
        if(check3.isSelected()) Selected += 5;
        if(check4.isSelected()) Selected += 7;

        System.out.println(Selected);
        if( combo.getValue() == "Оля" && Selected == 4 && radio1.isSelected() && choise.getValue() == "50"){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результати");
            alert.setHeaderText("Ви все вгадали");
            alert.setContentText("Гарного дня");

            alert.showAndWait();

        }else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Результати");
            alert.setHeaderText("Уви.. Але ви десь помилились");
            alert.setContentText("Спробуйте ще раз як наважитесь");
            alert.showAndWait();

        }



    }

}