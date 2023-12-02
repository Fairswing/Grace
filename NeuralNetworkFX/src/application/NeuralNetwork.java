

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
	private List<List<neuron>> layers;
	private double learning_rate;
	private double eps;
	private int nWeightsXNeuron;
	private double momentumFactor;// Rapresents how much of the momentum is retained

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<neuron>>();
		this.learning_rate=1f;
		this.eps=1e-1f;
		this.momentumFactor=0.6f;
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
	
	public List<List<neuron>> getLayers() {
		return layers;
	}

	public void setLayers(List<List<neuron>> layers) {
		this.layers = layers;
	}

	
	
	public void addLayer() {
		this.layers.add(new ArrayList<neuron>());
	}
	public void addLayer(int nNeurons) {
		this.layers.add(new ArrayList<neuron>());
		for(int i=0;i<nNeurons;i++) {
			this.addNeuronToLayer(layers.size()-1);
		}
	}
	public void addNeuronToLayer(int layerIndex) {
		if(layerIndex == 0)
			this.layers.get(layerIndex).add(new neuron(this.nWeightsXNeuron));
		else
			this.layers.get(layerIndex).add(new neuron(this.layers.get(layerIndex - 1).size()));
	}

	public List<Double> forward(List<Double> inputs) {
	    List<Double> currentInputs = List.copyOf(inputs);
	    
	    double neuronOutput;
	    List<Double> neuronInputs;
	    
	    for (List<neuron> layer : this.layers) {
	        List<Double> layerOutputs = new ArrayList<>();
	        int neuronIndex = 0;
	        for (neuron neuron : layer) {
	        	if(layers.indexOf(layer)==0) {
	        		// Extract the inputs for the current neuron
		            neuronInputs = currentInputs.subList(neuronIndex, neuronIndex + neuron.getWeights().size());

		            layerOutputs.addAll(neuronInputs);
	        	}else {
	        		// Calculate the output of the neuron
		            neuronOutput = neuron.calculate(currentInputs);
		            layerOutputs.add(neuronOutput);
	        	}
	        }

	        // Update inputs for the next layer
	        currentInputs = List.copyOf(layerOutputs);
	    }
	    return currentInputs; // Return the output of the last layer
	}
		
	// Calculate the average of the differences between the expected outputs and the actual output.
	public double cost(List<List<Double>> trainingData, List<Double> outTrainingData) {
	    double result = 0.0f;
	    double errorPow = 0.0f;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainCount; ++i) {
	    	List<Double> output = forward(trainingData.get(i));
	        for (int j = 0; j < output.size(); ++j) {
	            double error = output.get(j) - outTrainingData.get(i);
	            errorPow = error * error;// Used to emphasize errors, allowing the neural network to perceive them more easily.
	            //result += errorPow * ( neuron.activationFunction(output.get(j)+eps)- neuron.activationFunction(output.get(j)) )/eps; //da rivedere
	            result += errorPow;
	        }
        }
	    result /= trainCount;
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
	private double prevVt=0;
	private double vt = 0;
	private double savedWeight;
	private double finiteDiff;
	private double savedBias;
	public void train(List<List<Double>> trainingData, List<Double> outTrainingData) {
	    // Backpropagation
	    double c = cost(trainingData,outTrainingData);// Save the cost funcion's result before changing the weights by eps.
	    for (List<neuron> layer : this.layers) {
	    	if(layers.get(0)!=layer) {
		        for (neuron currentNeuron : layer) {
		        	// Calculating the approximation of the derivative of the "cost" function with respect to the weight of every neuron. then modify the neuron's weights accordingly.
		            List<Double> curWeights = currentNeuron.getWeights();
		            for(int i=0;i<curWeights.size(); ++i) {
		            	savedWeight = curWeights.get(i);// Adding and then subtracting eps would cause inconsistencies in the doubles.
		            	curWeights.set(i, savedWeight+eps);
		            	finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps); // FiniteDifference (approximation of the derivative of the cost function)
		            	prevVt = vt;
			            vt = (momentumFactor * prevVt) + (learning_rate * finiteDiff);
		            	curWeights.set(i, savedWeight-vt);
		            }
		            // Do the same for the bias of each neuron.
		            savedBias =currentNeuron.getBias();
		            currentNeuron.setBias(currentNeuron.getBias()+eps);
		            finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps);
		            currentNeuron.setBias(savedBias);
		            
		             //* momentum is used to dynamically modify the learning rate 
		             //* https://collab.dvb.bayern/display/TUMlfdv/Adaptive+Learning+Rate+Method#AdaptiveLearningRateMethod-AdaptiveLearningRateMethod
		             //* 
		            prevVt = vt;
		            vt = (momentumFactor * prevVt) + (learning_rate * finiteDiff);
	
		            currentNeuron.setBias(currentNeuron.getBias() - vt);
		        } 
	    	}
	    	
		}
	}
}
