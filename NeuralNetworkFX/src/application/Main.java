package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	 	public static double panelHeight;
	    public static double panelWidth;
	    DrawingPanel panel;
	    
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        panel = new DrawingPanel();
	        //sliders = new Sliders();
	        
	        panel.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
	        primaryStage.setTitle("Particle's gravity Simulation");
	        primaryStage.setScene(new Scene(panel, panelWidth, panelHeight));
	        
	        primaryStage.show();
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                Platform.exit();
	                System.exit(0);
	            }
	        });
	        panel.start();
	    }
	
	public static void main(String[] args) {
		panelHeight=768;
		panelWidth=1366;
		launch(args);		
	}
}
