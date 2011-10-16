import java.util.Random;

public class AIPaddle extends Paddle {
    private Random random;
    private double[] inputs;
    private double[] weights;
    public double [][]dna ;
    public double [] rate;
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
        //dna[0] ={1,1,0,1,1,0,1,1,0,1,1,0};
    }
    
    public double getMove(Ball ball) {
        inputs = new double[] { position, ball.yPosition, ball.xVelocity, ball.yVelocity};
        double r=0;
        for(int i=0;i<inputs.length;i++){
            for(int j=0;j<3;j++){
                //r += dna[i*3]*Math.pow(inputs[i],dna[i*3+1])+dna[i*3+2];
            }
        }
        return r;
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
