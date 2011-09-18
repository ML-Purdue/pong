public class Pong {
    public Player player;

    public Pong(){
        player = new SimpleCPU();
    }

    public static void main(String[] args){
        Pong game = new Pong();

        //continually update player positions
        //and handle collisions
        int i = 0;
        while(true){
            //do round
            System.out.println("Round "+i++);
            int j = 0;
            for(Player p : game.players){
                System.out.println("Player "+j++);
                System.out.println(p.location.x + " " + p.location.y);
                p.move();
                System.out.println(p.location.x + " " + p.location.y);
            }
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){}
        }
    }
}
