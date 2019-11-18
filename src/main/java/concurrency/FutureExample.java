package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureExample {

    private static class Task2 implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2: wash the glasspot");

            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2: wash the glass");

            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2: get the tea");

            TimeUnit.SECONDS.sleep(1);

            return "longjing tea";
        }
    }

    private static class Task1 implements Callable<String> {

        private FutureTask<String> ft1;

        private Task1(FutureTask<String> ft1) {
            this.ft1 = ft1;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1: wash the teapot");

            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1: boiling the water");

            TimeUnit.SECONDS.sleep(15);

            String tea = ft1.get();

            System.out.println("T1: successfully make the tea " + tea);

            return "done making the tea: " + tea;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> ft2 = new FutureTask<>(new Task2());

        FutureTask<String> ft1 = new FutureTask<>(new Task1(ft2));

        Thread t1 = new Thread(ft1);
        t1.start();

        Thread t2 = new Thread(ft2);
        t2.start();

        System.out.println(ft1.get());

    }
}
