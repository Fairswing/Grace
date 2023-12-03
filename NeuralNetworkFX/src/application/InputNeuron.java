package application;
import java.util.ArrayList;
import java.util.List;

public class InputNeuron implements Neuron {
	private List<Double> weights; // Only used to take in input
	public InputNeuron(int nInputs) {
		super();
		this.weights = new ArrayList<Double>();
		for(int i=0;i<nInputs; ++i) {
			this.weights.add(1d); //fixed value.
		}
	}

	
	@Override
	public double getBias() {
		return (Double) null;
	}
	@Override
	public void setBias(double bias) {
	}
	@Override
	public double getWeight(int index) {
		return (Double) null;
	}
	@Override
	public List<Double> getWeights() {
		return this.weights;
	}
	@Override
	public void setWeights(List<Double> weights) {
	}
	
	//generalized version of the calculate function
	@Override
	public double calculate(List<Double> inputs) {
		double result =0f;
		for(int i=0;i<inputs.size();++i) {
			 result+= inputs.get(i);
		}
		return activate(result);
	}
	
	@Override
	public double activate(double x){
		//return sigmoid(x);
		//return Math.max(0, x);
		return x;
	}
	
	
	public static double sigmoid(double x) {
	    return (double) (1.f / (1.f + (double)Math.exp(-x)));
	}

}
