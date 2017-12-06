import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

class Fork {
    private Semaphore mutex;
    private Date date;
    private SimpleDateFormat sdf;
    private Long ownerId;
    private Thread owner;

    public Fork() {
        this.mutex = new Semaphore(1);
        sdf = new SimpleDateFormat("hh:mm:ss a");
    }

    public boolean grabFork(Thread philosopher, String fork) {
            this.date = new Date();
            if (this.isForkAvailable()) {
                try {
                    mutex.acquire();
                    this.owner = philosopher;
                    ownerId = philosopher.getId();
                    System.out.println("#" + philosopher.getName() + " GRABS the " + fork + " fork at " + sdf.format(date));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("#" + philosopher.getName() + " could NOT grab the " + fork + " fork. " + this.owner.getName() + " has it!!!");
                return false;
            }
    }

    public boolean releaseFork(Thread philosopher, String fork) {
            if (!this.isForkAvailable() && this.ownerId == philosopher.getId()) {
                this.date = new Date();
                mutex.release();
                System.out.println("#" + philosopher.getName() + " RELEASE the " + fork + " fork at " + sdf.format(date));
                return true;
            }
            return false;
    }

    private boolean isForkAvailable() {
        return mutex.availablePermits() > 0;
    }

}
