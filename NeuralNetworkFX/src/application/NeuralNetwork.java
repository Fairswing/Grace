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
		this.learningRate=1e-5d;
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
			error = (output - expectedOutput);
			error = Math.pow(error, 2);
        return error/2;
	}
	// cost derivative
	public double costDerivative(Double output, Double expectedOutput) {
		double error=0d;
			error = output - expectedOutput;
        return error;
	}
	
	/* Back propagation algorithm
	// to be finished!!!
	// i think gradients of one layer should be multiplyied by the gradients of the next one.
	// it will probably be necesary to keep track of every calculated gradient instead of adding them to the same variable.
	// check how the chain rule works.
	// Stochastic gradient descent could be implemented when these problems are sorted to improve performance.*/
	public void backPropagation(double expectedOutput) {
		double cOnAl=0d;	// 2(aL-y) how much the activation of the current neuron and layer influence Cost
		double alOnZl=0d;	// activationF'(output(L)) derivative of the activation function of the current neuron and layer
		double zlOnWl=0d;	// a(L-1) how much Zl changes based on the change of weight
		// double zlOnBl=1d;	// how much zl changed base on the bias
		double weightGradient=0d;
		double biasGradient=0d;
		Neuron prevNeuron;
		
		// saving the gradients of the last layer
		for (Neuron currentNeuron : this.layers.get(layers.size()-1)) {	// iterating trough the neurons of the current layer
            List<Double> curNeuronWeights = currentNeuron.getWeights();	// getting the current neuron weights
            cOnAl = costDerivative(currentNeuron.activate(currentNeuron.getOutput()),expectedOutput);
			alOnZl = currentNeuron.AFDerivative(currentNeuron.getOutput());
            currentNeuron.setDelta(cOnAl*alOnZl);	// optimizing function calculating delta one time for every neuron weight
            for(int j=0;j<curNeuronWeights.size(); ++j) {
    			for(int k = 0; k < this.layers.get(layers.size()-2).size(); k++) {
    				prevNeuron = this.layers.get(layers.size()-2).get(k);
    				zlOnWl = prevNeuron.activate(prevNeuron.getOutput());
    				
    				// multiplying the current neuron delta by the activated output of the neuron of the previous layer
    				weightGradient += currentNeuron.getDelta() * zlOnWl;
    			}
    			currentNeuron.getWeightsGradient().set(j, currentNeuron.getWeightsGradient().get(j)+weightGradient);
    		}
            biasGradient = currentNeuron.getDelta();
            currentNeuron.setBiasGradient(currentNeuron.getBiasGradient()+biasGradient);
    	}
		
		// iterating trough the others layers
    	for (int i=this.layers.size()-2; i>0; --i) {	// moving trough all the layers from the last one to the first one
	    	for (Neuron currentNeuron : this.layers.get(i)) {	// iterating trough the neurons of the current layer
	    		weightGradient = 0d;
	            List<Double> curNeuronWeights = currentNeuron.getWeights();	// getting the current neuron weights
	            double delta = 0;
	            for(int j=0;j<curNeuronWeights.size(); ++j) {
	    			
	    			// calculating delta for each neuron in the next layer
	    			for(int k = 0; k < this.layers.get(i+1).size(); k++) {
	    				double prevNeuronDelta = this.layers.get(i+1).get(k).getDelta();
	    				double prevNeuronWeight = this.layers.get(i+1).get(k).getWeight(this.layers.get(i).indexOf(currentNeuron));
	    				double sigmoidDerivative = currentNeuron.AFDerivative(currentNeuron.getOutput());
	    				delta += prevNeuronDelta * prevNeuronWeight * sigmoidDerivative;
	    			}	
	    			currentNeuron.setDelta(delta);	// saving the delta of the current neuron
	    			
	    			// calculating the gradient for the current neuron considering the activated output of each neuron 
	    			//	of the previous layer
	    			for(int k = 0; k < this.layers.get(i-1).size(); k++) {
	    				prevNeuron = this.layers.get(i-1).get(k);
		    			zlOnWl = prevNeuron.activate(prevNeuron.getOutput());
	    				
	    				// multiplying the current neuron delta by the activated output of the neuron of the previous layer
	    				weightGradient += currentNeuron.getDelta() * zlOnWl;
	    			}
	    			currentNeuron.getWeightsGradient().set(j, currentNeuron.getWeightsGradient().get(j)+weightGradient);
	    		}
	            biasGradient = currentNeuron.getDelta();
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
