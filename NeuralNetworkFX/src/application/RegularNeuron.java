package application;
import java.util.List;
import java.util.ArrayList;

public class RegularNeuron implements Neuron{
	private List<Double> weights;
	private double bias;
	
	public RegularNeuron(int nWeights) {
		super();
		this.weights = new ArrayList<Double>();
		for(int i=0;i<nWeights; ++i) {
			this.weights.add((double)Math.random());// randomize the initial value of the weight
		}
		this.bias = (double)Math.random(); // should be randomly initialized like weights
	}

	
	
	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getWeight(int index) {
		return weights.get(index);
	}

	public List<Double> getWeights() {
		return weights;
	}
	
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	
	//generalized version of the calculate function
	public double calculate(List<Double> inputs) {
		//System.out.println("Input.size(): " + inputs.size());
		if(inputs.size()!=this.weights.size()) {
			System.out.println("calculate: inputs [ " + inputs.size() + " ] weights[ " + this.weights.size() + " ] sizes don't match");
			return 0f; //error 
		}
		double result =0f;
		for(int i=0;i<this.weights.size();++i) {
			 result+= inputs.get(i) * this.weights.get(i);
		}
		result += this.bias;
		return activate(result);
	}
	
	@Override
	public double activate(double x){
		return sigmoid(x);
		//return Math.max(0, x);
		//return x;
	}
	
	
	public static double sigmoid(double x) {
	    return (double) (1.f / (1.f + (double)Math.exp(-x)));
	}
}
