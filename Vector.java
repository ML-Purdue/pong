
public class Vector {
    public double x;
    public double y;

    public Vector(){
        x = 0;
        y = 0;
    }

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector(int x, int y){
        this.x = (double)x;
        this.y = (double)y;
    }

    public void unitize() {
        double length = Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
    }
}
