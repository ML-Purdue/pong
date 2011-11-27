import java.util.Random;

public class GeneticPaddle extends Paddle {
    private Random random;
    private double[] inputs;
    private DNA[] dna;//[DNA][weight]
    private int currentDNA;

    private class DNA{
        public double[] weights;
        public long bounces;
        public long misses;
        public double hitPercentage;
        
        public DNA(){
            weights = new double[2];
            for(int i = 0; i < weights.length; i++)
                weights[i] = random.nextDouble() - 1;
            bounces = 0;
            misses = 0;
            hitPercentage = 0;
        }
        
        public void printWeights() {
            for (int i = 0; i < weights.length; i++) {
                System.out.format("%.2f ", weights[i]);
            }
            System.out.println();
        }
    }

    public GeneticPaddle(Random random, int numDNA) {
        this(0.5, 0.3, 1, random, numDNA);
    }
    
    public GeneticPaddle(double position, double width, 
                         double speed, Random random, int numDNA) {
        super(position, width, speed);
        this.random = random;
        inputs = new double[2];
        //allocate the dna array
        dna = new DNA[numDNA];
        for(int i = 0; i < dna.length; i++)
            dna[i] = new DNA();
    }
    
    public double getMove(Ball ball) {
        inputs = new double[] { position, ball.yPosition };
        
        return inputs[0] * dna[currentDNA].weights[0] + inputs[1] * dna[currentDNA].weights[1];
    }
    
    public void sendPain() {
        // missed
        dna[currentDNA].misses++;
        
        // each dna plays for 30 bounces
        if ( dna[currentDNA].bounces < 30 ) 
            return;
        
        // record hit percentage and reset misses and bounces 
        dna[currentDNA].hitPercentage = (double)dna[currentDNA].bounces / (dna[currentDNA].bounces+dna[currentDNA].misses);
        dna[currentDNA].misses = 0;
        dna[currentDNA].bounces = 0;
        
        // play through each dna
        if(++currentDNA < dna.length) return;
        
        // reset the current dna
        currentDNA = 0;
        
        // find the best dna
        int best = 0;
        for(int i = 0; i < dna.length; i++)
            if (dna[best].hitPercentage < dna[i].hitPercentage) best=i;
        
        // print its hit percentage and weights
        System.out.format("%.2f ", dna[best].hitPercentage);
        dna[best].printWeights();
        
        // store the best dna in the first element
        for(int i = 0; i < 2; i++)
            dna[0].weights[i] = dna[best].weights[i];
        
        // mutate the other dna based on the best one
        for(int i = 1; i < dna.length; i++){
            for (int j = 0; j < 2; j++) {
                dna[i].weights[j] = dna[0].weights[j] * (1 + 0.1 * 2 * (random.nextDouble() - 0.5)) + 
                        1.0 * 2 * (random.nextDouble() - 0.5);
            }
        }
    }
    
    public void bounce(Ball ball, Random random) {
        double angle = Math.PI / 3 * 
            (ball.yPosition - position) / (width / 2);
        angle = angle + (2 * random.nextDouble() - 0.5) / 10;
        ball.xVelocity = ball.speed * Math.cos(angle);
        ball.yVelocity = ball.speed * Math.sin(angle);
        dna[currentDNA].bounces++;
    }
}
