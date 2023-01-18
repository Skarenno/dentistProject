package com.example.dentist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class QuestionApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // TODO AGGIUNGERE ICONA CHE NON FUNZIONA
        System.out.println(QuestionApplication.class.getResourceAsStream("/tooth.png").toString());
        stage.getIcons().clear();
        stage.getIcons().add(new Image(QuestionApplication.class.getResourceAsStream("/tooth.png")));
        FXMLLoader fxmlLoader = new FXMLLoader(QuestionApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setController(new QuestionController());
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(QuestionApplication.class.getResource("style.css").toExternalForm());        stage.setResizable(false);
        stage.setTitle("Questions!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}