public class Main {
    public static void main(String[] parameters) {
        int i;
        String p, lPaddle = "" , rPaddle = "", path = "";
        
        if ( parameters.length == 0) {
            Pong pong = new Pong("cpu", "wall", "");
            return ;
        }
        if ( parameters.length == 1 && parameters[0].equals("-?") ) {
            System.out.println("Usage: java Pong [OPTIONS]");
            System.out.println("-human: Create a human-controlled paddle");
            System.out.println("-cpu: Create a simple algorithmic paddle");
            System.out.println("-wall: Create a wall");
            System.out.println("-ai: Create an AI-controlled paddle");
            System.out.println("-paddles [leftPaddle] [rightPaddle]: Select paddles");
            System.out.println("-s [path]: Save the game to a file\n");
            return ;
        }
        
        i = 0;
        while(i < parameters.length){
            p = parameters[i].toLowerCase();
            if ( i + 3 <= parameters.length && p.equals("-paddles") ) {
                lPaddle = parameters[ i + 1 ];
                rPaddle = parameters[ i + 2 ];
                i += 3;
            } else if ( i + 2 <= parameters.length && p.equals("-s") ) {
                path = parameters[ i + 1 ];
                i += 2;
            } else if ( lPaddle.equals("") ) {
                lPaddle = p.substring(1);
                i++;
            } else if ( rPaddle.equals("") ) {
                rPaddle = p.substring(1);
                i++;
            } else {
                System.out.println("Error: Parameter " + parameters[i] + " is ignored.");
                i++;
            }
        }
        
        try{
            Pong pong = new Pong(lPaddle, rPaddle, path);
        } catch(Error e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }
}
