package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by yusik on 2020/07/03.
 */
@DisplayName("ThreadLocal 학습 테스트")
public class ThreadLocalLearningTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalLearningTest.class);

    public static ThreadLocal<Integer> local = new ThreadLocal<>();
    public static Map<Integer, String> results = new ConcurrentHashMap<>();

    @DisplayName("스레드 별 값 확인")
    @Test
    void name() throws InterruptedException {

        // given
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // when & then
        IntStream.rangeClosed(1, 10)
                .boxed()
                .forEach(index -> executorService.execute(() -> {
                    local.set(index);
                    logger.debug("## current thread: {}", Thread.currentThread());
                    results.put(local.get(), Thread.currentThread().getName());
                }));

        Thread.sleep(3000);
        logger.info("{}", results);
    }
}
