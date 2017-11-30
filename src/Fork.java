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

    //    public void grab(int philsopherName, String fork) {
//        Instant instant = Instant.now();
//        try {
//            if (this.isFree()) {
//                mutex.acquire();
//                System.out.println("Philosopher " + philsopherName + " grabs the " + fork + " fork at " + instant);
//            }
//            else {
//                System.out.println("Philosopher " + philsopherName + " could NOT grab the " + fork + " fork!!!!");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//    }
//
//    public void release(int philsopherName, String fork) {
//        if (!this.isFree()) {
//            Instant instant = Instant.now();
//            mutex.release();
//            System.out.println("Philosopher " + philsopherName + " RELEASE the " + fork + " fork at " + instant);
//        }
//    }
//
//    public boolean isFree() {
//        return mutex.availablePermits() > 0;
//    }

    public boolean grabFork(Thread philosopher, int philosopherName, String fork) {
            this.date = new Date();
            //System.out.println(this.isForkAvailable() + " ==" + philosopher.getName() + " == " + mutex.availablePermits());
            if (this.isForkAvailable()) {
                try {
                    mutex.acquire();
                    this.owner = philosopher;
                    ownerId = philosopher.getId();
                    //System.out.println(this.isForkAvailable() + " *** " + philosopher.getName() + " *** " + mutex.availablePermits());
                    System.out.println("#" + philosopher.getName() + " Philosopher GRABS the " + fork + " fork at " + sdf.format(date));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("#" + philosopher.getName() + " Philosopher could NOT grab the " + fork + " fork. " + this.owner.getName() + " has it!!!");
                return false;
            }
    }

    public boolean releaseFork(Thread philosopher, int philosopherName, String fork) {
            if (!this.isForkAvailable() && this.ownerId == philosopher.getId()) {
                this.date = new Date();
                mutex.release();
                System.out.println("#" + philosopher.getName() + " Philosopher RELEASE the " + fork + " fork at " + sdf.format(date));
                return true;
            }
            return false;
    }

    private boolean isForkAvailable() {
        return mutex.availablePermits() > 0;
    }

}
