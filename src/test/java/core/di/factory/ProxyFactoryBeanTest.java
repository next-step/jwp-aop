package core.di.factory;

import core.aop.Advice;
import core.aop.MatchMethodPointcut;
import core.aop.MethodInvocation;
import core.aop.ProxyFactoryBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProxyFactoryBean 테스트")
public class ProxyFactoryBeanTest {

    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBeanTest.class);

    private ProxyFactoryBean proxyFactoryBean;

    @BeforeEach
    public void setup() {
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
    }

    @DisplayName("대문자 전환 어드바이스")
    @Test
    void factoryBeanAdvice() {

        // given
        String name = "Yusik";
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());
        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        String result = helloTarget.sayHello(name);
        logger.info(result);

        // then
        assertThat(result).isEqualTo("HELLO YUSIK");
    }

    @DisplayName("포인트컷 동작 확인")
    @Test
    void factoryBeanPointcut() {

        // given
        String name = "Yusik";
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());
        proxyFactoryBean.setPointcut(new MatchMethodPointcut((method, targetClass) -> method.getName().startsWith("say")));
        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        String resultSay = helloTarget.sayHello(name);
        String resultPing = helloTarget.pingpong(name);
        logger.info(resultSay);
        logger.info(resultPing);

        // then
        assertThat(resultSay).isEqualTo("HELLO YUSIK");
        assertThat(resultPing).isEqualTo("Pong Yusik");
    }

    @DisplayName("어드바이스 등록 순서 대로 동작")
    @Test
    void addAdvices() {

        // given
        String name = "Yusik";
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());
        proxyFactoryBean.addAdvice(new LowerCaseAdvice());
        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        String result = helloTarget.sayHello(name);
        logger.info(result);

        // then
        assertThat(result).isEqualTo("HELLO YUSIK");
    }

    @DisplayName("Order 가 높은걸 가장 나중에 등록해도 체인의 맨 앞")
    @Test
    void addOrderedAdvices() {

        // given
        String name = "Yusik";
        proxyFactoryBean.addAdvice(new UpperCaseAdvice());
        proxyFactoryBean.addAdvice(new LowerCaseAdvice());
        proxyFactoryBean.addAdvice(new PrefixAdvice());
        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        // when
        String result = helloTarget.sayHello(name);
        logger.info(result);

        // then
        assertThat(result).isEqualTo("Prefix HELLO YUSIK");
    }

    private static class UpperCaseAdvice implements Advice {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            logger.debug("before UpperCaseAdvice");
            String ret = (String) invocation.proceed();
            logger.debug("after UpperCaseAdvice");
            return ret.toUpperCase();
        }
    }

    private static class LowerCaseAdvice implements Advice {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            logger.debug("before LowerCaseAdvice");
            String ret = (String) invocation.proceed();
            logger.debug("after LowerCaseAdvice");
            return ret.toLowerCase();
        }
    }

    private static class PrefixAdvice implements Advice {

        private static final int HIGHEST_ORDER = Integer.MIN_VALUE;

        @Override
        public int getOrder() {
            return HIGHEST_ORDER;
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            logger.debug("before PrefixAdvice");
            String ret = (String) invocation.proceed();
            logger.debug("after PrefixAdvice");
            return "Prefix " + ret;
        }
    }
}

