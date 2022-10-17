package core.di.beans.factory.support;

import core.di.beans.factory.aop.*;
import next.hello.Hello;
import next.hello.HelloTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultFactoryBeanTest {

    private Target target;
    private PointCut pointCut;
    private Advice advice;

    @BeforeEach
    void setUp() {
        target = new Target(HelloTarget.class, new HelloTarget());
        pointCut = (method, targetClass, args) -> method.getName().startsWith("say");
        advice = this::intercept;
    }

    private Object intercept(Object input) {
        if (input instanceof String) {
            return String.valueOf(input).toUpperCase(Locale.ROOT);
        }
        return input;
    }

    @DisplayName("CGLib을 사용하여 DefaultFactoryBean을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = "one")
    void construct(String name) throws Exception {
        Aspect aspect = new CGLibAspect(pointCut, advice);
        DefaultFactoryBean<HelloTarget> defaultFactoryBean = new DefaultFactoryBean<>(target, aspect);
        HelloTarget helloTarget = defaultFactoryBean.getObject();

        assertAll(
                () -> assertEquals(helloTarget.sayHello(name), "HELLO ONE"),
                () -> assertEquals(helloTarget.sayHi(name), "HI ONE"),
                () -> assertEquals(helloTarget.sayThankYou(name), "THANK YOU ONE")
        );
    }

    @DisplayName("JdkProxy를 사용하여 DefaultFactoryBean을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = "one")
    void constructWithJdkProxy(String name) throws Exception {
        Aspect aspect = new JdkAspect(pointCut, target, advice);
        DefaultFactoryBean<Hello> defaultFactoryBean = new DefaultFactoryBean<>(target, aspect);
        Hello hello = defaultFactoryBean.getObject();

        assertAll(
                () -> assertEquals(hello.sayHello(name), "HELLO ONE"),
                () -> assertEquals(hello.sayHi(name), "HI ONE"),
                () -> assertEquals(hello.sayThankYou(name), "THANK YOU ONE")
        );
    }
}