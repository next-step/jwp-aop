package proxy.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadLocalTest {

    private static ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> {
            executor.execute(() -> {
                String taskId = "Task" + i;
                ThreadLocalHolder.set(taskId);
                try {
                    TimeUnit.SECONDS.sleep(i);
                } catch (InterruptedException ignore) {}
                System.out.println(ThreadLocalHolder.get());
            });
        });

        executor.shutdown();
    }

    private static class ThreadLocalHolder {
        private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

        public static String get() {
            return threadLocal.get();
        }

        public static void set(String value) {
            threadLocal.set(value);
        }
    }

}
