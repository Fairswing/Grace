

/*
 * 
 * 
 * neural network object
 * creates and manages a neural network.
 * 
 * 
 * */


// TO DO!!! not finished

package application;
import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork {
	private List<List<Neuron>> layers;
	private double learning_rate;
	private double eps;
	private int nWeightsXNeuron;
	private double momentumFactor;// Rapresents how much of the momentum is retained

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<Neuron>>();
		this.learning_rate=1d;
		this.eps=1e-1d;
		this.momentumFactor=0.0d;
	}
	
	public int getnWeightsXNeuron() {
		return nWeightsXNeuron;
	}

	public void setnWeightsXNeuron(int nWeightsXNeuron) {
		this.nWeightsXNeuron = nWeightsXNeuron;
	}
	public double getLearning_rate() {
		return learning_rate;
	}

	public void setLearning_rate(double learning_rate) {
		this.learning_rate = learning_rate;
	}
	
	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}
	
	public List<List<Neuron>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<Neuron>> layers) {
		this.layers = layers;
	}

	
	
	public void addLayer() {
		this.layers.add(new ArrayList<Neuron>());
	}
	
	public void addLayer(int nNeurons, String activationFunction) {
		this.layers.add(new ArrayList<Neuron>());
		for(int i=0;i<nNeurons;i++) {
			this.addNeuronToLayer(layers.size()-1, activationFunction);
		}
	}
	public void addLayer(int nNeurons) {
		this.layers.add(new ArrayList<Neuron>());
		for(int i=0;i<nNeurons;i++) {
			this.addNeuronToLayer(layers.size()-1, null);
		}
	}
	
	public void addNeuronToLayer(int layerIndex, String activationFunction) {
		if(layerIndex == 0)
			this.layers.get(layerIndex).add(new InputNeuron(1));
		else
			this.layers.get(layerIndex).add(new RegularNeuron(this.layers.get(layerIndex - 1).size(), activationFunction));
	}
	
	
	public List<Double> forward(List<Double> inputs) {
		List<Double> currentInputs = new ArrayList<>(inputs);
	    double neuronOutput;
	    List<Double> neuronInputs;
	    for (List<Neuron> layer : this.layers) {
	        List<Double> layerOutputs = new ArrayList<>();
	        if(this.layers.indexOf(layer)==0) {
		        int neuronIndex = 0;
				for (Neuron neuron : layer) {
			    	// Extract the inputs for the current neuron
			        neuronInputs = currentInputs.subList(neuronIndex, neuronIndex + neuron.getWeights().size());
			        neuronOutput = neuron.calculate(neuronInputs);
			        layerOutputs.add(neuronOutput);
			        neuronIndex=neuronIndex+neuron.getWeights().size()%currentInputs.size();
		        }	
	        }else {
	        	for (Neuron neuron : layer) {
			        neuronOutput = neuron.calculate(currentInputs);
			        layerOutputs.add(neuronOutput);
			        neuron.setOutput(neuronOutput);
		        }	   
	        }
	        // Update inputs for the next layer
	        currentInputs = layerOutputs;
	    }
	    return currentInputs; // Return the output of the last layer
	}
	
	// Calculate the average of the differences between the expected outputs and the actual output.
	public double cost(List<List<Double>> trainingData, List<Double> outTrainingData) {//outTrainingData da rendere List<List<Double>> in caso ci si aspetti più output dalla rete neurale !!!
	    double result = 0.0d;
	    double errorPow = 0.0d;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainCount; ++i) {
	    	List<Double> output = forward(trainingData.get(i));
	        for (int j = 0; j < output.size(); ++j) {
	            double error = output.get(j) - outTrainingData.get(i);
	            errorPow = error * error;
	            result += errorPow;
	        }
        }
	    result /= (2*trainCount);
	    return result;
	}

	
	/*
	 * 
	 * 
	 * To visualize things let's imagine the cost funciton as a graph.
	 * Cost(x) is a single point in the graph, cost(x+eps) is a point really close to cost(x).
	 * So the finite difference draws a line between the two points and returns 
	 * the line's slope effectively approximating the derivative  of the cost function.
	 * so the smaller the eps, the more accurate this approximation will be.
	 * Using doubleing point variables we are limited to a minimum eps value, 
	 * due to the limitations of doubles.
	 * 
	 */
	private double prevVt=0d;
	private double vt = 0d;
	private double savedWeight;
	private double finiteDiff;
	private double savedBias;
	public void train(List<List<Double>> trainingData, List<Double> outTrainingData) {
	    // Forward pass.
	    double c = cost(trainingData,outTrainingData);// Save the cost funcion's result before changing the weights by eps.
	    
	    // Backpropagation
	    for (int i=this.layers.size()-1; i>0; --i) {
	    //for (int i=1; i<this.layers.size(); ++i) {
	    	for (Neuron currentNeuron : this.layers.get(i)) {
	            // Calculating the approximation of the derivative of the "cost" function with respect to the weight of every neuron. then modify the neuron's weights accordingly.
	            List<Double> curWeights = currentNeuron.getWeights();
	            double output = currentNeuron.getOutput(); // get the output of the neuron
	            double derivative = output * (1 - output); // calculate the derivative of the activation function
	            for(int j=0;j<curWeights.size(); ++j) {
	                savedWeight = curWeights.get(j);
	                curWeights.set(j, savedWeight+eps);
	                finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps) * derivative; // multiply the finite difference by the derivative of the activation function
	                prevVt = vt;
	                vt = (momentumFactor * prevVt) + (learning_rate * finiteDiff);
	                curWeights.set(j, savedWeight-vt);
	            }
	            // Do the same for the bias of each neuron.
	            savedBias =currentNeuron.getBias();
	            currentNeuron.setBias(currentNeuron.getBias()+eps);
	            finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps) * derivative; // multiply the finite difference by the derivative of the activation function
	            currentNeuron.setBias(savedBias);
	            
	            prevVt = vt;
	            vt = (momentumFactor * prevVt) + (learning_rate * finiteDiff);
	            currentNeuron.setBias(currentNeuron.getBias() - vt);
	            
	        }
	    }
	}

}
