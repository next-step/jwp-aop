package proxy.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    private static ExecutorService executor = Executors.newFixedThreadPool(3);

    @Test
    void threadLocal() {
        for (int i = 0 ; i < 10; i++) {
            final int id = i;
            executor.execute(() -> {
                String taskId = "Task" + id;
                ThreadLocalHolder.set(taskId);
                try {
                    TimeUnit.SECONDS.sleep(id);
                } catch (InterruptedException ignore) {}
                System.out.println(ThreadLocalHolder.get());
            });
        }

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
