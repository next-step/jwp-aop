package core.transaction;

import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerConverter;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TransactionalAdviceTest {
    private static final Logger log = LoggerFactory.getLogger(TransactionalAdviceTest.class);

    private AnnotationHandlerMapping handlerMapping;
    private TestTransactionalService transactionalService;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        handlerMapping = new AnnotationHandlerMapping(ac, ac.getBean(HandlerConverter.class));
        handlerMapping.initialize();

        transactionalService = ac.getBean(TestTransactionalService.class);
    }

    @Test
    void test_transactional() {
        String result = transactionalService.doService();
        log.debug("service: {}", result);
    }
}