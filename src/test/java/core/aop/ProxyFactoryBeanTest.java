package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.proxy.example.Counter;
import core.di.factory.proxy.example.CounterAdvice;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanTest {

    private BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        beanFactory = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

    @Test
    public void interceptorTest() throws Exception {
        Counter counter = new Counter();
        ProxyFactoryBean<MyService> proxyFactoryBean = new ProxyFactoryBean<>();

        proxyFactoryBean.setTarget(new MyService());
        proxyFactoryBean.setPointcut(method -> true);
        proxyFactoryBean.setAdvice(new CounterAdvice(counter));

        proxyFactoryBean.getObject().doProcess();

        assertThat(counter.getCount()).isEqualTo(1);
    }

    @Test
    public void createProxyFromWithObjenesisTest() throws Exception {
        ProxyFactoryBean<?> proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new MyService2(new MyService()));

        MyService2 myService2 = (MyService2) proxyFactoryBean.getObject();

        assertThat(myService2.getMyService()).isNotNull();
    }


    public static class MyService {
        public void doProcess() {
        }
    }

    public static class MyService2 {

        private MyService myService;

        public MyService2(MyService myService) {
            this.myService = myService;
        }

        public MyService getMyService() {
            return myService;
        }
    }
}
