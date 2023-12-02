package neuronBasicAI;
import java.util.List;
import java.util.ArrayList;

public class neuron {
	private List<Float> weights;
	private float bias;
	
	public neuron(int nWeights) {
		super();
		this.weights = new ArrayList<Float>();
		for(int i=0;i<nWeights; ++i) {
			this.weights.add((float)Math.random());// randomize the initial value of the weight
			//this.weights.add(1f);
		}
		this.bias = (float)Math.random(); // should be randomly initialized like weights
	};
	
	
	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}

	public Float getWeight(int index) {
		return weights.get(index);
	}

	public List<Float> getWeights() {
		return weights;
	}
	
	public void setWeights(List<Float> weights) {
		this.weights = weights;
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
	public static float activationFunction(float x) {
		return sigmoid(x);
		//return Math.max(0, x);
		//return x;
	}
	
	
	public static float sigmoid(float x) {
	    return (float) (1.f / (1.f + (float)Math.exp(-x)));
	}
	
	
	
	
}
