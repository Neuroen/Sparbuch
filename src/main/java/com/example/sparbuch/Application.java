package com.example.sparbuch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        //stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("dollar-icon.png"))));
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 685, 400);
        MainController mainController = fxmlLoader.getController();
        System.out.println(mainController);
        FileManager fm = new FileManager();
        SparbuchData mainData = fm.ReadFile();
        if(mainData == null)
        {
            mainData = new SparbuchData();
        }
        mainController.SetMainData(mainData);
        mainController.UpdateUI(true);
        mainController.Init();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Sparbuch");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}