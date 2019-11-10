package concurrency;

public class BadExample1 {
    private long count = 0;

    private void add10k() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    private long getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final BadExample1 badExample = new BadExample1();

        Thread t1 = new Thread(badExample::add10k);
        Thread t2 = new Thread(badExample::add10k);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(badExample.getCount());
    }
}
