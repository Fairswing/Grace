module NeuralNetworkFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javacsv;
	
	opens application to javafx.graphics, javafx.fxml;
}
