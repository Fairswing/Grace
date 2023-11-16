

/*
 * 
 * 
 * neural network object
 * creates and manages a neural network.
 * 
 * 
 * */


// TO DO!!! not finished

package neuronBasicAI;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	private List<List<neuron>> layers;
	private float learning_rate;
	private float eps;
	private int nWeightsXNeuron;
	

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<neuron>>();
		this.learning_rate=1e-1f;
		this.eps=1e-3f;
	}
	
	public int getnWeightsXNeuron() {
		return nWeightsXNeuron;
	}

	public void setnWeightsXNeuron(int nWeightsXNeuron) {
		this.nWeightsXNeuron = nWeightsXNeuron;
	}
	public float getLearning_rate() {
		return learning_rate;
	}

	public void setLearning_rate(float learning_rate) {
		this.learning_rate = learning_rate;
	}
	
	public float getEps() {
		return eps;
	}

	public void setEps(float eps) {
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
	public void addNeuronToLayer(int layerIndex) {
		if(layerIndex == 0)
			this.layers.get(layerIndex).add(new neuron(this.nWeightsXNeuron));
		else
			this.layers.get(layerIndex).add(new neuron(this.layers.get(layerIndex - 1).size()));
	}
	
	// Assuming every neuron of a layer is connected with every neuron of the next layer
	public List<Float> forward(List<Float >inputs) {
	    List<Float> currentInputs = List.copyOf(inputs);  // Copy the initial inputs
	    for (List<neuron> layer : layers) {
	        List<Float> layerOutputs = new ArrayList<>();

	        for (neuron neuron : layer) {
	        	//System.out.println("CurrentInputs.size(): " + currentInputs.size());
	            float neuronOutput = neuron.calculate(currentInputs);
	            layerOutputs.add(neuronOutput);
	        }

	        // Update inputs for the next layer
	        currentInputs = List.copyOf(layerOutputs);
	    }
	    return currentInputs;// Return the output of the first (and only) neuron
	}
	
	
	// Calculate the average of the differences between the expected outputs and the actual output.
	public float cost(List<List<Float>> trainingData, List<List<Float>> outTrainingData) {
	    float result = 0.0f;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainingData.size(); ++i) {
	    	List<Float> output = forward(trainingData.get(i));
	        for (int j = 0; j < output.size(); ++j) {
	            float error = output.get(j) - outTrainingData.get(i).get(j);
	            result += error * error;// Used to emphasize errors, allowing the neural network to perceive them more easily.
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
	 * Using floating point variables we are limited to a minimum eps value of 1e-3f, 
	 * due to the limitations of floats.
	 * 
	 */
	public void train(List<List<Float>> trainingData, List<List<Float>> outTrainingData, int iterations) {
		for (int iteration = 0; iteration < iterations; ++iteration) {
		    // Backpropagation
		    float c = cost(trainingData,outTrainingData);// Save the cost funcion's result before changing the weights by eps.
		    
		    for (List<neuron> layer : this.layers) {
		        for (neuron currentNeuron : layer) {
		        	// Calculating the approximation of the derivative of the "cost" function with respect to the weight of every neuron. then modify the neuron's weights accordingly.
		            List<Float> curWeights=currentNeuron.getWeights();
		            for(int i=0;i<curWeights.size(); ++i) {
		            	float savedWeight=curWeights.get(i);// Adding and then subtracting eps would cause inconsistencies in the floats.
		            	curWeights.set(i, savedWeight+eps);
		            	float finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps); // FiniteDifference (approximation of the derivative of the cost function)
		            	curWeights.set(i, savedWeight-(this.learning_rate*finiteDiff));
		            }
		            // Do the same for the bias of each neuron.
		            float savedBias =currentNeuron.getBias();
		            currentNeuron.setBias(currentNeuron.getBias()+eps);
		            float finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps);
		            currentNeuron.setBias(savedBias);
		            currentNeuron.setBias(currentNeuron.getBias()-(this.learning_rate*finiteDiff));
		        }
		    }   
	        System.out.println("Iteration " + iteration + ", Cost: " + c);
		}
	}
}
