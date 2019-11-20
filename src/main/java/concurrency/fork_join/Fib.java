package concurrency.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fib {

    static class FibTask extends RecursiveTask<Integer> {

        private final int n;

        FibTask(final int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }

            FibTask f1 = new FibTask(n - 1);
            f1.fork();

            FibTask f2 = new FibTask(n - 2);

            return f2.compute() + f1.join();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        FibTask fibTask = new FibTask(10);

        System.out.println(forkJoinPool.invoke(fibTask));
    }
}
