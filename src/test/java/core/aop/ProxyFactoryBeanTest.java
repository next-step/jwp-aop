package core.aop;

import core.annotation.Inject;
import core.annotation.PostConstruct;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.proxy.example.Counter;
import core.di.factory.proxy.example.CounterAdvice;
import net.sf.cglib.proxy.MethodProxy;
import next.config.MyConfiguration;
import next.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanTest {

    private BeanFactory beanFactory;

    @BeforeEach
    public void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        this.beanFactory = ac;
    }

    @Test
    public void interceptorTest() throws Exception {
        Counter counter = new Counter();
        ProxyFactoryBean<MyService> proxyFactoryBean = new ProxyFactoryBean<>(MyService.class,
                method -> true,
                new CounterAdvice(counter));

        proxyFactoryBean.getObject().doProcess();

        assertThat(counter.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Inject Method, PostConstructor test")
    public void interceptorTest2() throws Exception {
        Counter counter = new Counter();
        ProxyFactoryBean<MyService2> proxyFactoryBean = new ProxyFactoryBean<>(MyService2.class,
                method -> "doProcess".equals(method.getName()),
                new CounterAdvice(counter));

        proxyFactoryBean.setBeanFactory(beanFactory);

        MyService2 myService = proxyFactoryBean.getObject();
        myService.doProcess();

        assertThat(myService.getDataSource()).isNotNull();
        assertThat(myService.getUserDao()).isNotNull();
        assertThat(myService.getMessage()).isNotNull();
        assertThat(counter.getCount()).isEqualTo(1);
    }



    public static class MyService {
        public void doProcess() {
        }
    }

    public static class MyService2 {

        @Inject
        private DataSource dataSource;

        private UserDao userDao;

        private String message;

        @Inject
        public MyService2(UserDao userDao) {
            this.userDao = userDao;
        }

        @PostConstruct
        public void postConstruct() {
            this.message = "HelloWorld";
        }

        public void doProcess() {
            System.out.println("do");
        }

        public DataSource getDataSource() {
            return dataSource;
        }

        public UserDao getUserDao() {
            return userDao;
        }

        public String getMessage() {
            return message;
        }
    }
}
