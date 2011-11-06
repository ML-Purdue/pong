import java.util.Random;

public class GeneticPaddle extends Paddle {
    private Random random;
    private double[] inputs;
    private DNA[] dna;//[DNA][weight]
    private int currentDNA;

    private class DNA{
        public double[] weights;
        public double bounces;
        public long miss;
        public DNA(){
            weights = new double[2];
            for(int i = 0; i < weights.length; i++)
                weights[i] = random.nextDouble() - 1;
            bounces = 0;
            miss = 0;
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
        dna[currentDNA].miss++;
        if ( dna[currentDNA].bounces < 10 ) 
            return;

        dna[currentDNA].bounces/=(dna[currentDNA].bounces+dna[currentDNA].miss);
        dna[currentDNA].miss = 0;
        currentDNA++;
        if(currentDNA < dna.length) return;
        currentDNA = 1;
        int best = 0, i;
        for(i=1;i<dna.length;i++)
            if (dna[best].bounces<dna[i].bounces) best=i;
        if (best!=0){
            System.out.print("Best: " + dna[best].bounces+" ");
            for(i=0;i<2;i++){
                dna[0].weights[i] = dna[best].weights[i];
                System.out.print(dna[0].weights[i] + " ");
            }
            dna[0].bounces = dna[best].bounces;
            System.out.println("");
        }
        
        for(i=1;i<dna.length;i++){
            dna[i].bounces = 0;
            for (int j = 0; j < 2; j++) {
                dna[i].weights[j] = dna[0].weights[j] * (1 + 0.1 * 2 * (random.nextDouble() - 0.5)) + 
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
        dna[currentDNA].bounces++;
    }
}
