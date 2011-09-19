/*************************************
 * Class which represents the state of
 * the Pong game between rounds.
 *************************************/
 import java.io.File;
 import java.util.Scanner;

 public class GameState {
     public int score;
     public Paddle paddle;
     public Ball ball;

     public GameState() {
         score = 0;
         paddle = null;
         ball = null;
     }

     public GameState(Paddle p, Ball b){
         score = 0;
         paddle = p;
         ball = b;
     }

     public GameState(String filename){
         //read a state from a file
         File file = new File(filename);
         if(!file.exists()){
             System.err.println("File "+filename+" does not exist. Reverting to blank initial game state");
             score = 0;
             paddle = null;
             ball = null;
         }else{
             try{
                 Scanner in = new Scanner(file);
                 //Read the state in whatever format we define
                 score = 0;
                 paddle = null;
                 ball = null;
                 in.close();
             }catch(Exception e){
                 System.err.println("Error: "+e.getMessage());
             }
         }
     }
 }
