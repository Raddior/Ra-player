package com.example.efg;


import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.SVGPath;
import javafx.stage.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;


import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import java.io.File;
import java.util.*;
import java.util.Timer;


public class HelloController implements Initializable {

    @FXML
    private Button closepl;

    @FXML
    private Button open_labs;

    @FXML
    private Button openpl;

    @FXML
    private Label timePassed;

    @FXML
    private Pane shuffleButton;

    @FXML
    private Pane pauseButton;

    @FXML
    private Label timeRemaining;

    @FXML
    private ImageView nowPlayingArtwork;

    @FXML
    private Pane playButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider timeSlider;

    @FXML
    private StackPane controlBox;

    @FXML
    private ListView<String> PlayList;

    @FXML
    private Label nowPlayingTitle;

    @FXML
    private MediaView mediavier;

    @FXML
    private SVGPath svg_shuffleButton;

    @FXML
    private SVGPath svg_loopButton;

    @FXML
    private Pane mod;


    private Media media;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> songs;
    private int songNumber;

    private Timer timer;
    private TimerTask task;

    private boolean running;
    private Duration duration;

    private static boolean isLoopActive = false;
    private static boolean israndomActive = false;

    private Image artwork;
    private String musicDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File file = new File("./src/main/resources/img/121.png");
        String file_s = file.toURI().toString();
        artwork =new Image(file_s);
        nowPlayingArtwork.setImage(artwork);

        controlBox.getChildren().remove(pauseButton);

        timeSlider.setDisable(true);
        volumeSlider.setDisable(true);

        timeRemaining.setText("0:00");
        timePassed.setText("0:00");
        nowPlayingTitle.setText("Project ℤerø");

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);

            }
        });

        open_labs.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Labs-vier.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Labs");
            stage.setResizable(false);
            stage.initOwner(open_labs.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);


            stage.showAndWait();



        });


        mod.widthProperty().addListener((obs, oldVal, newVal) -> {

            mediavier.setFitWidth(mod.getWidth());
            nowPlayingArtwork.setFitWidth(mod.getWidth());
            nowPlayingArtwork.setX(mod.getWidth()/4 - 85);

        });

        mod.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediavier.setFitHeight(mod.getHeight());
            nowPlayingArtwork.setFitHeight( mod.getHeight() );
          
        });

    }



    @FXML
    protected  void toggleLoop() {
        isLoopActive = !isLoopActive;

        PseudoClass active = PseudoClass.getPseudoClass("active");
        svg_loopButton.pseudoClassStateChanged(active, isLoopActive);
    }

    @FXML
    protected void togglerandom() {
        israndomActive = !israndomActive;

        PseudoClass active = PseudoClass.getPseudoClass("active");
        svg_shuffleButton.pseudoClassStateChanged(active, israndomActive);
    }




    private void handleImport() {


        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Window Mwindow = volumeSlider.getScene().getWindow();
            musicDirectory = directoryChooser.showDialog(Mwindow).getPath();

            timeSlider.setDisable(false);
            volumeSlider.setDisable(false);
//// норм вивід
            File[] files = new File(musicDirectory).listFiles();
            songs = new ArrayList<File>();

            for (File file : files) {
                if (file.isFile() && isSupportedFileType(file.getName())) {
                    songs.add(file);
                    int i = file.getName().lastIndexOf('.');
                    PlayList.getItems().addAll(file.getName().substring(0, i));
                }
            }
/////
//// розмір
            PlayList.setCellFactory(param -> new ListCell<String>() {
                {
                    prefWidthProperty().bind(PlayList.widthProperty().subtract(20));
                    setMaxWidth(Control.USE_PREF_SIZE);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null && !empty) {
                        setText(item);
                    } else {
                        setText(null);
                    }
                }
            });
