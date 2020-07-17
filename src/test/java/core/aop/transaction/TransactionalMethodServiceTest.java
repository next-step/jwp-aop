package core.aop.transaction;

import core.aop.example.transactional.TransactionalMethodService;
import core.aop.transactional.TransactionalAdvice;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.DataAccessException;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import static core.aop.transactional.TransactionalAdvice.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionalMethodServiceTest extends BaseTransactionalServiceTest {
    private static TransactionalMethodService service;

    @BeforeAll
    static void beforeAll() {
        ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        service = ac.getBean(TransactionalMethodService.class);
    }

    @BeforeEach
    void setUp() {
        Logger serviceLogger = (Logger)LoggerFactory.getLogger(TransactionalAdvice.class);
        serviceLogger.addAppender(listAppender);
    }

    @Test
    @DisplayName("@Transactional 이 있는 메소드 테스트")
    void doServiceWithTransactional() {
        service.doServiceWithTransactional();

        assertLogMessages(listAppender.list, TRANSACTION_START_MESSAGE, TRANSACTION_COMMIT_MESSAGE, TRANSACTION_END_MESSAGE);
    }

    @Test
    @DisplayName("@Transactional 이 없는 메소드 테스트")
    void doServiceWithoutTransactional() {
        service.doServiceWithoutTransactional();

        isLogEmpty(listAppender.list);
    }

    @Test()
    @DisplayName("@Transactional 메소드 도중 RuntimeException 발생 테스트")
    void doExceptionalServiceWithTransactional() {
        assertThrows(DataAccessException.class, () -> service.doExceptionalServiceWithTransactional());

        assertLogMessages(listAppender.list, TRANSACTION_START_MESSAGE, TRANSACTION_ROLLBACK_MESSAGE, TRANSACTION_END_MESSAGE);
    }
}
