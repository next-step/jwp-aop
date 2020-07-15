package core.aop;

import core.aop.advice.Advice;
import core.aop.advisor.DefaultAdvisor;
import core.aop.example.AopTestService;
import core.aop.example.advice.AppendPostfixAdvice;
import core.aop.example.advice.AppendPrefixAdvice;
import core.aop.example.advice.ConvertToLowerCaseAdvice;
import core.aop.example.advice.ConvertToUpperCaseAdvice;
import core.aop.pointcut.MethodNamePrefixMatchPointCut;
import core.aop.pointcut.Pointcut;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.aop.example.advice.AppendPostfixAdvice.POSTFIX;
import static core.aop.example.advice.AppendPrefixAdvice.PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProxyFactoryBeanTest {
    private ProxyFactoryBean<AopTestService> proxyFactoryBean;

    @BeforeEach
    public void setup() {
        proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(new AopTestService());
    }

    @DisplayName("toUpperCaseAdvice 테스트")
    @Test
    void testToUpperCaseAdvice() throws Exception {
        String name = "ninjasul";
        Pointcut pointcut = new MethodNamePrefixMatchPointCut("sayHello");
        Advice advice = new ConvertToUpperCaseAdvice();

        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, advice));
        AopTestService aopTestService = proxyFactoryBean.getObject();

        String result = aopTestService.sayHello(name);

        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("toLowerCaseAdvice 테스트")
    @Test
    void testToLowerCaseAdvice() throws Exception {
        String name = "ninjasul";
        Pointcut pointcut = new MethodNamePrefixMatchPointCut("sayHello");
        Advice advice = new ConvertToLowerCaseAdvice();

        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, advice));
        AopTestService aopTestService = proxyFactoryBean.getObject();

        String result = aopTestService.sayHello(name);

        assertThat(result).isEqualTo("hello " + name.toLowerCase());
    }

    @DisplayName("복합 Advice 테스트")
    @Test
    void testMultipleAdvices() throws Exception {
        String name = "ninjasul";
        Pointcut pointcut = new MethodNamePrefixMatchPointCut("sayHello");

        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, new ConvertToLowerCaseAdvice()));
        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, new ConvertToUpperCaseAdvice()));
        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, new AppendPrefixAdvice()));
        proxyFactoryBean.addAdvisor(new DefaultAdvisor(pointcut, new AppendPostfixAdvice()));

        AopTestService aopTestService = proxyFactoryBean.getObject();

        String result = aopTestService.sayHello(name);

        String expected = PREFIX + "hello " + name + POSTFIX;
        assertThat(result).isEqualTo(expected.toLowerCase());
    }
}