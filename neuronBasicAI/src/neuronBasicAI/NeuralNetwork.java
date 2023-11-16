

/*
 * 
 * neural network object
 * creates and manages a neural network.
 * 
 * 
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
	private int nWeightsXNeuron;

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<neuron>>();
		this.learning_rate=1e-4f;
	}
	
	public int getnWeightsXNeuron() {
		return nWeightsXNeuron;
	}

	public void setnWeightsXNeuron(int nWeightsXNeuron) {
		this.nWeightsXNeuron = nWeightsXNeuron;
	}
	
	public void addLayer() {
		this.layers.add(new ArrayList<neuron>());
	}
	public void addNeuronToLayer(int layerIndex) {
		if(layerIndex == 0)
			this.layers.get(layerIndex).add(new neuron(this.nWeightsXNeuron,this.learning_rate));
		else
			this.layers.get(layerIndex).add(new neuron(this.layers.get(layerIndex - 1).size(),this.learning_rate));
	}
	
	public float getLearning_rate() {
		return learning_rate;
	}

	public void setLearning_rate(float learning_rate) {
		this.learning_rate = learning_rate;
	}

	
	// Assuming every neuron of a layer is connected with every neuron of the next layer
	public List<Float> forward(List<Float >inputs) {
	    List<Float> currentInputs = List.copyOf(inputs);  // Copy the initial inputs
	    //System.out.println("inputs: " + currentInputs.toString());
	    //System.out.println("CurrentInputs.size(): " + currentInputs.size());
	  
	    for (List<neuron> layer : layers) {
	        List<Float> layerOutputs = new ArrayList<>();

	        for (neuron neuron : layer) {
	        	//System.out.println("CurrentInputs.size(): " + currentInputs.size());
	            float neuronOutput = neuron.calculate(currentInputs);
	            layerOutputs.add(neuronOutput);
	        }
            System.out.println("output: " + layerOutputs.toString());


	        // Update inputs for the next layer
	        currentInputs = List.copyOf(layerOutputs);
	    }
        //System.out.println("inputs: " + currentInputs.toString());
	    return currentInputs;// Return the output of the first (and only) neuron
	}
	
	
	public float cost(List<List<Float>> trainingData) {
	    float result = 0.0f;
	    int trainCount = trainingData.size();

	    for (List<Float> train : trainingData) {
	        List<Float> inputs = new ArrayList<>(train.subList(0, train.size() - 1));// Get all the inputs and skip the expected output
	        List<Float> expectedOutputs = new ArrayList<>(train.subList(train.size() - 1, train.size()));

	        List<Float> output = forward(inputs); // Calculate the output using the forward method

	        for (int i = 0; i < output.size(); ++i) {
	            float error = output.get(i) - expectedOutputs.get(i);
	            result += error * error;
	        }
	    }

	    result /= trainCount;
	    return result;
	}

	
	public void train(List<List<Float>> trainingData, List<List<Float>> outTrainingData, int iterations) {
	    for (int iteration = 0; iteration < iterations; ++iteration) {
	        float totalCost = 0.0f;

	        for (int trainingExample = 0; trainingExample < trainingData.size(); ++trainingExample) {
	            float instanceCost = 0.0f;
	            List<Float> inputs = new ArrayList<>(trainingData.get(trainingExample));
	            List<Float> expectedOutputs = new ArrayList<>(outTrainingData.get(trainingExample));

	            // Forward pass to get the network output
	            List<Float> output = forward(inputs);

	            // Backpropagation
	            float error = 0f;

	            for (int i = 0; i < this.layers.size(); ++i) {
	                List<neuron> layer = this.layers.get(i);

	                for (int j = 0; j < layer.size(); ++j) {
	                    neuron currentNeuron = layer.get(j);

	                    for (int k = 0; k < expectedOutputs.size(); ++k) {
	                        error = expectedOutputs.get(k) - output.get(k);
	                        
	                        //calculate and update weights
	                        currentNeuron.changeWeight(error, output.get(k));
	                    }
	                    System.out.println("error:" + error);
	                }
	            }

	            // Calculate cost for monitoring purposes
	            for (int i = 0; i < output.size(); ++i) {
	                error = output.get(i) - expectedOutputs.get(i);
	                instanceCost += error * error;
	            }

	            totalCost += instanceCost;
	        }

	        // Calculate average cost for the epoch
	        float averageCost = totalCost / trainingData.size();

	        // Optionally print or log the cost for each iteration
	        System.out.println("Iteration " + iteration + ", Cost: " + averageCost);
	    }
	}
}
