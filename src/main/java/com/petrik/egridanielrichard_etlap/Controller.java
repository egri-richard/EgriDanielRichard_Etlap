package com.petrik.egridanielrichard_etlap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
    Stage stage;
    public static Controller newWindow(String view,
                                       String windowTitle,
                                       int width,
                                       int height) throws IOException {
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(view));
      Scene scene = new Scene(fxmlLoader.load(), width, height);
      stage.setTitle(windowTitle);
      stage.setScene(scene);
      Controller controller = fxmlLoader.getController();
      controller.stage = stage;
      return controller;
    }
}
