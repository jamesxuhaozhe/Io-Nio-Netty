package playground;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class Phantom {

    private static final ReferenceQueue<byte[]> RQ = new ReferenceQueue<>();

    public static void main(String[] args) {
        final Map<PhantomReference<byte[]>, Object> map = new HashMap<>();
        final Thread thread = new Thread(() -> {
            try {
                int cnt = 0;
                PhantomReference<byte[]> k;
                while ((k = (PhantomReference<byte[]>) RQ.remove()) != null) {
                    System.out.println("第 个回收对象，对象打印为：" + cnt++ + " " + k);
                }
            } catch (InterruptedException ignored) {
            }
        });
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 1000; i++) {
            map.put(new PhantomReference<>(new byte[1024 * 1024], RQ), new Object());
        }
        System.out.println("map.size: " + map.size());
    }
}
