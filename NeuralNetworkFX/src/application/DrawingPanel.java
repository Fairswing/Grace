package application;

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
	private static int imgX; // image X position
	private static int imgY; // image y position
	
	public DrawingPanel() throws IOException {
		canvas = new Canvas();
		getChildren().add(canvas);
		canvas.widthProperty().bind(widthProperty());
		canvas.heightProperty().bind(heightProperty());
		g2d = canvas.getGraphicsContext2D();
		
		pixel=20;
		imgDim=4;
		imgY= 50;
		imgX= (int)(Main.panelWidth/2)-(imgDim*pixel)/2;
		
		

		thread1 = new Thread(new Runnable() {
	            @Override
				public void run() {
	            	NeuralNetwork scervelo = new NeuralNetwork();
	        		scervelo.setnWeightsXNeuron(1);

	        		scervelo.addLayer(2);
	        		scervelo.addLayer(4);
	        		scervelo.addLayer(4);
	        		scervelo.addLayer(1);
	        		
	        		
	        		
	        		List<List<Double>> TrainIn = new ArrayList<>();
	        		List<Double> TrainOut = new ArrayList<>();

	        		for(int i=0; i<imgDim; ++i){
	        			for(int y=0; y<imgDim; ++y){
		        			TrainIn.add(List.of((double)i/(imgDim-1),(double)y/(imgDim-1)));
		        		}
	        		}
	        		
	        		// Generating sphere
	        		// Center coordinates of the sphere
	                double centerX = imgDim / 2.0d;
	                double centerY = imgDim / 2.0d;
	                double radius = imgDim / 4.0d;

	                for (int row = 0; row < imgDim; ++row) {
	                    for (int col = 0; col < imgDim; ++col) {
	                        // Calculate the distance from the current pixel to the center of the sphere
	                        double distance = (double) Math.sqrt(Math.pow(col - centerX, 2) + Math.pow(row - centerY, 2));

	                        // Check if the pixel is within the sphere
	                        if (distance <= radius) {
	                            // Use shading based on the distance from the center
	                            double intensity = 1.0d - (distance / radius); // Linear shading
	                            TrainOut.add(intensity);
	                        } else {
	                            TrainOut.add(0.0d);
	                        }
	                    }
	                }
	        		
	                
	                drawBackground();
	        		drawImage(TrainOut,imgX,imgY,pixel,imgDim);
	        		
	        		
	        		// Training neural network
	        		for(int i=0; i<100*1000; ++i) {
	        			scervelo.train(TrainIn, TrainOut);
	        			if(i%1000==0) {
	        				System.out.println("Iteration " + i + ", Cost: " + scervelo.cost(TrainIn, TrainOut));
	        				drawNN(scervelo);
	        			}
	        			
	        			List<Double> img = new ArrayList<>();
	        			for (int y = 0; y < TrainIn.size(); ++y) {
	        		    	List<Double> output = scervelo.forward(TrainIn.get(y));
	        		    	img.add(output.get(0));
	        	        }
        				drawImage(img,imgX,imgY*2+imgDim*pixel,pixel,imgDim);
        				
        				
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
	        		
	        		// Print the results.
	        		System.out.println("--------------------------- RESULT");
	        		for (int i = 0; i < TrainIn.size(); ++i) {
	        			System.out.print("input: "+TrainIn.get(i).toString());
	        	        System.out.print("\tExpected output: "+TrainOut.get(i).toString());
	        	        System.out.print(" | Actual output: "+ img.get(i).toString());
	        	        System.out.println(" \tError: [ "+ (TrainOut.get(i) - img.get(i)) + " ]");
	                }
	        		
	        		
	        		
	        		// Print images 
	        		System.out.println("given image:");
	        		for (int i = 0; i < TrainOut.size(); ++i) {
        				if(TrainOut.get(i)>=0.5d) {
        					System.out.print("■");
        				}else {
        					System.out.print("□");
        				}
        				if(((i+1)%imgDim)==0) {
        					System.out.println("");
        				}
	        			
	                }
	        		
	        		System.out.println("NN image:");
	        		for (int i = 0; i < img.size(); ++i) {

        				if(img.get(i)>=0.5d) {
        					System.out.print("■");
        				}else {
        					System.out.print("□");
        				}
        				if(((i+1)%imgDim)==0) {
        					System.out.println("");
        				}
	        			
	                }
	        		
	            }
           });
	}
	 
	public void start() {
		thread1.start();
	}
	
	public void drawNN(NeuralNetwork scervelo) {
		Platform.runLater(() -> {
			int k=0;
			for (List<Neuron> layer : scervelo.getLayers()) {
				int y=0;
		        for (Neuron currentNeuron : layer) {
		        	int curLayer=scervelo.getLayers().indexOf(layer);
		        	//g2d.setFill(Color.rgb(Math.abs((int)currentNeuron.getWeight(curLayer)%255), (int)255, (int)255));
					g2d.fillOval(k*(pixel*2)+10, y*(pixel*2)+10, pixel, pixel);
					y++;
		        }
		        k++;
			}
		});
	}
	
	public static void drawBackground() {
		g2d.setFill(Color.rgb(100, 100, 100));
		g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
