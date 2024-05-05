package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DrawingPanel extends StackPane{
    private static Canvas canvas;
	private static GraphicsContext g2d;
	private Thread thread1;
	private static int pixel;// pixel dimension
	private static int imgDim; // image dimension
	boolean toTrain = false;
	
	public DrawingPanel() throws IOException {
		canvas = new Canvas();
		
		getChildren().add(canvas);
		canvas.widthProperty().bind(widthProperty());
		canvas.heightProperty().bind(heightProperty());
		g2d = canvas.getGraphicsContext2D();
		pixel=16;
		imgDim=6;
		g2d.setLineWidth(2.0);

		thread1 = new Thread(new Runnable() {
	            @Override
				public void run() {
	            	NeuralNetwork scervelo = new NeuralNetwork();
	            	File nnData = new File("savedNN.dat");
	            	scervelo.setnWeightsXNeuron(1);
            		
	        		scervelo.addLayer(30);
	        		scervelo.addLayer(7, "relu");
	        		scervelo.addLayer(3, "relu");
	        		scervelo.addLayer(1, "sigmoid");
	            	
	            	if(!toTrain && nnData.exists())
	            		scervelo = NeuralNetwork.loadState();
	        		
	        		
	        		ArrayList<Cancer> data = DataReader.getCSV();;
	        		ArrayList<List<Double>> TrainIn = new ArrayList<List<Double>>();
	        		ArrayList<Double> TrainOut = new ArrayList<Double>();
	        		
	        		// setting up all the training data for the nn to use
	        		for(int i = 0; i < data.size(); i++) {
	        			double diagnosis;
	        			
	        			if("M".equals(data.get(i).getDiagnosis()))
	        				diagnosis = 0;
	        			else
	        				diagnosis = 1;
	        			
	        			TrainOut.add(diagnosis);
	        			TrainIn.add(data.get(i).getAllNormalizedData());
	        			
	        			// for debugging purpose only
	        			//System.out.println("first training data: " + TrainIn.get(i).toString());
	        			//System.out.println("number of inputs: " + TrainIn.get(i).size());
	        		}
	        		

	                drawBackground();
	        		for(int i=0; i<1000*100; ++i) {
	        			scervelo.train(TrainIn, TrainOut);
	        			// DEBUG
	        			if(i%10==0) {
	        				System.out.println("Iteration " + i + ", Cost: " + scervelo.cost(TrainIn, TrainOut));
	        			}
	        			drawNN(scervelo);
        				
	        		}
	        		
	        		
	        		/*
	        		 * 
	        		 * DEBUGGING
	        		 * 
	        		*/
	        		
	        		// Get the neural network's outputs.
	        		List<Double> img = new ArrayList<>();
        			for (int y = 0; y < TrainIn.size(); ++y) {
        		    	List<Double> output = scervelo.forward(TrainIn.get(y));
        		    	img.add(output.get(0));
        	        }
        			
        			boolean saved = scervelo.saveState();
	        		
        			double errorSum = 0;
        			double maxError = TrainOut.get(0) - img.get(0);
        			
	        		// Print the results.
	        		System.out.println("--------------------------- RESULT");
	        		for (int i = 0; i < TrainIn.size(); ++i) {
	        			//System.out.print("input: "+TrainIn.get(i).toString());
	        	        System.out.print("\tExpected output: "+TrainOut.get(i).toString());
	        	        System.out.print(" | Actual output: "+ img.get(i).toString());
	        	        System.out.println(" \tError: [ "+ (TrainOut.get(i) - img.get(i)) + " ]");
	        	        errorSum += Math.abs(TrainOut.get(i) - img.get(i));
	        	        if(maxError < Math.abs(TrainOut.get(i) - img.get(i)))
	        	        	maxError = Math.abs(TrainOut.get(i) - img.get(i));
	                }
	        		
	        		System.out.print("\tErrore medio: " + errorSum/500);
	        		System.out.print(" | Errore massimo: " + maxError);
	        		System.out.println("  \tultimo cost: " + scervelo.cost(TrainIn, TrainOut));
	        		
	        		if(saved)
	        			System.out.println("\tNeural Network saved correctly");
	        		else
	        			System.out.println("\tErrors saving the Neural Network");
	            }
           });
	}
	 
	public void start(boolean toTrain) {
		this.toTrain = toTrain;
		thread1.start();
	}
	
	
	
	// Define offset variables
	private int xOffset = 150;
	private int yOffset = 10;
	private int yMaxNNHeight = 60;
	public void drawNN(NeuralNetwork scervelo) {
	    Platform.runLater(() -> {
	    	int layerYOffset=0;
	    	int prevLayerYOffset=0;
	        int k = 0;
	        Color color;
	        for (List<Neuron> layer : scervelo.getLayers()) {
	        	int curLayer = scervelo.getLayers().indexOf(layer);
	            int y = 0;
	            prevLayerYOffset=layerYOffset;
	            layerYOffset=yMaxNNHeight/scervelo.getLayers().get(curLayer).size();
	            
	            for (Neuron currentNeuron : layer) {
	                // Skip drawing connections for the input layer
	                if (curLayer == 0) {
	                	g2d.setFill(Color.BLACK);
	                    g2d.fillOval(k * (pixel+xOffset)+xOffset, (y * (pixel*layerYOffset) + yOffset+pixel*layerYOffset/2), pixel, pixel);
	                    y++;
	                    continue;
	                }else {

	                }
	                
	                List<Double> weights = currentNeuron.getWeights();
	                
	                // Iterate over neurons in the previous layer
	                for (int i = 0; i < scervelo.getLayers().get(curLayer - 1).size(); i++) {
	                    double weight = weights.get(i); // Get the weight corresponding to the connection between currentNeuron and prevNeuron
	                    color = calculateColor(weight,"r");
	                    
	                    // Draw connection line with color based on weight
	                    drawConnection(((k - 1) * (pixel+xOffset)+xOffset)+pixel/2, (i * (pixel*prevLayerYOffset) + yOffset+pixel*prevLayerYOffset/2)+pixel/2, (k * (pixel+xOffset)+xOffset)+pixel/2, (y * (pixel*layerYOffset) + yOffset+pixel*layerYOffset/2)+pixel/2, color);
	                }
	                color = calculateColor(currentNeuron.getBias(),"g");
                	g2d.setFill(color);
	                g2d.fillOval(k * (pixel+xOffset)+xOffset, (y * (pixel*layerYOffset) + yOffset+pixel*layerYOffset/2), pixel, pixel);
	                y++;
	            }
	            k++;
	        }
	    });
	}



	// Helper method to calculate color based on weight
	private Color calculateColor(Double x, String color) {
		if(x==null) {
			return Color.rgb(0, 0, 0);
		}
	    // Map the weight to a value between 0 and 255
	    int colorValue = (int) (Math.abs(x) * 255);
	    // Ensure the color value is within the valid range of 0 to 255
	    colorValue = Math.min(Math.max(colorValue, 0), 255);
	    // Use the color value for red component, and set green and blue to 0
	    switch(color) {
	    	case "r":
	    		return Color.rgb(colorValue, 0, 0);
	    	case "g":
	    		return Color.rgb(0, colorValue, 0);
	    	case "b":
	    		return Color.rgb(0, 0, colorValue);
	    	default:
	    		return Color.rgb(colorValue, 0, 0);
	    }
	    
	}

	// Helper method to draw connection line with specified color
	private void drawConnection(int x1, int y1, int x2, int y2, Color color) {
	    g2d.setStroke(color);
	    g2d.strokeLine(x1, y1, x2, y2);
	}

	// Method to set the offset variables
	public void setOffset(int xOffset, int yOffset) {
	    this.xOffset = xOffset;
	    this.yOffset = yOffset;
	}


	
	public static void drawBackground() {
		Platform.runLater(() -> {
			g2d.setFill(Color.rgb(100, 100, 100));
			g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		});
	}
	

	public static void drawImage(List<Double> image, int x,int y, int pixelSize, int imgSize) {
		
		Platform.runLater(() -> {
			List<Double> imageCPY = List.copyOf(image);
			int curY=0;
			for(int i=0; i<imageCPY.size(); ++i) {
				
				g2d.setFill(Color.rgb((int)(imageCPY.get(i)*255), (int)(imageCPY.get(i)*255), (int)(imageCPY.get(i)*255)));
				g2d.fillRect((i%imgDim)*pixel+x, curY*pixel+y, pixel, pixel);
				if(((i+1)%imgDim)==0) {
					curY+=1;
				}
			}
		});
		
	}
	
}
