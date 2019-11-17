package concurrency.my_thread_pool;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SimpleThreadPool {

    private BlockingQueue<Runnable> blockingQueue;

    private List<Thread> threads = new ArrayList<>();

    public SimpleThreadPool(int poolSize, BlockingQueue<Runnable> blockingQueue) {
        this.blockingQueue = blockingQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            threads.add(workerThread);
        }
    }

    public void execute(Runnable task) {
        try {
            blockingQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task = null;
                try {
                    task = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