/////

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static boolean isSupportedFileType(String fileName) {

        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1).toLowerCase();
        }
        switch (extension) {
            // MP3
            case "mp3":
                // MP4
            case "mp4":
            case "m4a":
            case "m4v":
                // WAV
            case "wav":
                return true;
            default:
                return false;
        }
    }



    @FXML
    protected void onk() {
        handleImport();
        mediainstal();

    }

    @FXML
    protected void deleteList() {

        if (songs == null) return;
        PlayList.getItems().clear();
        songs.clear();

     //   mediavier.setMediaPlayer(null);
     //   nowPlayingArtwork.setImage(null);
    }




    public void playMedia() {
        if (mediaPlayer == null) return;

        if (controlBox.getChildren().get(4).toString().contains("playButton") ) {
            controlBox.getChildren().remove(playButton);
            controlBox.getChildren().add(4, pauseButton);
        }

        beginTimer();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }


    public void pauseMedia() {
        if (mediaPlayer == null ) return;

        if (controlBox.getChildren().get(4).toString().contains("pauseButton") ) {
            controlBox.getChildren().remove(pauseButton);
            controlBox.getChildren().add(4, playButton);
        }

        cancelTimer();
        mediaPlayer.pause();
    }


    public void previousMedia() {
        if (mediaPlayer == null || songs.isEmpty()) return;

        if (songNumber > 0) {
            songNumber--;

        } else {
            songNumber = songs.size() - 1;

        }
        mediaPlayer.stop();

        if (running) {
            cancelTimer();
        }
        mediainstal();
    }


    public void nextMedia() {
        if (mediaPlayer == null || songs.isEmpty()) return;

        if (israndomActive ) {

            Random rand = new Random();
            songNumber =  rand.nextInt(songs.size());

            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }
            mediainstal();
        }else
        if (songNumber < songs.size() - 1 ) {

            songNumber++;
            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }
            mediainstal();
        } else {
            songNumber = 0;
            mediaPlayer.stop();
            mediainstal();
        }
    }




    protected void imagetovideo() {

        try {
            mediavier.setMediaPlayer(mediaPlayer);
        }catch (Exception ex) {
            File file = new File("./src/main/resources/img/12.png");
            String file_s = file.toURI().toString();
            artwork =new Image(file_s);
            nowPlayingArtwork.setImage(artwork);
        }

    }


    protected void updateimage() {

        try {
            mediavier.setMediaPlayer(null);
            nowPlayingArtwork.setImage(null);


            String location = songs.get(songNumber).toURI().getPath();
            System.out.println(location);
            AudioFile audioFile = AudioFileIO.read(new File(location));
            Tag tag = audioFile.getTag();
            byte[] bytes = tag.getFirstArtwork().getBinaryData();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            artwork = new Image(in, 300, 300, true, true);
            nowPlayingArtwork.setImage(artwork);

            if (artwork.isError()) {

                File file = new File("./src/main/resources/img/12.png");
                String file_s = file.toURI().toString();
                artwork =new Image(file_s);
                nowPlayingArtwork.setImage(artwork);
            }

        } catch (Exception ex) {
            File file = new File("./src/main/resources/img/12.png");
            String file_s = file.toURI().toString();
            artwork =new Image(file_s);
            nowPlayingArtwork.setImage(artwork);
        }

    }


    protected void ImgOrVid() {

        mediavier.setMediaPlayer(null);
        nowPlayingArtwork.setImage(null);

        String extension = "";

        int i = songs.get(songNumber).toURI().getPath().toString().lastIndexOf('.');
        extension = songs.get(songNumber).toURI().getPath().toString().substring(i+1).toLowerCase();


        if(extension.equals("mp4")  || extension.equals("m4a")  || extension.equals("m4v") ) {
            imagetovideo();

        }else if (extension.equals("mp3") || extension.equals("wav")){
            updateimage();

        }else {
            File file = new File("./src/main/resources/img/12.png");
            String file_s = file.toURI().toString();
            artwork =new Image(file_s);
            nowPlayingArtwork.setImage(artwork);
        }



    }




    public void mediainstal() {

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        int i = songs.get(songNumber).getName().lastIndexOf('.');
        nowPlayingTitle.setText(songs.get(songNumber).getName().substring(0,i));

        playMedia();
        ImgOrVid();




        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {

                    if (media.getDuration() != null) {
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue() / 100.0));
                    }
                    updateValues();

                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                updateValues();
            }
        });

    }




    public void beginTimer() {

        timer = new Timer();

        task = new TimerTask() {

            public void run() {

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();


                int sec = (int) current;
                Platform.runLater(new Runnable() {
                    public void run() {

                        int secondsPassed = sec ;
                        int minutes = secondsPassed / 60;
                        int seconds = secondsPassed % 60;
                        var now = Integer.toString(minutes) + ":" + (seconds < 10 ? "0" + seconds : Integer.toString(seconds));
                        timePassed.setText(now);

                        long passsecondsPassed = sec ;
                        long totalSeconds = (int) mediaPlayer.getTotalDuration().toSeconds();
                        long secondsRemaining = totalSeconds - passsecondsPassed;
                        long passminutes = secondsRemaining / 60;
                        long passseconds = secondsRemaining % 60;
                        var Remaining = Long.toString(passminutes) + ":" + (passseconds < 10 ? "0" + passseconds : Long.toString(passseconds));
                        timeRemaining.setText(Remaining);

                    }
                });
                if (current / end == 1) {

                    cancelTimer();
                    Platform.runLater(new Runnable() {public void run() {
                        if (songs.isEmpty() || songs.size() <= 1) pauseMedia();
                        if (isLoopActive  ) { mediainstal(); }else nextMedia();

                    }});
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 100);
    }


    public void cancelTimer() {
        running = false;
        timer.cancel();
    }


    protected void updateValues() {
        duration = mediaPlayer.getMedia().getDuration();
        if ( timeSlider != null && duration != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                     Duration currentTime = mediaPlayer.getCurrentTime();
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()* 100.0);
                    }
                }
            });
        }
    }



}






