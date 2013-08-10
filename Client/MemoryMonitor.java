public class MemoryMonitor implements Runnable {
    public void run() {
        while (true) {
            System.gc();
            try {
                Thread.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}