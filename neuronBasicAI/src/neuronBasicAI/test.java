package neuronBasicAI;

import java.util.*;

public class test {
	
	public static void main(String[] args) {

		NeuralNetwork scervelo = new NeuralNetwork();
		scervelo.setnWeightsXNeuron(2);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(0);
		scervelo.addNeuronToLayer(0);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(1);
		
		List<List<Float>> TrainIn = new ArrayList<>();
		List<List<Float>> TrainOut = new ArrayList<>();

		// XOR truth table
        TrainIn.add(List.of(0f,0f));
        TrainIn.add(List.of(0f,1f));
        TrainIn.add(List.of(1f,0f));
        TrainIn.add(List.of(1f,1f));
        
        TrainOut.add(List.of(0f));
        TrainOut.add(List.of(1f));
        TrainOut.add(List.of(1f));
        TrainOut.add(List.of(0f));
		
		
		scervelo.train(TrainIn, TrainOut, 100*1000);
		
		
		// Get the neuraln network's outputs.
		List<List<Float>> outputs = new ArrayList<>();
		for(int i=0; i<TrainOut.size(); ++i) {
			outputs.add(List.copyOf(scervelo.forward(TrainIn.get(i))));
		}
		
		// Print the results.
		System.out.println("--------------------------- RESULT");
		for (int i = 0; i < TrainIn.size(); ++i) {
			System.out.print("input: "+TrainIn.get(i).toString());
	        System.out.print(" Expected output: "+TrainOut.get(i).toString());
	        System.out.println(" | Actual output: "+outputs.get(i).toString());
        }
	}
}