public class Pong {
    public GameState state;

    public Pong(){
        state = new GameState();
    }

    public Pong(GameState initialState){
        state = initialState;
    }

    public static void main(String[] args){
        if(args.length == 1){
            //Set up the game parameters
            GameState initialState = new GameState();

            if(args[0].equals("--ai")){
//                Paddle p = new AIPaddle();
//                Ball b = new StandardBall();
//                initialState = new GameState(p, b);
            }else if(args[0].equals("--cpu")){
//                Paddle p = new SimpleCPUPaddle();
//                Ball b = new StandardBall();
//                initialState = new GameState(p, b);
            }else if(args[0].equals("--human")){
//                Paddle p = new HumanPaddle();
//                Ball b = new StandardBall();
//                initialState = new GameState(p, b);
            }else{
                System.err.println("Unknown argument: "+args[0]);
                System.exit(1);
            }

            Pong game = new Pong(initialState);

            //Continually run rounds on the game
            for(int i = 0;;i++){
                System.out.println("Round "+i);

                //save the game state in some way
                //game.state.save();

                //Set up round to run from the game's 'state'
                Round r = new Round(game.state);

                //Simulate the round (which changes the state of the game
                //and save that state back into the game
                game.state = r.simulate();

                System.out.println("\tScore: "+game.state.score);

                //just used for simple testing right now
                break;
            }
        }else{
            System.err.println("Usage: java Pong [OPTIONS]");
            System.err.println("\t[OPTIONS]:");
            System.err.println("\t--ai: Create an AI-controlled paddle");
            System.err.println("\t--cpu: Create a simple algorithmic paddle");
            System.err.println("\t--human: Create a human-controlled paddle");
        }
    }
}
