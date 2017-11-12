import java.util.concurrent.Semaphore;

public class Fork {
    public Semaphore mutex = new Semaphore(1);

    public void grab() {
        try {
            mutex.acquire();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    void release() {
        mutex.release();
    }

    boolean isFree() {
        return mutex.availablePermits() > 0;
    }

}
