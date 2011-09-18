
public class Vector {
    public double x;
    public double y;

    public Vector(){
        x = 0;
        y = 0;
    }

    public Vector(double x, double y, double magnitude){
        this.x = x;
        this.y = y;
    }

    public Vector(int x, int y, int magnitude){
        this.x = (double)x;
        this.y = (double)y;
    }
}
