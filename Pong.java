public class Pong {
    public GameState state;

    public Pong(){
        state = new GameState();
    }

    public static void main(String[] args){
        Pong game = new Pong();

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
        }
    }
}
