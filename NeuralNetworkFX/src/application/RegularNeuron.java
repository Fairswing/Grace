package application;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class RegularNeuron implements Neuron, Serializable{
	
	private static final long serialVersionUID = -6524856887066826088L;
	
	private List<Double> weights;
	private double bias;
	private double output;
	private String activationFunction;
	
	public RegularNeuron(int nWeights, String activationFunction) {
		super();
		this.weights = new ArrayList<Double>();
		for(int i=0;i<nWeights; ++i) {
			Random rand = new Random();
			this.weights.add((double)rand.nextGaussian() * Math.sqrt(2.0 / nWeights));// randomize the initial value of the weight
		}
		this.bias = (double)Math.random(); // should be randomly initialized like weights
		this.activationFunction=activationFunction;
	}

	
	
	public Double getBias() {
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
	
	//aggiunta poissibilità di scegliere che tipo di funzione di attivazione usare.
	@Override
	public double activate(double x){
		if(activationFunction==null) {
			return x;
		}
		if(activationFunction.contentEquals("sigmoid")) {
			return sigmoid(x);
		}
		if(activationFunction.contentEquals("relu")) {
			return relu(x);
		}
		return x;
	}
	public double AFDerivative(double x) {
		if(activationFunction==null) {
			return x;
		}
		if(activationFunction.contentEquals("sigmoid")) {
			return sigmoidDerivative(x);
		}
		if(activationFunction.contentEquals("relu")) {
			return reluDerivative(x);
		}
		return x;
	}
	
	public static double sigmoid(double x) {
	    return (double) (1.f / (1.f + (double)Math.exp(-x)));
	}
	public static double sigmoidDerivative(double x) {
	    return (double) x * (1 - x);
	}
	
	
	public static double relu(double x) {
	    return Math.max(0,x);
	}
	public static double reluDerivative(double x) {
        return x > 0 ? 1 : 0;
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
