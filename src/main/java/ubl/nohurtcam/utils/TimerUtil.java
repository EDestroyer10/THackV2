package ubl.nohurtcam.utils;

public class TimerUtil {

    private long lastMS;

    public TimerUtil() {
        this.reset();
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {

        if (lastMS > System.currentTimeMillis()) {
            lastMS = System.currentTimeMillis();
        }

        if (System.currentTimeMillis()-lastMS > time) {

            if (reset)
                reset();

            return true;


        }else {
            return false;
        }

    }
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
}