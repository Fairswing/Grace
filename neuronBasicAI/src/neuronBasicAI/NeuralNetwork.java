

/*
 * 
 * 
 * neural network object
 * creates and manages a neural network.
 * 
 * 
 * */
package neuronBasicAI;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	private List<List<neuron>> layers;
	private float learning_rate;
	private float eps;
	private int nWeightsXNeuron;
	private float momentumFactor;// rapresents how much of the momentum is retained
	private float prevVt;

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<neuron>>();
		this.learning_rate=1e-1f;
		this.eps=1e-1f;
		this.momentumFactor=0.9f;
		this.prevVt=0;
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
	
	public List<Float> forward(List<List<Float>> inputs) {
	    List<Float> currentInputs = new ArrayList<>();
		
	    // Flatten the input list
	    for (List<Float> inputList : inputs) {
	        currentInputs.addAll(inputList);
	    }

	    for (List<neuron> layer : this.layers) {
	        List<Float> layerOutputs = new ArrayList<>();
	        int neuronIndex = 0;
	        for (neuron neuron : layer) {
	            // Extract the inputs for the current neuron
	            List<Float> neuronInputs = currentInputs.subList(neuronIndex, neuronIndex + neuron.getWeights().size());
	            
	            // Calculate the output of the neuron
	            float neuronOutput = neuron.calculate(neuronInputs);
	            layerOutputs.add(neuronOutput);
	            neuronIndex =(neuronIndex + neuron.getWeights().size())%currentInputs.size();
	        }

	        // Update inputs for the next layer
	        currentInputs = List.copyOf(layerOutputs);
	    }

	    return currentInputs; // Return the output of the last layer
	}
	
	
	// Calculate the average of the differences between the expected outputs and the actual output.
	public float cost(List<List<Float>> trainingData, List<Float> outTrainingData) {
	    float result = 0.0f;
	    float errorPow = 0.0f;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainingData.size(); ++i) {
	    	List<Float> output = forward(trainingData);
	        for (int j = 0; j < output.size(); ++j) {
	            float error = output.get(j) - outTrainingData.get(j);
	            errorPow = error * error;// Used to emphasize errors, allowing the neural network to perceive them more easily.
	            result += errorPow * ( neuron.activationFunction(output.get(j)+eps)- neuron.activationFunction(output.get(j)) )/eps;
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
	 * Using floating point variables we are limited to a minimum eps value, 
	 * due to the limitations of floats.
	 * 
	 */
	
	private float vt = 0;
	private float savedWeight;
	private float finiteDiff;
	private float savedBias;
	public void train(List<List<Float>> trainingData, List<Float> outTrainingData) {
	    // Backpropagation
	    float c = cost(trainingData,outTrainingData);// Save the cost funcion's result before changing the weights by eps.
	    for (List<neuron> layer : this.layers) {
	    	if(layers.get(0)!=layer) {
		        for (neuron currentNeuron : layer) {
		        	// Calculating the approximation of the derivative of the "cost" function with respect to the weight of every neuron. then modify the neuron's weights accordingly.
		            List<Float> curWeights = currentNeuron.getWeights();
		            for(int i=0;i<curWeights.size(); ++i) {
		            	savedWeight = curWeights.get(i);// Adding and then subtracting eps would cause inconsistencies in the floats.
		            	curWeights.set(i, savedWeight+eps);
		            	finiteDiff = ((cost(trainingData,outTrainingData)-c)/eps); // FiniteDifference (approximation of the derivative of the cost function)
		            	curWeights.set(i, savedWeight-(this.learning_rate*finiteDiff));
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
