package core.aop.transaction;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import core.aop.example.transactional.NonTransactionalService;
import core.aop.example.transactional.TransactionalMethodService;
import core.aop.transactional.TransactionalAdvice;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.jdbc.DataAccessException;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static core.aop.transactional.TransactionalAdvice.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NonTransactionalServiceTest {
    private NonTransactionalService service;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        service = ac.getBean(NonTransactionalService.class);

        listAppender = new ListAppender<>();
        listAppender.start();

        Logger serviceLogger = (Logger)LoggerFactory.getLogger(TransactionalAdvice.class);
        serviceLogger.addAppender(listAppender);
    }

    @Test
    @DisplayName("@Transactional 이 없는 메소드 테스트")
    void doServiceWithTransactional() {
        service.doService();

        List<ILoggingEvent> logs = listAppender.list;

        assertThat(logs).isEmpty();
    }

    @Test()
    @DisplayName("@Transactional 이 없는 메소드 도중 RuntimeException 발생 테스트")
    void doExceptionalServiceWithTransactional() {
        assertThrows(RuntimeException.class, () -> service.doExceptionalService());

        List<ILoggingEvent> logs = listAppender.list;

        assertThat(logs).isEmpty();
    }
}
