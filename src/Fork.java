import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

class Fork {
    private Semaphore sem;
    private Date date;
    private SimpleDateFormat sdf;
    private Thread owner;

    public Fork() {
        this.sem = new Semaphore(1);
        this.sdf = new SimpleDateFormat("hh:mm:ss a");
    }

    public boolean grabFork(Thread philosopher, String fork) {
            this.date = new Date();
            if (this.isForkAvailable()) {
                try {
                    this.sem.acquire();
                    this.owner = philosopher;
                    System.out.println("#" + philosopher.getName() + " GRABS the " + fork + " fork at " + this.sdf.format(date));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("#" + philosopher.getName() + " could NOT grab the " + fork + " fork. #" + this.owner.getName() + " has it!!!");
                return false;
            }
    }

    public boolean releaseFork(Thread philosopher, String fork) {
            if (!this.isForkAvailable() && this.owner.getId() == philosopher.getId()) {
                this.date = new Date();
                this.sem.release();
                System.out.println("#" + philosopher.getName() + " RELEASE the " + fork + " fork at " + this.sdf.format(date));
                return true;
            }
            return false;
    }

    private boolean isForkAvailable() {
        return this.sem.availablePermits() > 0;
    }

}
