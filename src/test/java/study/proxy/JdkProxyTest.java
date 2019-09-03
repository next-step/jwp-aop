package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JdkProxyTest {
    private static final Logger log = LoggerFactory.getLogger(JdkProxyTest.class);
    private MethodMatcher startWithSayMethodMatcher;

    @BeforeEach
    void setUp() {
        startWithSayMethodMatcher = (m, targetClass, args) -> m.getName().startsWith("say");
    }

    @Test
    @DisplayName("DynamicProxy 와 MethodMatcher를 이용하여 say로 시작하는 메소드만 대문자로 변환한다.")
    void toUppercaseByDynamicProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class},
                getUppercaseHandler(startWithSayMethodMatcher));

        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

    private InvocationHandler getUppercaseHandler(MethodMatcher methodMatcher) {
        return (proxy, method, args) -> {
            log.debug("before method invoke");

            String result = (String) method.invoke(new HelloTarget(), args);
            if (methodMatcher.matches(method, proxy.getClass(), args)) {
                result = result.toUpperCase();
            }

            log.debug("after to upper case , result : {}", result);
            return result;
        };
    }

    @Test
    @DisplayName("CGLib Proxy 와 MethodMatcher를 이용하여 say로 시작하는 메소드만 대문자로 변환한다.")
    void name() {
        Enhancer enhancer = getEnhancer();
        Hello2Target hello = (Hello2Target) enhancer.create();

        AssertionsForClassTypes.assertThat(hello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        AssertionsForClassTypes.assertThat(hello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        AssertionsForClassTypes.assertThat(hello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        AssertionsForClassTypes.assertThat(hello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

    private Enhancer getEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello2Target.class);
        enhancer.setCallback(getMethodInterceptor(startWithSayMethodMatcher));
        return enhancer;
    }

    private MethodInterceptor getMethodInterceptor(MethodMatcher startWithSayMethodMatcher) {
        return (obj, method, args, proxy) -> {
            log.debug("before method invoke");

            String returnValue = (String) proxy.invokeSuper(obj, args);
            if (startWithSayMethodMatcher.matches(method, obj.getClass(), args)) {
                returnValue = returnValue.toUpperCase();
            }

            log.debug("after to upper case , result : {}", returnValue);
            return returnValue;
        };
    }
}
