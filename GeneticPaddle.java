import java.util.Random;

public class GeneticPaddle extends Paddle {
	private Random random;
    private int currentDNA;
	private double[] inputs;
	private double[][] weights;//[DNA][weight]
    private double[] bounces;
	
	public GeneticPaddle(Random random, int numDNA) {
		this(0.5, 0.3, 1, random, numDNA);
	}
	
	public GeneticPaddle(double position, double width, 
			double speed, Random random, int numDNA) {
		super(position, width, speed);
		this.random = random;
		inputs = new double[2];
        //allocate the dna array
        weights = new double[numDNA];
        //allocate each dna's weight array
        for(int i = 0; i < numDNA; i++){
            weights[i] = new double[2];
            //initialize the dna's weight array
            for (int j = 0; j < weights.length; j++) {
                weights[j] = 2 * random.nextDouble() - 1;
            }
        }
        //allocate and initialize the bounces array
        bounces = new double[numDNA];
        for(int i = 0; i < numDNA; i++){
            bounces[i] = 0;
        }
	}
	
	public double getMove(Ball ball) {
		inputs = new double[] { position, ball.yPosition };
		
		return inputs[0] * weights[currentDNA][0] + inputs[1] * weights[currentDNA][1];
	}
	
	public void sendPain() {
        //do some stuff
        //currentDNA++
        if(currentDNA == weights.length){
            //pick the best dna and generate decendents
            bounces[i] = 0
        }
		//for (int i = 0; i < weights.length; i++) {
			//weights[i] = (2 * weights[i] + 2 * random.nextDouble() - 1) / 3;
		//}
	}

	public void bounce(Ball ball, Random random) {
		double angle = Math.PI / 3 * 
			(ball.yPosition - position) / (width / 2);
		angle = angle + (2 * random.nextDouble() - 0.5) / 10;
		ball.xVelocity = ball.speed * Math.cos(angle);
		ball.yVelocity = ball.speed * Math.sin(angle);
        bounces[currentDNA]++;
	}
	
	public void printWeights() {
		for (int i = 0; i < weights.length; i++) {
			System.out.format("%.2f ", weights[i]);
		}
		System.out.println();
	}
}
