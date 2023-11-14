package neuronBasicAI;

import java.util.Random;

public class neuron {
	Random random;
	float eps;
	float weight;
	
	public neuron() {
		random = new Random();
		eps = (float) 1e-3;
		weight = (float) random.nextGaussian();
	};
	
	public void changeWeight(float error) {
		//weight = 
	}
	
}
