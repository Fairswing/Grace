package neuronBasicAI;
import java.util.List;
import java.util.ArrayList;

public class test {
	
	public static void main(String[] args) {
		/*
		NeuralNetwork scervelo = new NeuralNetwork();
		scervelo.setnWeightsXNeuron(2);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(0);
		scervelo.addNeuronToLayer(0);
		scervelo.addLayer();
		scervelo.addNeuronToLayer(1);
		
		
		
		List<List<Float>> xorTrain = new ArrayList<>();

        xorTrain.add(List.of(0.0f, 0.0f, 0.0f));
        xorTrain.add(List.of(1.0f, 0.0f, 1.0f));
        xorTrain.add(List.of(0.0f, 1.0f, 1.0f));
        xorTrain.add(List.of(1.0f, 1.0f, 0.0f));
		
		
		
		scervelo.train(xorTrain, 10);
		
		*/
		
		neuron n1 = new neuron(1);
		float n1Calc = 0;
		float in=0;
		float out=0;
		float[] inputs = {1, 2, 3};
		float[] outputs = {2, 4, 6};
		
		//training
		for(int j = 0; j < 100000; j++) {
			
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
		System.out.println("weight:" + n1.getWeight(0) + " error: " + (out - n1Calc));
		
		for(int i = 0; i < inputs.length; i++) {
			in = inputs[i];
			out = outputs[i];
			n1Calc  = n1.calculate(in);
			System.out.println("expected:" + out + " actual: " + n1Calc);
			n1.changeWeight((out - n1Calc), n1Calc);
		}
		
	}

}
