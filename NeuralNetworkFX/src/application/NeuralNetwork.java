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
	private double momentumFactor;// Rapresents how much of the momentum is retained

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<Neuron>>();
		this.learningRate=1e-1d;
		this.momentumFactor=0.0d;

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
	
	// Calculate the average of the cost of each training example.
	public double costAverage(List<List<Double>> trainingData, List<Double> outTrainingData) {
	    double result = 0.0d;
	    int trainCount = trainingData.size();
	    for (int i = 0; i < trainCount; ++i) {
	    	List<Double> output = forward(trainingData.get(i));
	    	result+=cost(output.get(0), outTrainingData.get(i));
        }
	    result /= (trainingData.size());
	    return result;
	}
	
	// cost function is used to calculate the difference between the output and the expected output.
	// The result is squared to penalize larger errors.
	// MSE
	public double cost(double output, double expectedOutput) {
		double error=0d;
			error = output - expectedOutput;
			error*=error;
        return error;
	}
	// cost derivative
	public double costDerivative(Double output, Double expectedOutput) {
		double error=0d;
			error = output - expectedOutput;
			error*=2;
        return error;
	}
	
	// Back propagation algorithm
	// to be finished!!!
	// i think gradients of one layer should be multiplyied by the gradients of the next one.
	// it will probably be necesary to keep track of every calculated gradient instead of adding them to the same variable.
	// check how the chain rule works.
	// Stochastic gradient descent could be implemented when these problems are sorted to improve performance.
	public void backPropagation(double expectedOutput) {
		double cOnAl=0d;
		double alOnZl=0d;
		double zlOnWl=0d;
		final double zlOnBl=1d;
		double weightGradient=0d;
		double biasGradient=0d;
		Neuron prevNeuron;
		
    	for (int i=this.layers.size()-1; i>0; --i) {
	    	for (Neuron currentNeuron : this.layers.get(i)) {
	            List<Double> curNeuronWeights = currentNeuron.getWeights();
	            for(int j=0;j<curNeuronWeights.size(); ++j) {
	    			cOnAl = costDerivative(currentNeuron.activate(currentNeuron.getOutput()),expectedOutput);
	    			alOnZl = currentNeuron.AFDerivative(currentNeuron.getOutput());
	    			prevNeuron = this.layers.get(i-1).get(j);
	    			zlOnWl = prevNeuron.activate(prevNeuron.getOutput());
	    			weightGradient=cOnAl*alOnZl*zlOnWl;
	    			currentNeuron.getWeightsGradient().set(j, currentNeuron.getWeightsGradient().get(j)+weightGradient);
	    		}
	            biasGradient = cOnAl*alOnZl*zlOnBl;
	            currentNeuron.setBiasGradient(currentNeuron.getBiasGradient()+biasGradient);
	    	}
    	}
        
	}
	
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
