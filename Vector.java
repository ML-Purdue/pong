
public class Vector {
    public double x;
    public double y;
    public double mag;

    public Vector(){
        x = 0;
        y = 0;
        mag = 0;
    }

    public Vector(double x, double y, double magnitude){
        this.x = x;
        this.y = y;
        this.mag = magnitude;
    }

    public Vector(int x, int y, int magnitude){
        this.x = (double)x;
        this.y = (double)y;
        this.mag = (double)magnitude;
    }
}
