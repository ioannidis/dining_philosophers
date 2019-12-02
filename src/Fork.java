import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

class Fork {
    private Semaphore mutex ;
    Date date;
    SimpleDateFormat sdf;

    public Fork() {
        this.mutex = new Semaphore(1);
        sdf = new SimpleDateFormat("hh:mm:ss a");
    }

    public boolean grabFork(int philosopherName, String fork) {
        synchronized (this) {
            this.date = new Date();

            if (this.isForkAvailable()) {
                try {
                    mutex.acquire();
                    System.out.println("#" + philosopherName + " Philosopher GRABS the " + fork + " fork at " + sdf.format(date));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("#" + philosopherName + " Philosopher could NOT grab the " + fork + " fork!!!!");
                return false;
            }
        }

    }

    public boolean releaseFork(int philosopherName, String fork) {
        synchronized (this) {
            if (!this.isForkAvailable()) {
                this.date = new Date();
                mutex.release();
                System.out.println("#" + philosopherName + " Philosopher RELEASE the " + fork + " fork at " + sdf.format(date));
                return true;
            }
            return false;
        }

    }

    public boolean isForkAvailable() {
        return mutex.availablePermits() > 0;
    }

}
