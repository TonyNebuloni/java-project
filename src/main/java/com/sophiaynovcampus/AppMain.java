package com.sophiaynovcampus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gestionnaire de Collection de Jeux Vidéo");

        URL fxmlUrl = getClass().getResource("/MainView.fxml");
        if (fxmlUrl == null) {
            System.err.println("❌ MainView.fxml introuvable !");
            return;
        }

        Parent root = FXMLLoader.load(fxmlUrl);

        Scene scene = new Scene(root, 800, 500);

        URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("⚠️ style.css introuvable, pas de styles appliqués.");
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
