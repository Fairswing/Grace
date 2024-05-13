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
import java.util.Collections;
import java.util.List;


public class NeuralNetwork implements Serializable{

	private static final long serialVersionUID = 972856922459233840L;
	
	private List<List<Neuron>> layers;
	private double learningRate;
	private int nWeightsXNeuron;
	//private double momentumFactor; // Represents how much of the momentum is retained ( to be implemented)

	public NeuralNetwork() {
		super();
		this.layers = new ArrayList<List<Neuron>>();
		this.learningRate=0.5d;
		// this.momentumFactor=0.0d;	commenting the momentumFactori because we are not considering it now

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
	 * @return the output of the neural network
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
	public double lossDerivative(double output, double expectedOutput) {
		double error=0d;
			error = 2*(output - expectedOutput);
        return error;
	}
	
	/**
	 * This function is used to propagate the error of the output layer to all the hidden layers
	 * 
	 * @param expectedOutput the output that we expect from the neural network
	 */
	public void backPropagation(double expectedOutput) {
		ArrayList<List<Double>> curLayersInGradient = new ArrayList<List<Double>>();	// to store the current layer's gradients of the input which wil be used in the next layer to apply the chain rule
		for(int i = this.getLayers().size() - 1; i>0; --i) {
			List<Neuron> previousLayerNeurons = this.getLayers().get(i-1); // to save the neurons of the previous layer
			int neuronNumber = this.getLayers().get(i).size();	// the number of neurons preset in the layer
			
			// just for the output layer
			if(i == this.getLayers().size()-1) {	
				for(int j = 0; j<neuronNumber;++j) {
					Neuron currentNeuron=this.getLayers().get(i).get(j);
					double dLoss = lossDerivative(currentNeuron.activate(currentNeuron.getOutput()),expectedOutput); // derivative of the loss function
					double dActivationOnOutput = currentNeuron.AFDerivative(currentNeuron.getOutput());	// derivative of the activation function with the non activated output as input
					double delta = dLoss*dActivationOnOutput;	// chain rule on the partial derivatives calculated up to now. Delta is the same for every weight of a given neuron.
					curLayersInGradient.add(j, new ArrayList<Double>());
					for(int k = 0; k < currentNeuron.getWeights().size(); k++){
						Neuron previousNeuron = previousLayerNeurons.get(k);	// taking the previous neuron that gives the weight the input
						double weightGradient = delta * previousNeuron.activate(previousNeuron.getOutput());	// calculating the gradient using the derivative of l(S(Z))	
						currentNeuron.setWeightGradient(k, currentNeuron.getWeightGradient(k) + weightGradient);	// setting the weightGradient of the current neuron
						curLayersInGradient.get(j).add(0d);
						curLayersInGradient.get(j).set(k,curLayersInGradient.get(j).get(k) + (delta * currentNeuron.getWeight(k)));
					}
					double biasGradient = delta;
					currentNeuron.setBiasGradient(currentNeuron.getBiasGradient()+biasGradient);
				
				}
				
			}
			// just for the hidden layers
			else{
				ArrayList<List<Double>> prevLayersInGradient = new ArrayList<List<Double>>(curLayersInGradient);	// to store the previous layer's gradients of the input
				Collections.copy(prevLayersInGradient, curLayersInGradient);
				curLayersInGradient.clear();

				for(int j = 0; j<neuronNumber;++j) {
					Neuron currentNeuron=this.getLayers().get(i).get(j);
					double dActivationOnOutput = currentNeuron.AFDerivative(currentNeuron.getOutput());	// derivative of the activation function with the non activated output as input
					double prevLayerGradientSum = 0;
					for(int k=0; k< prevLayersInGradient.size(); ++k) {
						prevLayerGradientSum += prevLayersInGradient.get(k).get(j);	// considering the sum of the next layer input of the neuron considered
					}
					double delta = prevLayerGradientSum*dActivationOnOutput;	// chain rule on the partial derivatives calculated up to now. Delta is the same for every weight of a given neuron.
					curLayersInGradient.add(j, new ArrayList<Double>());
					for(int k = 0; k < currentNeuron.getWeights().size(); k++){
						Neuron previousNeuron = previousLayerNeurons.get(k);	// taking the previous neuron that gives the weight the input
						double weightGradient = delta * previousNeuron.activate(previousNeuron.getOutput());	// calculating the gradient using the derivative of l(S(Z))	
						currentNeuron.setWeightGradient(k, currentNeuron.getWeightGradient(k) + weightGradient);
						curLayersInGradient.get(j).add(0d);
						curLayersInGradient.get(j).set(k,curLayersInGradient.get(j).get(k) + (delta * currentNeuron.getWeight(k)));
					}
					double biasGradient = delta;
					currentNeuron.setBiasGradient(currentNeuron.getBiasGradient()+biasGradient);
				
				}
			}
		}
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
	                curNeuronWeightsGradients.set(j,0d);
	            }
	            currentNeuron.setBiasGradient(currentNeuron.getBiasGradient() / trainCount);
	            currentNeuron.setBias(currentNeuron.getBias() - (currentNeuron.getBiasGradient() * learningRate));
	            currentNeuron.setBiasGradient(0d);
	        }
	    }
	}
	
	/**
	 * This function trains the neural network
	 * 
	 * @param trainingData: the inputs of the inputs layer
	 * @param outTrainingData: the expected output
	 * @param miniBatchSize: >0 to use mini-batch gradient descent; ==0 to use stochastic gradient descent
	 */
	public void train(List<List<Double>> trainingData, List<Double> outTrainingData, int miniBatchSize) {
		int trainCount=trainingData.size();
		// Loop over training examples
	    for (int k = 0; k < trainCount; ++k) {
	        // Forward pass
	        forward(trainingData.get(k));
	        // Backword pass
	        backPropagation(outTrainingData.get(k));
	        if(miniBatchSize==0) {
	        	// Update weights and biases
	    	    updateWeightsAndBiases(1);
	        }else if(k%miniBatchSize==0){
	        	// Update weights and biases
	    	    updateWeightsAndBiases(miniBatchSize);
	        }
	    }
	}
	/**
	 * This function trains the neural network
	 * batch gradient descent
	 * 
	 * @param trainingData the inputs of the inputs layer
	 * @param outTrainingData the expected output
	 */
	public void train(List<List<Double>> trainingData, List<Double> outTrainingData) {
		int trainCount=trainingData.size();
		// Loop over training examples
	    for (int k = 0; k < trainCount; ++k) {
	        // Forward pass
	        forward(trainingData.get(k));
	        // Backword pass
	        backPropagation(outTrainingData.get(k));
	    }
	    // Update weights and biases
	    updateWeightsAndBiases(trainCount);
	}
	
	/**
	 * 
	 * This method is used to save the state of the neural network
	 * 
	 * @return void
	 */
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
	
	/**
	 * 
	 * This function load the state of the previous neural network
	 * 
	 * @return void
	 */
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
	
	/**
	 * 
	 * This function is used to make a trained neural network make calculated guesses on given inputs and debugging it
	 * 
	 * @param inputs The inputs that the neural network need to do the guessing on
	 * @param expectedOutputs The outputs that we expect from the neural network
	 * @return A list of all the calculated guess of the neural network
	 */
	public List<Double> nnGuessing(List<List<Double>> inputs, List<Double> expectedOutputs){
		List<Double> calculatedOutputGuess = null;
		if(NeuralNetwork.loadState() != null)
		{
			calculatedOutputGuess = new ArrayList<Double>();
			int inputsNumber = inputs.size();
			int wronGuess = 0;
			
			for (int k = 0; k < inputsNumber; ++k) {
		        // Forward pass to make the trained neural network guess the output
				calculatedOutputGuess.add(forward(inputs.get(k)).get(0));
		    }
			
			for(int k = 0; k < expectedOutputs.size(); k++) {
				if((Math.abs(calculatedOutputGuess.get(k)-expectedOutputs.get(k)) > 0.15)) {
					wronGuess++;
				}
			}
			
			for(int i = 0; i<calculatedOutputGuess.size(); i++) {
				System.out.print("\tExpected output: "+expectedOutputs.get(i).toString());
    	        System.out.print(" | Actual output: "+ calculatedOutputGuess.get(i).toString());
    	        System.out.println(" \tError: [ "+ Math.abs(expectedOutputs.get(i) - calculatedOutputGuess.get(i)) + " ]");
			}
			System.out.println(" \tThe percentage of error is: " + (double)wronGuess/expectedOutputs.size() * 100 + "%");
			
		} else
			System.out.println("Impossibile fare il guessing da una rete neurale non trainata");
		return calculatedOutputGuess;
	}
	
	/**
	 * 
	 * This function is used to make a trained neural network make calculated guesses on given inputs
	 * 
	 * @param inputs The inputs that the neural network need to do the guessing on
	 * @return A list of all the calculated guess of the neural network
	 */
	public List<List<Double>> nnGuessing(List<List<Double>> inputs){
		List<List<Double>> calculatedOutputGuess = null;
		if(NeuralNetwork.loadState() != null)
		{
			calculatedOutputGuess = new ArrayList<List<Double>>();
			int inputsNumber = inputs.size();
			
			for (int k = 0; k < inputsNumber; ++k) {
		        // Forward pass to make the trained neural network guess the output
				calculatedOutputGuess.add(forward(inputs.get(k)));
		    }
			
		} else
			System.out.println("Impossibile fare il guessing da una rete neurale non trainata");
		return calculatedOutputGuess;
	}

}