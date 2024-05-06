package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	 	public static double panelHeight=1000;
	    public static double panelWidth=1000;
	    boolean toTrain = true;
	    DrawingPanel panel;

    @Override
    public void start(Stage primaryStage) throws IOException {
        panel = new DrawingPanel();

        // Set up the scene
        Scene scene = new Scene(panel, panelWidth, panelHeight);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // Set up the primary stage
        primaryStage.setTitle("Grace");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        
        // Show the primary stage
        primaryStage.show();

        // Start the drawing panel
        panel.start(toTrain);
    }

    public static void main(String[] args) {
        launch(args);
    }
}