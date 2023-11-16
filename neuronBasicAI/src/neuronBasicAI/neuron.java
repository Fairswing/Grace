package neuronBasicAI;
import java.util.List;
import java.util.ArrayList;

public class neuron {
	private List<Float> weights;
	private float learning_rate;
	private float bias;
	
	public neuron(int nWeights, float learning_rate) {
		super();
		this.weights = new ArrayList<Float>();
		for(int i=0;i<nWeights; ++i) {
			//this.weights.add((float)Math.random()*10);// randomize the initial value of the weight
			this.weights.add(1f);
		}
		this.bias=0f; // should be randomly initialized like weights
		this.learning_rate = learning_rate;
	};
	
	public neuron(int nWeights) {
		super();
		this.weights = new ArrayList<Float>();
		for(int i=0;i<nWeights; ++i) {
			//this.weights.add((float)Math.random()*10); // randomize the initial value of the weight
			this.weights.add(1f);
		}
		this.bias=0f;// should be randomly initialized like weights
		this.learning_rate = 1e-8f;
	};
	
	
	public Float getWeight(int index) {
		return weights.get(index);
	}

	public List<Float> getWeights() {
		return weights;
	}
	public void setWeights(List<Float> weights) {
		this.weights = weights;
	}
	public float getLearning_rate() {
		return learning_rate;
	}
	public void setLearning_rate(float learning_rate) {
		this.learning_rate = learning_rate;
	}

	public void changeWeight(float error, float output) {
		
	    float clippedError = Math.max(0f, Math.min(1.0f, error)); // Clip error between -1 and 1
	    
	    for (int i = 0; i < this.weights.size(); i++) {
	        // Calculate the derivative of the error with respect to the weight
	        float derivative = clippedError * sigmoid(output) * (1.0f - output) * sigmoid(this.weights.get(i));

	        // Update the weight using the derivative and learning rate
	        float newWeight = this.weights.get(i) - this.learning_rate * derivative;

	        // Update the weight with the clipped value
	        this.weights.set(i, newWeight);
	    }
	}
	
	
	public float calculate(float input) {
		float result = input * this.weights.get(0) + this.bias;
		return activationFunction(result);
	}
	
	
	//generalized version of the calculate function
	public float calculate(List<Float> inputs) {
		//System.out.println("Input.size(): " + inputs.size());
		if(inputs.size()!=this.weights.size()) {
			System.out.println("calculate: inputs [ " + inputs.size() + " ] and weights[ " + this.weights.size() + " ] sizes don't match");
			return 0f; //error 
		}
		float result =0f;
		for(int i=0;i<this.weights.size();++i) {
			 result+= inputs.get(i) * this.weights.get(i);
		}
		result += this.bias;
		return activationFunction(result);
	}
	
	
	// activation function:  adds bends and curvature to an otherwise linear output allowing it to capture and rapresent more complex patterns in the data.
	public float activationFunction(float x) {
		return Math.max(0, x);
		//return x;
	}
	
	public float sigmoid(float x) {
	    return (float) (1.0f / (1.0f + Math.exp(-x)));
	}
	
	
	
	
}
