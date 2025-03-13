package logic;

public class GameTimer extends Thread {

    private double time;
    private static int timeStep = 10;

    public GameTimer() {
        time = 0;
    }

    @Override
    public void run() {
        while (Thread.currentThread() == this) {
            try {
                Thread.sleep(timeStep);
                time += timeStep;
                // System.out.println(time);
            } catch (InterruptedException e) {
                System.out.println("Failed to" + e);
            }
        }
    }

    public void stopTimer() {
        this.interrupt();
    }

    public double getTime() {
        return time;
    }
}
