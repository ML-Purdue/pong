import java.lang.NumberFormatException;

public class Main {
    public static void main(String[] argv) {
        //Each arg takes both a switch and a value
        if(argv.length % 2 != 0){
            System.err.println("Usage: java Main <-p paddletype> <-t tickduration> <-s speedup>");
            System.exit(1);
        }

        //Set default parameters
        Pong.PaddleType type = Pong.PaddleType.Human;
        double tickDuration = 1/10.0;
        double speedup = 10000;

        //Parse parameters
        for(int i = 0; i < argv.length; i++){
            if(argv[i].equals("-p")){//Paddle Type
                i++;
                if(argv[i].equals("Genetic"))
                    type = Pong.PaddleType.Genetic;
                else if(argv[i].equals("AI"))
                    type = Pong.PaddleType.AI;
                else if(argv[i].equals("CPU"))
                    type = Pong.PaddleType.CPU;
                else if(argv[i].equals("Human"))
                    type = Pong.PaddleType.Human;
                else{
                    System.err.println("Invalid paddle type");
                    System.exit(1);
                }
            } else if(argv[i].equals("-t")){//TickDuration
                i++;
                try{
                    tickDuration = Double.parseDouble(argv[i]);
                //Error handling
                }catch(NumberFormatException e){
                    System.err.println("Invalid tick duration");
                    System.exit(1);
                }
            } else if(argv[i].equals("-s")){//Speedup
                i++;
                try{
                    speedup = Double.parseDouble(argv[i]);
                //Error handling
                }catch(NumberFormatException e){
                    System.err.println("Invalid speedup");
                    System.exit(1);
                }
            }
        }
        new Pong(type, tickDuration, speedup);
    }
}
