package art_of_java_concurrency_programming.chapter_1;

public class DeadLockDemo {

    private static final String A = "a";

    private static final String B = "b";

    public static void main(String[] args) {
        DeadLockDemo.deadLock();
    }

    private static void deadLock() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
