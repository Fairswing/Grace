package application;

import java.util.List;

public interface Neuron {		
	public double getBias();

	public void setBias(double bias);

	public double getWeight(int index);

	public List<Double> getWeights();
	
	public void setWeights(List<Double> weights);
	
	//generalized version of the calculate function
	public double calculate(List<Double> inputs);
	
	
	// activation function:  adds bends and curvature to an otherwise linear output allowing it to capture and rapresent more complex patterns in the data.
	public double activate(double x);


}
