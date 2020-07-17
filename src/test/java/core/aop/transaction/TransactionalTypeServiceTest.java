package core.aop.transaction;

import ch.qos.logback.classic.Logger;
import core.aop.example.transactional.TransactionalMethodService;
import core.aop.example.transactional.TransactionalTypeService;
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
import org.slf4j.LoggerFactory;

import static core.aop.transactional.TransactionalAdvice.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionalTypeServiceTest extends BaseTransactionalServiceTest {
    private static TransactionalTypeService service;

    @BeforeAll
    static void beforeAll() {
        ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        service = ac.getBean(TransactionalTypeService.class);
    }

    @BeforeEach
    void setUp() {
        Logger serviceLogger = (Logger)LoggerFactory.getLogger(TransactionalAdvice.class);
        serviceLogger.addAppender(listAppender);
    }

    @Test
    @DisplayName("타입 @Transactional 테스트")
    void doServiceWithTransactional() {
        service.doServiceWithTransactional();

        assertLogMessages(listAppender.list, TRANSACTION_START_MESSAGE, TRANSACTION_COMMIT_MESSAGE, TRANSACTION_END_MESSAGE);
    }

    @Test()
    @DisplayName("타입 @Transactional 메소드 실행 도중 RuntimeException 발생 테스트")
    void doExceptionalServiceWithTransactional() {
        assertThrows(DataAccessException.class, () -> service.doExceptionalServiceWithTransactional());

        assertLogMessages(listAppender.list, TRANSACTION_START_MESSAGE, TRANSACTION_ROLLBACK_MESSAGE, TRANSACTION_END_MESSAGE);
    }
}
