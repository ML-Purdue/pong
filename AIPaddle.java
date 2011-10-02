import java.util.Random;

public class AIPaddle extends Paddle {
	private Random random;
	private double[] inputs;
	private double[] weights;
	
	public AIPaddle(Random random) {
		this(0.5, 0.3, 1, random);
	}
	
	public AIPaddle(double width, double speed, Random random) {
		this(0.5, width, speed, random);
	}
	
	public AIPaddle(double width, Random random) {
		this(0.5, width, 1, random);
	}
	
	public AIPaddle(double position, double width, 
			double speed, Random random) {
		super(position, width, speed);
		this.width = width;
		this.random = random;
		inputs = new double[2];
		weights = new double[2];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 2 * random.nextDouble() - 1;
		}
	}
	
	public double getMove(Ball ball) {
		inputs = new double[] { position, ball.yPosition };
		
		return inputs[0] * weights[0] + inputs[1] * weights[1];
	}
	
	public void sendPain() {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = (2 * weights[i] + 2 * random.nextDouble() - 1) / 3;
		}
	}
	
	public void printWeights() {
		for (int i = 0; i < weights.length; i++) {
			System.out.format("%.2f ", weights[i]);
		}
		System.out.println();
	}
}
