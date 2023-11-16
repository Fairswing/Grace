package neuronBasicAI;

import java.util.*;

public class test {
	
	public static void main(String[] args) {

		NeuralNetwork scervelo = new NeuralNetwork();
		scervelo.setnWeightsXNeuron(1);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(0);
		scervelo.addNeuronToLayer(0);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(1);
		
		
		
		List<List<Float>> TrainIn = new ArrayList<>();
		List<List<Float>> TrainOut = new ArrayList<>();

        TrainIn.add(List.of(1f));
        TrainIn.add(List.of(2f));
        TrainIn.add(List.of(3f));
        
        TrainOut.add(List.of(100f));
        TrainOut.add(List.of(200f));
        TrainOut.add(List.of(300f));
		
		
		
		scervelo.train(TrainIn, TrainOut, 10000);
		
		
		neuron n1 = new neuron(1);
		float n1Calc = 0;
		float in=0;
		float out=0;
		float[] inputs = {1, 2, 3};
		float[] outputs = {2, 4, 6};
		
		//training
		for(int j = 0; j < 1000000; j++) {
			
			//cost/error function
			for(int i = 0; i < inputs.length; i++) {
				in = inputs[i];
				out = outputs[i];
				n1Calc  = n1.calculate(in);
				//System.out.println("expected:" + out + " actual: " + n1Calc);
				n1.changeWeight((out - n1Calc), n1Calc);
			}
		}
		  
		//result
		System.out.println("------------------------------ RESULT");
		System.out.println("weight: " + n1.getWeight(0) + " error: " + (out - n1Calc));
		
		for(int i = 0; i < inputs.length; i++) {
			in = inputs[i];
			out = outputs[i];
			n1Calc  = n1.calculate(in);
			System.out.println("expected:" + out + " actual: " + n1Calc);
			//n1.changeWeight((out - n1Calc), n1Calc);
		}
		
	}

}