package de.fhtrier.gdw.commons.utils;

/**
 *
 * @author Santo Pfingsten
 */
public class FpsCalculator {

    private final int MAX = 100;
    private int index = 0;
    private int sum = 0;
    private long[] values = new long[MAX];
    private long lastTime = -1;
    private final int waitTime;
    private int nextUpdate;
    private float value;
    
    /**
     * @param waitTime time to wait before updating the time, to avoid flickering
     */
    public FpsCalculator(int waitTime) {
        this.waitTime = waitTime;
        this.nextUpdate = 0;
    }

    public float calculate() {
        return value;
    }

    public void update() {
        long time = System.currentTimeMillis();
        if (lastTime == -1) {
            lastTime = time;
        }

        long delta = time - lastTime;
        lastTime = time;

        sum -= values[index];
        sum += delta;
        values[index] = delta;
        if (++index == MAX) {
            index = 0;
        }
        
        nextUpdate -= delta;
        if(nextUpdate <= 0) {
            nextUpdate = waitTime;
            value = 1000 / Math.max(1, (sum / (float) MAX));
        }
    }
}
