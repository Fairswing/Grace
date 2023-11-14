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
	
	public void changeWeight(float error_prime, float output) {
		//calcolo la derivata dell'errore finale in base al mio peso
		float derivative = (float) (error_prime * output * (1.0 - output) * weight);
		
		//calcolo il mio nuovo peso in base alla derivata e al mio learning rate
		weight = (weight-eps) * derivative;
	}
	
	public float calculate(float input) {
		return input * weight;
	}
	
}
