package neuronBasicAI;

public class test {
	
	public static void main(String[] args) {
		  neuron n1 = new neuron();
		  float n1Calc = 0;
		  float in;
		  float out;
		  float[] inputs = {1, 2, 3};
		  float[] outputs = {0.5f, 1, 1.5f};

		  for(int j = 0; j < 1000; j++) {
			  for(int i = 0; i < inputs.length; i++) {
				    in = inputs[i];
				    out = outputs[i];
				    n1Calc  = n1.calculate(in);
				    System.out.println("n1Calc(" + out + "): " + n1Calc);
				    n1.changeWeight((out - n1Calc), n1Calc);
				  }
		  }
		  
	}

}
