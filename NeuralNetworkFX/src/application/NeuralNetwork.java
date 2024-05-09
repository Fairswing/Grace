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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork implements Serializable{

	private static final long serialVersionUID = 972856922459233840L;
	
	private List<List<Neuron>> layers;
	private double learningRate;
	private int nWeightsXNeuron;
	//private double momentumFactor;// Rapresents how much of the momentum is retained

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<Neuron>>();
		this.learningRate=1d;
//		this.momentumFactor=0.0d;

	}
	
	public int getnWeightsXNeuron() {
		return nWeightsXNeuron;
	}

	public void setnWeightsXNeuron(int nWeightsXNeuron) {
		this.nWeightsXNeuron = nWeightsXNeuron;
	}
	public double getLearning_rate() {
		return learningRate;
	}

	public void setLearning_rate(double learningRate) {
		this.learningRate = learningRate;
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
			this.layers.get(layerIndex).add(new InputNeuron());
		else
			this.layers.get(layerIndex).add(new RegularNeuron(this.layers.get(layerIndex - 1).size(), activationFunction));
	}
	
	/**
	 * 
	 * @param inputs a list of all the inputs to give to the inputs layer
	 * @return the inputs for the next layers
	 */
	public List<Double> forward(List<Double> inputs) {
		List<Double> currentInputs = new ArrayList<>(inputs);
	    double neuronOutput;
	    List<Double> neuronInputs;
	    for (List<Neuron> layer : this.layers) {
	    	List<Double> layerOutputs = new ArrayList<>();
	        if(this.layers.indexOf(layer)==0) {
		        int neuronIndex = 0;
				for (Neuron neuron : layer) {
					// Just for the input layer every neuron gets one input each.
			    	// Extract the inputs for the current neuron
					neuronInputs = List.of(currentInputs.get(neuronIndex));
					neuronOutput = neuron.calculate(neuronInputs);
			        layerOutputs.add(neuronOutput);
					neuronIndex++;
		        }	
	        }else {
	        	for (Neuron neuron : layer) {
	        		// other layer's neurons get every previous neuron's output as input each.
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
	
	/**
	 * 
	 * 
	 * @param trainingData a list of all the training data
	 * @param outTrainingData a list of all the expected output
	 * @return an average of all the distances from the expected output and the actual output ( loss)
	 */
	public double lossAverage(List<List<Double>> trainingData, List<Double> outTrainingData) {
	    double result = 0.0d;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainCount; ++i) {
	    	List<Double> output = forward(trainingData.get(i));
	    	result+=loss(output.get(0), outTrainingData.get(i));
        }
	    result /= (trainingData.size());
	    return result;
	}
	
	/**
	 * This function calculate the value of the loss
	 * 
	 * @param output the output of the nn
	 * @param expectedOutput the output that we expect from the nn
	 * @return loss the loss value
	 */
	public double loss(double output, double expectedOutput) {
		double error=0d;
			error = (output - expectedOutput);
			error = Math.pow(error, 2);
        return error;
	}
	
	/**
	 * This function calculate the derivative of the loss function in the point x(weight)
	 * 
	 * @param x the point in x in the function loss
	 * @return the derivative of the loss(x)
	 */
	public double lossDerivative(double weight) {
		double error=0d;
			error = 2*weight;
        return error;
	}
	
	/**
	 * This function is used to propagate the error of the output layer to all the hidden layers
	 * 
	 * @param expectedOutput the output that we expect from the neural network
	 */
	public void backPropagation(double expectedOutput) {
		
        
	}
	
	/**
	 * This function is used to update weight and biases using each gradient calculated with the chainrule
	 * 
	 * @param trainCount the number of the train iterations
	 */
	private void updateWeightsAndBiases(int trainCount) {
	    // Update weights and biases
	    for (int i = 1; i < this.layers.size(); ++i) {
	        for (Neuron currentNeuron : this.layers.get(i)) {
	            List<Double> curNeuronWeights = currentNeuron.getWeights();
	            List<Double> curNeuronWeightsGradients = currentNeuron.getWeightsGradient();
	            for (int j = 0; j < curNeuronWeights.size(); ++j) {
	                curNeuronWeightsGradients.set(j, curNeuronWeightsGradients.get(j) / trainCount);
	                curNeuronWeights.set(j, curNeuronWeights.get(j) - (curNeuronWeightsGradients.get(j) * learningRate));
	            }
	            currentNeuron.setBiasGradient(currentNeuron.getBiasGradient() / trainCount);
	            currentNeuron.setBias(currentNeuron.getBias() - (currentNeuron.getBiasGradient() * learningRate));
	        }
	    }
	}
	
	/**
	 * This function train the neural network
	 * 
	 * @param trainingData the inputs of the inputs layer
	 * @param outTrainingData the expected output
	 */
	public void train(List<List<Double>> trainingData, List<Double> outTrainingData) {
		int trainCount=trainingData.size();
		// Loop over training examples
	    for (int i = 0; i < trainCount; ++i) {
	        // Forward pass
	        forward(trainingData.get(i));
	        // Backword pass
	        backPropagation(outTrainingData.get(i));
	    }
	    // Update weights and biases
	    updateWeightsAndBiases(trainCount);
	    
	    for(int i = 1; i < this.layers.size(); i++) {
	    	for(Neuron currentNeuron : this.layers.get(i)) {
	    		for(int j = 0; j < currentNeuron.getWeightsGradient().size(); j++) {
	    			currentNeuron.getWeightsGradient().set(j, 0d);
	    			currentNeuron.setBiasGradient(0d);
	    		}
	    	}
	    }
	}
	
	
	public boolean saveState() {
		boolean saved = false;
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedNN.dat"));
			
			// writing the object to savedNN.dat and the closing the oos
			oos.writeObject(this);
			oos.close();
			
			// setting the saved value to true
			saved = true;
			
			// printing the completion of the save
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		return saved;
	}
	
	public static NeuralNetwork loadState(){
		
		NeuralNetwork loadedNN = null;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savedNN.dat"));
				
			loadedNN = (NeuralNetwork) ois.readObject();	// reading the serialize NN
			
			ois.close();	// closing the input stream
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return loadedNN;
	}

}
