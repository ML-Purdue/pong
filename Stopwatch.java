public class Stopwatch {
    long startTime;
    long endTime;
    long buildup;
    boolean isRunning;
    
    public Stopwatch() {
        startTime = 0;
        endTime = 0;
        buildup = 0;
        isRunning = false;
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
        isRunning = true;
    }
    
    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            isRunning = false;
            buildup += endTime - startTime;
        }
    }
    
    public void reset() {
        startTime = 0;
        endTime = 0;
        buildup = 0;
        isRunning = false;
    }
    
    public void restart() {
        reset(); start();
    }
    
    public long getElapsedMilliseconds() {
        return buildup + (System.currentTimeMillis() - startTime);
    }
    
    public double getElapsedSeconds() {
        return getElapsedMilliseconds() / 1000.0;
    }
}
