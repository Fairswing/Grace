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
	boolean toTrain;
	
	public DrawingPanel() throws IOException {
		canvas = new Canvas();
		
		getChildren().add(canvas);
		canvas.widthProperty().bind(widthProperty());
		canvas.heightProperty().bind(heightProperty());
		g2d = canvas.getGraphicsContext2D();
		pixel=16;
		g2d.setLineWidth(2.0);

		thread1 = new Thread(new Runnable() {
	            @Override
				public void run() {
	            	NeuralNetwork scervelo = new NeuralNetwork();
	            	ArrayList<List<Double>> TrainIn = new ArrayList<>();
	            	ArrayList<List<Double>> TrainOut = new ArrayList<>();
	        		ArrayList<List<Double>> GuessIn = new ArrayList<>();
	        		ArrayList<Double> GuessOut = new ArrayList<Double>();
	            	File nnData = new File("savedNN.dat");
	            	int i;
	            	scervelo.setnWeightsXNeuron(1);
	            	scervelo.addLayer(30);
	        		scervelo.addLayer(7, "relu");
	        		scervelo.addLayer(1, "sigmoid");
	        		
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
	        			
	        			TrainOut.add(List.of(diagnosis));
	        			TrainIn.add(data.get(i).getAllNormalizedData());
	        			
	        			// for debugging purpose only
	        			//System.out.println("first training data: " + TrainIn.get(i).toString());
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
	        		
	            	
	        		
	                drawBackground();	// drawing the nn to be shown

	        		if(toTrain) {
	        			double startTime = System.currentTimeMillis();
	        			double endTime;
	        			double elapsedTime;
	        			for(i=0; i<1000*100; ++i) {
		        			scervelo.train(TrainIn, TrainOut);
		        			// DEBUG
		        			if(i%100==0) {
		        				endTime = System.currentTimeMillis();
		        				elapsedTime = endTime - startTime;
		        				System.out.println("Iteration " + i + ", Cost: " + scervelo.lossAverage(TrainIn, TrainOut) + ",time: " + elapsedTime);
		        				startTime = endTime;
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
	        			ArrayList<List<Double>> outputs = new ArrayList<>();
	        			for (int y = 0; y < TrainIn.size(); ++y) {
	        		    	List<Double> curOutputs = scervelo.forward(TrainIn.get(y));
	        		    	outputs.add(curOutputs);
	        	        }
	        			
	        			boolean saved = scervelo.saveState();
		        		
	        			double errorSum = 0;
	        			double maxError = TrainOut.get(0).get(0) - outputs.get(0).get(0);
	        			
		        		// Printing the results of the train.
			        	System.out.println("--------------------------- RESULT");
			        	for (i = 0; i < TrainIn.size(); ++i) {
			        		for(int k=0; k<TrainOut.get(i).size(); k++) {
			        			//System.out.print("input: "+TrainIn.get(i).toString());
			        			double curOutput = outputs.get(i).get(k);
			        			double curExpectedOutput = TrainOut.get(i).get(k);
			        			System.out.print("\tExpected output: "+curExpectedOutput);
			        			System.out.print(" | Actual output: "+ curOutput);
			        			System.out.println(" \tError: [ "+ (TrainOut.get(i).get(k) - curOutput) + " ]");
			        			errorSum += Math.abs(curExpectedOutput - curOutput);
			        			if(maxError < Math.abs(curExpectedOutput - curOutput))
			        				maxError = Math.abs(curExpectedOutput - curOutput);
			        		}
			        	   
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
	

	// offset configuration
	private int layerHorizontalSpacing = 200;
	private int networkVerticalOffset = 100;
	private int maxLayerHeight = 800;

	public void drawNN(NeuralNetwork network) {
	    Platform.runLater(() -> {
	        List<List<Neuron>> layers = network.getLayers();
	        int neuronDiameter = 10;
	        
	        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
	            List<Neuron> currentLayer = layers.get(layerIndex);
	            int neuronsInLayer = currentLayer.size();
	            int verticalSpacing = maxLayerHeight / (neuronsInLayer + 1);
	            
	            for (int neuronIndex = 0; neuronIndex < neuronsInLayer; neuronIndex++) {
	                Neuron neuron = currentLayer.get(neuronIndex);
	                int xPosition = layerIndex * (neuronDiameter + layerHorizontalSpacing) + layerHorizontalSpacing;
	                int yPosition = networkVerticalOffset + verticalSpacing * (neuronIndex + 1) - neuronDiameter/2;
	                
	                if (layerIndex == 0) {
	                    drawInputNeuron(xPosition, yPosition, neuronDiameter);
	                } else {
	                    drawHiddenNeuron(xPosition, yPosition, neuronDiameter, neuron);
	                    drawConnections(layers, layerIndex, neuronIndex, neuron, xPosition, yPosition, neuronDiameter);
	                }
	            }
	        }
	    });
	}

	private void drawInputNeuron(int x, int y, int diameter) {
	    g2d.setFill(Color.LIGHTGRAY);
	    g2d.fillOval(x, y, diameter, diameter);
	    g2d.setStroke(Color.BLACK);
	    g2d.strokeOval(x, y, diameter, diameter);
	}

	private void drawHiddenNeuron(int x, int y, int diameter, Neuron neuron) {
	    Color biasColor = calculateColor(neuron.getBias(), "g");
	    g2d.setFill(biasColor);
	    g2d.fillOval(x, y, diameter, diameter);
	    g2d.setStroke(Color.BLACK);
	    g2d.strokeOval(x, y, diameter, diameter);
	}

	private void drawConnections(List<List<Neuron>> layers, int layerIndex, int neuronIndex, 
	                           Neuron neuron, int x, int y, int diameter) {
	    List<Double> weights = neuron.getWeights();
	    List<Neuron> prevLayer = layers.get(layerIndex - 1);
	    int prevLayerNeuronCount = prevLayer.size();
	    int prevVerticalSpacing = maxLayerHeight / (prevLayerNeuronCount + 1);

	    for (int prevNeuronIndex = 0; prevNeuronIndex < prevLayerNeuronCount; prevNeuronIndex++) {
	        double weight = weights.get(prevNeuronIndex);
	        Color connectionColor = calculateColor(weight, "r");
	        
	        int prevX = (layerIndex - 1) * (diameter + layerHorizontalSpacing) + layerHorizontalSpacing + diameter/2;
	        int prevY = networkVerticalOffset + prevVerticalSpacing * (prevNeuronIndex + 1);
	        int currX = x + diameter/2;
	        int currY = y + diameter/2;
	        
	        drawWeightedConnection(prevX, prevY, currX, currY, connectionColor, weight);
	    }
	}

	private Color calculateColor(Double value, String colorChannel) {
	    if (value == null) return Color.BLACK;
	    
	    int intensity = (int) (Math.min(Math.max(Math.abs(value), 0), 1) * 255);
	    double alpha = colorChannel.equals("r") ? 0.7 : 1.0;
	    
	    switch(colorChannel) {
	        case "r": return Color.rgb(intensity, 0, 0, alpha);
	        case "g": return Color.rgb(0, intensity, 0, alpha);
	        case "b": return Color.rgb(0, 0, intensity, alpha);
	        default: return Color.BLACK;
	    }
	}

	private void drawWeightedConnection(int x1, int y1, int x2, int y2, Color color, double weight) {
	    g2d.setStroke(color);
	    g2d.setLineWidth(1 + (float) (Math.abs(weight)));
	    g2d.strokeLine(x1, y1, x2, y2);
	    g2d.setLineWidth(1);
	}
	
	public static void drawBackground() {
		Platform.runLater(() -> {
			g2d.setFill(Color.rgb(100, 100, 100));
			g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		});
	}
	
}
