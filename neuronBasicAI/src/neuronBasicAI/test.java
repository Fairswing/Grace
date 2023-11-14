package neuronBasicAI;

public class test {
	
	public static void main(String[] args) {
		neuron n1 = new neuron();
		float n1Calc = 0;
		float in = 1f;
		float out = 10f;
		
		while((out + 0.05) > n1Calc && (out - 0.05) < n1Calc || n1Calc != out) {
			n1Calc  = n1.calculate(in);
			System.out.println("n1Calc: " + n1Calc);
		}
	}

}
