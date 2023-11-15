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
			this.weights.add((float)Math.random()*10);// randomize the initial value of the weight
		}
		this.bias=0f; // should be randomly initialized like weights
		this.learning_rate = learning_rate;
	};
	public neuron(int nWeights) {
		super();
		this.weights = new ArrayList<Float>();
		for(int i=0;i<nWeights; ++i) {
			this.weights.add((float)Math.random()*10);// randomize the initial value of the weight
		}
		this.bias=0f;// should be randomly initialized like weights
		this.learning_rate = 1e-4f;
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
		for (int i = 0; i < this.weights.size(); i++) {
	        // Calculate the derivative of the error with respect to the weight
	        float derivative = error * output * (1.0f - output) * weights.get(i);

	        // Update the weight using the derivative
	        this.weights.set(i, this.weights.get(i) - this.learning_rate * derivative);
	    }
        
        //System.out.println("New weight: " + weight);
	}
	
	
	public float calculate(float input) {
		float result = input * this.weights.get(0) + this.bias;
		return activationFunction(result);
	}
	
	
	//generalized version of the calculate function
	public float calculate(List<Float> inputs) {
		if(inputs.size()!=this.weights.size()) {
			System.out.println("calculate: inputs and weights sizes don't match");
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
	}
	
	
	
	
}
