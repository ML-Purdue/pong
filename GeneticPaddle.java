import java.util.Random;

public class GeneticPaddle extends Paddle {
    private Random random;
    private int currentDNA;
    private double[] inputs;
    private double[][] weights;//[DNA][weight]
    private double[] bounces;
    private int miss;
    public GeneticPaddle(Random random, int numDNA) {
        this(0.5, 0.3, 1, random, numDNA);
    }
    
    public GeneticPaddle(double position, double width, 
                         double speed, Random random, int numDNA) {
        super(position, width, speed);
        this.random = random;
        inputs = new double[2];
        //allocate the dna array
        weights = new double[numDNA][2];
        //allocate each dna's weight array
        for(int i = 0; i < numDNA; i++){
            weights[i] = new double[2];
            //initialize the dna's weight array
            for (int j = 0; j < 2; j++) {
                weights[i][j] = 2 * random.nextDouble() - 1;
            }
            //-0.6303822774643533 0.5743760335584333
            //weights[i][0] = -0.6303822774643533;
            //weights[i][1] =  0.5743760335584333;
        }
        //allocate and initialize the bounces array
        bounces = new double[numDNA];
        for(int i = 0; i < numDNA; i++){
            bounces[i] = 0;
        }
        miss = 0;
    }
    
    public double getMove(Ball ball) {
        inputs = new double[] { position, ball.yPosition };
        
        return inputs[0] * weights[currentDNA][0] + inputs[1] * weights[currentDNA][1];
    }
    
    public void sendPain() {
        miss++;
        if ( bounces[currentDNA] < 100 ) return ;
        bounces[currentDNA]/=(bounces[currentDNA]+miss);
        miss = 0;
        //do some stuff
        currentDNA++;
        if(currentDNA < weights.length) return;
        currentDNA = 2;
        int best = 0, i;
        for(i=1;i<weights.length;i++)
            if (bounces[best]<bounces[i]) best=i;
        if (best!=0){
            System.out.print("Best: " + bounces[best]+" ");
            for(i=0;i<2;i++){
                weights[0][i] = weights[best][i];
                System.out.print(weights[0][i] + " ");
            }
            bounces[0] = bounces[best];
            System.out.println("");
        }
        
        for(i=1;i<weights.length;i++){
            bounces[i] = 0;
            for (int j = 0; j < 2; j++) {
                weights[i][j] = weights[0][j] * (1 + 0.1 * 2 * (random.nextDouble() - 0.5)) + 
                        2 * 2 * (random.nextDouble() - 0.5);
            }
        }
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
