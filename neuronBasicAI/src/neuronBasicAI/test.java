package neuronBasicAI;

import java.util.*;

public class test {
	
	public static void main(String[] args) {

		NeuralNetwork scervelo = new NeuralNetwork();
		scervelo.setnWeightsXNeuron(1);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(0);
		//scervelo.addNeuronToLayer(0);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(1);
		
		List<List<Float>> TrainIn = new ArrayList<>();
		List<List<Float>> TrainOut = new ArrayList<>();

		// Training dataset
        TrainIn.add(List.of(1f));
        TrainIn.add(List.of(2f));
        TrainIn.add(List.of(3f));
        
        TrainOut.add(List.of(10f));
        TrainOut.add(List.of(20f));
        TrainOut.add(List.of(30f));
		
		
		scervelo.train(TrainIn, TrainOut, 1000000);
		
		
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
	        System.out.print(" | Actual output: "+ outputs.get(i).toString());
	        System.out.println(" Error: [ "+ (TrainOut.get(i).get(0) - outputs.get(i).get(0)) + " ]");
        }
	}
}