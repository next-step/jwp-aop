package core.jdbc;

import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by yusik on 2020/07/03.
 */
@DisplayName("트랜잭션 커넥션 테스트")
class DataSourceUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtilsTest.class);

    private static DefaultBeanFactory beanFactory;

    @BeforeAll
    static void beforeAll() {
        beanFactory = new DefaultBeanFactory();
        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(ExampleConfig.class);
        beanFactory.preInstantiateSingletons();
    }

    @DisplayName("ThreadLocal 기반의 DB 커넥션")
    @Test
    void getConnection() throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        DataSource dataSource = beanFactory.getBean(DataSource.class);

        // when & then
        IntStream.rangeClosed(1, 10)
                .boxed()
                .forEach(index -> executorService.execute(() -> {
                    Connection conn = getThreadLocalConnection(dataSource);
                    Connection reConn = getThreadLocalConnection(dataSource);
                    logger.info("# current thread: {}", Thread.currentThread().getName());
                    logger.info("### {} ::: {}", Thread.currentThread().getName(), conn);
                    logger.info("### {} ::: {}", Thread.currentThread().getName(), reConn);
                    assertThat(conn).isEqualTo(reConn);
                }));

        Thread.sleep(3000);
    }

    private Connection getThreadLocalConnection(DataSource dataSource) {
        try {
            return DataSourceUtils.getConnection(dataSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void afterAll() {
        beanFactory.clear();
    }
}