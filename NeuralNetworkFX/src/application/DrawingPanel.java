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
import com.csvreader.CsvReader;

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
	            	ArrayList<List<Double>> TrainIn = new ArrayList<>();
	        		ArrayList<Double> TrainOut = new ArrayList<Double>();
	        		ArrayList<List<Double>> GuessIn = new ArrayList<>();
	        		ArrayList<Double> GuessOut = new ArrayList<Double>();
	            	File nnData = new File("savedNN.dat");
	            	int i;
	            	scervelo.setnWeightsXNeuron(1);
            		
	        		scervelo.addLayer(30);
	        		scervelo.addLayer(30, "relu");
	        		scervelo.addLayer(15, "relu");
	        		scervelo.addLayer(7, "relu");
	        		scervelo.addLayer(1, "sigmoid");
	        		/*
	            	scervelo.addLayer(2);
	            	scervelo.addLayer(2, "relu");
	            	scervelo.addLayer(1, "sigmoid");
					*/
	            	if(!toTrain && nnData.exists())
	            		scervelo = NeuralNetwork.loadState();
	        		
	        		
	            	CsvReader dataset = DataReader.getCSV("dataset.csv");
	            	ArrayList<Cancer> data = new ArrayList<Cancer>();
		
					// for debugging purpose only
					//System.out.println(dataset.toString());
					
					try {
						
						dataset.readHeaders();	// reading the headers of the csv
						
						while (dataset.readRecord()) {	// populating the cancers dataset
							data.add(new Cancer(dataset.get("id"), dataset.get("diagnosis"), Float.parseFloat(dataset.get("radius_mean")), Float.parseFloat(dataset.get("texture_mean")), 
									Float.parseFloat(dataset.get("perimeter_mean")), Float.parseFloat(dataset.get("area_mean")), Float.parseFloat(dataset.get("smoothness_mean")),
									Float.parseFloat(dataset.get("compactness_mean")), Float.parseFloat(dataset.get("concavity_mean")), Float.parseFloat(dataset.get("concave points_mean")),
									Float.parseFloat(dataset.get("symmetry_mean")), Float.parseFloat(dataset.get("fractal_dimension_mean")), Float.parseFloat(dataset.get("radius_se")),
									Float.parseFloat(dataset.get("texture_se")), Float.parseFloat(dataset.get("perimeter_se")), Float.parseFloat(dataset.get("area_se")), Float.parseFloat(dataset.get("smoothness_se")),
									Float.parseFloat(dataset.get("compactness_se")), Float.parseFloat(dataset.get("concavity_se")), Float.parseFloat(dataset.get("concave points_se")),
									Float.parseFloat(dataset.get("symmetry_se")), Float.parseFloat(dataset.get("fractal_dimension_se")), Float.parseFloat(dataset.get("radius_worst")),
									Float.parseFloat(dataset.get("texture_worst")), Float.parseFloat(dataset.get("perimeter_worst")), Float.parseFloat(dataset.get("area_worst")),
									Float.parseFloat(dataset.get("smoothness_worst")), Float.parseFloat(dataset.get("compactness_worst")), Float.parseFloat(dataset.get("concavity_worst")),
									Float.parseFloat(dataset.get("concave points_worst")), Float.parseFloat(dataset.get("symmetry_worst")), Float.parseFloat(dataset.get("fractal_dimension_worst"))));
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					dataset.close();
	        		
	        		// setting up all the training data for the nn to use
	        		for(i = 0; i < data.size()-50; i++) {
	        			double diagnosis;
	        			
	        			if("M".equals(data.get(i).getDiagnosis()))
	        				diagnosis = 0;
	        			else
	        				diagnosis = 1;
	        			
	        			TrainOut.add(diagnosis);
	        			TrainIn.add(data.get(i).getAllNormalizedData());
	        			
	        			// for debugging purpose only
	        			System.out.println("first training data: " + TrainIn.get(i).toString());
	        			//System.out.println("number of inputs: " + TrainIn.get(i).size());
	        		}
	        		
	        		// setting up the guess data
	        		for(i++; i < data.size(); i++) {
	        			double diagnosis;
	        			
	        			if("M".equals(data.get(i).getDiagnosis()))
	        				diagnosis = 0;
	        			else
	        				diagnosis = 1;
	        			
	        			GuessOut.add(diagnosis);
	        			GuessIn.add(data.get(i).getAllNormalizedData());
	        			
	        			// for debugging purpose only
	        			//System.out.println("first training data: " + TrainIn.get(i).toString());
	        			//System.out.println("number of inputs: " + TrainIn.get(i).size());
	        		}
	        		/*
	        	    TrainIn.add(List.of(0d,0d));
	        	    TrainIn.add(List.of(1d,0d));
	        	    TrainIn.add(List.of(0d,1d));
	        	    TrainIn.add(List.of(1d,1d));
	        	    
 	        		TrainOut.add((double)0);
 	        		TrainOut.add((double)1);
 	        		TrainOut.add((double)1);
 	        		TrainOut.add((double)0);
	        		*/
	                drawBackground();
	        		if(toTrain) {
	        			for(i=0; i<1000*10; ++i) {
		        			scervelo.train(TrainIn, TrainOut);
		        			// DEBUG
		        			if(i%100==0) {
		        				System.out.println("Iteration " + i + ", Cost: " + scervelo.lossAverage(TrainIn, TrainOut));
		        			}
		        			drawNN(scervelo);
	        				
		        		}
	        		} else {
	        			drawNN(scervelo);
	        			scervelo.nnGuessing(GuessIn,GuessOut);
	        		}
	        	
	        		
	        		/*
	        		 * 
	        		 * DEBUGGING
	        		 * 
	        		*/
	        		
	        		// Training code part of the neuralNetwork
	        		if(toTrain) {
	        			// Get the neural network's outputs.
		        		List<Double> img = new ArrayList<>();
	        			for (int y = 0; y < TrainIn.size(); ++y) {
	        		    	List<Double> output = scervelo.forward(TrainIn.get(y));
	        		    	img.add(output.get(0));
	        	        }
	        			
	        			boolean saved = scervelo.saveState();
		        		
	        			double errorSum = 0;
	        			double maxError = TrainOut.get(0) - img.get(0);
	        			
		        		// Printing the results of the train.
			        	System.out.println("--------------------------- RESULT");
			        	for (i = 0; i < TrainIn.size(); ++i) {
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
				        	System.out.println("  \tultimo cost: " + scervelo.lossAverage(TrainIn, TrainOut));
		        		
		        		if(saved)
		        			System.out.println("\tNeural Network saved correctly");
		        		else
		        			System.out.println("\tErrors saving the Neural Network");
	        		}
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
	private int yMaxNNHeight = 55;
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
