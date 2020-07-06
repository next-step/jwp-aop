package core.di.beans.factory;

import core.annotation.Component;
import core.annotation.Inject;
import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.MethodMatcher;
import core.di.beans.factory.aop.PointCut;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.aop.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * memo: CglibProxyTest에서 하지 못한 내용들 여기서 해결.
 */
@DisplayName("ProxyFactoryBean 동작 테스트")
class ProxyFactoryBeanTest {

    private ProxyFactoryBean proxyFactoryBean;

    @BeforeEach
    void setUp() {
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
    }

    @DisplayName("HelloTarget 어드바이스")
    @Test
    void advice() throws Exception {

        // given
        final String name = "hyeyoom";
        proxyFactoryBean.setAdvice(new UpperCaseAdvice());
        final HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        오브젝트가_존재_해야함(helloTarget);

        // when
        final String result = helloTarget.sayHello(name);

        // then
        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("HelloTarget 포인트컷")
    @Test
    void pointcut() throws Exception {

        // given
        final String name = "hyeyoom";
        proxyFactoryBean.setAdvice(new UpperCaseAdvice());
        proxyFactoryBean.setPointCut(new StartWithSayPointCut());
        final HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        오브젝트가_존재_해야함(helloTarget);

        // when
        final String result1 = helloTarget.sayHello(name);
        final String result2 = helloTarget.pingpong(name);

        // then
        assertThat(result1).isEqualTo("HELLO " + name.toUpperCase());
        assertThat(result2).isEqualTo("Pong " + name);
    }

    @DisplayName("ProxyFactoryBean에서 생성하려는 객체가 의존성을 가지고 있다.")
    @Test
    void injectDependencies() throws Exception {

        // given
        final DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(ProxyFactoryBeanTest.class);
        final IAmADep bean = beanFactory.getBean(IAmADep.class);
        오브젝트가_존재_해야함(bean);

        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new IAmATarget());
        proxyFactoryBean.setAdvice(new UpperCaseAdvice());
        proxyFactoryBean.setPointCut(new StartWithSayPointCut());
        proxyFactoryBean.setBeanFactory(beanFactory);
        final IAmATarget target = (IAmATarget) proxyFactoryBean.getObject();
        오브젝트가_존재_해야함(target);

        // when
        final String say = target.say();

        // then
        assertThat(say).isEqualTo("Hello World".toUpperCase());
    }

    public static class IAmATarget {
        private IAmADep iAmADep;

        public IAmATarget() {
        }

        @Inject
        public IAmATarget(IAmADep iAmADep) {
            this.iAmADep = iAmADep;
        }

        public String say() {
            return iAmADep.sayHello();
        }
    }

    @Component
    public static class IAmADep {
        public String sayHello() {
            return "Hello World";
        }
    }

    // HelloCglibInterceptor
    public static class UpperCaseAdvice implements Advice {
        @Override
        public Object invoke(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            final String returnValue = (String) methodProxy.invokeSuper(obj, args);
            return returnValue.toUpperCase();
        }
    }

    public static class StartWithSayPointCut implements PointCut {

        private final MethodMatcher methodMatcher = new StartWithSayMethodMatcher();

        @Override
        public MethodMatcher getMethodMatcher() {
            return methodMatcher;
        }
    }

    public static class StartWithSayMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getName().startsWith("say");
        }
    }

    private static void 오브젝트가_존재_해야함(Object obj) {
        assertThat(obj).isNotNull();
    }
}