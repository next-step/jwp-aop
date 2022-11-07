package core.aop;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.proxy.Advice;
import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.jdkdynamic.JdkDynamicProxyFactory;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;

public class BeanFactoryTest {

    @DisplayName("팩토리빈을 BeanFactory에서 꺼낼 수 있다.")
    @Test
    void name2() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanFactoryConfiguration.class);

        var helloTargetA = applicationContext.getBean(ByeTarget.class);
        var helloTargetB = applicationContext.getBean(ByeTarget.class);

        assertThat(helloTargetA).isEqualTo(helloTargetB);
    }

    @DisplayName("빈팩토리 빈은 객체를 생성할 수 있다")
    @Test
    void name() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanFactoryConfiguration.class);

        var bean = applicationContext.getBean(ProxyFactoryBean.class);
        Bye bye = (Bye)bean.getObject();

        var actual = bye.sayHi("hongbin");

        assertThat(actual).isEqualTo("HI HONGBIN");
    }

    @Configuration
    @ComponentScan(basePackages = {"core.aop"})
    public static class BeanFactoryConfiguration {

        @Bean
        public ByeTargetFactoryBean byeTarget() {
            return new ByeTargetFactoryBean();
        }

        @Bean
        public ProxyFactoryBean<Bye> byeTarget2() {
            Advice advice = joinPoint -> {
                var proceed = joinPoint.proceed();

                var result = (String)proceed;
                return result.toUpperCase();
            };

            ProxyFactoryBean<Bye> helloProxyFactoryBean = new ProxyFactoryBean<>(
                Bye.class,
                new ByeTarget(),
                new Advisor(advice, (targetClass, method) -> true),
                new JdkDynamicProxyFactory()
            );

            return helloProxyFactoryBean;
        }
    }
}
