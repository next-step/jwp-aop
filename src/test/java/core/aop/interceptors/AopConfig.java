package core.aop.interceptors;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.annotation.Inject;
import core.aop.ProxyFactoryBean;
import core.di.context.ApplicationContext;
import core.di.factory.example.*;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Configuration
public class AopConfig {
    @Pointcut("execution(public * core.di.factory.example.MyQnaService.*(..))")
    public void myQnaServiceMethods() {}

    @Inject
    private ApplicationContext applicationContext;

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository();
    }

    @Bean
    public QuestionRepository questionRepository() {
        return new JdbcQuestionRepository();
    }

    @Bean
    public MyTestService myQnaService() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("core.aop.interceptors.AopConfig.myQnaServiceMethods()");

        Advisor advisor = new DefaultPointcutAdvisor(pointcut, new PerformanceInterceptor());
        Advisor advisor2 = new DefaultPointcutAdvisor(pointcut, new LoggingInterceptor());

        ProxyFactoryBean<MyTestService> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setInterface(MyTestService.class);
        proxyFactoryBean.setTarget(new MyTestServiceImpl());
        proxyFactoryBean.addAdvisor(advisor);
        proxyFactoryBean.addAdvisor(advisor2);
        return proxyFactoryBean.getObject();
    }
}