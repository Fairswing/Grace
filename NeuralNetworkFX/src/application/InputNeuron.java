package application;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputNeuron implements Neuron, Serializable {
	private static final long serialVersionUID = 2662866372940198955L;
	private List<Double> weights; // Only used to take in input
	private double output;

	public InputNeuron() {
		super();
		this.weights = new ArrayList<Double>();
		this.weights.add(1d); //fixed value.
	}

	
	@SuppressWarnings("null")	// warning suppressed because fully unfixable
	@Override
	public Double getBias() {
		return  null;
	}
	@Override
	public void setBias(double bias) {
	}
	@SuppressWarnings("null")	// warning suppressed because fully unfixable
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
		return x;
	}
	@Override
	public double AFDerivative(double x) {
		return x;
	}
	
	public static double sigmoid(double x) {
	    return (double) (1.f / (1.f + (double)Math.exp(-x)));
	}

	public void setOutput(double output) {
		this.output = output;
	}
	
	@Override
	public double getOutput() {
		// TODO Auto-generated method stub
		return this.output;
	}


	

}
