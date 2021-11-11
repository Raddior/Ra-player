package com.example.efg;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class La2Controller implements Initializable {

    @FXML
    private TextField ttel;

    @FXML
    private TextField tlastname;

    @FXML
    private TableView<Person> tablevier;

    @FXML
    private Button safe;

    @FXML
    private TextField tname;

    @FXML
    private Pane pane;
    @FXML
    private Button back;



    ObservableList<Person> people;

    Stage prevStage;

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }



    public class Person{

        private SimpleStringProperty name;
        private SimpleIntegerProperty age;
        private SimpleStringProperty email;


        Person(String name , int age, String email){
            this.name = new SimpleStringProperty(name);
            this.age = new SimpleIntegerProperty(age);
            this.email = new SimpleStringProperty(email);
        }

        public String getName(){ return name.get();}
        public void setName(String value){ name.set(value);}

        public int getAge(){ return age.get();}
        public void setAge(int value){ age.set(value);}

        public String getEmail(){ return email.get();}
        public void setEmail(String value){ email.set(value);}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        load();

        back.setOnAction(event -> {


            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("La.fxml"));

            BorderPane myPane = null;
            try {
                myPane = (BorderPane)myLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            LaController controller = (LaController) myLoader.getController();

            Stage tstage = (Stage) tablevier.getScene().getWindow();
            controller.setPrevStage(tstage);

            Scene myScene = new Scene(myPane);
            tstage.setScene(myScene);
            tstage.show();


        });
        ///
    }

    public  void   load(){

        people = FXCollections.observableArrayList(

                new Person("Tom", 34,  "godvarior@gmail.com"),
                new Person("Bob", 22,  "ergre@mail.ua")

        );
        tablevier.setItems(people);

        TableColumn<Person, String> nameColumn = new TableColumn<Person, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        tablevier.getColumns().add(nameColumn);

        TableColumn<Person, Integer> ageColumn = new TableColumn<Person, Integer>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
        tablevier.getColumns().add(ageColumn);

        TableColumn<Person, String> telColumn = new TableColumn<Person, String>("Email");
        telColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        tablevier.getColumns().add(telColumn);

    }

    @FXML
    void totable() {

        if (tname.getText().trim().isEmpty() || tlastname.getText().trim().isEmpty()   ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText("Ви не ввели обов'язкові дані");
            alert.setContentText("Будь ласка сбробуйте ще");
            alert.showAndWait();
        } else {

            try {

                Person fe = new Person(tname.getText(), Integer.parseInt(tlastname.getText()), ttel.getText());
                people.addAll(fe);

            }catch (NumberFormatException e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText("Ви  ввели невірні дані");
                alert.setContentText("Будь ласка сбробуйте ще");
                alert.showAndWait();
            }
        }

    }







}

