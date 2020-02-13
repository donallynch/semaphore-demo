import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    private static Semaphore semaphore = new Semaphore(2);

    /**
     * MAIN
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Total available Semaphore permits : " + semaphore.availablePermits());
        new thread("AA").start();
        new thread("BB").start();
        new thread("CC").start();
        new thread("DD").start();
        new thread("EE").start();
        new thread("FF").start();
        new thread("GG").start();
    }

    private static class thread extends Thread {

        private String name = "";

        /**
         * Thread constructor
         * @param name
         */
        public thread(String name) {
            this.name = name;
        }

        public void run() {
            try {
                /**
                 * Try to acquire resource
                 *  If the semaphore is unavailable the application will pause the thread at this point
                 *  waiting for the resource to become available
                 */
                semaphore.acquire();

                /**
                 * Mock thread doing something with resource
                 *  In a real application this would be something meaningful
                 *  like writing to a file or some other business logic
                 */
                System.out.println(name + " : RESOURCE ACQUIRED");
                for (int step = 1; step <= 5; step++) {
                    operateOnResource(step);
                    Thread.sleep(500);
                }

                /**
                 * Release the remaphore which makes the resource available to the next waiting thread
                 */
                semaphore.release();

                System.out.println(name + " : RESOURCE RELEASED, permits: " + semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * Perform operations with resource
         * @param step
         */
        private void operateOnResource(int step) {
            System.out.println(name + " : Processing step " + step + ", permits: " + semaphore.availablePermits());
        }
    }
}

