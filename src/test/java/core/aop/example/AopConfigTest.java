/*
package core.aop.interceptors;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopConfigTest {
    private static final Logger log = LoggerFactory.getLogger(AopConfigTest.class);

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
    }

    @Test
    public void getName() throws Exception {
        AopTestServiceImpl myTestService = applicationContext.getBean(AopTestServiceImpl.class);

        String name = myTestService.getUppercasedName("ninjasul");
        log.debug("name: {}", name);
    }
}
*/
