package neuronBasicAI;

import java.util.*;

public class test {
	
	public static void main(String[] args) {

		NeuralNetwork scervelo = new NeuralNetwork();
		scervelo.setnWeightsXNeuron(16);
		//scervelo.addLayer(8); // Reduce the number of neurons in each hidden layer to 8 or experiment with other values
		scervelo.addLayer(1);
		scervelo.addLayer(16);

		List<List<Float>> TrainIn = new ArrayList<>();
		List<Float> TrainOut = new ArrayList<>();

		
		List<Float> finputs = new ArrayList<Float>();
		List<Float> foutputs = new ArrayList<Float>();
		
		//4*4
		// Training dataset
		for(int i=0;i<4*4;++i){
			finputs.add((float)Math.random());
		}
		TrainIn.add(finputs);
		
		for(int i=0;i<4*4;++i){
			if(i==0||i==5||i==10||i==15 || i==3||i==6||i==9||i==12) {
				TrainOut.add(1f);
				
			}else {
				TrainOut.add(0f);
			}
		}
		//TrainOut.add(foutputs);
		

		for(int i=0; i<100*1000; ++i) {
			scervelo.train(TrainIn, TrainOut);
			if(i%1000==0) {
				System.out.println("Iteration " + i + ", Cost: " + scervelo.cost(TrainIn, TrainOut));
			}
			
    		
		}
		
		// Get the neuraln network's outputs.
		List<Float> outputs = new ArrayList<>();

			outputs=List.copyOf(scervelo.forward(TrainIn));
		
		
		// Print the results.
		System.out.println("--------------------------- RESULT");
		for (int i = 0; i < TrainIn.size(); ++i) {
			System.out.print("input: "+TrainIn.get(i).toString());
	        System.out.print(" Expected output: "+TrainOut.get(i).toString());
	        System.out.print(" | Actual output: "+ outputs.get(i).toString());
	        System.out.println(" Error: [ "+ (TrainOut.get(i) - outputs.get(i)) + " ]");
        }
		System.out.println("given image:");
		for (int i = 0; i < TrainOut.size(); ++i) {
			if(TrainOut.get(i)>=0.5f) {
				System.out.print("■");
			}else {
				System.out.print("□");
			}
			if(((i+1)%4)==0) {
				System.out.println("");
			}
			
        }
	
		System.out.println("NN image:");
		for (int i = 0; i < outputs.size(); ++i) {
			if(outputs.get(i)>=0.5f) {
				System.out.print("■");
			}else {
				System.out.print("□");
			}
			if(((i+1)%4)==0) {
				System.out.println("");
			}
			
        }
	}
}




