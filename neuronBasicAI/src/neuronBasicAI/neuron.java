package neuronBasicAI;

public class neuron {
	private float weight;
	private float learning_rate;
	
	public neuron() {
		weight = 1;
		learning_rate = 0.001f;
	};
	
	public void changeWeight(float error, float output) {
		// Calculate the derivative of the error with respect to the weight
        float derivative = error * output * (1.0f - output) * weight;

        // Update the weight using the derivative
        weight = weight - learning_rate * derivative;

        System.out.println("New weight: " + weight);
	}
	
	public float calculate(float input) {
		return Math.max(0, input * weight);
	}
}
