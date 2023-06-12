package optimizer.utils;

public class Timer {
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (this.lastMS > System.currentTimeMillis())
            this.lastMS = System.currentTimeMillis();
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset)
                reset();
            return true;
        }
        return false;
    }
}