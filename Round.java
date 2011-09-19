/*******************************************.
 * Describes the functionality of a round of
 * 1-player Pong.  This starts with the initial
 * ball movement and ends with a loss by the 
 * player.
 *******************************************/

public class Round {
    private GameState internalState;

    public Round(GameState state){
        internalState = state;
    }

    public GameState simulate(){
        internalState.score++;
        return internalState;
    }
}

