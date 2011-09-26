public class Main {
    public static void main(String[] parameters) {
        int i;
        String p, lPaddle = "" , rPaddle = "", path = "";
        
        if ( parameters.length == 0) {
            Pong pong = new Pong();
            return ;
        }
        if ( parameters.length == 1 && parameters[0].equals("-?") ) {
            System.out.println("Pong Command Help");
            System.out.println("1) To select paddles\n"
                                   + "  -paddles [leftPaddle] [rightPaddle]");
            System.out.println("2) To save the game to a file\n"
                                   + "  -s [path]");
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
            } else {
                System.out.println("Error: Parameter " + parameters[i] + " is ignored.");
                i++;
            }
        }
        
        try{
            Pong pong = new Pong(lPaddle, rPaddle, path);
        } catch(Error e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
