public class Stopwatch {
    long startTime;
    long endTime;
    boolean isRunning;
    
    public Stopwatch() {
        this(false);
    }
    
    public Stopwatch(boolean startImmediately) {
        startTime = startImmediately ? System.nanoTime() : 0;
        endTime = 0;
        isRunning = startImmediately;
    }
    
    public void start() {
        if (isRunning) return;
        
        startTime = System.nanoTime() - (endTime - startTime);
        isRunning = true;
    }
    
    public void pause() {
        if (!isRunning) return;
        
        endTime = System.nanoTime();
        isRunning = false;
    }
    
    public void reset() {
        startTime = 0;
        endTime = 0;
        isRunning = false;
    }
    
    public void restart() {
        startTime = System.nanoTime();
        isRunning = true;
    }
    
    public long nanoseconds() {
        return isRunning ? System.nanoTime() - startTime : endTime - startTime;
    }
    
    public long milliseconds() {
        return nanoseconds() / 1000000;
    }
    
    public double seconds() {
        return nanoseconds() / 1000000000.0;
    }
}
